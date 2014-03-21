package packblisters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JLabel;

import java.awt.Dialog;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import javax.swing.JRadioButton;
import javax.swing.JTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;


public class VTablaMedicamentos extends JPanel implements ListSelectionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTable table;
    private ModeloTablaMeds modelotablao = new ModeloTablaMeds();
    private Medicamento medselect ;
    private DBFacade consultameds = new DBFacade();
    private JRadioButton rdbtnNombre;
    private JRadioButton rdbtnCodigoNacinal;
    private JRadioButton rdbttCodBar;
    private JTextField busqueda;
    private JLabel imagen;
  
    /**
     * Create the frame.
     */
    public VTablaMedicamentos() {
	
	setBackground(new Color(224, 255, 255));
    	setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));

	JLabel medslb = new JLabel(
		Messages.getString("VTablaMedicamentos.Medicamentos")); //$NON-NLS-1$
	medslb.setForeground(new Color(107, 142, 35));
	medslb.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
	medslb.setBounds(30, 128, 130, 15);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(28, 147, 647, 373);
	setLayout(null);
	add(medslb);
	add(scrollPane);

	//modelotablao = new ModeloTablaMeds();
	//consultameds = new DBFacade();
	// rellenar modelo
	consultameds.getMedicamentos(modelotablao);

	table = new JTable(modelotablao);
	table.setBounds(0, 0, 1, 1);
	// contentPane.add(table);

	// Instanciamos el TableRowSorter y lo añadimos al JTable
	TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(
		table.getModel());
	table.setRowSorter(elQueOrdena);
	elQueOrdena.setSortsOnUpdates(true);

	// table.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null,
	// null, null));

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	ListSelectionModel selectionModel = table.getSelectionModel();
	selectionModel.addListSelectionListener(this);
	// table.setSelectionModel(selectionModel);
	scrollPane.setViewportView(table);

	JButton btnNuevoMed = new JButton();
		//Messages.getString("VTablaMedicamentos.NuevoMedicamento")); //$NON-NLS-1$
	btnNuevoMed.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		
		
		NuevoMed nuevomed = new NuevoMed(modelotablao);
		nuevomed.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		nuevomed.setVisible(true);
	    }
	});
	btnNuevoMed.setBounds(30, 531, 142, 59);
	btnNuevoMed.setToolTipText("Crear nuevo medicamento");
	adaptajbuttonabajo(btnNuevoMed, "/iconos/addition.png");
	btnNuevoMed.setIconTextGap(1);
//	btnNuevoMed.setForeground(Color.BLACK);
//	btnNuevoMed.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
	btnNuevoMed.setHorizontalTextPosition(SwingConstants.CENTER);
	btnNuevoMed.setVerticalTextPosition(SwingConstants.BOTTOM);
	
	add(btnNuevoMed);

	JButton btnModificarMed = new JButton();
	btnModificarMed.setToolTipText("Modificar medicamento");
		//Messages.getString("VTablaMedicamentos.ModificarMedicamento")); //$NON-NLS-1$
	btnModificarMed.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO
	    }
	});
	btnModificarMed.setBounds(188, 531, 165, 59);
	
	 adaptajbuttonabajo(btnModificarMed, "/iconos/edition.png");
	 btnModificarMed.setIconTextGap(1);
//	 btnModificarMed.setForeground(Color.BLACK);
//	 btnModificarMed.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
	 btnModificarMed.setHorizontalTextPosition(SwingConstants.CENTER);
	 btnModificarMed.setVerticalTextPosition(SwingConstants.BOTTOM);
	
	add(btnModificarMed);

	JButton btnBorrarMed = new JButton();
		//Messages.getString("VTablaMedicamentos.BorrarMedicamento")); //$NON-NLS-1$
	btnBorrarMed.setToolTipText("Borrar medicamento");
	btnBorrarMed.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO
	    }
	});
	btnBorrarMed.setBounds(365, 531, 149, 59);
	 adaptajbuttonabajo(btnBorrarMed, "/iconos/deletion.png");
	 btnBorrarMed.setIconTextGap(1);
