package packblisters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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

    /**
     * Create the frame.
     */
    public VProcesoCorte() {
	

	// modelolista = new DefaultListModel<Medicamento>();

	consultamedica = new DBFacade();

	modelotablao = new ModeloTablaMeds();
	consultamedica = new DBFacade();
	// rellenar modelo
	consultamedica.getMedicamentos(modelotablao);
	
	setLayout(null);

	JButton btnSeleccionar = new JButton(
		Messages.getString("VProcesoCorte.SeleccionBtn")); //$NON-NLS-1$
	btnSeleccionar.setBounds(179, 337, 139, 39);
	add(btnSeleccionar);

	JScrollPane tablapane = new JScrollPane();
	tablapane.setBounds(12, 43, 306, 259);
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

	JLabel lblNewLabel = new JLabel(
		Messages.getString("VProcesoCorte.Medicamentos"));
	lblNewLabel.setBounds(12, 27, 103, 15);
	add(lblNewLabel);

	imagen = new JLabel("");
	imagen.setBounds(349, 43, 139, 259);
	add(imagen);
	
	
	btnSeleccionar.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent arg0) {

		// LLAMAR A LA NUEVA VENTANA

		setVisible(false);
		VMed vmed = new VMed(medselect);
		vmed.setBounds((Principal.d.width/2)-400, (Principal.d.height/2)-300, 800, 600);
		Principal.Panel.add(vmed);
		vmed.repaint();
		vmed.validate();
		vmed.setVisible(true);

		

		
		System.out.println("BOTON seleccionado" + medselect);
	    }
	});

	if (VLogin.UsuarioLogueado.getRoot()) {
	    btnAtras = new JButton(
		    Messages.getString("VProcesoCorte.btnAtras.text")); //$NON-NLS-1$
	    btnAtras.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    // TODO
		    VProcesoCorte.this.setVisible(false);
		    VLogin.vadmin.setVisible(true);
		}
	    });
	    btnAtras.setBounds(24, 337, 132, 39);
	    add(btnAtras);
	}
    }

    // PARA LA LISTA Jlist
    // @Override
    // public void valueChanged(ListSelectionEvent arg0)
    // {
    // // TODO AKI HAY QUE HACER CUANDO SE SELACCIONA DE LA LISTA
    //
    // JList<Medicamento> lista = (JList<Medicamento>) arg0.getSource();
    //
    // if (lista.isSelectionEmpty())
    // {
    // System.out.println("NADA");
    // }
    // else
    // {
    // int min= lista.getMinSelectionIndex();
    // int max = lista.getMaxSelectionIndex();
    // System.out.println(max +","+min);
    //
    // for (int i= min; i<=max;i++)
    // {
    // if (lista.isSelectedIndex(i))
    // {
    // System.out.println("El medicamento seleccionado es : " +i);
    // medselect = lista.getSelectedValue();
    // //CARGAR LA IMAGEN
    // this.imagen.setIcon(new ImageIcon(medselect.getRutaimg()));
    //
    // }
    // }
    //
    // }
    // }

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
}
