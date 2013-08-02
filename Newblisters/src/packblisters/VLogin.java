package packblisters;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VLogin extends JPanel {

    /**
	 * 
	 */
    // Consigo la resolución de la pantalla
    public static Dimension d;

    private static final long serialVersionUID = 1L;

    private JTextField textField;
    private JButton btnNewButton;
    private DBFacade login;
    //static VLogin frame = new VLogin();
    protected static VAdmin vadmin = new VAdmin();
    protected static Usuario UsuarioLogueado;
    protected static VProcesoCorte vprocesocorte;
    private JPasswordField passwordField;

    public VLogin() {

	
	GridBagLayout gridBagLayout = new GridBagLayout();
	gridBagLayout.columnWidths = new int[] { 150, 150, 0 };
	gridBagLayout.rowHeights = new int[] { 30, 30, 30, 0 };
	gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
	gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
		Double.MIN_VALUE }

	;
	
	setLayout(gridBagLayout);
	
	
	JLabel lblNewLabel = new JLabel(Messages.getString("VLogin.User"));
	GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
	gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
	gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
	gbc_lblNewLabel.gridx = 0;
	gbc_lblNewLabel.gridy = 0;
	add(lblNewLabel, gbc_lblNewLabel);

	textField = new JTextField();
	GridBagConstraints gbc_textField = new GridBagConstraints();
	gbc_textField.anchor = GridBagConstraints.NORTH;
	gbc_textField.fill = GridBagConstraints.HORIZONTAL;
	gbc_textField.insets = new Insets(0, 0, 5, 0);
	gbc_textField.gridx = 1;
	gbc_textField.gridy = 0;
	add(textField, gbc_textField);
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
	GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
	gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHWEST;
	gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
	gbc_lblNewLabel_1.gridx = 0;
	gbc_lblNewLabel_1.gridy = 1;
	add(lblNewLabel_1, gbc_lblNewLabel_1);

	passwordField = new JPasswordField();
	GridBagConstraints gbc_passwordField = new GridBagConstraints();
	gbc_passwordField.anchor = GridBagConstraints.NORTH;
	gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
	gbc_passwordField.insets = new Insets(0, 0, 5, 0);
	gbc_passwordField.gridx = 1;
	gbc_passwordField.gridy = 1;
	add(passwordField, gbc_passwordField);
	passwordField.setColumns(10);
	btnNewButton = new JButton(Messages.getString("VLogin.Introbtn"));
	GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
	gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
	gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
	gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
	gbc_btnNewButton.gridx = 0;
	gbc_btnNewButton.gridy = 2;
	add(btnNewButton, gbc_btnNewButton);

	
	btnNewButton.addActionListener(new ActionListener() {
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
			vadmin.setBounds((Principal.d.width/2)-225, (Principal.d.height/2)-140, 450, 280);
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

	JButton btnSalir = new JButton(Messages.getString("VLogin.Quit"));
	GridBagConstraints gbc_btnSalir = new GridBagConstraints();
	gbc_btnSalir.fill = GridBagConstraints.HORIZONTAL;
	gbc_btnSalir.anchor = GridBagConstraints.NORTH;
	gbc_btnSalir.gridx = 1;
	gbc_btnSalir.gridy = 2;
	add(btnSalir, gbc_btnSalir);

	btnSalir.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// Hay que depurar y cerrar posibles conexiones abiertas
		VLogin.this.removeAll();
		System.exit(0);
	    }
	});

	
    }
    
    
   


}


