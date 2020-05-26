package com.kede.ihm;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.kede.Message;
import com.kede.Wclient;
import javax.swing.JPasswordField;

public class ClientIhm {
	private Wclient client;
	private Thread clientThr;
	private String login, password;
	public  DefaultListModel modelUser;

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	public JButton btnNewButton;
	private JTextField textField_2;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JPasswordField textField_3;
	public JButton btnNewButton_1;
	public JButton btnNewButton_2;
	private JTextArea textPane;
	private JTextField textField_4;
	private JComboBox comboBox;
	public JButton btnNewButton_3;
	private JList list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ClientIhm window = new ClientIhm();
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
	public ClientIhm() {
		initialize();
		modelUser.addElement("TOUT LE MONDE");
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 713, 466);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Host:");
		lblNewLabel.setBounds(30, 26, 45, 13);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setText("127.0.0.1");
		textField.setBounds(85, 23, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText("3000");
		textField_1.setBounds(285, 23, 96, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Port:");
		lblNewLabel_1.setBounds(205, 26, 45, 13);
		frame.getContentPane().add(lblNewLabel_1);

		btnNewButton = new JButton("Joindre");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionJoinServer();

			}
		});
		btnNewButton.setBounds(430, 22, 85, 21);
		frame.getContentPane().add(btnNewButton);

		textField_2 = new JTextField();
		textField_2.setBounds(85, 66, 96, 19);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(30, 69, 45, 13);

		frame.getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Mot de passe:");
		lblNewLabel_3.setBounds(205, 69, 85, 13);
		frame.getContentPane().add(lblNewLabel_3);

		textField_3 = new JPasswordField();
		textField_3.setBounds(285, 66, 136, 19);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);

		btnNewButton_1 = new JButton("Se connecter");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionLogin();
			}
		});
		btnNewButton_1.setBounds(430, 65, 111, 21);
		frame.getContentPane().add(btnNewButton_1);

		btnNewButton_2 = new JButton("S'inscrire");
		btnNewButton_2.setEnabled(false);

		btnNewButton_2.setBounds(571, 65, 104, 21);
		frame.getContentPane().add(btnNewButton_2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 139, 151, 170);
		frame.getContentPane().add(scrollPane);

		list = new JList();
		scrollPane.setViewportView(list);
		list.setModel((modelUser = new DefaultListModel()));

		comboBox = new JComboBox();
		scrollPane.setColumnHeaderView(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Groupes", "Membres" }));

		textPane = new JTextArea();
		textPane.setBounds(205, 139, 470, 170);
		textPane.setLineWrap(true);
		textPane.setWrapStyleWord(true);
		textPane.setFont(new java.awt.Font("Consolas", 0, 12));
		frame.getContentPane().add(textPane);

		textField_4 = new JTextField();
		textField_4.setBounds(205, 341, 364, 19);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		btnNewButton_3 = new JButton("Envoyer");
		btnNewButton_3.setEnabled(false);

		btnNewButton_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionSendMessage();
			}
		});
		btnNewButton_3.setBounds(590, 340, 85, 21);
		frame.getContentPane().add(btnNewButton_3);
	}

	public void actionJoinServer() {
		String host = this.textField.getText();
		int port = Integer.parseInt(this.textField_1.getText());

		if (!host.isEmpty() && !this.textField.getText().isEmpty()) {
			try {
				client = new Wclient(this);
				clientThr = new Thread(client);
				clientThr.start();
				client.sendMessage(new Message("CONNECTION", "testUser", "testContent", "SERVER"));
			} catch (Exception ex) {
				System.out.println(ex);
			}

		}

	}

	public void logIt(String from, String to, String msg) {
		this.textPane.append("[ " + from + "> " + to + " ] : "+msg+"\n");

	}

	@SuppressWarnings("deprecation")
	public void actionLogin() {
		this.login = this.textField_2.getText();
    	password = this.textField_3.getText();
        
        if(!login.isEmpty() && !password.isEmpty()){
            client.sendMessage(new Message("LOGIN", login, password, "SERVER"));
        }

	}
	
	
	    
	public void actionSendMessage() {
		String body = this.textField_4.getText();
		String recipient = this.list.getSelectedValue().toString();
		client.sendMessage(new Message("MESSAGE", login, body, recipient));
		
	}
	
	
	
	
	

}
