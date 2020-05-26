package com.kede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Wthread extends Thread {

	private Wserver server;
	private Socket socket;
	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private int ID;

	private String clientLogin = "";

	public Wthread(Wserver server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
	}

	// @SuppressWarnings("deprecation")
	@Override
	public void run() {

		this.server.getIhm().logIt("Server Thread " + ID + " running.");
		while (true) {
			try {
				Message msg = (Message) this.input.readObject();
				System.out.println(msg.body);
				server.messageHandler(this.ID, msg);

			} catch (Exception e) {
				System.out.println(e);
				stop();
				// server.remove(port);
			}
		}

	}

	public String getClientLogin() {
		return clientLogin;
	}

	public void setClientLogin(String clientLogin) {
		this.clientLogin = clientLogin;
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

	public int getID() {
		return ID;
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
