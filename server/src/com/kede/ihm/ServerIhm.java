package com.kede.ihm;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.kede.Wserver;

public class ServerIhm {

	private Wserver server;
	private JFrame frame;
	private JTextField textField;
	public JTextArea textArea_1;
	public JButton btnNewButton_1;
	private JLabel lblNewLabel_1;
	public JLabel lblNewLabel_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerIhm window = new ServerIhm();
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
	public ServerIhm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		 try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()	);
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
		frame.getContentPane().setFont(new Font("Consolas", Font.PLAIN, 10));
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 639, 451);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(123, 24, 69, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		textField.setText("3000");

		JLabel lblNewLabel = new JLabel("Server Logs");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBounds(10, 136, 114, 21);
		frame.getContentPane().add(lblNewLabel);

		btnNewButton_1 = new JButton("Lancer le serveur");
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				launchSeverBtn(e);
				
				

			}

		});
		btnNewButton_1.setForeground(new Color(0, 0, 0));
		btnNewButton_1.setBounds(329, 23, 154, 21);
		frame.getContentPane().add(btnNewButton_1);

		lblNewLabel_1 = new JLabel("Port d'\u00E9coute:");
		lblNewLabel_1.setFont(new Font("Lucida Bright", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 27, 85, 13);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_3 = new JLabel("Clients connect\u00E9s:");
		lblNewLabel_3.setFont(new Font("Lucida Bright", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(10, 87, 114, 13);
		frame.getContentPane().add(lblNewLabel_3);

		lblNewLabel_4 = new JLabel("0");
		lblNewLabel_4.setFont(new Font("Lucida Bright", Font.BOLD, 12));
		lblNewLabel_4.setBounds(123, 87, 45, 13);
		frame.getContentPane().add(lblNewLabel_4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 194, 605, 164);
		frame.getContentPane().add(scrollPane);

		// JTextArea textArea_1 = new JTextArea();
		// textArea.setBounds(557, 122, -113, 175);
		// frame.getContentPane().add(textArea_1);

		textArea_1 = new JTextArea();
		scrollPane.setViewportView(textArea_1);
		textArea_1.setBackground(SystemColor.text);
		textArea_1.setColumns(20);
		textArea_1.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
		textArea_1.setRows(5);
		this.textArea_1
				.setBorder(new CompoundBorder(new LineBorder(new Color(130, 135, 144)), new EmptyBorder(0, 0, 0, 0)));
		textArea_1.setFocusable(false);

		JButton btnNewButton = new JButton("Tout effacer");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea_1.setText("");
			}
		});
		btnNewButton.setBounds(257, 368, 114, 21);
		frame.getContentPane().add(btnNewButton);
	}

	private void launchSeverBtn(ActionEvent e) {
		if(!textField.getText().isEmpty()) {
			try {
				server = new Wserver(this, Integer.parseInt(textField.getText()));
				
				
			}catch(Exception ex){
				this.logIt("VEUILLEZ ENTRER UN PORT VALIDE");
			}
			
		}else {
			this.logIt("VEUILLEZ ENTRER UN PORT VALIDE");
		}
		
	}

	public void logIt(String message) {
		textArea_1.append("\n" + message);
	}
}
