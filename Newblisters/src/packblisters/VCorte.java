package packblisters;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class VCorte extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jpContentPanel;

	private JTextArea jtaGcode;
	private JSpinner jsPerforaciones;
	private JOptionPane warnincorte;
	private DefaultComboBoxModel mc;

	/**
	 * Create the frame.
	 */
	public VCorte(DefaultComboBoxModel modeloCombo) {
		mc = modeloCombo;

		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Nuevo Corte");
		setBounds((Principal.d.width / 2) - 175,
				(Principal.d.height / 2) - 150, 350, 347);

		jpContentPanel = new JPanel();
		jpContentPanel.setLayout(null);
		getContentPane().add(jpContentPanel, BorderLayout.CENTER);

		JLabel lblNombre = new JLabel("Añadir nuevo corte "
				+ ((int) mc.getSize() + 1));
		lblNombre.setBounds(25, 25, 202, 15);
		jpContentPanel.add(lblNombre);

		
		JLabel lblPerforaciones = new JLabel("Perforaciones:");
		lblPerforaciones.setBounds(25, 52, 120, 15);
		jpContentPanel.add(lblPerforaciones);
		
		jsPerforaciones = new JSpinner();
		jsPerforaciones.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		jsPerforaciones.setBounds(200, 49, 28, 20);
		jpContentPanel.add(jsPerforaciones);
		
		jtaGcode = new JTextArea();
		jtaGcode.setText("gcode");
		jtaGcode.setBounds(25, 52, 300, 215);

		JScrollPane scroll = new JScrollPane(jtaGcode);
		scroll.setBounds(25, 79, 300, 188);
		jpContentPanel.add(scroll);

		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Window vPadre = SwingUtilities.getWindowAncestor((Component) e
						.getSource());
				if (jtaGcode.getText().isEmpty()
						|| jtaGcode.getText().contentEquals("gcode")) {
					JOptionPane.showMessageDialog(null,
							"Debes introducir un código GCODE", "Warning",
							JOptionPane.WARNING_MESSAGE);
					// Cierro ventana
					dispose();
				} else {
					DBFacade dbfacade = new DBFacade();
					int i = dbfacade.insertarIdCorte(jtaGcode.getText(),(Integer)jsPerforaciones.getValue());
					JOptionPane.showMessageDialog(null, "Nuevo corte añadido",
							"Corte Guardado", JOptionPane.INFORMATION_MESSAGE);

					mc.addElement(i);
					// Cierro ventana
					vPadre.repaint();
					dispose();
				}

			}
		});
		guardar.setBounds(75, 279, 92, 25);
		jpContentPanel.add(guardar);

		JButton cancelar = new JButton("Cancelar");
		cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cierro ventana
				dispose();
			}
		});
		cancelar.setBounds(200, 279, 98, 25);
		jpContentPanel.add(cancelar);


	}
}
