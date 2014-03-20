package packblisters;

import java.awt.Dimension;

import java.awt.Image;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class VLogin extends JPanel {

    /**
	 * TODO: poner marco y logo de la aplicación
	 */
    // Consigo la resolución de la pantalla
    public static Dimension d;

    private static final long serialVersionUID = 1L;

    private JTextField textField;
    private JButton btnEntrar, btnSalir;
    private DBFacade login;
    //static VLogin frame = new VLogin();
    protected static VAdmin vadmin = new VAdmin();
    protected static Usuario UsuarioLogueado;
    protected static VProcesoCorte vprocesocorte;
    private JPasswordField passwordField;

    public VLogin() {
	setLayout(null);
	
	
	JLabel lblNewLabel = new JLabel(Messages.getString("VLogin.User"));
	lblNewLabel.setBounds(206, 358, 60, 15);
	add(lblNewLabel);

	textField = new JTextField();
	textField.setBounds(356, 358, 150, 19);
	add(textField);
	textField.setColumns(1);
	textField.setHorizontalAlignment(SwingConstants.LEFT);

	textField.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// Pasar el foco al passwordfield

		if (!textField.getText().isEmpty()) {
		    passwordField.requestFocus();
		}
	    }
	});

	JLabel lblNewLabel_1 = new JLabel(Messages.getString("VLogin.Pass"));
	lblNewLabel_1.setBounds(206, 389, 88, 18);
	add(lblNewLabel_1);

	passwordField = new JPasswordField();
	passwordField.setBounds(356, 389, 150, 18);
	add(passwordField);
	passwordField.setColumns(10);
	
	btnEntrar = new JButton(Messages.getString("VLogin.labelentrar.text")); //$NON-NLS-1$
	btnEntrar.setVerticalAlignment(SwingConstants.CENTER);
	btnEntrar.setBounds(206, 445, 130, 67);
	adaptajbutton(btnEntrar, "/iconos/Ok-icon.png");
	btnEntrar.setIconTextGap(2);
	btnEntrar.setHorizontalTextPosition(SwingConstants.CENTER);
	btnEntrar.setVerticalTextPosition(SwingConstants.BOTTOM);
	add(btnEntrar);

	
	btnEntrar.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {
		// Aki tiene que comprobar, en una función nueva si el
		// usuario existe y si la contraseña es correcta y si es
		// correcta abrir nueva venta

		login = new DBFacade();
		if (login.comprobarLogin(textField.getText(),
			((JPasswordField) passwordField).getPassword())) {
		    // Captura el usuario
		    UsuarioLogueado = login.getUsr(textField.getText());

		    if (UsuarioLogueado == null) {
			System.out.println("El usuario no existe.");
			System.exit(0);
		    }
		    // Con el primer parametro frame se consigue que la ventana
		    // salga centrada en la ventana anterior
		   
		    JOptionPane.showMessageDialog( Principal.Panel,
			    Messages.getString("VLogin.Welcome"), //$NON-NLS-1$
			    Messages.getString("VLogin.AccesOK"), //$NON-NLS-1$
			    JOptionPane.INFORMATION_MESSAGE);

		    if (UsuarioLogueado.getRoot()) {
			

			setVisible(false);
			vadmin.setBounds((Principal.d.width/2)-(690/2), (Principal.d.height/2)-(550/2), 690, 550);
			Principal.Panel.add(vadmin);
			vadmin.repaint();
			vadmin.validate();
			vadmin.setVisible(true);
		
			
//			CardLayout CL = (CardLayout)(Principal.Tarjetero.getLayout());
//			CL.next(Principal.Tarjetero);

		    } else {
			// Aki habría que llamar a la siguiente ventana
			// listamedicamentos
			setVisible(false);
			vprocesocorte = new VProcesoCorte();
			vprocesocorte.setBounds((Principal.d.width/2)-400, (Principal.d.height/2)-300, 800, 600);
			Principal.Panel.add(vprocesocorte);
			vprocesocorte.repaint();
			vprocesocorte.validate();
			vprocesocorte.setVisible(true);

		    }

		} else {
		    JOptionPane.showMessageDialog(
			    getParent(),
			    Messages.getString("VLogin.UNWelcome"), Messages.getString("VLogin.AccesKO"), //$NON-NLS-1$ //$NON-NLS-2$
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	});

	btnSalir = new JButton(Messages.getString("VLogin.Quit"));
	btnSalir.setBounds(376, 445, 130, 67);
	
	adaptajbutton(btnSalir, "/iconos/Close-2-icon.png");
	btnSalir.setIconTextGap(2);
	btnSalir.setHorizontalTextPosition(SwingConstants.CENTER);
	btnSalir.setVerticalTextPosition(SwingConstants.BOTTOM);
	add(btnSalir);
	
	
	JPanel panel = new JPanel();
	panel.setBounds(74, 48, 550, 275);
	panel.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(107, 142, 35)));
	add(panel);
	panel.setLayout(null);
	
	JLabel lbllogo = new JLabel("");
	lbllogo.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(107, 142, 35)));
	lbllogo.setBounds(0, 0, 550, 275);
	adaptajlabel (lbllogo, "/iconos/NEOMED4.png");
	panel.add(lbllogo);
	
	btnSalir.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// Hay que depurar y cerrar posibles conexiones abiertas
		VLogin.this.removeAll();
		System.exit(0);
	    }
	});

	
    }
    
   public void adaptajlabel (JLabel lbl, String ruta){
       
       ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
	//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
	lbl.setIcon(new ImageIcon(fot.getImage().getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH)));
	//this.repaint();
       
   }
   
   public void adaptajbutton (JButton but, String ruta){
       
       ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
	//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
	but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()/2, but.getHeight()/2, Image.SCALE_SMOOTH)));
	//this.repaint();
       
   }
}


