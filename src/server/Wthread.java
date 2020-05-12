package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Wthread implements Runnable {

	private Wserver server;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private int port;

	public Wthread(Wserver server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.port = socket.getPort();
	}

	@Override
	public void run() {
		while (true) {
			try {

			} catch (Exception e) {
				// server.remove(port);
			}
		}

	}

}