//	 btnBorrarMed.setForeground(Color.BLACK);
//		btnBorrarMed.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 24));
	 btnBorrarMed.setHorizontalTextPosition(SwingConstants.CENTER);
	 btnBorrarMed.setVerticalTextPosition(SwingConstants.BOTTOM);
	
	add(btnBorrarMed);

	JButton btnAtras = new JButton( Messages.getString("VTablaMedicamentos.btnAtras")); //$NON-NLS-1$
	btnAtras.setBounds(526, 531, 149, 59);
	btnAtras.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		VTablaMedicamentos.this.setVisible(false);
		VLogin.vadmin.setVisible(true);
	    }
	});
	 	   
	    //btnAtras.setVerticalAlignment(SwingConstants.CENTER);
	    adaptajbutton(btnAtras, "/iconos/patras.png");
	    btnAtras.setIconTextGap(1);
	    btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
	    btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
	
	add(btnAtras);
	
	Box horizontalBox = Box.createHorizontalBox();
	horizontalBox.setBorder(new LineBorder(new Color(107, 142, 35), 2, true));
	horizontalBox.setBackground(new Color(154, 205, 50));
	horizontalBox.setBounds(74, 16, 356, 92);
	add(horizontalBox);
	
	JLabel label = new JLabel("Búsqueda");
	label.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
	label.setBounds(90, 26, 133, 15);
	add(label);
	
	rdbtnCodigoNacinal = new JRadioButton(
		Messages.getString("VProcesoCorte.rdbtnCodigoNacinal.text")); //$NON-NLS-1$
	rdbtnCodigoNacinal.setBackground(new Color(224, 255, 255));
	rdbtnCodigoNacinal .setBounds(162, 43, 121, 23);
	rdbtnCodigoNacinal.setSelected(false);
	rdbtnCodigoNacinal.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnNombre.setSelected(false);
		rdbttCodBar.setSelected(false);
		// busqueda.setText(null);

	    }
	});
	
	
	add(rdbtnCodigoNacinal );
	
	rdbttCodBar = new JRadioButton("Cod de barras");
	rdbttCodBar.setSelected(false);
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
	rdbttCodBar.setBounds(287, 43, 129, 23);
	add(rdbttCodBar);


	
	rdbtnNombre = new JRadioButton(
		Messages.getString("VProcesoCorte.rdbtnNombre.text")); //$NON-NLS-1$
	rdbtnNombre.setBackground(new Color(224, 255, 255));
	rdbtnNombre.setBounds(82, 43, 81, 23);
	rdbtnNombre.setSelected(true);
	rdbtnNombre.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
		rdbtnCodigoNacinal.setSelected(false);
		rdbttCodBar.setSelected(false);

	    }
	});
	add(rdbtnNombre);
	
	
	
	busqueda = new JTextField();
	busqueda.setText("Medicamento a buscar");
	
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

	busqueda.setColumns(10);
	busqueda.setBounds(89, 69, 325, 26);
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
	add(busqueda);
	
	imagen = new JLabel("");
	imagen.setBorder(new LineBorder(new Color(107, 142, 35), 2, true));
	imagen.setBounds(519, 12, 109, 98);
	add(imagen);
	
	
	
	
    }

    public void valueChanged(ListSelectionEvent e) {

	int filaVista = table.getSelectedRow();

	if (filaVista < 0) {
	    // No hay selección
	    System.out.println(Messages
		    .getString("VTablaMedicamentos.ErrorNoSelection")); //$NON-NLS-1$
	} else {
	    int filaModelo = table.convertRowIndexToModel(filaVista);
	    medselect = modelotablao.getmed(filaModelo);
	    System.out.println("valueChanged VTablaMeds: " + medselect);
	    ImageIcon fot = new ImageIcon(medselect.getRutaimg());
	    //this.imagen.setIcon(new ImageIcon(medselect.getRutaimg()));
	    this.imagen.setIcon(new ImageIcon(fot.getImage().getScaledInstance(this.imagen.getWidth(), this.imagen.getHeight(), Image.SCALE_SMOOTH)));


	}

    }
    
    
    public void adaptajbutton (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()/2, (int) (but.getHeight()), Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
    
    public void adaptajbuttonabajo (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()-6, but.getHeight()-6, Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
 }
    

