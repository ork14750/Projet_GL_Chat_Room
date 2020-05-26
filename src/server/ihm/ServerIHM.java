package server.ihm;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ServerIHM {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerIHM window = new ServerIHM();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerIHM() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(100, 92, 131));
		frame.setBounds(100, 100, 639, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(246, 23, 85, 21);
		frame.getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(10, 24, 229, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Server Logs");
		lblNewLabel.setBounds(465, 23, 85, 21);
		frame.getContentPane().add(lblNewLabel);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(349, 54, 266, 276);
		frame.getContentPane().add(textPane);

		JButton btnNewButton_1 = new JButton("Lancer le serveur");
		btnNewButton_1.setBackground(new Color(210, 105, 30));
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBounds(104, 171, 154, 21);
		frame.getContentPane().add(btnNewButton_1);
	}
}
