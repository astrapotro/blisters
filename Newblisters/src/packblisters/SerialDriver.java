package packblisters;

//import gnu.io.*;
import jssc.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;

//////////
//////////
//////////
////////////////
////////////////  CAMBIADA LIBRERÍA RXTX por JSSC (revisar rendimiento)
//////////////// Con linux socat -d -d pty,raw pty,raw para emular puertos serie. 

//////////////// Cada terminal abierta se abre en un pts nuevo. OJO !!

/////////////// TO-DO Hay que cambiarlo para lectura/escritura orientada a eventos

public class SerialDriver implements Runnable {

    private static SerialDriver msd;
    // private InputStream in;
    // private OutputStream out;
    private String puerto = VLogin.vadmin.puerto;
    private boolean conectado = false;

    public Thread hiloescribe;
    public Thread hilolee;
    private SerialWriter sw;
    private SerialReader sr;
    private String cort;
    private SerialPort serialPort;

    private static int planningbuff = 28;

    private SerialDriver() {
	super();

    }

    public static SerialDriver getInstance() {
	if (msd == null)
	    msd = new SerialDriver();

	return msd;
    }
    
  

    @Override
    public void run() {
	// TODO Auto-generated method stub
	// while (true);

	// serialPort = new SerialPort(puerto);

	try {
	    // conexion.connect(Messages.getString("VMed.SerialPort"));
	    if (!this.isConectado())
		this.connect(VLogin.vadmin.puerto);
	} catch (Exception e2) {
	    // TODO Auto-generated catch block
	    e2.printStackTrace();
	} //$NON-NLS-1$

	setSr(new SerialDriver.SerialReader(serialPort));

	setSw(new SerialDriver.SerialWriter(serialPort));

	
	hiloescribe = new Thread(msd.getSw());
	hiloescribe.start();
	
	hilolee = new Thread(msd.getSr());
	hilolee.start();

	// Forzar el filtrado para que solo nos devuelva reportes breves
	// conexion.getOut().write("$sv=1".getBytes());

	// ESCRIBIR AL PUERTO
	// escribir al puerto el med.corte

	String home = new String();
	// this.getSr().in.skip(this.getSr().in);
	home = "%\ng28.2 x0y0z0\n";

	this.getSr().setFinalizado(false);
	this.getSw().escribe(home);
	// conexion.getOut().write(home.getBytes(),0,home.length());

	for (int i = 200; i > 0; i--) {

	    this.getSw().escribe("$home");
	    System.out.println(this.getSr());

	    if (this.getSr().isFinalizado()) {
		System.out.println("Saliendo del for");
		break;
	    }

	    try {
		Thread.sleep(300);

	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	}

	
	this.getSw().escribe(this.cort);

    }

    public void connect(String portName) throws IOException {
	System.setProperty(
		Messages.getString("SerialDriver.RXTXSerialPorts"), Messages.getString("SerialDriver.Puerto")); //$NON-NLS-1$ //$NON-NLS-2$
	System.out.println("PUERTO en serialdriver: " + VLogin.vadmin.puerto);

	serialPort = new SerialPort(puerto);

	try {

	    serialPort.openPort(); // serialPort.setParams(9600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
	    serialPort.setParams(115200, 8, 1, 0);
	    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
		    | SerialPort.FLOWCONTROL_RTSCTS_OUT);
	    serialPort.setRTS(true);

	    // //Preparing a mask. In a mask, we need to specify the types of
	    // events that we want to track.
	    // //Well, for example, we need to know what came some data, thus in
	    // the mask must have the
	    // //following value: MASK_RXCHAR. If we, for example, still need to
	    // know about changes in states
	    // //of lines CTS and DSR, the mask has to look like this:
	    // SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS +
	    // SerialPort.MASK_DSR
	    // int mask = SerialPort.MASK_RXCHAR;
	    // //Set the prepared mask
	    // serialPort.setEventsMask(mask);
	    // //Add an interface through which we will receive information
	    // about events
	    // serialPort.addEventListener(this);

	} catch (SerialPortException ex) {
	    System.out.println(ex);
	    conectado = false;
	}

	conectado = true;

	// static class SerialPortReader implements SerialPortEventListener {
	//
	// public void serialEvent(SerialPortEvent event) {
	// //Object type SerialPortEvent carries information about which event
	// occurred and a value.
	// //For example, if the data came a method event.getEventValue()
	// returns us the number of bytes in the input buffer.
	// if(event.isRXCHAR()){
	// if(event.getEventValue() == 10){
	// try {
	// byte buffer[] = serialPort.readBytes(10);
	// }
	// catch (SerialPortException ex) {
	// System.out.println(ex);
	// }
	// }
	// }
	// //If the CTS line status has changed, then the method
	// event.getEventValue() returns 1 if the line is ON and 0 if it is OFF.
	// else if(event.isCTS()){
	// if(event.getEventValue() == 1){
	// System.out.println("CTS - ON");
	// }
	// else {
	// System.out.println("CTS - OFF");
	// }
	// }
	// else if(event.isDSR()){
	// if(event.getEventValue() == 1){
	// System.out.println("DSR - ON");
	// }
	// else {
	// System.out.println("DSR - OFF");
	// }
	// }
	// }
	// }
	// }
	//

	// hilolee = (new Thread());
	// hilolee.start();

	// hiloescribe = (new Thread(new SerialWriter(out)));
	// hiloescribe.start();

	// (new Thread(new SerialReader(in))).start();
	// (new Thread(new SerialWriter(out))).start();

	// } else {
	// System.out
	//			.println(Messages.getString("SerialDriver.ErrorNoSerial")); //$NON-NLS-1$
	// }
	// }
	// }
    }

