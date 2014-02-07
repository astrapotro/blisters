package packblisters;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Principal extends JFrame {

    /**
     * 
     */
      private static final long serialVersionUID = 1L;

      protected static JPanel Panel;
      
    // Consigo la resolución de la pantalla
    public static Dimension d;
    
    //Hago statico el JPanel.
    protected static JPanel vlogin = new VLogin();
    
   
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    Principal frame = new Principal();
		   
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
//		int anchura= (d.width*80)/100;
//		int altura= (d.height*80)/100;
		
		vlogin.setBounds((d.width/2)-151,(d.height/2)-50 , 302, 100);
		Panel.add(vlogin);

    }

}
