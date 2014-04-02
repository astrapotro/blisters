package packblisters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;

import java.math.BigInteger;

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

public class NuevoMed extends JDialog implements ItemListener {
    /**
     * TODO: cambiar nombre a VNuevoMed, añadir código de barras ¿se verá la foto subida?
     * TODO: campo de gcode con mayor tamaño
     */
    private static final long serialVersionUID = 1L;
    private JTextField textNombre;
    private String textnewCorte;
    private JTextField textCodNac;
    private JTextField textRuta;

    private DBFacade insertamed;
    private Class<? extends VTablaMedicamentos> vtablameds;
    private JDialog nuevocorte;

    protected DefaultComboBoxModel modeloCombo;

    private DBFacade consultaids;
    private DBFacade insertaid;

    private JOptionPane warnincorte;
    private JTextArea textArea;

    private JDialog Vid;
    private JComboBox comboBoxcorte;
    private ModeloTablaMeds mt;
    private JTextField textCodBar;

    public NuevoMed(ModeloTablaMeds modelotablao) {
	
	mt=modelotablao;
	
	setTitle("Nuevo Medicamento");
	setBounds((Principal.d.width / 2) - 125,
		(Principal.d.height / 2) - 150, 250, 347);
	{

	    consultaids = new DBFacade();

	    JPanel panel = new JPanel();
	    getContentPane().add(panel, BorderLayout.CENTER);
	    panel.setLayout(null);

	    JLabel nombre = new JLabel("Nombre");
	    nombre.setBounds(25, 12, 202, 15);
	    panel.add(nombre);

	    textNombre = new JTextField();
	    textNombre.setBounds(25, 27, 202, 19);
	    panel.add(textNombre);
	    textNombre.setColumns(10);
	    
	  

	    JLabel codnac = new JLabel("Código Nacional");
	    codnac.setBounds(25, 58, 141, 15);
	    panel.add(codnac);

	    JButton guardar = new JButton("Guardar");
	    guardar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		    // if ()
//		    if (textArea.getText().isEmpty()) {
//			    JOptionPane.showMessageDialog(warnincorte,
//				    "Debes introducir un código GCODE",
//				    "Warning", JOptionPane.WARNING_MESSAGE);
			
		    
		    // Guardar el nuevo usuario en bbdd
		    insertamed = new DBFacade();
		    insertamed.insertarMed(textNombre.getText(),
			    Integer.valueOf(textCodNac.getText()),
			    Long.valueOf(textCodBar.getText()),
			    Integer.valueOf(textnewCorte),
			    textRuta.getText());
		    
		    Medicamento nuevoMed = new Medicamento();
		    nuevoMed.setNombre(textNombre.getText());
		    nuevoMed.setCodnac(Integer.valueOf(textCodNac.getText()));
		    nuevoMed.setCodbar(Long.valueOf(textCodBar.getText()));
		    nuevoMed.setIdcorte(Integer.valueOf(textnewCorte));
		    nuevoMed.setRutaimg(textRuta.getText());
		    
		    
		   // insertamed.getMedicamentos(mt);
		// Guardar e n el modelo
		   mt.anademed(nuevoMed);

		    // PAra repintar VTABLAMEDS NOVA
//		    Window vPadre = SwingUtilities
//			    .getWindowAncestor((Component) e.getSource());
//
//		    Window vAbuelo = SwingUtilities
//			    .getWindowAncestor(vPadre);
//
//		    if (vPadre instanceof JDialog) {
//			vAbuelo.repaint();
//		    }

		    // Cierro ventana
		    dispose();
		    
		  
		    
		    // PAra repintar VTABLAMEDS NOVA
			Window vPadre = SwingUtilities
				.getWindowAncestor((Component) e.getSource()); 
			vPadre.repaint();
		  
			    JOptionPane.showMessageDialog(warnincorte,
				    "Medicamento añadido correctamente",
				    "Info", JOptionPane.INFORMATION_MESSAGE);
		
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

	    // textnewCorte = new JTextField();
	    // textnewCorte.setBounds(25, 175, 114, 19);
	    // panel.add(textnewCorte);
	    // textnewCorte.setColumns(10);
	    // textnewCorte.addKeyListener(new KeyAdapter() {
	    // //PAra que sólo lea números !
	    // public void keyTyped(KeyEvent e) {
	    // char caracter = e.getKeyChar();
	    // if (((caracter < '0') || (caracter > '9'))
	    // && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter !=
	    // KeyEvent.VK_DELETE))
	    // || (textnewCorte.getText().length()>=8))
	    //
	    // e.consume();
	    // }
	    // });

	    textCodNac = new JTextField();
	    textCodNac.setBounds(25, 76, 202, 19);
	    panel.add(textCodNac);
	    textCodNac.setColumns(10);
	    textCodNac.addKeyListener(new KeyAdapter() {
		// PAra que sólo lea números !
		public void keyTyped(KeyEvent e) {
		    char caracter = e.getKeyChar();
		    if (((caracter < '0') || (caracter > '9'))
			    && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter != KeyEvent.VK_DELETE))
			    // || (textCodNac.getText().length()
			    // >=Integer.toString(Integer.MAX_VALUE).length()))
			    || (textCodNac.getText().length() >= 9))

			e.consume();
		}
	    });