    public void dicepuerto() {

	// Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
	// System.out.println("DICEPUERTO");
	//
	// while( ports.hasMoreElements() )
	// {
	// CommPortIdentifier port = (CommPortIdentifier)ports.nextElement();
	// String type;
	//
	// switch( port.getPortType() )
	// {
	// case CommPortIdentifier.PORT_PARALLEL:
	// type = "Parallel";
	// break;
	//
	// case CommPortIdentifier.PORT_SERIAL:
	// type = "Serial";
	// break;
	//
	// default:
	// type = "Desconocido";
	// break;
	// }
	//
	// System.out.println( port.getName() + ": " + type);
	//
	// }

    }


    /** Clase hilo que lee respuestas de la tiny */
    public static class SerialReader implements Runnable {
	// InputStream in;

	SerialPort puerto;
	boolean finalizado;

	public SerialReader(SerialPort puerto) {
	    this.puerto = puerto;
	}

	public void run() {
	    byte[] buffer = new byte[1024];
	    
	    finalizado = false;


	    while (true) {

		try {
		    buffer = puerto.readBytes();

		    // System.out.println("BUFFER lectura: "+buffer.);

		    if (buffer != null) {
			// FILTRO DE ENTRADA
			String buff = new String(buffer);
			System.out.print("BUFF: " + buff);

			// Capturar lo que manda tinyG
			// Bucle de escucha a la tinyg y le pida su estado
			// byte[] estado=null;

			if (!finalizado) {
			    // conexion.getOut().write("$sr".getBytes());

			    if (buff.contains("Homed")) {
				finalizado = true;
				System.out.println(this);
				System.out.println("Homing finalizado!");

				// TODO NotiFY
				// synchronized
				// (VLogin.vadmin.vprocesocorte.getVmed().conexion.hiloescribe){
				// VLogin.vadmin.vprocesocorte.getVmed().conexion.hiloescribe.notify();
				// }

			    }

			} else {

			    if (buff.contains("stat:3")) {
				System.out
					.println("EL movimiento ha finalizado");
				// TODO NotiFY
				// synchronized
				// (VLogin.vadmin.vprocesocorte.getVmed().conexion.hiloescribe){
				// VLogin.vadmin.vprocesocorte.getVmed().conexion.hiloescribe.notify();
				// }
			    }

			    else if (buff.contains("stat:4")) {
				System.out.println("EL PROGRAMA HA FINALIZADO");

				// TODO Hay que llamar a cortefinalizado
				VLogin.vadmin.vprocesocorte.getVmed()
					.cortefinalizado();

				// synchronized
				// (VLogin.vadmin.vprocesocorte.getVmed().conexion.hiloescribe){
				// VLogin.vadmin.vprocesocorte.getVmed().conexion.hiloescribe.notify();
				// }
			    }

			}

			if (buff.contains("qr:") && !buff.contains("stat")) {

			    buff = buff.substring(buff.lastIndexOf("qr:") + 3);
			    StringTokenizer st = new StringTokenizer(buff);

			    if (st.hasMoreTokens()) {
				msd.setDisponibles(Integer.parseInt(st.nextToken()));

				System.out.println("GETDISPONIBLES: "
					+ msd.getDisponibles());
			    }


			    // Aviso al escritor de que puede seguir escribiendo
			    if (msd.getDisponibles() >= 5) {

				synchronized (msd.getSw()) {
				    System.out
					    .println("Aviso al hilo de escritura");
				    msd.getSw().notify();
				}

			    }
			}
		    }
		    
		    try {
			Thread.sleep(40);
		    } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }

		} catch (SerialPortException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		//
		try {
		    puerto.writeBytes("$qr\n".getBytes());
		} catch (SerialPortException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }

	}

	public boolean isFinalizado() {
	    return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
	    this.finalizado = finalizado;
	}

	// @Override
	// public void serialEvent(SerialPortEvent arg0) {
	// // TODO Auto-generated method stub
	//
	// }

    }

