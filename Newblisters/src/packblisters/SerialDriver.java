package packblisters;

import gnu.io.*;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

//////////
//////////
//////////
////////////////
//////////////// Sólo funciona en linux con la libreria rxtx-2.2pre2-bins. Con inferiores casca el .so !!!!!!
//////////////// Con linux socat -d -d pty,raw pty,raw para emular puertos serie. 
//////////////// Cada terminal abierta se abre en un pts nuevo. OJO !!

/////////////// TO-DO Hay que cambiarlo para lectura/escritura orientada a eventos

 class SerialDriver {
    private static SerialDriver msd;
    private InputStream in;
    private OutputStream out;
    String puerto=VLogin.vadmin.puerto;

    private SerialDriver() {
	super();
    }
    
    public static SerialDriver getInstance(){
	if(msd==null)
	    msd=new SerialDriver();
	
	return msd;
    }

    public void connect(String portName) throws Exception {
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

		(new Thread(new SerialReader(in))).start();
		(new Thread(new SerialWriter(out))).start();
		

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
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    /** */
    public static class SerialWriter implements Runnable {
	OutputStream out;
	
	
	
	public SerialWriter(OutputStream out) {
	    this.out = out;
	}

	public void run() {
	    try {
		int c = 0;
		while ((c = System.in.read()) > -1) {
		    
		    this.out.write(c);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }


}
