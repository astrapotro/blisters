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

import packblisters.SerialDriver.SerialWriter;

import java.awt.Color;


public class VMed extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String corte,cancelar,pausar,reanudar;
    private DBFacade dbf = new DBFacade();
    private Medicamento m;
    private JToggleButton cortarBtn;
    public SerialDriver conexion;
    private JButton btnCancelar ;
    
    public VIncidencia vincidencia;

    
    public VMed(Medicamento med) {
	
	//super();

	// AKI COGEMOS EL RECORTE ASOCIADO
	
	dbf.getRecorteMedicamento(med);
	corte = med.getMicorte().getGcode();
	cancelar = med.getMicorte().getCancelar();
	pausar =  med.getMicorte().getPausar();
	reanudar =  med.getMicorte().getReanudar();
	m = med;
	conexion = SerialDriver.getInstance(corte);
	
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
	
	
	 
	
	cortarBtn = new JToggleButton("CORTAR"); //$NON-NLS-1$
	cortarBtn.setBounds(216, 558, 135, 77);
	adaptajbuttonabajo(cortarBtn, "/iconos/cortar.png");
	cortarBtn.setIconTextGap(1);
	cortarBtn.setHorizontalTextPosition(SwingConstants.CENTER);
	cortarBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
	add(cortarBtn);
	cortarBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		try {
		    	
		    	btnCancelar.setEnabled(true);
		    	Thread hilocorte = new Thread (conexion);
		    	hilocorte.start();
		   
		    	if  (cortarBtn.getText().contentEquals("CORTAR")){
		    	     cortarBtn.setText("PAUSAR");
		    	
		    	}
		    	
		    	try {
		    	    
		        if (cortarBtn.isSelected()){
//                    		    if ( cortarBtn.getText().contentEquals("PAUSAR")){
//                    			
//                    			//hay que mandar la PAUSA: ! 
//                    			cortarBtn.setText("Reanudar");
//                    			//conexion.getSw().pausa=true;
//                    			
//                    		    }
                    		    
                    		    if ( cortarBtn.getText().contentEquals("Reanudar")){
                    			//mandar la reanudación: ~
                    			
                    			cortarBtn.setText("PAUSAR");
                    			//conexion.getSw().notify();
                    			conexion.getSw().reanuda=true;
                    		
                    		    }
		    
		       }else if (cortarBtn.isSelected()==false){
           		    if ( cortarBtn.getText().contentEquals("PAUSAR")){
            			
           			//hay que mandar la PAUSA: ! 
           			cortarBtn.setText("Reanudar");
           			conexion.getSw().pausa=true;
           			
           		    }
           		    
           		    else if ( cortarBtn.getText().contentEquals("Reanudar")){
           			//mandar la reanudación: ~
           			
           			cortarBtn.setText("PAUSAR");
           			//conexion.getSw().notify();
           			conexion.getSw().reanuda=true;
           		
           		    }
	    
	       }
		    } catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    } //$NON-NLS-1$
		    	
		  	    
        		            		    
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    
		    //dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Fallo en corte",null);
		    
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
	
	btnCancelar = new JButton(Messages.getString("VMed.btnCancelar.text")); //$NON-NLS-1$
	btnCancelar.setEnabled(false);
	btnCancelar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    
		    conexion.getSw().cancela=true;

		}
	});
	btnCancelar.setBounds(363, 558, 135, 77);
	add(btnCancelar);
	
//	pausa = new JToggleButton();
//	pausa.setText("PAUSAR");
//	pausa.setEnabled(true);
//	pausa.setBounds(312, 603, 186, 25);
//	pausa.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//		    
//		    try {  
//		    
//        		    if ( pausa.isSelected()){
//        			
//        			//hay que mandar la PAUSA: ! 
//        			pausa.setText("Reanudar");
//        			conexion.getSw().pausa=true;
//        			
//        		    }
//        		    
//        		    else{
//        			//mandar la reanudación: ~
//        			
//        			pausa.setText("Pausar");
//        			//conexion.getSw().notify();
//        			conexion.getSw().reanuda=true;
//        		
//        		    }
//		    } catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		    } //$NON-NLS-1$
//		    		   
//		    
//		}
//	});
//	add(pausa);
	
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
	btnAtras.setBounds(26, 558, 178, 77);
	adaptajbutton(btnAtras, "/iconos/patras.png");
	btnAtras.setIconTextGap(1);
	btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
	btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
	add(btnAtras);

    }
    
    
    public void cortefinalizado (){
    ///Insert del evento en la tabla HISTORICO
    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Comienzo corte",null);
    
    //TODO AKI JDIALOG de incidencia
    vincidencia = new VIncidencia(m);
    vincidencia.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    vincidencia.setVisible(true);
    
    
  ///Insert del evento en la tabla HISTORICO
    dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),m.getNombre(),m.getCodnac(),m.getIdcorte(),"Fin corte",null);

   }
    public void adaptajbutton (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()/2, (int) (but.getHeight()), Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
    
    public void adaptajbuttonabajo (JToggleButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()-55, but.getHeight()-25, Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
}
