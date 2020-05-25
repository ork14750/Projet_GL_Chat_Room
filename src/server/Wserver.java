package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import commun.Message;

public class Wserver implements Runnable {

	private Wthread clients[];
	private ServerSocket server = null;
	private Thread thread = null;
	private int nbClients = 0;
	private int sPort = 3000;

	public Wserver() {
		clients = new Wthread[10];

		try {
			server = new ServerSocket(sPort);
			sPort = server.getLocalPort();

			System.out.println(
					"Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
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
				// System.out.println("waiting for connection ... ");

				System.out.println("Hello :" + thread.currentThread().getName());
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
		System.out.println("new message " + ID);
		if (msg.type == "CONNECTION") {
			System.out.print("Connection youpi !!! : " + msg.body);
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
