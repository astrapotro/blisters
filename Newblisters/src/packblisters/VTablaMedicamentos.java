package packblisters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JLabel;

import java.awt.Dialog;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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

	private static final long serialVersionUID = 1L;
	private JTable table;
	private ModeloTablaMeds modelotablao = new ModeloTablaMeds();
	private Medicamento medselect;
	private DBFacade consultameds = new DBFacade();
	private JRadioButton rdbtnNombre;
	private JRadioButton rdbtnCodigoNacinal;
	private JRadioButton rdbttCodBar;
	private JTextField busqueda;
	private JLabel imagen;

	private JButton btnAtras;

	private JButton btnNuevoMed;
	private JButton btnModificarMed;
	private JButton btnBorrarMed;

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
		add(medslb);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 147, 647, 347);
		scrollPane.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(107,
				142, 35)));
		setLayout(null);
		add(scrollPane);

		// Busqueda
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox
				.setBorder(new LineBorder(new Color(107, 142, 35), 2, true));
		horizontalBox.setBackground(new Color(154, 205, 50));
		horizontalBox.setBounds(74, 16, 356, 92);
		add(horizontalBox);

		JLabel label = new JLabel("Búsqueda");
		label.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(90, 26, 133, 15);
		add(label);

		rdbtnNombre = new JRadioButton(
				Messages.getString("VProcesoCorte.rdbtnNombre.text")); //$NON-NLS-1$
		rdbtnNombre.setMnemonic('o');
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

		rdbtnCodigoNacinal = new JRadioButton(
				Messages.getString("VProcesoCorte.rdbtnCodigoNacinal.text")); //$NON-NLS-1$
		rdbtnCodigoNacinal.setMnemonic('c');
		rdbtnCodigoNacinal.setBackground(new Color(224, 255, 255));
		rdbtnCodigoNacinal.setBounds(162, 43, 121, 23);
		rdbtnCodigoNacinal.setSelected(false);
		rdbtnCodigoNacinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnNombre.setSelected(false);
				rdbttCodBar.setSelected(false);
				// busqueda.setText(null);

			}
		});
		add(rdbtnCodigoNacinal);

		rdbttCodBar = new JRadioButton("Cod de barras");
		rdbttCodBar.setMnemonic('b');
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

		busqueda = new JTextField();
		busqueda.setText("Medicamento a buscar");
		busqueda.setColumns(10);
		busqueda.setBounds(89, 69, 325, 26);
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
				} else if (rdbttCodBar.isSelected()) {
					for (int row = 0; row <= table.getRowCount() - 1; row++) {
						try {
							if ((((Long) (modelotablao.getmed(row).getCodbar()))
									.toString().startsWith(value))) {
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

		// rellenar modelo
		consultameds.getMedicamentos(modelotablao);

		table = new JTable(modelotablao);
		table.setBounds(0, 0, 1, 1);

		// Instanciamos el ordenador de filas de tabla TableRowSorter y lo
		// añadimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(
				table.getModel());
		table.setRowSorter(elQueOrdena);
		elQueOrdena.setSortsOnUpdates(true);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(this);
		scrollPane.setViewportView(table);

		btnNuevoMed = new JButton();
		btnNuevoMed.setToolTipText(Messages
				.getString("VTablaMedicamentos.NuevoMedicamento")); //$NON-NLS-1$
		btnNuevoMed.setMnemonic('n');
		btnNuevoMed.setBounds(236, 506, 130, 84);
		btnNuevoMed.setIconTextGap(1);
		btnNuevoMed.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNuevoMed.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNuevoMed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VNuevoMed nuevomed = new VNuevoMed(modelotablao);
				nuevomed.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				nuevomed.setVisible(true);
			}
		});
		adaptajbuttonabajo(btnNuevoMed, "/iconos/addition.png");

		add(btnNuevoMed);

		btnModificarMed = new JButton();
		btnModificarMed.setToolTipText(Messages
				.getString("VTablaMedicamentos.ModificarMedicamento")); //$NON-NLS-1$
		btnModificarMed.setMnemonic('m');
		btnModificarMed.setEnabled(false);
		btnModificarMed.setBounds(392, 506, 130, 84);
		btnModificarMed.setIconTextGap(1);
		btnModificarMed.setHorizontalTextPosition(SwingConstants.CENTER);
		btnModificarMed.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnModificarMed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VModMed vmodmed = new VModMed(modelotablao, medselect);
				vmodmed.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				vmodmed.setVisible(true);
			}
		});
		adaptajbuttonabajo(btnModificarMed, "/iconos/edition.png");
		add(btnModificarMed);

		btnBorrarMed = new JButton();
		btnBorrarMed.setToolTipText(Messages
				.getString("VTablaMedicamentos.BorrarMedicamento")); //$NON-NLS-1$
		btnBorrarMed.setMnemonic('b');
		btnBorrarMed.setEnabled(false);
		btnBorrarMed.setBounds(545, 506, 130, 84);
		btnBorrarMed.setIconTextGap(1);
		btnBorrarMed.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBorrarMed.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBorrarMed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = new String[] { "OK", "Cancelar" };
				int option = JOptionPane.showOptionDialog(null, "Borrar "
						+ medselect.getNombre(), "Borrar medicamento",
						JOptionPane.NO_OPTION, JOptionPane.WARNING_MESSAGE,
						null, options, options[1]);
				if (option == 0) // pressing OK button
				{
					consultameds.borrarMed(medselect.getNombre());
					int filaVista = table.getSelectedRow();
					int filaModelo = table.convertRowIndexToModel(filaVista);
					modelotablao.borramed(filaModelo);
				}
			}
		});
		adaptajbuttonabajo(btnBorrarMed, "/iconos/deletion.png");
		add(btnBorrarMed);

		btnAtras = new JButton();
		btnAtras.setText(Messages.getString("VTablaMedicamentos.btnAtras.text")); //$NON-NLS-1$
		//btnAtras.setToolTipText(Messages.getString("VTablaMedicamentos.btnAtras")); //$NON-NLS-1$
		btnAtras.setMnemonic('a');
		btnAtras.setBounds(26, 506, 184, 84);
		btnAtras.setIconTextGap(1);
		btnAtras.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnAtras.setVerticalTextPosition(SwingConstants.CENTER);
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VTablaMedicamentos.this.setVisible(false);
				VLogin.vadmin.setVisible(true);
			}
		});
		adaptajbutton(btnAtras, "/iconos/patras.png");
		add(btnAtras);
	}

	public void valueChanged(ListSelectionEvent e) {

		int filaVista = table.getSelectedRow();

		if (filaVista < 0) {
			btnBorrarMed.setEnabled(false);
			btnModificarMed.setEnabled(false);
			medselect = null;
			this.imagen.setIcon(null);
			// No hay selección
			System.out.println(Messages
					.getString("VTablaMedicamentos.ErrorNoSelection")); //$NON-NLS-1$
		} else {
			btnBorrarMed.setEnabled(true);
			btnModificarMed.setEnabled(true);

			int filaModelo = table.convertRowIndexToModel(filaVista);
			medselect = modelotablao.getmed(filaModelo);
			System.out.println("valueChanged VTablaMeds: " + medselect);
			ImageIcon fot = new ImageIcon(medselect.getRutaimg());
			// this.imagen.setIcon(new ImageIcon(medselect.getRutaimg()));
			this.imagen.setIcon(new ImageIcon(fot.getImage().getScaledInstance(
					this.imagen.getWidth(), this.imagen.getHeight(),
					Image.SCALE_SMOOTH)));

		}

	}

	public void adaptajbutton(JButton but, String ruta) {
		ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
		// Icon icono = new
		// ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(),
		// lbllogo.getHeight(), Image.SCALE_DEFAULT));
		but.setIcon(new ImageIcon(fot.getImage()
				.getScaledInstance(but.getWidth() / 2, (int) (but.getHeight()),
						Image.SCALE_SMOOTH)));
		// this.repaint();

	}

	public void adaptajbuttonabajo(JButton but, String ruta) {
		ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
		// Icon icono = new
		// ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(),
		// lbllogo.getHeight(), Image.SCALE_DEFAULT));
		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(
				but.getWidth() - 6, but.getHeight() - 6, Image.SCALE_SMOOTH)));
		// this.repaint();

	}
}
