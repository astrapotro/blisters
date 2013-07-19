package packblisters;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class VTablaMedicamentos extends JPanel implements ListSelectionListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTable table;
    private ModeloTablaMeds modelotablao;
    private Medicamento medselect;
    private DBFacade consultameds;

    /**
     * Create the frame.
     */
    public VTablaMedicamentos() {

	JLabel medslb = new JLabel(
		Messages.getString("VTablaMedicamentos.Medicamentos")); //$NON-NLS-1$
	medslb.setBounds(15, 12, 440, 15);

	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(15, 32, 473, 333);
	setLayout(null);
	add(medslb);
	add(scrollPane);

	modelotablao = new ModeloTablaMeds();
	consultameds = new DBFacade();
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

	JButton btnNuevoMed = new JButton(
		Messages.getString("VTablaMedicamentos.NuevoMedicamento")); //$NON-NLS-1$
	btnNuevoMed.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO
		
		NuevoMed nuevomed = new NuevoMed();
		nuevomed.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		nuevomed.setVisible(true);
	    }
	});
	btnNuevoMed.setBounds(22, 395, 117, 25);
	add(btnNuevoMed);

	JButton btnModificarMed = new JButton(
		Messages.getString("VTablaMedicamentos.ModificarMedicamento")); //$NON-NLS-1$
	btnModificarMed.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO
	    }
	});
	btnModificarMed.setBounds(193, 395, 129, 25);
	add(btnModificarMed);

	JButton btnBorrarMed = new JButton(
		Messages.getString("VTablaMedicamentos.BorrarMedicamento")); //$NON-NLS-1$
	btnBorrarMed.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// TODO
	    }
	});
	btnBorrarMed.setBounds(371, 395, 117, 25);
	add(btnBorrarMed);

	JButton btnAtras = new JButton(
		Messages.getString("VTablaMedicamentos.btnAtras")); //$NON-NLS-1$
	btnAtras.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		VTablaMedicamentos.this.setVisible(false);
		VLogin.vadmin.setVisible(true);
	    }
	});
	btnAtras.setBounds(193, 453, 129, 25);
	add(btnAtras);
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

	}

    }
}
