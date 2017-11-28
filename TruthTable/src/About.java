import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;

public class About extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			About dialog = new About();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public About() {
		setTitle("About");
		setBounds(100, 100, 850, 229);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTextArea txtrcsDigitalComputer = new JTextArea();
		txtrcsDigitalComputer.setEnabled(false);
		txtrcsDigitalComputer.setFont(new Font("SansSerif", Font.BOLD, 19));
		txtrcsDigitalComputer.setForeground(Color.WHITE);
		txtrcsDigitalComputer.setText("**CS- 211: Mathematics for Computer Science - Discrete Math.\r\n*Truth Table Constractor Project\r\n*Version : 1.0\r\n----------------------------------------------------------------------------------\r\n*Team Memebers : Karim Mohamed Mostafa - Kamal Abd El-Azeez Kamal");
		txtrcsDigitalComputer.setEditable(false);
		txtrcsDigitalComputer.setBackground(Color.LIGHT_GRAY);
		scrollPane.setViewportView(txtrcsDigitalComputer);
	}
}
