package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import commun.Message;

public class Wthread extends Thread {

	private Wserver server;
	private Socket socket;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private int port;

	public Wthread(Wserver server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.port = socket.getPort();
	}

	// @SuppressWarnings("deprecation")
	@Override
	public void run() {
		System.out.println("new message ");

		while (true) {
			System.out.println("hello in while");
			try {

				Message msg = (Message) this.input.readObject();
				System.out.println(msg.body);
				server.messageHandler(this.port, (Message) this.input.readObject());
				System.out.println(msg.body);

			} catch (Exception e) {
				System.out.println(e);
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
