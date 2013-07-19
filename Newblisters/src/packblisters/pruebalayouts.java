package packblisters;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class pruebalayouts extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    pruebalayouts frame = new pruebalayouts();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public pruebalayouts() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
	
	JButton btnNewButton = new JButton("New button");
	btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
	btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	contentPane.add(btnNewButton);
	
	JButton btnNewButton_1 = new JButton("New button");
	contentPane.add(btnNewButton_1);
	
	JButton btnNewButton_2 = new JButton("New button");
	contentPane.add(btnNewButton_2);
	
	JButton btnNewButton_3 = new JButton("New button");
	contentPane.add(btnNewButton_3);
    }

}
