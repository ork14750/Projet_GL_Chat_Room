package server.ihm;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import server.Wserver;

public class ServerIHM {

	private Wserver server;
	private JFrame frame;
	private JTextField textField;
	public JTextArea textArea_1;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField textField_1;
	private JButton btnNewButton_2;
	private JLabel lblNewLabel_4;

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
		frame.getContentPane().setFont(new Font("Consolas", Font.PLAIN, 10));
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 639, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(123, 51, 69, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("Server Logs");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBounds(10, 129, 85, 21);
		frame.getContentPane().add(lblNewLabel);

		btnNewButton_1 = new JButton("Lancer le serveur");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				launchSeverBtn(e);
				btnNewButton_1.setEnabled(false);

			}

		});
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBounds(334, 50, 154, 21);
		frame.getContentPane().add(btnNewButton_1);

		// JTextArea textArea_1 = new JTextArea();
		// textArea.setBounds(557, 122, -113, 175);
		// frame.getContentPane().add(textArea_1);

		textArea_1 = new JTextArea();
		textArea_1.setBackground(SystemColor.text);
		textArea_1.setColumns(20);
		textArea_1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
		textArea_1.setRows(5);
		textArea_1.setBounds(10, 154, 605, 204);
		this.textArea_1
				.setBorder(new CompoundBorder(new LineBorder(new Color(130, 135, 144)), new EmptyBorder(0, 0, 0, 0)));
		textArea_1.setFocusable(false);
		frame.getContentPane().add(textArea_1);

		lblNewLabel_1 = new JLabel("Port d'\u00E9coute:");
		lblNewLabel_1.setFont(new Font("Lucida Bright", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 54, 85, 13);
		frame.getContentPane().add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Base de donn\u00E9e:");
		lblNewLabel_2.setFont(new Font("Lucida Bright", Font.BOLD, 11));
		lblNewLabel_2.setBounds(10, 13, 114, 13);
		frame.getContentPane().add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(123, 10, 179, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		btnNewButton_2 = new JButton("Selectionner");
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(334, 9, 154, 21);
		frame.getContentPane().add(btnNewButton_2);

		JLabel lblNewLabel_3 = new JLabel("Messages echang\u00E9s:");
		lblNewLabel_3.setFont(new Font("Lucida Bright", Font.PLAIN, 10));
		lblNewLabel_3.setBounds(10, 87, 114, 13);
		frame.getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("0");
		lblNewLabel_4.setFont(new Font("Lucida Bright", Font.BOLD, 11));
		lblNewLabel_4.setBounds(123, 87, 45, 13);
		frame.getContentPane().add(lblNewLabel_4);

		JButton btnNewButton = new JButton("Tout effacer");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(257, 368, 114, 21);
		frame.getContentPane().add(btnNewButton);
	}

	private void launchSeverBtn(ActionEvent e) {
		server = new Wserver(this);
		btnNewButton_1.setBackground(Color.GRAY);
		System.out.println("ok");
	}
}