    /** */

    /** Clase hilo que escribe órdenes a la tiny */
    public static class SerialWriter implements Runnable {
	// OutputStream out;
	SerialPort puerto;

	public boolean pausa = false;
	public boolean reanuda = false;
	public boolean cancela = false;
	private boolean pausado = false;

	public SerialWriter(SerialPort puerto) {
	    this.puerto = puerto;
	}

	public void run() {

	    while (true) {
	    }

	}

	public void escribe(String s) {

	    StringTokenizer gcodes = new StringTokenizer(s, "\n\r");

	    String flush = "%\n";
	    String home = "g28.2 x0y0z0\n";
	    String pausar = "!\n";
	    String reanudar = "~\n";

	    while (gcodes.hasMoreTokens()) {

		if (pausa) {
		    try {
			if (!pausado) {
			    puerto.writeBytes(pausar.getBytes());
			    try {
				Thread.sleep(300);
			    } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
			}
			pausa = false;
			pausado = true;
			// Thread.currentThread().wait();

		    } catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }

		} else if (reanuda) {
		    try {
			puerto.writeBytes(reanudar.getBytes());

			try {
			    Thread.sleep(300);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			reanuda = false;
			pausado = false;
			pausa = false;

		    } catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		} else if (cancela) {
		    try {
			puerto.writeBytes(pausar.getBytes());
			try {
			    Thread.sleep(300);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			puerto.writeBytes(flush.getBytes());

			try {
			    Thread.sleep(300);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			puerto.writeBytes(reanudar.getBytes());
			try {
			    Thread.sleep(300);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			puerto.writeBytes(home.getBytes());
			try {
			    Thread.sleep(300);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

			cancela = false;

			return;
		    } catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		} else {

		    if (!pausado) {

			String token = gcodes.nextToken();
			token = token + "\n\r";
			System.out.println("TOKEN" + token);

//			if (token.startsWith("G") || token.startsWith("g")
//				|| token.startsWith("M")
//				|| token.startsWith("m")) {

			    System.out.println("Disponibles en serialwriter: "
				    + msd.getDisponibles());

//			    if (msd.getDisponibles() <= 12) {
				System.out
					.println("Hilo escritura se va a quedar esperando");
				synchronized (msd.getSw()) {
				    try {

					msd.getSw().wait();
				    } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				    }

				}
				System.out
					.println("hilo de escritura ha sido despertado");

			    //}
			    
			 //   }
			try {

			    puerto.writeBytes(token.getBytes());

			} catch (SerialPortException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

			// conexion.getOut()
			// .write(s.getBytes(), 0, s.length());

			// try {
			// Thread.sleep(500);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			// if
			// (token.startsWith("G")||token.startsWith("g")||token.startsWith("M")||token.startsWith("m")){
			// System.out.println("Voy a esperar a que que termine el comandogm");
			// synchronized (this){
			// try {
			// this.wait();
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
		    }

		}
	    }

	}
    }

//    public Thread getHiloescribe() {
//	return hiloescribe;
//    }
//
//    public void setHiloescribe(Thread hiloescribe) {
//	this.hiloescribe = hiloescribe;
//    }
//
//    public Thread getHilolee() {
//	return hilolee;
//    }
//
//    public void setHilolee(Thread hilolee) {
//	this.hilolee = hilolee;
//    }

    public boolean isConectado() {
	return conectado;
    }

    public void setConectado(boolean conectado) {
	this.conectado = conectado;
    }

    public SerialWriter getSw() {
	return sw;
    }

    public SerialReader getSr() {
	return sr;
    }

    public void setSr(SerialReader sr) {
	this.sr = sr;
    }

    public void setSw(SerialWriter sw) {
	this.sw = sw;
    }

    synchronized public int getDisponibles() {
	return planningbuff;
    }

    synchronized public void setDisponibles(int planningbuff) {
	this.planningbuff = planningbuff;
    }

    public String getCort() {
        return cort;
    }

    public void setCort(String cort) {
        this.cort = cort;
    }

}
