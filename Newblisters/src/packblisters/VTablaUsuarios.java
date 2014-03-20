package packblisters;

import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    private JLabel usuarioslb;
    private JScrollPane scrollPane;
    private JButton btnNuevoUsuario;
    private JButton btnModUsuario;
    private JButton btnBorrarUsuario;
    private JButton btnAtras;
    private ModeloTablaUsr modelotablao;
    private Usuario usrselected;
    private DBFacade dbFacade;

    /**
     * Create the frame.
     */
    public VTablaUsuarios() {    	
	
	usuarioslb = new JLabel(Messages.getString("VTablaUsuarios.Usuarios")); //$NON-NLS-1$
	usuarioslb.setBounds(15, 12, 440, 15);
	
	scrollPane = new JScrollPane();
	scrollPane.setBounds(15, 32, 474, 349);
	setLayout(null);
	add(usuarioslb);
	add(scrollPane);
	
	modelotablao = new ModeloTablaUsr();
	modelotablao.addTableModelListener(this);
	dbFacade = new DBFacade();
	// rellenar modelo si está vacío
	dbFacade.getUsuarios(modelotablao);

	table = new JTable(modelotablao);
	table.setBounds(0, 0, 1, 1);
	
	// Instanciamos el TableRowSorter y lo añadimos al JTable
	TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(
		table.getModel());
	table.setRowSorter(elQueOrdena);
	elQueOrdena.setSortsOnUpdates(true);

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	ListSelectionModel selectionModel = table.getSelectionModel();
	selectionModel.addListSelectionListener(this);
	scrollPane.setViewportView(table);
	
	btnNuevoUsuario = new JButton(Messages.getString("VTablaUsuarios.NuevoUsuario")); //$NON-NLS-1$
	btnNuevoUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    VNuevoUsr nuevousr = new VNuevoUsr(modelotablao);
			nuevousr.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			nuevousr.setVisible(true);
		}
	});	
	btnNuevoUsuario.setBounds(33, 413, 106, 25);
	add(btnNuevoUsuario);
	
	btnModUsuario = new JButton(Messages.getString("VTablaUsuarios.ModificarUsuario")); //$NON-NLS-1$
	btnModUsuario.setEnabled(false);
	btnModUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel();
			JLabel lblPass = new JLabel("Nueva contraseña:");
			JPasswordField pass = new JPasswordField(5);
			JLabel lblRoot = new JLabel("Es Administrador:");
			JCheckBox root= new JCheckBox(null, null, usrselected.getRoot());
			panel.add(lblPass);
			panel.add(pass);
			panel.add(lblRoot);
			panel.add(root);
			String[] options = new String[]{"OK", "Cancelar"};
			int option = JOptionPane.showOptionDialog(null, panel, "Modificar a "+usrselected.getNombre(),
			                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
			                         null, options, options[1]);
			if(option == 0) // pressing OK button
			{
				usrselected.setRoot(root.isSelected());
			    dbFacade.modificarUsr(usrselected,pass.getPassword());//rellena el id de ususario
			}
			
		}
	});
	btnModUsuario.setBounds(164, 413, 162, 25);
	add(btnModUsuario);
	
	btnBorrarUsuario = new JButton(Messages.getString("VTablaUsuarios.BorrarUsuario")); //$NON-NLS-1$
	btnBorrarUsuario.setEnabled(false);
	btnBorrarUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  //TODO
			String[] options = new String[]{"OK", "Cancelar"};
			int option = JOptionPane.showOptionDialog(null, "Borrar a "+usrselected.getNombre(),"Borrar usuario",
			                         JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE,
			                         null, options, options[1]);
			if(option == 0) // pressing OK button
			{				
				dbFacade.borrarUsr(usrselected.getNombre());
				int filaVista = table.getSelectedRow();
				int filaModelo = table.convertRowIndexToModel(filaVista);
				modelotablao.borraUsr(filaModelo);
			}
		}
	});
	btnBorrarUsuario.setBounds(349, 413, 106, 25);
	add(btnBorrarUsuario);
	
	btnAtras = new JButton(Messages.getString("VTablaUsuarios.btnAtras")); //$NON-NLS-1$
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
   		btnBorrarUsuario.setEnabled(false);
   		btnModUsuario.setEnabled(false);
   	    // No hay selección
   	    System.out.println(Messages.getString("VTablaUsuarios.ErrorNoSelection")); //$NON-NLS-1$
   	} else {
   		btnBorrarUsuario.setEnabled(true);
   		btnModUsuario.setEnabled(true);
   	    int filaModelo = table.convertRowIndexToModel(filaVista);
   	    usrselected = modelotablao.getUsr(filaModelo);
   	    System.out.println("valueChanged VTablaUsuarios: " +usrselected);
   	   
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
