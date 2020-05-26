package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import commun.Message;

public class Wclient implements Runnable {

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;
	private int port;
	private String address;

	public Wclient() {
		this.address = "127.0.0.1";
		this.port = 3000;
		try {
			this.socket = new Socket(InetAddress.getByName(this.address), port);

			this.output = new ObjectOutputStream(socket.getOutputStream());

			this.input = new ObjectInputStream(socket.getInputStream());

			this.sendMessage(new Message("CONNECTION", "hello", "NO", "hii"));

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void stopThread(Thread th) {
		th = null;
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			this.sendMessage(new Message("CONNECTION ", "hello", "NO", "hii"));

		}

	}

	public void sendMessage(Message msg) {
		try {

			this.output.writeObject(msg);
			this.output.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
