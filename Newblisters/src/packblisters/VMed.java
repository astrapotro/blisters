package packblisters;




import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import java.awt.Color;


public class VMed extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String corte,cancelar,pausar,reanudar;
    private DBFacade dbf = new DBFacade();
    private Medicamento m;
    private JToggleButton pausa;

    
    public VMed(Medicamento med) {
	
	//super();

	// AKI COGEMOS EL RECORTE ASOCIADO
	
	dbf.getRecorteMedicamento(med);
	corte = med.getMicorte().getGcode();
	cancelar = med.getMicorte().getCancelar();
	pausar =  med.getMicorte().getPausar();
	reanudar =  med.getMicorte().getReanudar();
	m = med;
	setLayout(null);
	this.setBackground(new Color(224, 255, 255));

	

	JLabel afoto = new JLabel();
	this.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));
	afoto.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));
	afoto.setHorizontalAlignment(SwingConstants.CENTER);
	afoto.setBounds(26, 27, 472, 519);
	afoto.setIcon(new ImageIcon(new ImageIcon(med.getRutaimg()).getImage().getScaledInstance(afoto.getWidth(), afoto.getHeight(), Image.SCALE_SMOOTH)));
	afoto.repaint();
	System.out.println(med.getRutaimg());
	add(afoto);
	
	
	 
	
	JButton cortarBtn = new JButton(Messages.getString("VMed.CutBtn")); //$NON-NLS-1$
	cortarBtn.setBounds(186, 558, 114, 77);
	adaptajbuttonabajo(cortarBtn, "/iconos/cortar.png");
	cortarBtn.setIconTextGap(1);
//	btnNuevoMed.setForeground(Color.BLACK);
//	btnNuevoMed.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
	cortarBtn.setHorizontalTextPosition(SwingConstants.CENTER);
	cortarBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
	add(cortarBtn);
	cortarBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		try {
		  
		    	SerialDriver conexion = SerialDriver.getInstance();	
		    	
		    	//Miro que puertos hay en el sistema 
		    	conexion.dicepuerto();
		        
			    try {
				//conexion.connect(Messages.getString("VMed.SerialPort"));
				conexion.connect(VLogin.vadmin.puerto);
			    } catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			    } //$NON-NLS-1$
			    
			    
			    //Forzar el filtrado para que solo nos devuelva reportes breves
			    //conexion.getOut().write("$sv=1".getBytes());
        
        		    // ESCRIBIR AL PUERTO
        		    // escribir al puerto el med.corte
        		    
			    StringTokenizer tokens = new StringTokenizer(corte,"\n");
			    String home = new String();
			    home = "g28.2 x0y0z0\n\r";
			    conexion.getOut().write(home.getBytes(),0,home.length());
			    
			    Thread.sleep(14000);

			    ///Insert del evento en la tabla HISTORICO
        		    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Comienzo corte",null);

			    while(tokens.hasMoreTokens()){
				String s = tokens.nextToken();
				conexion.getOut()
    			    .write(s.getBytes(), 0, s.length());
				Thread.sleep(320);
			    }
			    
        
        		    System.out.println("CORTE en "+VLogin.vadmin.puerto);
        		        
        		   
        		    //TODO AKI JDIALOG de incidencia
        		    VIncidencia vincidencia = new VIncidencia(m);
        		    vincidencia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        		    vincidencia.setVisible(true);
        		  ///Insert del evento en la tabla HISTORICO
        		    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Fin corte",null);
        		    
        		            		    
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    
		    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Fallo en corte",null);
		    
		    e.printStackTrace();
		    System.out
			    .println(Messages.getString("VMed.ErrorMsg")); //$NON-NLS-1$
		}

	    }
	});
	

	JLabel lblNewLabel_1 = new JLabel(med.toString());
	lblNewLabel_1.setBounds(16, 4, 311, 25);
	lblNewLabel_1.setForeground(new Color(107, 142, 35));
	lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 13));
	add(lblNewLabel_1);
	
	JButton btnCancelar = new JButton(Messages.getString("VMed.btnCancelar.text")); //$NON-NLS-1$
	btnCancelar.setEnabled(false);
	btnCancelar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    
		    
		    //TODO Cancelar todo: Ctrl+x
		    SerialDriver conexion = SerialDriver.getInstance();

		    try {
			conexion.connect(Messages.getString("VMed.SerialPort"));
			conexion.getOut()
			    .write(cancelar.getBytes(), 0, cancelar.length());
		    } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    } //$NON-NLS-1$

		    
		}
	});
	btnCancelar.setBounds(313, 566, 185, 25);
	add(btnCancelar);
	
	pausa = new JToggleButton();
	pausa.setText("PAUSAR");
	pausa.setEnabled(true);
	pausa.setBounds(312, 603, 186, 25);
	pausa.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		 

//		    AbstractButton abstractButton = (AbstractButton) e.getSource();
//		    boolean selected = abstractButton.getModel().isSelected();
		    
		    try {
//			 	SerialDriver conexion = SerialDriver.getInstance();
//        			conexion.connect(Messages.getString("VMed.SerialPort"));
        			//conexion.
        		   
		    
        		    if ( pausa.isSelected()){
        			
        			//hay que mandar la PAUSA: ! 
        			pausa.setText("Reanudar");
//        			conexion.getOut()
//        			    .write(pausar.getBytes(), 0, pausar.length());
        		    }
        		    
        		    else{
        			//mandar la reanudación: ~
        			pausa.setText("Pausar");
//        			conexion.getOut()
//        			    .write(reanudar.getBytes(), 0, reanudar.length());
        		    }
		    } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    } //$NON-NLS-1$
		    		   
		    
		}
	});
	add(pausa);
	
	JButton btnAtras = new JButton(Messages.getString("VMed.btnAtras.text")); //$NON-NLS-1$
	btnAtras.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		   
		    VMed.this.setVisible(false);
		    
		    if (VLogin.UsuarioLogueado.getRoot()) 
			VLogin.vadmin.vprocesocorte.setVisible(true);
		    else
			VLogin.vprocesocorte.setVisible(true);
		}
	});
	btnAtras.setBounds(26, 558, 148, 77);
	adaptajbutton(btnAtras, "/iconos/patras.png");
	btnAtras.setIconTextGap(1);
	btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
	btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
	add(btnAtras);

    }
    
    public void adaptajbutton (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()/2, (int) (but.getHeight()), Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
    
    public void adaptajbuttonabajo (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()-55, but.getHeight()-25, Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
}
