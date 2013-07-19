package packblisters;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class VNuevoUsuario extends JPanel {

   
    private JTextField nombreField;


    /**
     * Create the frame.
     */
    public VNuevoUsuario() {
    	
	
	JLabel lblNombre = new JLabel("Nombre");
	lblNombre.setBounds(12, 12, 70, 15);
	add(lblNombre);
	
	nombreField = new JTextField();
	nombreField.setBounds(12, 32, 245, 19);
	add(nombreField);
	nombreField.setColumns(10);
	
	JCheckBox chckbxRoot = new JCheckBox("Administrador");
	chckbxRoot.setBounds(12, 74, 129, 23);
	add(chckbxRoot);
    }
}
