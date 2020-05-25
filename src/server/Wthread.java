package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Wthread extends Thread {

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

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while (true) {
			try {

			} catch (Exception e) {
				stop();
				// server.remove(port);
			}
		}

	}

	public void openConnection() {
		try {
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.output.flush();
			this.input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
