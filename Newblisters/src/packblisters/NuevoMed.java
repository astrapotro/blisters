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

public class NuevoMed extends JDialog  implements ItemListener{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textNombre;
    private JTextField textnewCorte;
    private JTextField textCodNac;
    private JTextField textRuta;

    private DBFacade actualizausuario;
    private Class<? extends VTablaMedicamentos> vtablameds;
    
    protected static DefaultComboBoxModel modeloCombo;
    
    private DBFacade consultaids;
    
    private JDialog Vid;
    private JComboBox comboBoxcorte;
    
    public NuevoMed()  {
	setTitle("Nuevo Medicamento");
	setBounds((Principal.d.width / 2) - 125,
		(Principal.d.height / 2) - 150, 250, 347);
	{
	    
	    
	    consultaids = new DBFacade();
	    
	    JPanel panel = new JPanel();
	    getContentPane().add(panel, BorderLayout.CENTER);
	    panel.setLayout(null);

	    JLabel nombre = new JLabel("Nombre");
	    nombre.setBounds(25, 30, 70, 15);
	    panel.add(nombre);

	    textNombre = new JTextField();
	    textNombre.setBounds(25, 50, 202, 19);
	    panel.add(textNombre);
	    textNombre.setColumns(10);
	   
	    JLabel codnac = new JLabel("Código Nacional");
	    codnac.setBounds(25, 89, 141, 15);
	    panel.add(codnac);

	    JButton guardar = new JButton("Guardar");
	    guardar.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    
		    //if ()
		    // TODO Guardar el nuevo usuario en bbdd
		    actualizausuario = new DBFacade();
		    actualizausuario.insertarMed(textNombre.getText(),
			    Integer.valueOf(textCodNac.getText()),
			    Integer.valueOf(textnewCorte.getText()),
			    textRuta.getText());
		    
		   
		    //PAra repintar VTABLAMEDS NOVA
		    Window vPadre = SwingUtilities.getWindowAncestor(
                            (Component) e.getSource());
		    
		    Window vAbuelo = SwingUtilities.getWindowAncestor(
                            (Component) e.getSource());
		    
		    if (vPadre instanceof JDialog)
                    {
                        vAbuelo.repaint();
                    }
		   
		    // Cierro ventana
		    dispose();
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

//	    textnewCorte = new JTextField();
//	    textnewCorte.setBounds(25, 175, 114, 19);
//	    panel.add(textnewCorte);
//	    textnewCorte.setColumns(10);
//	    textnewCorte.addKeyListener(new KeyAdapter() {
//	    		//PAra que sólo lea números !
//	  		public void keyTyped(KeyEvent e) {
//	  		    char caracter = e.getKeyChar();
//	  		    if (((caracter < '0') || (caracter > '9'))
//	  			    && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter != KeyEvent.VK_DELETE))
//	  		    	    || (textnewCorte.getText().length()>=8))
// 
//	  			e.consume();
//	  		    }
//	  	    });

	    textCodNac = new JTextField();
	    textCodNac.setBounds(25, 113, 202, 19);
	    panel.add(textCodNac);
	    textCodNac.setColumns(10);
	    textCodNac.addKeyListener(new KeyAdapter() {
		//PAra que sólo lea números !
		public void keyTyped(KeyEvent e) {
		    char caracter = e.getKeyChar();
		    if (((caracter < '0') || (caracter > '9'))
  			    && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter != KeyEvent.VK_DELETE))
  		    	    //|| (textCodNac.getText().length() >=Integer.toString(Integer.MAX_VALUE).length()))
  			    || (textCodNac.getText().length()>=9))

			
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
	  
	    
	    
	    
//	    textRuta.addFocusListener(new FocusAdapter() {
//		@Override
//		public void focusGained(FocusEvent e) {
	    
	    

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
	    

//	    ListSelectionModel selectionModel = modeloCombo.getSelectedItem();
//	    selectionModel.addListSelectionListener(this);
	   
	    
	    
	    consultaids.getIdCorte(modeloCombo);
	    
	    
	    //Nuevo Corte
	    String newcorte= "Nuevo Corte";
	    modeloCombo.addElement(newcorte);
	    
	    comboBoxcorte.setModel(modeloCombo);
	    comboBoxcorte.addItemListener(this);
	    
	    //comboBoxcorte.
	    comboBoxcorte.setBounds(25, 169, 202, 24);
	    panel.add(comboBoxcorte);
	    
	    JLabel lblNewLabel = new JLabel("ID Corte");
	    lblNewLabel.setBounds(25, 153, 70, 15);
	    panel.add(lblNewLabel);
	   
	}
    }


//    @Override
//    public void valueChanged(ListSelectionEvent e) {
////	// TODO Auto-generated method stub
////	int corte = table.getSelectedRow();
////
////	if (filaVista < 0) {
////	    // No hay selección
////	    System.out.println(Messages
////		    .getString("VTablaMedicamentos.ErrorNoSelection")); //$NON-NLS-1$
////	} else {
////	    int filaModelo = table.convertRowIndexToModel(filaVista);
////	    medselect = modelotablao.getmed(filaModelo);
////	    System.out.println("valueChanged VTablaMeds: " + medselect);
////
////	}
//
//	if (modeloCombo.getSelectedItem() ==){
//		
//	    }
//	 System.out.println((modeloCombo.getIndexOf(comboBoxcorte)));
//	 
//	    if ((modeloCombo.getElementAt(modeloCombo.getIndexOf(comboBoxcorte)).toString().contains("Nuevo Corte"))){
//		System.out.println("NUEVO CORTEEEEEEEEEEEEEEEEEEEE");
//	       JDialog nuevocorte = new JDialog();
//	       setTitle("Nuevo Corte");
//		setBounds((Principal.d.width / 2) - 125,
//			(Principal.d.height / 2) - 150, 250, 347);
//		nuevocorte.setVisible(true);
//	    }
//	    
//	
//    }


    @Override
    public void itemStateChanged(ItemEvent e) {
	// TODO Auto-generated method stub
	if (e.getStateChange() == ItemEvent.SELECTED) {
	          Object item = e.getItem();
	         
	         if (item.toString()=="Nuevo Corte"){
	             NuevoMed.this.setModal(false);
	             System.out.println("NUEVO CORTEEEEEEEEEEEEEEEEEEEE");
		       JDialog nuevocorte = new JDialog();
		       setTitle("Nuevo Corte");
			setBounds((Principal.d.width / 2) - 125,
				(Principal.d.height / 2) - 150, 250, 347);
			JPanel panel = new JPanel();
			    getContentPane().add(panel, BorderLayout.CENTER);
			    panel.setLayout(null);

			    JLabel nombre = new JLabel("Nombre");
			    nombre.setBounds(25, 30, 70, 15);
			    panel.add(nombre);
			nuevocorte.setVisible(true);
	         }
	       }
    }
}
