package packblisters;


import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;



public  class VAdmin extends JPanel {

    private static final long serialVersionUID = 1L;
    
    public VTablaUsuarios vtablausuarios ;
    public VTablaHistorico vtablahistorico;
    public VTablaMedicamentos vtablamedicamentos;
    public VProcesoCorte vprocesocorte;
    
  


   
    public VAdmin() {
	setLayout(null);
	
	JButton btnProcesoCorte = new JButton("ProcesoCorte");
	btnProcesoCorte.setBounds(24, 30, 180, 47);
	add(btnProcesoCorte);
	
	JButton btnHistorico = new JButton("Historico");
	btnHistorico.setBounds(24, 127, 180, 45);
	add(btnHistorico);
	
	JButton btnUsuarios = new JButton("Usuarios");
	btnUsuarios.setBounds(246, 127, 180, 45);
	add(btnUsuarios);
	
	JButton btnMedicamentos = new JButton("Medicamentos");
	btnMedicamentos.setBounds(246, 30, 180, 47);
	add(btnMedicamentos);
	
	JButton btnSalir = new JButton("Salir");
	btnSalir.setBounds(175, 205, 108, 47);
	add(btnSalir);
	
	JSeparator separator = new JSeparator();
	separator.setBounds(0, 0, 450, 2);
	add(separator);
	
	JSeparator separator_1 = new JSeparator();
	separator_1.setOrientation(SwingConstants.VERTICAL);
	separator_1.setBounds(0, 0, 2, 280);
	add(separator_1);
	
	JSeparator separator_2 = new JSeparator();
	separator_2.setOrientation(SwingConstants.VERTICAL);
	separator_2.setBounds(448, 0, 2, 280);
	add(separator_2);
	
	JSeparator separator_3 = new JSeparator();
	separator_3.setBounds(0, 278, 450, 2);
	add(separator_3);
	btnSalir.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    
		    System.exit(0);
		}
	});
	
	btnMedicamentos.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    
		    setVisible(false);
		    if (vtablamedicamentos == null){
			vtablamedicamentos = new VTablaMedicamentos();
		    }
		    
		    vtablamedicamentos.setBounds((Principal.d.width/2)-250, (Principal.d.height/2)-250, 500, 500);
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
		    vtablausuarios.setBounds((Principal.d.width/2)-250, (Principal.d.height/2)-250, 500, 500);
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
		    vtablahistorico.setBounds((Principal.d.width/2)-350, (Principal.d.height/2)-250, 700, 500);
		    Principal.Panel.add(vtablahistorico);
		    vtablahistorico.repaint();
		    vtablahistorico.validate();
		    vtablahistorico.setVisible(true);
		}
	});
	
	btnProcesoCorte.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    	setVisible(false);
			vprocesocorte = new VProcesoCorte();
			vprocesocorte.setBounds((Principal.d.width/2)-400, (Principal.d.height/2)-300, 800, 600);
			Principal.Panel.add(vprocesocorte);
			vprocesocorte.repaint();
			vprocesocorte.validate();
			vprocesocorte.setVisible(true);

		}
	});
    }
}
