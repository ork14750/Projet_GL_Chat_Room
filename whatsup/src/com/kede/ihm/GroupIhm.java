package com.kede.ihm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupIhm {

	private JFrame frame;
	private JTextField textField;
	private ClientIhm cIhm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupIhm window = new GroupIhm();
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
	public GroupIhm(ClientIhm cIhm) {
		this.cIhm = cIhm;
		initialize();
	}
	
	public GroupIhm() {
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
		frame.setBounds(100, 100, 409, 365);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setTitle("Nouveau groupe");
	    frame.getContentPane().setLayout(null);
	    
	    JLabel lblNewLabel = new JLabel("Nom du groupe :");
	    lblNewLabel.setBounds(41, 41, 121, 13);
	    frame.getContentPane().add(lblNewLabel);
	    
	    textField = new JTextField();
	    textField.setBounds(199, 38, 107, 19);
	    frame.getContentPane().add(textField);
	    textField.setColumns(10);
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(199, 87, 107, 102);
	    frame.getContentPane().add(scrollPane);
	    
	    JList list = new JList();
	    scrollPane.setViewportView(list);
	    list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    list.setModel(this.cIhm.modelUser);
	    
	    JLabel lblNewLabel_1 = new JLabel("Ajouter des membres :");
	    lblNewLabel_1.setBounds(41, 88, 148, 13);
	    frame.getContentPane().add(lblNewLabel_1);
	    
	    JButton btnNewButton = new JButton("Creer le groupe");
	    btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textField.getText().isEmpty() && list.getSelectedValuesList().size()>0) {
					System.out.println("group Created "+list.getSelectedValuesList());
				}
				
			}
	    	
	    });
	    btnNewButton.setBounds(41, 231, 107, 21);
	    frame.getContentPane().add(btnNewButton);
	    
	    JTextPane txtpnMaintenerLanTouche = new JTextPane();
	    txtpnMaintenerLanTouche.setBackground(SystemColor.inactiveCaptionBorder);
	    txtpnMaintenerLanTouche.setText("Maintener la touche Ctrl pour selectioner plusieurs membres");
	    txtpnMaintenerLanTouche.setBounds(41, 119, 121, 51);
	    frame.getContentPane().add(txtpnMaintenerLanTouche);

	}

	public JFrame getFrame() {
		return frame;
	}
}
