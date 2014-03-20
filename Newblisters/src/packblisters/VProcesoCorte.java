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
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.Box;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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
	tablapane.setBounds(13, 162, 356, 428);
	add(tablapane);
	tablapane
		.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
	tablapane.setViewportBorder(null);
	tablapane
		.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	table = new JTable(modelotablao);
	table.setBorder(null);
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
	    lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 25));
	    lblNewLabel_2.setForeground(new Color(107, 142, 35));
	    lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
	    lblNewLabel_2.setBounds(17, 7, 783, 37);
	    add(lblNewLabel_2);

	imagen = new JLabel("");
	imagen.setBorder(new LineBorder(new Color(107, 142, 35), 3, true));
	imagen.setBounds(377, 52, 411, 458);
	add(imagen);

	JLabel lblNewLabel_1 = new JLabel(
		Messages.getString("VProcesoCorte.lblNewLabel_1.text_1")); //$NON-NLS-1$
	lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
	lblNewLabel_1.setBounds(31, 61, 133, 15);
	add(lblNewLabel_1);

	busqueda = new JTextField();
	busqueda.addFocusListener(new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
		    busqueda.setText("");
		}
		@Override
		public void focusLost(FocusEvent e) {
		    busqueda.setText("Medicamento a buscar");
		}
	});

	rdbtnNombre = new JRadioButton(
		Messages.getString("VProcesoCorte.rdbtnNombre.text")); //$NON-NLS-1$
	rdbtnNombre.setBackground(new Color(224, 255, 255));
	rdbtnNombre.setSelected(true);
	rdbtnNombre.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnCodigoNacinal.setSelected(false);
		rdbttCodBar.setSelected(false);

	    }
	});
	
	Box verticalBox = Box.createVerticalBox();
	verticalBox.setBounds(0, 0, 1, 1);
	add(verticalBox);
	rdbtnNombre.setBounds(25, 75, 87, 23);
	add(rdbtnNombre);

	rdbtnCodigoNacinal = new JRadioButton(
		Messages.getString("VProcesoCorte.rdbtnCodigoNacinal.text")); //$NON-NLS-1$
	rdbtnCodigoNacinal.setBackground(new Color(224, 255, 255));
	rdbtnCodigoNacinal.setSelected(false);
	rdbtnCodigoNacinal.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnNombre.setSelected(false);
		rdbttCodBar.setSelected(false);
		// busqueda.setText(null);

	    }
	});
	rdbtnCodigoNacinal.setBounds(108, 75, 121, 23);
	add(rdbtnCodigoNacinal);

	
	rdbttCodBar = new JRadioButton(Messages.getString("VProcesoCorte.rdbttCodBar.text"));
	rdbttCodBar.setBackground(new Color(224, 255, 255));
	rdbttCodBar.setSelected(false);
	rdbttCodBar.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnCodigoNacinal.setSelected(false);
		rdbtnNombre.setSelected(false);
		// busqueda.setText(null);

	    }
	});
	rdbttCodBar.setBounds(227, 75, 129, 23);
	add(rdbttCodBar);

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
			    if ((((Long) (modelotablao.getmed(row)
				    .getCodbar())).toString().startsWith(value))) {
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
	busqueda.setBounds(28, 102, 325, 26);
	add(busqueda);
	busqueda.setColumns(10);
	
	Box horizontalBox = Box.createHorizontalBox();
	horizontalBox.setBorder(new LineBorder(new Color(107, 142, 35), 2, true));
	horizontalBox.setBackground(new Color(154, 205, 50));
	horizontalBox.setBounds(13, 53, 356, 85);
	add(horizontalBox);
	
	
	JLabel lblNewLabel = new JLabel(
		Messages.getString("VProcesoCorte.Medicamentos"));
	lblNewLabel.setForeground(new Color(107, 142, 35));
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setBounds(12, 143, 121, 18);
	
	//lblNewLabel.setBorder(new LineBorder(new Color(107, 142, 35), 2, true));
	
	add(lblNewLabel);
	lblNewLabel.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 13));


	
	if (VLogin.UsuarioLogueado.getRoot()) {

	    btnSeleccionar = new JButton(
		    Messages.getString("VProcesoCorte.SeleccionBtn")); //$NON-NLS-1$
	
	    btnSeleccionar.setBounds(595, 517, 190, 72);
	    //btnSeleccionar.setVerticalAlignment(SwingConstants.CENTER);
	    adaptajbutton( btnSeleccionar, "/iconos/palante.png");
	    btnSeleccionar.setIconTextGap(1);
	    btnSeleccionar.setHorizontalTextPosition(SwingConstants.LEFT);
	    btnSeleccionar.setVerticalTextPosition(SwingConstants.CENTER);
	    add(btnSeleccionar);

	    btnAtras = new JButton(
		    Messages.getString("VProcesoCorte.btnAtras.text")); //$NON-NLS-1$
	    btnAtras.setBounds(377, 517, 190, 72);
	    //btnAtras.setVerticalAlignment(SwingConstants.CENTER);
	    adaptajbutton(btnAtras, "/iconos/patras.png");
	    btnAtras.setIconTextGap(1);
	    btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
	    btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
	    
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
	    btnSeleccionar.setBounds(500, 517, 190, 72);
	    adaptajbutton( btnSeleccionar, "/iconos/palante.png");
	    btnSeleccionar.setIconTextGap(1);
	    btnSeleccionar.setHorizontalTextPosition(SwingConstants.RIGHT);
	    btnSeleccionar.setVerticalTextPosition(SwingConstants.CENTER);
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
	    ImageIcon fot = new ImageIcon(medselect.getRutaimg());
	    //this.imagen.setIcon(new ImageIcon(medselect.getRutaimg()));
	    this.imagen.setIcon(new ImageIcon(fot.getImage().getScaledInstance(this.imagen.getWidth(), this.imagen.getHeight(), Image.SCALE_SMOOTH)));

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
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()/2, (int) (but.getHeight()), Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
}
