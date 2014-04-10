package packblisters;

import gnu.io.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;

//////////
//////////
//////////
////////////////
//////////////// Sólo funciona en linux con la libreria rxtx-2.2pre2-bins. Con inferiores casca el .so !!!!!!
//////////////// Con linux socat -d -d pty,raw pty,raw para emular puertos serie. 
//////////////// Cada terminal abierta se abre en un pts nuevo. OJO !!

/////////////// TO-DO Hay que cambiarlo para lectura/escritura orientada a eventos

public class SerialDriver implements Runnable {
    private static SerialDriver msd;
    private InputStream in;
    private OutputStream out;
    private String puerto=VLogin.vadmin.puerto;
    private boolean conectado = false;
    
    private Thread hiloescribe;
    private Thread hilolee;
    private SerialWriter sw;
    private String cort;

    private SerialDriver(String corte) {
	super();
	this.cort=corte;

    }
    
    public static SerialDriver getInstance(String corte){
	if(msd==null)
	    msd=new SerialDriver(corte);
	
	return msd;
    }

    public void connect(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException{
	System.setProperty(Messages.getString("SerialDriver.RXTXSerialPorts"), Messages.getString("SerialDriver.Puerto")); //$NON-NLS-1$ //$NON-NLS-2$
	System.out.println("PUERTO en serialdriver: "+VLogin.vadmin.puerto);
	CommPortIdentifier portIdentifier = CommPortIdentifier
		.getPortIdentifier(portName);
	if (portIdentifier.isCurrentlyOwned()) {
	    System.out.println(Messages.getString("SerialDriver.PuertoBusy")); //$NON-NLS-1$
	} else {
	    CommPort commPort = portIdentifier.open(this.getClass().getName(),
		    2000);

	    if (commPort instanceof SerialPort) {

		SerialPort serialPort = (SerialPort) commPort;
		
		conectado = true;
		//
		// CONFIGURACION de LA CONEXION SERIE
		// tinyg 115200 baud
		int b=Integer.parseInt(Messages.getString("SerialDriver.Baudios"));
		int databits =Integer.parseInt(Messages.getString("SerialDriver.Databits"));
		int stopbits =Integer.parseInt(Messages.getString("SerialDriver.Stopbits"));
		int paridad =Integer.parseInt(Messages.getString("SerialDriver.Paridad"));
		serialPort.setSerialPortParams(b, databits,stopbits,paridad);
		serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_OUT | SerialPort.FLOWCONTROL_RTSCTS_IN); 
		serialPort.setRTS(true);
//		SerialPort.DATABITS_8,
//			SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

		in = serialPort.getInputStream();
		out = serialPort.getOutputStream();
		sw = new SerialWriter(out);
		
		hilolee = (new Thread(new SerialReader(in)));
		hilolee.start();
		
		hiloescribe = (new Thread(new SerialWriter(out)));
		hiloescribe.start();
		
//		(new Thread(new SerialReader(in))).start();
//		(new Thread(new SerialWriter(out))).start();
		

	    } else {
		System.out
			.println(Messages.getString("SerialDriver.ErrorNoSerial")); //$NON-NLS-1$
	    }
	}
    }
    
    public void dicepuerto (){
	
	Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers(); 
	System.out.println("DICEPUERTO");
	
	while( ports.hasMoreElements() ) 
	{ 
        	CommPortIdentifier port = (CommPortIdentifier)ports.nextElement(); 
        	String type; 
        
        	switch( port.getPortType() ) 
        	{ 
                	case CommPortIdentifier.PORT_PARALLEL: 
                	type = "Parallel"; 
                	break; 
                
                	case CommPortIdentifier.PORT_SERIAL: 
                	type = "Serial"; 
                	break; 
                
                	default: 
                	type = "Desconocido"; 
                	break; 
        	} 

           System.out.println( port.getName() + ": " + type); 
           
	 } 
} 
	
    public InputStream getIn() {
	return in;
    }

    public void setIn(InputStream in) {
	this.in = in;
    }

    public OutputStream getOut() {
	return out;
    }

    public void setOut(OutputStream out) {
	this.out = out;
    }

    /** */
    public static class SerialReader implements Runnable {
	InputStream in;

	public SerialReader(InputStream in) {
	    this.in = in;
	}

