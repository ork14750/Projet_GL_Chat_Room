package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Wserver implements Runnable {

	private Wthread clients[];
	private ServerSocket server = null;
	private Thread thread = null;
	private int nbClients = 0;
	private int sPort = 3000;

	public Wserver() {
		clients = new Wthread[50];

		try {
			server = new ServerSocket(sPort);
			sPort = server.getLocalPort();

			System.out.println(
					"Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
			startTheard();
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
				thread.sleep(1000);
				System.out.println("Hello" + thread.currentThread().getName());
				server.accept();
				System.out.println("Hello new client");

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

	public void addClientThread(Socket socket) {
		this.clients[this.nbClients] = new Wthread(this, socket);

		this.clients[this.nbClients].openConnection();
		this.clients[this.nbClients].start();

		this.nbClients++;

	}

	@SuppressWarnings("deprecation")
	public void stopTheard() {
		if (thread == null) {
			thread.stop();
			thread = null;
		}
	}

}
