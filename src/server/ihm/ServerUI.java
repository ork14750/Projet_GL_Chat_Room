package server.ihm;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIManager;

import server.Wserver;

public class ServerUI extends JFrame {
	private Wserver server;
	private Thread serverThr;

	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JScrollPane jScrollPane1;
	public javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField3;

	public ServerUI() {
		jButton1 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jLabel3 = new javax.swing.JLabel();
		jTextField3 = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();

		jTextField3.setEditable(false);
		jTextField3.setBackground(Color.WHITE);

		jTextArea1.setEditable(false);
		this.windowUI();

	}

	public void windowUI() {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Whatsup");
	}

	public static void main(String args[]) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			System.out.println(ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ServerUI().setVisible(true);
			}
		});
	}

}