	    JLabel lblImagen = new JLabel("Ruta de imagen");
	    lblImagen.setBounds(25, 212, 114, 15);
	    panel.add(lblImagen);

	    textRuta = new JTextField();
	    textRuta.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {

		    // textRuta.addFocusListener(new FocusAdapter() {
		    // @Override
		    // public void focusGained(FocusEvent e) {

		    // QUe salga el explorador de archivos
		    JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			    "JPG, PNG & GIF Images", "jpg", "gif", "png");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(getParent());
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: "
				+ chooser.getSelectedFile().getName());
			textRuta.setText(chooser.getSelectedFile().getPath());
			textNombre.requestFocus();
		    }

		}

	    });

	    textRuta.setBounds(25, 229, 202, 19);
	    panel.add(textRuta);
	    textRuta.setColumns(10);

	    comboBoxcorte = new JComboBox();
	    modeloCombo = new DefaultComboBoxModel();
	    modeloCombo.addListDataListener(comboBoxcorte);

	    // ListSelectionModel selectionModel =
	    // modeloCombo.getSelectedItem();
	    // selectionModel.addListSelectionListener(this);

	    

	    // Nuevo Corte
	    String newcorte = "Nuevo Corte";
	    modeloCombo.addElement(newcorte);
	    consultaids.getIdCorte(modeloCombo);
	    modeloCombo.addListDataListener(comboBoxcorte);
	    
	    comboBoxcorte.addItemListener(this);
	    comboBoxcorte.setModel(modeloCombo);
	   

	    // comboBoxcorte.
	    comboBoxcorte.setBounds(25, 169, 202, 24);
	    panel.add(comboBoxcorte);

	    JLabel lblNewLabel = new JLabel("ID Corte");
	    lblNewLabel.setBounds(25, 153, 70, 15);
	    panel.add(lblNewLabel);
	    
	    JLabel codbar = new JLabel("Código de barras");
	    codbar.setBounds(25, 107, 141, 15);
	    panel.add(codbar);
	    
	    textCodBar = new JTextField();
	    textCodBar.setColumns(10);
	    textCodBar.setBounds(25, 125, 202, 19);
	    textCodBar.addKeyListener(new KeyAdapter() {
		// PAra que sólo lea números !
		public void keyTyped(KeyEvent e) {
		    char caracter = e.getKeyChar();
		    if (((caracter < '0') || (caracter > '9'))
			    && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter != KeyEvent.VK_DELETE))
			    // || (textCodNac.getText().length()
			    // >=Integer.toString(Integer.MAX_VALUE).length()))
			    || (textCodBar.getText().length() >= 13))

			e.consume();
		}
	    });
	    panel.add(textCodBar);

	}
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
	if (e.getStateChange() == ItemEvent.SELECTED) {
	    Object item = e.getItem();
	    
	 
	    if (item.toString() == "Nuevo Corte") {
		NuevoMed.this.setModal(false);
		System.out.println("NUEVO CORTEEEEEEEEEEEEEEEEEEEE");

		nuevocorte = new JDialog();
		nuevocorte.setModal(true);
		nuevocorte.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		nuevocorte.setTitle("Nuevo Corte");
		nuevocorte.setBounds((Principal.d.width / 2) - 175,
			(Principal.d.height / 2) - 150, 350, 347);
		JPanel panel = new JPanel();
		nuevocorte.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel nombre = new JLabel("Numero de corte "
			+ ((int) modeloCombo.getSize() + 1));
		nombre.setBounds(25, 30, 202, 15);
		panel.add(nombre);

		textArea = new JTextArea();
		textArea.setBounds(25, 52, 300, 215);
		panel.add(textArea);
		// Se inicializa el objeto y se le agrega un JTextArea
		JScrollPane scroll = new JScrollPane(textArea);
		// Se le asigna una posicion y un tamaño
		scroll.setBounds(25, 52, 300, 215);
		panel.add(scroll);

		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

			// PAra repintar VTABLAMEDS NOVA
			Window vPadre = SwingUtilities
				.getWindowAncestor((Component) e.getSource());


			if (textArea.getText().isEmpty()) {
			    JOptionPane.showMessageDialog(warnincorte,
				    "Debes introducir un código GCODE",
				    "Warning", JOptionPane.WARNING_MESSAGE);
			 // Cierro ventana
				nuevocorte.dispose();
			} else {
			    	insertaid = new DBFacade();
			    	int i= insertaid.insertarIdCorte(textArea.getText());
				JOptionPane.showMessageDialog(warnincorte,
					    "Nuevo corte añadido",
					    "", JOptionPane.WARNING_MESSAGE);
				
				modeloCombo.addElement(i);
		                
				//comboBoxcorte.addItem( ((int) NuevoMed.modeloCombo.getSize() + 1));
				// Cierro ventana
				vPadre.repaint();
				nuevocorte.dispose();
			}
			
		    }
		});
		guardar.setBounds(75, 279, 92, 25);
		panel.add(guardar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			// Cierro ventana
			nuevocorte.dispose();
			NuevoMed.this.setModal(true);
		    }
		});
		cancelar.setBounds(200, 279, 98, 25);
		panel.add(cancelar);

		nuevocorte.setVisible(true);

	    }else{

		
		textnewCorte = item.toString();
		
		
		
	    }
	}
    }
}
