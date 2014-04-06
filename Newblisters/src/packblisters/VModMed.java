package packblisters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VModMed extends JDialog implements ItemListener {
	
    private static final long serialVersionUID = 1L;
    
    private String idModificadoCorte;
	private JTextField jtfNombre, jtfCodNac, jtfCodBar, jtfIdCorte, jtfRutaImg, jtfMiCorte, jtfRecorte;
	
	private DBFacade dbfacade = new DBFacade();
	private Class<? extends VTablaMedicamentos> vtablameds;
	
	private VCorte nuevocorte;
	DefaultComboBoxModel<String> modeloCombo;
    private ModeloTablaMeds mt;
    private Medicamento medSeleccionado;
    private JTextField textField;

	/**
     * Create the frame.
	 * @param mseleccionado 
     */
    public VModMed(ModeloTablaMeds modelotablao, Medicamento mseleccionado) { 		
  	
    	mt=modelotablao;
    	medSeleccionado=mseleccionado;
    	
    	setTitle("Modificar Medicamento");
    	setBounds((Principal.d.width / 2) - 125, (Principal.d.height / 2) - 150, 250, 347);
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
    	    jtfNombre.setText(mseleccionado.getNombre());
    	    panel.add(jtfNombre);
    	    
    	 
    	    JLabel lblCodnac = new JLabel("Código Nacional");
    	    lblCodnac.setBounds(25, 63, 141, 15);
    	    panel.add(lblCodnac);
    	    
    	    jtfCodNac = new JTextField();
    	    jtfCodNac.setBounds(25, 90, 202, 19);
    	    jtfCodNac.setDocument(new DocumentoLimitado(11));
    	    jtfCodNac.setText(Integer.toString(mseleccionado.getCodnac()));
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
    	    jtfCodBar.setBounds(25, 142, 202, 19);
    	    jtfCodBar.setDocument(new DocumentoLimitado(11));
    	    jtfCodBar.setText(Long.toString(mseleccionado.getCodbar()));
    	    jtfCodBar.addKeyListener(new KeyAdapter() {
				// Para que sólo lea números !
				public void keyTyped(KeyEvent e) {
				    char caracter = e.getKeyChar();
				    if (((caracter < '0') || (caracter > '9'))
					    && ((caracter != KeyEvent.VK_BACK_SPACE) || (caracter != KeyEvent.VK_DELETE))
					    || (jtfCodBar.getText().length() >= 11))
				
					e.consume();
				}
    	    });
    	    panel.add(jtfCodBar);
    	    
    	    JLabel lblIdCorte = new JLabel("ID Corte");
    	    lblIdCorte.setBounds(25, 173, 70, 15);
    	    panel.add(lblIdCorte);
    	    
    	    modeloCombo = new DefaultComboBoxModel<String>();

    	    // Poblar modelo corte
    	    String newcorte = "Nuevo Corte";
    	    modeloCombo.addElement(newcorte);
    	    dbfacade.getIdCorte(modeloCombo);

    	    JComboBox<String> comboBoxcorte = new JComboBox<String>();
    	    modeloCombo.addListDataListener(comboBoxcorte);    	    
    	    comboBoxcorte.addItemListener(this);
    	    comboBoxcorte.setModel(modeloCombo);
    	    comboBoxcorte.setBounds(25, 189, 202, 24);
    	    comboBoxcorte.setSelectedIndex(mseleccionado.getIdcorte());
    	    panel.add(comboBoxcorte);
    	    

    	    
    	    JLabel lblRutaImg = new JLabel("Ruta de imagen");
    	    lblRutaImg.setBounds(25, 225, 114, 15);
    	    panel.add(lblRutaImg);

    	    jtfRutaImg = new JTextField();
    	    jtfRutaImg.setBounds(25, 242, 202, 19);
    	    jtfRutaImg.setDocument(new DocumentoLimitado(100));
    	    jtfRutaImg.setText(mseleccionado.getRutaimg());
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
    	    guardar.setBounds(25, 279, 92, 25);
    	    guardar.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    			
	    		    medSeleccionado.setNombre(jtfNombre.getText());
	    		    medSeleccionado.setCodnac(Integer.valueOf(jtfCodNac.getText()));
	    		    medSeleccionado.setCodbar(Long.valueOf(jtfCodBar.getText()));
	    		    medSeleccionado.setIdcorte(Integer.valueOf(idModificadoCorte));
	    		    medSeleccionado.setRutaimg(jtfRutaImg.getText());
	    		    
	    		    // Guardar cambios en el modelo y BBDD 	    		    
	    		    mt.modificamed(medSeleccionado);    		    
	    		    
	    		    // Cierro ventana
	    		    dispose();
	    		    
	    		    // Para repintar
	    			Window vPadre = SwingUtilities.getWindowAncestor((Component) e.getSource()); 
	    			vPadre.repaint();
	    			 
	   			    JOptionPane.showMessageDialog(vPadre,
	    				    "Medicamento modificado correctamente",
	    				    "Info", JOptionPane.INFORMATION_MESSAGE);
	    			}
	    	});    	    
    	    panel.add(guardar);
    	        	    
    	    JButton cancelar = new JButton("Cancelar");
    	    cancelar.setBounds(129, 279, 98, 25);
    	    cancelar.addActionListener(new ActionListener() {
	    		public void actionPerformed(ActionEvent e) {
	    		    // Cierro ventana
	    		    dispose();
	    		}
    	    });    	    
    	    panel.add(cancelar);
    	    
    	    
    	}
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
    	
    	if (e.getStateChange() == ItemEvent.SELECTED) {
    	    Object item = e.getItem();
    	    if (item.toString() == "Nuevo Corte") {
	    		//NuevoMed.this.setModal(false);
	    	    nuevocorte = new VCorte(modeloCombo);    		
	    		nuevocorte.setVisible(true);
    	    }else{	
    	    	idModificadoCorte = item.toString();    		
    	    }
    	}
    }
}
