package packblisters;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;




import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.net.URL;


public class Principal extends JFrame {

    /**
     * 
     */
      private static final long serialVersionUID = 1L;

      protected static JPanel Panel;
      
    // Consigo la resolución de la pantalla
    public static Dimension d;
    
    //Hago statico el JPanel.
    public static JPanel vlogin = new VLogin();
    
   
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Principal frame = new Principal();
		    frame.setTitle(" B L I S T E R S");
		    URL pathIcon = this.getClass().getClassLoader().getResource("iconos/1 (14).jpg");
		    Toolkit kit = Toolkit.getDefaultToolkit();
		    Image img = kit.createImage(pathIcon);
		    frame.setIconImage(img);
		    
		  
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    
    public Principal() {

	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// Pongo la resolución de la pantalla
	d = Toolkit.getDefaultToolkit().getScreenSize();
	this.setSize(d.width, d.height);

	Panel = new JPanel();
	Panel.setBorder(new EmptyBorder(5, 5, 5, 5));
	Panel.setBounds(0, 0, d.width, d.height);
	setContentPane(Panel);
	
	
	
		Panel.setLayout(null);
		vlogin.setBackground(new Color(224, 255, 255));
		vlogin.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));		
		vlogin.setBounds((Panel.getWidth()/2)-(690/2),(Panel.getHeight()/2)-(600/2) , 690, 550);
		
		//Al ancho de la pantalla lo divido en 2 y le resto la mitad del ancho de mi ventana, con eso queda centrada en el eje X, para el eje Y es lo mismo pero con el alto: 
		//vlogin.setLocation((d.width/2)-(vlogin.getWidth()/2), (d.height/2)-(vlogin.getHeight()/2)); 
		
		
		Panel.add(vlogin);

    }

}
