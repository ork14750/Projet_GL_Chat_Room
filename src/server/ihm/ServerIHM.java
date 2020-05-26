package server.ihm;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.Wserver;

public class ServerIHM {

	private Wserver server;
	private JFrame frame;
	private JTextField textField;
	public JTextArea textArea_1;
	private JButton btnNewButton_1;

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
		frame.getContentPane().setBackground(new Color(192, 192, 192));
		frame.setBounds(100, 100, 639, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(168, 9, 85, 21);
		frame.getContentPane().add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(10, 10, 148, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Server Logs");
		lblNewLabel.setBounds(10, 86, 85, 21);
		frame.getContentPane().add(lblNewLabel);

		btnNewButton_1 = new JButton("Lancer le serveur");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				launchSeverBtn(e);
				btnNewButton_1.setEnabled(false);

			}

		});
		btnNewButton_1.setBackground(new Color(0, 255, 0));
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBounds(461, 86, 154, 21);
		frame.getContentPane().add(btnNewButton_1);

		// JTextArea textArea_1 = new JTextArea();
		// textArea.setBounds(557, 122, -113, 175);
		// frame.getContentPane().add(textArea_1);

		textArea_1 = new JTextArea();
		textArea_1.setColumns(20);
		textArea_1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
		textArea_1.setRows(5);
		textArea_1.setBounds(10, 109, 605, 221);
		this.textArea_1.setBorder(BorderFactory.createCompoundBorder(this.textArea_1.getBorder(),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		textArea_1.setFocusable(false);
		frame.getContentPane().add(textArea_1);
	}

	private void launchSeverBtn(ActionEvent e) {
		server = new Wserver(this);
		btnNewButton_1.setBackground(Color.GRAY);
		System.out.println("ok");
	}

}
