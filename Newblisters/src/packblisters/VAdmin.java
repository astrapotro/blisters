package packblisters;

import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import java.awt.Font;

public class VAdmin extends JPanel {

	private static final long serialVersionUID = 1L;

	public VTablaUsuarios vtablausuarios;
	public VTablaHistorico vtablahistorico;
	public VTablaMedicamentos vtablamedicamentos;
	public VProcesoCorte vprocesocorte;
	public String puerto;

	public VAdmin() {
		setLayout(null);
		this.setBackground(new Color(224, 255, 255));
		this.setBorder(new MatteBorder(4, 4, 4, 4, (Color) new Color(107, 142,
				35)));

		JButton btnProcesoCorte = new JButton("Proceso de corte");
		btnProcesoCorte.setFont(new Font("Lucida Sans", Font.BOLD, 12));
		// Para que no salga el cuadrado de selección alrededor del icono !!
		btnProcesoCorte.setFocusPainted(false);
		btnProcesoCorte.setBounds(97, 66, 180, 120);
		btnProcesoCorte.setVerticalAlignment(SwingConstants.CENTER);
		adaptajbutton(btnProcesoCorte, "/iconos/1 (31).png");
		btnProcesoCorte.setIconTextGap(4);
		btnProcesoCorte.setHorizontalTextPosition(SwingConstants.CENTER);
		btnProcesoCorte.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnProcesoCorte);

		JButton btnHistorico = new JButton("Historico");
		btnHistorico.setBounds(97, 248, 180, 120);
		adaptajbutton(btnHistorico, "/iconos/1 (5).png");
		btnHistorico.setIconTextGap(4);
		btnHistorico.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHistorico.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnHistorico);

		JButton btnUsuarios = new JButton("Usuarios");
		btnUsuarios.setBounds(413, 248, 180, 120);
		adaptajbutton(btnUsuarios, "/iconos/1 (1).png");
		btnUsuarios.setIconTextGap(4);
		btnUsuarios.setHorizontalTextPosition(SwingConstants.CENTER);
		btnUsuarios.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnUsuarios);

		JButton btnMedicamentos = new JButton("Medicamentos");
		btnMedicamentos.setBounds(413, 66, 180, 120);
		adaptajbutton(btnMedicamentos, "/iconos/1 (14).jpg");
		btnMedicamentos.setIconTextGap(4);
		btnMedicamentos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnMedicamentos.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnMedicamentos);

		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(263, 405, 163, 111);
		adaptajbutton(btnSalir, "/iconos/Close-2-icon.png");
		btnSalir.setIconTextGap(5);
		btnSalir.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSalir.setVerticalTextPosition(SwingConstants.BOTTOM);
		add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
			}
		});

		btnMedicamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				if (vtablamedicamentos == null) {
					vtablamedicamentos = new VTablaMedicamentos();
				}

				vtablamedicamentos.setBounds((Principal.d.width / 2) - 350,
						(Principal.d.height / 2) - 325, 700, 610);

				Principal.Panel.add(vtablamedicamentos);
				vtablamedicamentos.repaint();
				vtablamedicamentos.validate();
				vtablamedicamentos.setVisible(true);
			}
		});

		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				setVisible(false);
				vtablausuarios = new VTablaUsuarios();
				vtablausuarios.setBounds((Principal.d.width / 2) - 265,
						(Principal.d.height / 2) - 190, 530, 340);
				Principal.Panel.add(vtablausuarios);
				vtablausuarios.repaint();
				vtablausuarios.validate();
				vtablausuarios.setVisible(true);
			}
		});

		btnHistorico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				vtablahistorico = new VTablaHistorico();
				vtablahistorico.setBounds((Principal.d.width / 2) - 375,
						(Principal.d.height / 2) - 270, 750, 500);
				Principal.Panel.add(vtablahistorico);
				vtablahistorico.repaint();
				vtablahistorico.validate();
				vtablahistorico.setVisible(true);
			}
		});

		btnProcesoCorte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// puerto = "/dev/ttyUSB0";

				puerto = (String) JOptionPane.showInputDialog(
						Principal.Panel,
						"Introduzca el puerto:", //$NON-NLS-1$
						"Configuración puerto serie", //$NON-NLS-1$
						JOptionPane.QUESTION_MESSAGE, null, null,
						"/dev/ttyUSB0");

				System.out.println("PUERTO: " + puerto);

				if (puerto != null) {

					setVisible(false);
					vprocesocorte = new VProcesoCorte();
					vprocesocorte.setBounds((Principal.d.width / 2) - 400,
							(Principal.d.height / 2) - 320, 800, 600);
					Principal.Panel.add(vprocesocorte);
					vprocesocorte.repaint();
					vprocesocorte.validate();
					vprocesocorte.setVisible(true);
				}

			}
		});
	}

	public void adaptajlabel(JLabel lbl, String ruta) {

		ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
		// Icon icono = new
		// ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(),
		// lbllogo.getHeight(), Image.SCALE_DEFAULT));
		lbl.setIcon(new ImageIcon(fot.getImage().getScaledInstance(
				lbl.getWidth(), lbl.getHeight(), Image.SCALE_SMOOTH)));
		// this.repaint();

	}

	public void adaptajbutton(JButton but, String ruta) {
		ImageIcon fot = new ImageIcon(VLogin.class.getResource(ruta));
		// Icon icono = new
		// ImageIcon(fot.getImage().getScaledInstance(lbllogo.getWidth(),
		// lbllogo.getHeight(), Image.SCALE_DEFAULT));
		but.setIcon(new ImageIcon(fot.getImage().getScaledInstance(
				but.getWidth() - 80, but.getHeight() - 40, Image.SCALE_SMOOTH)));
		// this.repaint();

	}

}
