package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import commun.Message;
import server.ihm.ServerIHM;

public class Wserver implements Runnable {

	private Wthread clients[];
	private ServerSocket server = null;
	private Thread thread = null;
	private int nbClients = 0;
	private int sPort = 3000;
	private ServerIHM ihm;

	public Wserver(ServerIHM ihm) {
		this.ihm = ihm;
		clients = new Wthread[10];

		try {
			server = new ServerSocket(sPort);
			sPort = server.getLocalPort();
			for (int i = 0; i < 50; i++) {
				ihm.textArea_1.append("\nje suis un ling temos fjnfknf");
			}
			ihm.textArea_1
					.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());

			this.startTheard();
		} catch (IOException ioe) {
			System.out.println("Can not bind to port : " + sPort + "\nRetrying");
			// ui.RetryStart(0);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (thread != null) {
			try {
				if (this.nbClients == 0) {
					ihm.textArea_1.append("\nAttente de connection client ...");
				}

				this.addClientThread(server.accept());

			} catch (Exception e) {
				System.out.println("Error");

			}
		}

	}

	public void startTheard() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public void addClientThread(Socket socket) throws IOException {

		this.clients[this.nbClients] = new Wthread(this, socket);

		Wthread currentClient = this.clients[this.nbClients];

		currentClient.openConnection();
		currentClient.start();

		System.out.println("Hello new client " + currentClient.getName());
		this.nbClients++;

	}

	public synchronized void messageHandler(int ID, Message msg) {
		System.out.println("new message " + msg.type + msg.type.equals("CONNECTION"));
		if (msg.type.equals("CONNECTION")) {
			ihm.textArea_1.append("\nUn nouveau client s'est connecté au serveur !");
		}

	}

	@SuppressWarnings("deprecation")
	public void stopTheard() {
		if (thread == null) {
			thread.stop();
			thread = null;
		}
	}

}
