package com.kede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.kede.ihm.ClientIhm;

public class Wclient implements Runnable {

	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;
	private int port;
	private String address;
	private ClientIhm ihm;

	public Wclient(ClientIhm ihm) {
		this.address = "127.0.0.1";
		this.port = 3000;
		this.ihm = ihm;

		try {
			this.socket = new Socket(InetAddress.getByName(this.address), port);

			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.output.flush();
			this.input = new ObjectInputStream(socket.getInputStream());

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
			Message msg;
			try {
				msg = (Message) input.readObject();
				System.out.println("Incoming : " + msg.toString());

				if (msg.type.equals("LOGIN")) {
					if (msg.body.equals("OK")) {
						this.ihm.btnNewButton_1.setEnabled(false);
						this.ihm.logIt("SERVER", "MOI");

					}
				}

			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
