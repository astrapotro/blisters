package packblisters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
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
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VNuevoMed extends JDialog implements ItemListener {
	/**
	 * TODO: cambiar nombre a VNuevoMed, añadir código de barras ¿se verá la
	 * foto subida? TODO: campo de gcode con mayor tamaño
	 */
	private static final long serialVersionUID = 1L;
	private JTextField jtfNombre, jtfCodNac, jtfCodBar, jtfIdCorte, jtfRutaImg, jtfMiCorte, jtfRecorte;

	private String idModificadoCorte;

	private DBFacade dbfacade;
	private JDialog nuevocorte;
	protected DefaultComboBoxModel<String> modeloCombo;
	private JComboBox<String> comboBoxcorte;
	private ModeloTablaMeds mt;

	public VNuevoMed(ModeloTablaMeds modelotablao) {
		dbfacade = new DBFacade();
		mt = modelotablao;

		setTitle("Nuevo Medicamento");
		setBounds((Principal.d.width / 2) - 125,
				(Principal.d.height / 2) - 150, 250, 347);
		{
			JPanel panel = new JPanel();
    	    getContentPane().add(panel, BorderLayout.CENTER);
    	    panel.setLayout(null);

    	    JLabel lblNombre = new JLabel("Nombre");
    	    lblNombre.setBounds(25, 12, 202, 15);
    	    panel.add(lblNombre);

    	    jtfNombre = new JTextField();
    	    jtfNombre.setBounds(25, 32, 202, 19);
    	    jtfNombre.setDocument(new DocumentoLimitado(20));
    	    panel.add(jtfNombre);
    	    
    	 
    	    JLabel lblCodnac = new JLabel("Código Nacional");
    	    lblCodnac.setBounds(25, 63, 141, 15);
    	    panel.add(lblCodnac);
    	    
    	    jtfCodNac = new JTextField();
    	    jtfCodNac.setBounds(25, 90, 202, 19);
    	    jtfCodNac.setDocument(new DocumentoLimitado(11));
    	    jtfCodNac.addKeyListener(new KeyAdapter() {
				// Para que sólo lea números !
				public void keyTyped(KeyEvent e) {
				    char caracter = e.getKeyChar();
				    if (((caracter < '0') || (caracter > '9'))
					    && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter != KeyEvent.VK_DELETE))
					    || (jtfCodNac.getText().length() >= 9))
				
					e.consume();
				}
    	    });
    	    panel.add(jtfCodNac);
    	    
    	    JLabel lblCodBarras = new JLabel("Código barras");
    	    lblCodBarras.setBounds(25, 118, 141, 15);
    	    panel.add(lblCodBarras);
    	    
    	    jtfCodBar = new JTextField();
    	    jtfCodBar.setText("0");
    	    jtfCodBar.setBounds(25, 142, 202, 19);
    	    jtfCodBar.setDocument(new DocumentoLimitado(20));
    	    panel.add(jtfCodBar);
    	    
    	    JLabel lblIdCorte = new JLabel("ID Corte");
    	    lblIdCorte.setBounds(25, 173, 70, 15);
    	    panel.add(lblIdCorte);
    	    
    	    modeloCombo = new DefaultComboBoxModel<String>();

    	    // Poblar modelo
    	    String newcorte = "Nuevo Corte";
    	    modeloCombo.addElement(newcorte);
    	    dbfacade.getIdCorte(modeloCombo);

    	    JComboBox<String> comboBoxcorte = new JComboBox<String>();
    	    modeloCombo.addListDataListener(comboBoxcorte);    	    
    	    comboBoxcorte.addItemListener(this);
    	    comboBoxcorte.setModel(modeloCombo);
    	    comboBoxcorte.setBounds(25, 189, 202, 24);
    	    panel.add(comboBoxcorte);
    	    

    	    
    	    JLabel lblRutaImg = new JLabel("Ruta de imagen");
    	    lblRutaImg.setBounds(25, 225, 114, 15);
    	    panel.add(lblRutaImg);

    	    jtfRutaImg = new JTextField();
    	    jtfRutaImg.setBounds(25, 242, 202, 19);
    	    jtfRutaImg.setDocument(new DocumentoLimitado(100));
    	    jtfRutaImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
			
					// Que salga el explorador de archivos
				    JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
					    "Imagenes PNG", "png");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(getParent());
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
					jtfRutaImg.setText(chooser.getSelectedFile().getPath());
					jtfNombre.requestFocus();
				    }
				}
    	    });
    	    panel.add(jtfRutaImg);

			JButton guardar = new JButton("Guardar");
			guardar.setEnabled(false);
			guardar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// Guardar el nuevo usuario en bbdd
					dbfacade = new DBFacade();
					dbfacade.insertarMed(jtfNombre.getText(),
							Integer.valueOf(jtfCodNac.getText()),
							Integer.valueOf(jtfCodBar.getText()),
							Integer.valueOf(idModificadoCorte),
							jtfRutaImg.getText());

					// Medicamento nuevoMed = new Medicamento();
					// nuevoMed.setNombre(textNombre.getText());
					// nuevoMed.setCodnac(Integer.valueOf(textCodNac.getText()));
					// nuevoMed.setIdcorte(Integer.valueOf(textnewCorte));
					// nuevoMed.setRutaimg(textRuta.getText());

					// Guardar en el modelo
					// mt.anademed(nuevoMed);
					dbfacade.getMedicamentos(mt);

					// Cierro ventana
					dispose();

					// PAra repintar VTABLAMEDS NOVA
					Window vPadre = SwingUtilities
							.getWindowAncestor((Component) e.getSource());
					vPadre.repaint();

					JOptionPane.showMessageDialog(vPadre,
							"Medicamento añadido correctamente", "Info",
							JOptionPane.INFORMATION_MESSAGE);

				}
			});

			guardar.setBounds(25, 279, 92, 25);
			panel.add(guardar);

			JButton cancelar = new JButton("Cancelar");
			cancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Cierro ventana
					dispose();
				}
			});
			cancelar.setBounds(129, 279, 98, 25);
			panel.add(cancelar);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
	
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Object item = e.getItem();

			if (item.toString() == "Nuevo Corte") {
				//VNuevoMed.this.setModal(false);
				nuevocorte = new VCorte(modeloCombo);    		
	    		nuevocorte.setVisible(true);
			} else {

				idModificadoCorte = item.toString();

			}
		}
	}
}