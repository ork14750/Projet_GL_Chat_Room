package com.kede.ihm;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.ConnectException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.kede.Message;
import com.kede.Wclient;

public class ClientIhm {
	private Wclient client;
	public Thread clientThr;
	private File file;
	public GroupIhm gIhm;

	private String login, password;
	public  DefaultListModel modelUser;
	public  DefaultListModel modelGroup;

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	private JFrame frmWhatsup;
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
	public JList list;
	
	private JLabel lblNewLabel_4;
	public JButton btnNewButton_5;
	public JButton btnNewButton_4;
	public JButton btnNewButton_6;
	private JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ClientIhm window = new ClientIhm();
					window.frmWhatsup.setVisible(true);
				
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
		this.modelGroup = new DefaultListModel();
		
		initialize();
		//modelUser.addElement("TOUT LE MONDE");
		
		frmWhatsup.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				try{ client.sendMessage(new Message("WINDOW_CLOSED", login, ".bye", "SERVER")); clientThr.stop();  }catch(Exception ex){}
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				try{ client.sendMessage(new Message("WINDOW_CLOSED", login, "closed", "SERVER")); clientThr.stop();  }catch(Exception ex){}

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWhatsup = new JFrame();
		frmWhatsup.getContentPane().setEnabled(false);
		 try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()	);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frmWhatsup.setTitle("Whatsup");
		frmWhatsup.setBounds(100, 100, 713, 484);
		frmWhatsup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWhatsup.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Host:");
		lblNewLabel.setBounds(30, 26, 45, 13);
		frmWhatsup.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setText("127.0.0.1");
		textField.setBounds(85, 23, 96, 19);
		frmWhatsup.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setText("3000");
		textField_1.setBounds(285, 23, 96, 19);
		frmWhatsup.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Port:");
		lblNewLabel_1.setBounds(205, 26, 45, 13);
		frmWhatsup.getContentPane().add(lblNewLabel_1);

		btnNewButton = new JButton("Joindre");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionJoinServer();

			}
		});
		btnNewButton.setBounds(430, 22, 85, 21);
		frmWhatsup.getContentPane().add(btnNewButton);

		textField_2 = new JTextField();
		textField_2.setBounds(85, 66, 96, 19);
		frmWhatsup.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setText("chris");

		lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(30, 69, 45, 13);

		frmWhatsup.getContentPane().add(lblNewLabel_2);

		lblNewLabel_3 = new JLabel("Mot de passe:");
		lblNewLabel_3.setBounds(205, 69, 85, 13);
		frmWhatsup.getContentPane().add(lblNewLabel_3);

		textField_3 = new JPasswordField();
		textField_3.setBounds(285, 66, 136, 19);
		frmWhatsup.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText("kede");


		btnNewButton_1 = new JButton("Se connecter");
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				actionLogin();
			}
		});
		btnNewButton_1.setBounds(430, 65, 111, 21);
		frmWhatsup.getContentPane().add(btnNewButton_1);

		btnNewButton_2 = new JButton("S'inscrire");
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				actionSignup();
				
			}
			
		});

		btnNewButton_2.setBounds(571, 65, 104, 21);
		frmWhatsup.getContentPane().add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 139, 151, 170);
		frmWhatsup.getContentPane().add(scrollPane);

		list = new JList();
		scrollPane.setViewportView(list);
		

		comboBox = new JComboBox();
		scrollPane.setColumnHeaderView(comboBox);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Membres", "Groupes" }));
		
		if((String) comboBox.getSelectedItem() == "Membres") {
			list.setModel((modelUser = new DefaultListModel()));
			modelUser.addElement("TOUT LE MONDE");
			list.setSelectedIndex(0);
		}else {
			list.setModel(modelGroup); 
			list.setSelectedIndex(0);
		}
		
		 ActionListener cbActionListener = new ActionListener() {//add actionlistner to listen for change
	            @Override
	            public void actionPerformed(ActionEvent e) {

	                String s = (String) comboBox.getSelectedItem();//get the selected item

	                switch (s) {//check for a match
	                    case "Membres":
	                    	list.setModel((modelUser));
	                    	list.setSelectedIndex(0);
	                        break;
	                    case "Groupes":
	                    	list.setModel(modelGroup);
	                    	list.setSelectedIndex(0);
	                        break;
	               
	                    default:
	                       
	                        break;
	                }
	            }
	        };
	        
	    comboBox.addActionListener(cbActionListener);    
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(205, 139, 470, 170);
		frmWhatsup.getContentPane().add(scrollPane_1);


		textPane = new JTextArea();
		scrollPane_1.setViewportView(textPane);
		textPane.setLineWrap(true);
		textPane.setWrapStyleWord(true);
		textPane.setFont(new java.awt.Font("Consolas", 0, 12));

		textField_4 = new JTextField();
		textField_4.setBounds(205, 341, 364, 19);
		frmWhatsup.getContentPane().add(textField_4);
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
		frmWhatsup.getContentPane().add(btnNewButton_3);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(205, 370, 457, 13);
		frmWhatsup.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(29, 116, 646, 13);
		frmWhatsup.getContentPane().add(separator_1);
		
		lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(326, 388, 141, 13);
		frmWhatsup.getContentPane().add(lblNewLabel_4);
		
	
		
		btnNewButton_5 = new JButton("Envoie PJ");
		btnNewButton_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if((String) comboBox.getSelectedItem() == "Groupes" || (String) list.getSelectedValue() =="TOUT LE MONDE") {
					logIt("Erreur", "moi", "L'envoie de PJ est autorisé seulement avec un utilisateur !");
				}else if(!(list.getSelectedValue().toString().isEmpty())){
					sendAttach();
				}
				
				
			}
			
		});
		btnNewButton_5.setVisible(false);
		btnNewButton_5.setBounds(484, 384, 85, 21);
		frmWhatsup.getContentPane().add(btnNewButton_5);
		
		
		btnNewButton_4 = new JButton("Piece-jointe");
		btnNewButton_4.setEnabled(false);
		btnNewButton_4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				actionChooseAttach();
			}
			
		});
		
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNewButton_4.setBounds(205, 384, 111, 21);
		frmWhatsup.getContentPane().add(btnNewButton_4);
		
		btnNewButton_6 = new JButton("Nouveau groupe");
		btnNewButton_6.setEnabled(false);
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionOpenGroupIhm();
			}
		});
		btnNewButton_6.setBounds(30, 340, 111, 21);
		frmWhatsup.getContentPane().add(btnNewButton_6);
	}

	public JFrame getFrame() {
		return this.frmWhatsup;
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
			} catch (Exception ce) {
				this.logIt("ERREUR", "moi", "Le serveur n'est pas disponible..");
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
	
	public void actionSignup() {
		this.login = this.textField_2.getText();
    	password = this.textField_3.getText();
        
        if(!login.isEmpty() && !password.isEmpty()){
            client.sendMessage(new Message("SIGNUP", login, password, "SERVER"));
        }

	}
	
	
	    
	public void actionSendMessage() {
		String body = this.textField_4.getText();
		try {
			if(this.comboBox.getSelectedItem() =="Groupes") {
				String recipient = this.list.getSelectedValue().toString();
				client.sendMessage(new Message("MESSAGE_GROUP", login, body, recipient));
				
			}else {
				String recipient = this.list.getSelectedValue().toString();
				client.sendMessage(new Message("MESSAGE", login, body, recipient));
			}
			
		}catch(Exception e) {
			this.logIt("Erreur", "moi", "Veuillez Selectionner un destinataire");
			
		}
		
		
	}
	
	public void actionChooseAttach() {
        JFileChooser chooser = new JFileChooser();
        chooser.showDialog(this.frmWhatsup, "Choisissez un ficher");
        file = chooser.getSelectedFile();
        
        if(file!=null){
        	if(!file.getName().isEmpty()) {
        		  String filePath = file.getName();
        		  this.lblNewLabel_4.setText(filePath);
        			btnNewButton_5.setVisible(true);

        	} 
          
        }    

	}
	public void actionOpenGroupIhm() {
		
		 this.modelUser.removeElement("TOUT LE MONDE");
		 gIhm = new GroupIhm(this);
		 gIhm.getFrame().setLocation(this.getFrame().getLocation());
	     gIhm.getFrame().setVisible(true);
		 System.out.println("grou");
         gIhm.getFrame().setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
         //gIhm.getFrame().setVisible(false);
	}
	
	
	public void addGroup(List<String> members, String name) {
		String arrToString =arrToString(members)+";"+this.getLogin();
		
		client.sendMessage(new Message("NEW_GROUP", login, arrToString, name ));
		
	}
	
	public String arrToString(List<String> l) {
		String res ="";
		for(int i =0; i<l.size(); i++) {
			res+=l.get(i)+";";
		}
		return res.substring(0, res.length() -1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public File getFile() {
		return file;
	}
	
	public void sendAttach() {
		long size = file.length();
        if(size < 120 * 1024 * 1024){
            client.sendMessage(new Message("REQ_UPLOAD", login, file.getName(), list.getSelectedValue().toString()));
        }
        else{
            this.logIt("Erreur", "Moi", "Le fichier est trop lourd !");
        }
	}
	
	
}
