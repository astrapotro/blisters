package packblisters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VNuevoUsr extends JDialog {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private JTextField textNombre;
	private JCheckBox chkAdministrador;
	private JPasswordField pfContraseña;

	private DBFacade dbFacade;
	// private Class<? extends VTablaUsuarios> vTablaUsr;
	private JOptionPane warninUsuario;
	private ModeloTablaUsr mtu;

	public VNuevoUsr(ModeloTablaUsr modelotablao) {

		URL pathIcon = this.getClass().getClassLoader()
				.getResource("iconos/1 (14).jpg");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(pathIcon);
		this.setIconImage(img);

		mtu = modelotablao;

		setTitle("Nuevo Usuario");
		setBounds((Principal.d.width / 2) - 125,
				(Principal.d.height / 2) - 150, 235, 245);
		{

			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblNombre = new JLabel("Nombre");
			lblNombre.setBounds(10, 12, 217, 15);
			panel.add(lblNombre);

			textNombre = new JTextField();
			textNombre.setBounds(13, 33, 202, 19);
			panel.add(textNombre);
			textNombre.setColumns(10);

			// JLabel contraseña2 = new JLabel("Reescriba Contraseña");
			// nombre.setBounds(25, 30, 202, 15);
			// panel.add(nombre);
			//
			// pfContraseña2 = new JPasswordField(5);
			// pfContraseña2.setBounds(25, 50, 202, 19);
			// panel.add(pfContraseña2);
			//

			JLabel administrador = new JLabel("Administrador");
			administrador.setBounds(13, 128, 141, 15);
			panel.add(administrador);

			chkAdministrador = new JCheckBox();
			chkAdministrador.setBounds(186, 124, 35, 19);
			panel.add(chkAdministrador);

			JLabel label = new JLabel("Contraseña");
			label.setBounds(10, 64, 202, 27);
			panel.add(label);

			pfContraseña = new JPasswordField(5);
			pfContraseña.setBounds(13, 90, 202, 19);
			panel.add(pfContraseña);

			JButton guardar = new JButton("Guardar");
			guardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// Guardar el nuevo usuario en bbdd:crear objerto y darselo
					// al facade

					Usuario nuevoUsr = new Usuario();
					nuevoUsr.setNombre(textNombre.getText());
					nuevoUsr.setRoot(chkAdministrador.isSelected());
					char[] pass = pfContraseña.getPassword();
					dbFacade = new DBFacade();
					dbFacade.insertarUsr(nuevoUsr, pass);// rellena el id de
															// ususario
					// seguridad de contraseña
					Arrays.fill(pass, '0');
					// Guardar en el modelo
					mtu.anadeUsr(nuevoUsr);

					// Cierro ventana
					dispose();

					// dbFacade.getUsuarios(mtu);

					// Para repintar VTABLAUSR NOVA
					Window vPadre = SwingUtilities
							.getWindowAncestor((Component) e.getSource());
					vPadre.repaint();

					JOptionPane.showMessageDialog(warninUsuario,
							"Usuario añadido correctamente", "Info",
							JOptionPane.INFORMATION_MESSAGE);

				}
			});
			guardar.setBounds(10, 173, 92, 25);
			panel.add(guardar);

			JButton cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Cierro ventana
					dispose();
				}
			});
			cancelar.setBounds(117, 173, 98, 25);
			panel.add(cancelar);

		}
	}
}
