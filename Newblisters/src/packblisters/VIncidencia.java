package packblisters;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VIncidencia extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;
	private DBFacade dbf = new DBFacade();
	private Medicamento m;
	private JOptionPane incidenciaok;

	/**
	 * Create the dialog.
	 * 
	 * @param med
	 */
	public VIncidencia(Medicamento med) {

		this.m = med;

		setBounds((Principal.d.width / 2) - 250,
				(Principal.d.height / 2) - 175, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 450, 231);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblIncidencia = new JLabel("Incidencia:");
		lblIncidencia.setBounds(23, 12, 93, 15);
		contentPanel.add(lblIncidencia);

		textArea = new JTextArea();
		textArea.setBounds(33, 39, 388, 180);
		contentPanel.add(textArea);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					dbf.insertarHistorico(VLogin.UsuarioLogueado.getNombre(),
							m.getNombre(), m.getCodnac(), m.getIdcorte(),
							"incidencia", textArea.getText());
					JOptionPane.showMessageDialog(incidenciaok,
							"Incidencia guardada", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			});
			okButton.setBounds(152, 237, 71, 25);
			getContentPane().add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}

		});

		cancelButton.setBounds(228, 237, 81, 25);
		getContentPane().add(cancelButton);
		cancelButton.setActionCommand("Cancel");

	}
}
