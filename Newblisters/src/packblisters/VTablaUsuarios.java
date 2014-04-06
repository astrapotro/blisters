package packblisters;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
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
	
	setBackground(new Color(224, 255, 255));
    	setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));
    	setLayout(null);
    	
	usuarioslb = new JLabel(Messages.getString("VTablaUsuarios.Usuarios")); //$NON-NLS-1$
	usuarioslb.setBounds(18, 16, 440, 15);
	usuarioslb.setForeground(new Color(107, 142, 35));
	usuarioslb.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
	
	scrollPane = new JScrollPane();
	scrollPane.setBounds(22, 32, 485, 200);
	scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(107, 142, 35)));
	
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
	
	btnNuevoUsuario = new JButton(); //$NON-NLS-1$
	btnNuevoUsuario.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    VNuevoUsr nuevousr = new VNuevoUsr(modelotablao);
			nuevousr.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			nuevousr.setVisible(true);
		}
	});	
	btnNuevoUsuario.setBounds(197, 244, 97, 77);
	btnNuevoUsuario.setToolTipText("Crear nuevo usuario");
	adaptajbuttonabajo(btnNuevoUsuario, "/iconos/adition-user.png");
	add(btnNuevoUsuario);
	
	btnModUsuario = new JButton(); //$NON-NLS-1$
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
	btnModUsuario.setBounds(306, 243, 97, 78);
	btnModUsuario.setToolTipText("Modificar usuario");
	adaptajbuttonabajo(btnModUsuario, "/iconos/edition-user.png");
	add(btnModUsuario);
	
	
	btnBorrarUsuario = new JButton(); //$NON-NLS-1$
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
	btnBorrarUsuario.setBounds(415, 243, 92, 78);
	btnBorrarUsuario.setToolTipText("Borrar usuario");
	adaptajbuttonabajo(btnBorrarUsuario, "/iconos/deletion-user.png");
	add(btnBorrarUsuario);
	
	btnAtras = new JButton(Messages.getString("VTablaUsuarios.btnAtras")); //$NON-NLS-1$
	btnAtras.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    VTablaUsuarios.this.setVisible(false);
		    VLogin.vadmin.setVisible(true);
		}
	});
	btnAtras.setBounds(24, 243, 161, 79);
	adaptajbutton(btnAtras, "/iconos/patras.png");
        btnAtras.setIconTextGap(1);
	btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
	btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
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
