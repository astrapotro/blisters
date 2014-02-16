package packblisters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Image;


import javax.swing.border.MatteBorder;
import java.awt.Color;

public class VProcesoCorte extends JPanel implements ListSelectionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // private JList<Medicamento> lista;
    // private DefaultListModel<Medicamento> modelolista;
    private Medicamento medselect;
    private DBFacade consultamedica;
    private JLabel imagen;
    private JTable table;
    private ModeloTablaMeds modelotablao;
    private JButton btnAtras;
    private JButton btnSeleccionar;
    private JTextField busqueda;
    private JRadioButton rdbtnNombre;
    private JRadioButton rdbtnCodigoNacinal;
    private JRadioButton rdbttCodBar;
  

    public VProcesoCorte() {
    	setBackground(new Color(224, 255, 255));
    	setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));

	// modelolista = new DefaultListModel<Medicamento>();

	consultamedica = new DBFacade();

	modelotablao = new ModeloTablaMeds();
	consultamedica = new DBFacade();
	// rellenar modelo
	consultamedica.getMedicamentos(modelotablao);

	setLayout(null);

	JScrollPane tablapane = new JScrollPane();
	tablapane.setBounds(12, 157, 353, 432);
	add(tablapane);
	tablapane
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	tablapane.setViewportBorder(null);
	tablapane
		.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	table = new JTable(modelotablao);
	// Instanciamos el TableRowSorter y lo añadimos al JTable
	TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(
		table.getModel());
	table.setRowSorter(elQueOrdena);

	// table.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
	// null, null));

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	ListSelectionModel selectionModel = table.getSelectionModel();
	// table.setSelectionModel(selectionModel);
	tablapane.setViewportView(table);
	elQueOrdena.setSortsOnUpdates(true);
	selectionModel.addListSelectionListener(this);

	// consultamedica.getmedicamentos(modelolista);

	// Lista + Scroll Pane
	// ====================
	//
	// JScrollPane scrollPane = new JScrollPane();
	// scrollPane.setBounds(28, 79, 185, 84);

	// contentPane.add(scrollPane);

	// TODO: Hay que cambiar la lista x una tabla de dos columnas que
	// permita ordenar cada columna

	// lista = new JList<Medicamento>(modelolista);
	// scrollPane.setViewportView(lista);
	// lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	// lista.setSelectedIndex(0);
	// medselect= lista.getSelectedValue();

	// lista.addListSelectionListener(this);
	// lista.setVisibleRowCount(2);

	   JLabel lblNewLabel_2 = new JLabel(Messages.getString("VProcesoCorte.lblNewLabel_2.text")); //$NON-NLS-1$
	    lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 18));
	    lblNewLabel_2.setForeground(new Color(107, 142, 35));
	    lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel_2.setBounds(12, 7, 788, 33);
	    add(lblNewLabel_2);
	
	
	JLabel lblNewLabel = new JLabel(
		Messages.getString("VProcesoCorte.Medicamentos"));
	lblNewLabel.setBounds(12, 143, 103, 15);
	add(lblNewLabel);

	imagen = new JLabel("");
	imagen.setBounds(377, 58, 411, 452);
	add(imagen);

	JSeparator separator = new JSeparator();
	separator.setOrientation(SwingConstants.VERTICAL);
	separator.setBounds(13, 58, 1, 72);
	add(separator);

	JSeparator separator_1 = new JSeparator();
	separator_1.setOrientation(SwingConstants.VERTICAL);
	separator_1.setBounds(364, 58, 1, 72);
	add(separator_1);

	JSeparator separator_2 = new JSeparator();
	separator_2.setBounds(13, 58, 352, 2);
	add(separator_2);

	JLabel lblNewLabel_1 = new JLabel(
		Messages.getString("VProcesoCorte.lblNewLabel_1.text_1")); //$NON-NLS-1$
	lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 10));
	lblNewLabel_1.setBounds(21, 40, 133, 15);
	add(lblNewLabel_1);

	busqueda = new JTextField();

	rdbtnNombre = new JRadioButton(
		Messages.getString("VProcesoCorte.rdbtnNombre.text")); //$NON-NLS-1$
	rdbtnNombre.setSelected(true);
	rdbtnNombre.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnCodigoNacinal.setSelected(false);
		rdbttCodBar.setSelected(false);

	    }
	});
	rdbtnNombre.setBounds(21, 67, 87, 23);
	add(rdbtnNombre);

	rdbtnCodigoNacinal = new JRadioButton(
		Messages.getString("VProcesoCorte.rdbtnCodigoNacinal.text")); //$NON-NLS-1$
	rdbtnCodigoNacinal.setSelected(false);
	rdbtnCodigoNacinal.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnNombre.setSelected(false);
		rdbttCodBar.setSelected(false);
		// busqueda.setText(null);

	    }
	});
	rdbtnCodigoNacinal.setBounds(104, 67, 121, 23);
	add(rdbtnCodigoNacinal);

	
	rdbttCodBar = new JRadioButton(Messages.getString("VProcesoCorte.rdbttCodBar.text"));
	rdbttCodBar.setSelected(false);
	rdbttCodBar.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnCodigoNacinal.setSelected(false);
		rdbtnNombre.setSelected(false);
		// busqueda.setText(null);

	    }
	});
	rdbttCodBar.setBounds(223, 67, 129, 23);
	add(rdbttCodBar);
	
	JSeparator separator_3 = new JSeparator();
	separator_3.setBounds(13, 131, 352, 2);
	add(separator_3);

	busqueda.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyTyped(KeyEvent e) {

		String value = busqueda.getText();

		if (rdbtnNombre.isSelected()) {

		    for (int row = 0; row <= table.getRowCount() - 1; row++) {
			if ((modelotablao.getmed(row).getNombre()
				.startsWith(value))) {
			    // this will automatically set the view of the
			    // scroll in
			    // the location of the value
			    table.scrollRectToVisible(table.getCellRect(row, 0,
				    true));
			    // this will automatically set the focus of the
			    // searched/selected row/value
			    table.setRowSelectionInterval(row, row);
			}
		    }
		} else if (rdbtnCodigoNacinal.isSelected()) {
		    for (int row = 0; row <= table.getRowCount() - 1; row++) {
			try {
			    if ((((Integer) (modelotablao.getmed(row)
				    .getCodnac())).toString().startsWith(value))) {
				// this will automatically set the view of the
				// scroll in
				// the location of the value
				table.scrollRectToVisible(table.getCellRect(
					row, 0, true));
				// this will automatically set the focus of the
				// searched/selected row/value
				table.setRowSelectionInterval(row, row);
			    }
			} catch (NumberFormatException e1) {
			    // TODO Auto-generated catch block
			    // e1.printStackTrace();
			}
		    }
		}else if (rdbttCodBar.isSelected()) {
		    for (int row = 0; row <= table.getRowCount() - 1; row++) {
			try {
			    if ((((Integer) (modelotablao.getmed(row)
				    .getCodnac())).toString().startsWith(value))) {
				// this will automatically set the view of the
				// scroll in
				// the location of the value
				table.scrollRectToVisible(table.getCellRect(
					row, 0, true));
				// this will automatically set the focus of the
				// searched/selected row/value
				table.setRowSelectionInterval(row, row);
			    }
			} catch (NumberFormatException e1) {
			    // TODO Auto-generated catch block
			    // e1.printStackTrace();
			}
		    }
		}
		
		

	    }
	});
	
	busqueda.setText(Messages.getString("VProcesoCorte.textField.text")); //$NON-NLS-1$
	busqueda.setBounds(24, 98, 325, 26);
	add(busqueda);
	busqueda.setColumns(10);


	
	if (VLogin.UsuarioLogueado.getRoot()) {

	    btnSeleccionar = new JButton(
		    Messages.getString("VProcesoCorte.SeleccionBtn")); //$NON-NLS-1$
	    btnSeleccionar.setBounds(598, 517, 179, 72);
	    //btnSeleccionar.setVerticalAlignment(SwingConstants.CENTER);
	    adaptajbutton( btnSeleccionar, "/iconos/palante.png");
	    btnSeleccionar.setIconTextGap(1);
	    btnSeleccionar.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnSeleccionar.setVerticalTextPosition(SwingConstants.BOTTOM);
	    add(btnSeleccionar);

	    btnAtras = new JButton(
		    Messages.getString("VProcesoCorte.btnAtras.text")); //$NON-NLS-1$
	    btnAtras.setBounds(377, 517, 179, 72);
	    //btnAtras.setVerticalAlignment(SwingConstants.CENTER);
	    adaptajbutton(btnAtras, "/iconos/patras.png");
	    btnAtras.setIconTextGap(1);
	    btnAtras.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnAtras.setVerticalTextPosition(SwingConstants.BOTTOM);
	    
	    btnAtras.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    // TODO
		    VProcesoCorte.this.setVisible(false);
		    VLogin.vadmin.setVisible(true);
		}
	    });
	   
	    add(btnAtras);
	} else {

	    btnSeleccionar = new JButton(
		    Messages.getString("VProcesoCorte.SeleccionBtn")); //$NON-NLS-1$
	    btnSeleccionar.setBounds(598, 555, 179, 72);
	    adaptajbutton( btnSeleccionar, "/iconos/palante.png");
	    btnSeleccionar.setIconTextGap(1);
	    btnSeleccionar.setHorizontalTextPosition(SwingConstants.CENTER);
	    btnSeleccionar.setVerticalTextPosition(SwingConstants.BOTTOM);
	    add(btnSeleccionar);

	}

	btnSeleccionar.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		// LLAMAR A LA NUEVA VENTANA

		if (medselect != null) {
		    setVisible(false);
		    VMed vmed = new VMed(medselect);
		    vmed.setBounds((Principal.d.width / 2) - 400,
			    (Principal.d.height / 2) - 300, 800, 600);
		    Principal.Panel.add(vmed);
		    vmed.repaint();
		    vmed.validate();
		    vmed.setVisible(true);

		    System.out.println("BOTON seleccionado" + medselect);
		}
	    }
	});

    }

  
    // PARA LA TABLA

    public void valueChanged(ListSelectionEvent e) {

	int filaVista = table.getSelectedRow();

	if (filaVista < 0) {
	    // No hay selección
	    System.out.println(Messages
		    .getString("VProcesoCorte.ErrorNoSelection")); //$NON-NLS-1$
	} else {
	    int filaModelo = table.convertRowIndexToModel(filaVista);
	    medselect = modelotablao.getmed(filaModelo);
	    System.out.println(medselect);
	    this.imagen.setIcon(new ImageIcon(medselect.getRutaimg()));

	}

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