	public void run() {
	    byte[] buffer = new byte[1024];
	    int len = -1;
	    try {
		while ((len = this.in.read(buffer)) > -1) {
		    // FILTRO DE ENTRADA
		    System.out.print(new String(buffer, 0, len));
		    
		    //Capturar lo que manda tinyG
		    //Bucle de escucha a la tinyg y le pida su estado
		    byte[] estado=null;
		    boolean finalizado=false;
		    
		   while (!finalizado)
		   {
		       //conexion.getOut().write("$sr".getBytes());
		       
		       if (buffer.toString().contains("stat:3"))
			   finalizado=true;
		       
		   }
		    
		}
		System.out.println("BUFFER: "+buffer);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    /** */
    public static class SerialWriter implements Runnable {
	OutputStream out;
	public boolean pausa = false;
	public boolean reanuda = false;
	public boolean cancela = false;
	private boolean pausado = false;
	
	
	
	public SerialWriter(OutputStream out) {
	    this.out = out;
	}

	public void run() {
	    //try {
		//int c = 0;

		    while (true){}

//		while ((c = System.in.read()) > -1) {
//		    
//		    this.out.write(c);
////		}
//	    } catch (IOException e) {
//		e.printStackTrace();
//	    } catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	    }
	}
	
	public void escribe (String s){
	    
	    StringTokenizer tokens = new StringTokenizer(s,"\n\r");
	    
	    String flush ="%\n";
	    String home = "g28.2 x0y0z0\n";
	    String pausar = "!\n";
	    String reanudar = "~\n";
	    
	    
	    while(tokens.hasMoreTokens()){
		
		
		
		if (pausa ){
		    try {
			if (!pausado)
			    this.out.write(pausar.getBytes(), 0 ,pausar.length());
			pausa=false;
			pausado=true;
			//Thread.currentThread().wait();
			
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		    
		}
		else if (reanuda){
		    try {
			this.out.write(reanudar.getBytes(), 0 , reanudar.length());
			reanuda=false;
			pausado=false;
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		else if (cancela ){
		    try {
			this.out.write(pausar.getBytes(), 0 , pausar.length());
			try {
        		    Thread.sleep(300);
        		} catch (InterruptedException e) {
        		    // TODO Auto-generated catch block
        		    e.printStackTrace();
        		}
			this.out.write(flush.getBytes(), 0 , flush.length());
			
			try {
        		    Thread.sleep(300);
        		} catch (InterruptedException e) {
        		    // TODO Auto-generated catch block
        		    e.printStackTrace();
        		}
			this.out.write(reanudar.getBytes(), 0 , reanudar.length());
			try {
        		    Thread.sleep(300);
        		} catch (InterruptedException e) {
        		    // TODO Auto-generated catch block
        		    e.printStackTrace();
        		}
			this.out.write(home.getBytes(), 0 , home.length());
			try {
        		    Thread.sleep(300);
        		} catch (InterruptedException e) {
        		    // TODO Auto-generated catch block
        		    e.printStackTrace();
        		}
			
			cancela=false;
			
			return;
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		}
		else{ 
		    
		    if (!pausado){
			String token = tokens.nextToken();
			token = token +"\n\r";
			
        		try {
        		    this.out.write(token.getBytes(), 0 ,token.length());
        		} catch (IOException e) {
        		    // TODO Auto-generated catch block
        		    e.printStackTrace();
        		}
        		
        //		conexion.getOut()
        //		    .write(s.getBytes(), 0, s.length());
        		try {
        		    Thread.sleep(300);
        		} catch (InterruptedException e) {
        		    // TODO Auto-generated catch block
        		    e.printStackTrace();
        		}
		    }
		}
	    }
	    
	}
    }

//    @Override
//    public void run() {
//	// TODO Auto-generated method stub
//	
//	
//    }

    public Thread getHiloescribe() {
        return hiloescribe;
    }

    public void setHiloescribe(Thread hiloescribe) {
        this.hiloescribe = hiloescribe;
    }

    public Thread getHilolee() {
        return hilolee;
    }

    public void setHilolee(Thread hilolee) {
        this.hilolee = hilolee;
    }

    public boolean isConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    public SerialWriter getSw() {
        return sw;
    }

    public void setSw(SerialWriter sw) {
        this.sw = sw;
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub
	//while (true);
	

	    try {
		//conexion.connect(Messages.getString("VMed.SerialPort"));
		if (!this.isConectado())
		    this.connect(VLogin.vadmin.puerto);
	    } catch (Exception e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	    } //$NON-NLS-1$
	    
	    
	    //Forzar el filtrado para que solo nos devuelva reportes breves
	    //conexion.getOut().write("$sv=1".getBytes());

	    // ESCRIBIR AL PUERTO
	    // escribir al puerto el med.corte
	    
	    
	    String home = new String();
	    home = "%\ng28.2 x0y0z0\n";
	    this.getSw().escribe(home);
	    //conexion.getOut().write(home.getBytes(),0,home.length());
	    try {
		Thread.sleep(20000);
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    this.getSw().escribe(this.cort);
	
    }


}
