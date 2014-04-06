package packblisters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.LineBorder;

public class VTablaHistorico extends JPanel implements ListSelectionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JTable table;
    private ModeloTablaHistorico modelotablao;
    private Historico histselect;
    private DBFacade consultahist;
   

    /**
     * Create the frame.
     */
    public VTablaHistorico() {
	
	setBackground(new Color(224, 255, 255));
    	setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142, 35)));
    
	JLabel histlb = new JLabel(
		Messages.getString("VTablaHistorico.Historico")); //$NON-NLS-1$
	histlb.setForeground(new Color(107, 142, 35));
	histlb.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 14));
	histlb.setBounds(14, 21, 149, 15);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(107, 142, 35)));
	scrollPane.setBounds(18, 38, 714, 380);
	setLayout(null);
	add(histlb);
	add(scrollPane);

	modelotablao = new ModeloTablaHistorico();
	//modelotablao.addTableModelListener(this);
	consultahist = new DBFacade();
	// rellenar modelo
	consultahist.getHistorico(modelotablao);

	table = new JTable(modelotablao);
	table.setBounds(0, 0, 1, 1);
	// contentPane.add(table);

	// Instanciamos el TableRowSorter y lo añadimos al JTable
	TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(
	table.getModel());
	table.setRowSorter(elQueOrdena);
	elQueOrdena.setSortsOnUpdates(true);

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	ListSelectionModel selectionModel = table.getSelectionModel();
	selectionModel.addListSelectionListener(this);
	// table.setSelectionModel(selectionModel);
	scrollPane.setViewportView(table);
	
	
	 //Fuerzo a varias columnas a tener un ancho determinado
	 TableColumn columnafecha = table.getColumn("Fecha y hora");
	 TableColumn columnausuario = table.getColumn("Usuario");
	 columnausuario.setPreferredWidth(50);
	 columnafecha.setPreferredWidth(120);


	JButton btnAtras = new JButton(
		Messages.getString("VTablaHistorico.btnAtras")); //$NON-NLS-1$
	btnAtras.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		VTablaHistorico.this.setVisible(false);
		VLogin.vadmin.setVisible(true);
	    }
	});
	
	btnAtras.setBounds(290, 426, 149, 59);
	    adaptajbutton(btnAtras, "/iconos/patras.png");
	    btnAtras.setIconTextGap(1);
	    btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
	    btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
	add(btnAtras);	
	
    }

    public void valueChanged(ListSelectionEvent e) {
	System.out.println("VTABLAHISTORICO entrando en valuechanged");

	int filaVista = table.getSelectedRow();

	if (filaVista < 0) {
	    // No hay selección
	    System.out.println(Messages
		    .getString("VTablaHistorico.ErrorNoSelection")); //$NON-NLS-1$
	} else {
	    int filaModelo = table.convertRowIndexToModel(filaVista);
	    histselect = modelotablao.getHistorico(filaModelo);
	    System.out.println("valueChanged VTablaMeds: " + histselect);

	}
	System.out.println("VTABLAHISTORICO saliendo de valuechanged");
    }


    public void adaptajbutton (JButton but, String ruta){      
        ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
 		//Icon icono = new ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(), lbllogo.getHeight(), Image.SCALE_DEFAULT));
 		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(but.getWidth()/2, (int) (but.getHeight()), Image.SCALE_SMOOTH)));
 		//this.repaint();
 	       
 	   }
    
    
}

