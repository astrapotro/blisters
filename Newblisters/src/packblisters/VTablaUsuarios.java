package packblisters;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VTablaUsuarios extends JPanel implements ListSelectionListener, TableModelListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JTable table;
    private ModeloTablaUsr modelotablao;
    private Usuario usrselect,aux;
    private DBFacade consultausuarios;
    

    /**
     * Create the frame.
     */
    public VTablaUsuarios() {
    	
	
	JLabel usuarioslb = new JLabel(Messages.getString("VTablaUsuarios.Usuarios")); //$NON-NLS-1$
	usuarioslb.setBounds(15, 12, 440, 15);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(15, 32, 474, 349);
	setLayout(null);
	add(usuarioslb);
	add(scrollPane);
	
	//table = new JTable();
	
	
	modelotablao = new ModeloTablaUsr();
	modelotablao.addTableModelListener(this);
	consultausuarios = new DBFacade();
	// rellenar modelo si está vacío
	consultausuarios.getUsuarios(modelotablao);

	table = new JTable(modelotablao);
	table.setBounds(0, 0, 1, 1);
	//contentPane.add(table);
	
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
	
	JButton btnNuevoUsuario = new JButton(Messages.getString("VTablaUsuarios.NuevoUsuario")); //$NON-NLS-1$
	btnNuevoUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    //TODO
		    //Meter una nueva fila en la tabla
		    //modelotablao.fireTableRowsInserted(modelotablao.getRowCount()+1,modelotablao.getRowCount()+1);
		    //table.addRowSelectionInterval(modelotablao.getRowCount(),modelotablao.getRowCount());
		    //modelotablao.anadeUsr(null);
		     aux = new Usuario();
		    modelotablao.anadeUsr(aux);
		    
		}
	});
	btnNuevoUsuario.setBounds(33, 413, 106, 25);
	add(btnNuevoUsuario);
	
	JButton btnGuardarCambios = new JButton(Messages.getString("VTablaUsuarios.ModificarUsuario")); //$NON-NLS-1$
	btnGuardarCambios.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  //TODO
		  
		
		}
	});
	btnGuardarCambios.setBounds(164, 413, 162, 25);
	add(btnGuardarCambios);
	
	JButton btnBorrarUsuario = new JButton(Messages.getString("VTablaUsuarios.BorrarUsuario")); //$NON-NLS-1$
	btnBorrarUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  //TODO
		}
	});
	btnBorrarUsuario.setBounds(349, 413, 106, 25);
	add(btnBorrarUsuario);
	
	JButton btnAtras = new JButton(Messages.getString("VTablaUsuarios.btnAtras")); //$NON-NLS-1$
	btnAtras.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    VTablaUsuarios.this.setVisible(false);
		    VLogin.vadmin.setVisible(true);
		}
	});
	btnAtras.setBounds(183, 463, 129, 25);
	add(btnAtras);
    }
    
    
    public void valueChanged(ListSelectionEvent e) {

   	int filaVista = table.getSelectedRow();

   	if (filaVista < 0) {
   	    // No hay selección
   	    System.out.println(Messages.getString("VTablaUsuarios.ErrorNoSelection")); //$NON-NLS-1$
   	} else {
   	    int filaModelo = table.convertRowIndexToModel(filaVista);
   	    usrselect = modelotablao.getUsr(filaModelo);
   	    System.out.println("valueChanged VTablaUsuarios: " +usrselect);
   	   
   	}

       }


    @Override
    public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	//modelotablao.fireTableDataChanged();
	//table.updateUI();
	System.out.println("Tabla cambiada");
	
    }
}
