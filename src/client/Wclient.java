package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

			// send(new Message("upload_res", "hello", "NO", "hii"));
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
		// TODO Auto-generated method stub

	}

	public void sendMessage(Message msg) {
		try {
			this.output.writeObject(msg);
			this.output.flush();
			System.out.println("User message : " + msg.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
