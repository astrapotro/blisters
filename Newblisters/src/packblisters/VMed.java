package packblisters;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VMed extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String corte,cancelar,pausar,reanudar;
    private DBFacade dbf = new DBFacade();
    private Medicamento m;

    
    public VMed(Medicamento med) {
	
	super();

	// AKI COGEMOS EL RECORTE ASOCIADO
	
	dbf.getRecorteMedicamento(med);
	corte = med.getMicorte().getGcode();
	cancelar = med.getMicorte().getCancelar();
	pausar =  med.getMicorte().getPausar();
	reanudar =  med.getMicorte().getReanudar();
	m = med;
	setLayout(null);
	

	

	JLabel lblNewLabel = new JLabel();
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setIcon(new ImageIcon(med.getRutaimg()));
	System.out.println(med.getRutaimg());
	lblNewLabel.setBounds(50, 50, 688, 476);
	add(lblNewLabel);
	lblNewLabel.repaint();
	
	 
	
	JButton cortarBtn = new JButton(Messages.getString("VMed.CutBtn")); //$NON-NLS-1$
	cortarBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		try {
		  
		    	SerialDriver conexion = SerialDriver.getInstance();	
		        
			    try {
				conexion.connect(Messages.getString("VMed.SerialPort"));
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
			    home = "g28.2 x0y0z0\n";
			    conexion.getOut().write(home.getBytes(),0,home.length());
			    
			    Thread.sleep(14000);
	
			    while(tokens.hasMoreTokens()){
				String s = tokens.nextToken();
				conexion.getOut()
    			    .write(s.getBytes(), 0, s.length());
				Thread.sleep(320);
			    }
			    
        
        		    System.out.println(corte);
        		    
        		
        		    
        		   
        		    
        		    ///Insert del evento en la tabla HISTORICO
        		    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Comienzo corte");
        		    
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    
		    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Fallo en corte");
		    
		    e.printStackTrace();
		    System.out
			    .println(Messages.getString("VMed.ErrorMsg")); //$NON-NLS-1$
		}

	    }
	});
	cortarBtn.setBounds(294, 550, 109, 50);
	adaptajbuttonabajo(cortarBtn, "/iconos/cortar.png");
	cortarBtn.setIconTextGap(1);
//	btnNuevoMed.setForeground(Color.BLACK);
//	btnNuevoMed.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
	cortarBtn.setHorizontalTextPosition(SwingConstants.CENTER);
	cortarBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
	add(cortarBtn);

	JLabel lblNewLabel_1 = new JLabel(med.toString());
	lblNewLabel_1.setBounds(39, 12, 311, 25);
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
	btnCancelar.setBounds(419, 563, 96, 25);
	add(btnCancelar);
	
	JToggleButton pausa = new JToggleButton("PAUSAR");
	pausa.setEnabled(false);
	pausa.setBounds(527, 563, 89, 25);
	pausa.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  

		    AbstractButton abstractButton = (AbstractButton) e.getSource();
		    boolean selected = abstractButton.getModel().isSelected();
		    
		    try {
			 	SerialDriver conexion = SerialDriver.getInstance();
        			conexion.connect(Messages.getString("VMed.SerialPort"));
        			//conexion.
        		   
		    
        		    if (selected){
        			
        			//hay que mandar la PAUSA: ! 
        			
        			conexion.getOut()
        			    .write(pausar.getBytes(), 0, pausar.length());
        		    }
        		    
        		    else{
        			//mandar la reanudación: ~

        			conexion.getOut()
        			    .write(reanudar.getBytes(), 0, reanudar.length());
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
	btnAtras.setBounds(173, 550, 109, 50);
	adaptajbuttonabajo(btnAtras, "/iconos/patras.png");
	btnAtras.setIconTextGap(1);
//	btnNuevoMed.setForeground(Color.BLACK);
//	btnNuevoMed.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
	btnAtras.setHorizontalTextPosition(SwingConstants.CENTER);
	btnAtras.setVerticalTextPosition(SwingConstants.BOTTOM);
	
	add(btnAtras);

    }
    
    public void adaptajbuttonabajo (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()-55, but.getHeight()-25, Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
}
