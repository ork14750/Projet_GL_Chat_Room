package com.kede;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Methode permettant de gérer les Thread du serveur/client
 * @author Christophe Kede
 *
 */
public class Wthread extends Thread {

	private Wserver server;
	private Socket socket;

	private ObjectInputStream input = null;
	private ObjectOutputStream output = null;
	private int ID;

	private String clientLogin = "";

	/**
	 * Initialisation de Wthread
	 * @param server  Wserver
	 * @param socket  Socket
	 */
	public Wthread(Wserver server, Socket socket) {
		super();
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
	}

	/**
	 * Lancer le thread
	 */
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

	/**
	 * Get login client
	 * @return String login client
	 */
	public String getClientLogin() {
		return clientLogin;
	}
	
	/**
	 * Set login client
	 */
	public void setClientLogin(String clientLogin) {
		this.clientLogin = clientLogin;
	}

	/**
	 * Methode d'envoie de message vers un client
	 * @param msg Message
	 */
	public void sendMessage(Message msg) {
		try {
			this.output.writeObject(msg);
			this.output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(ID + " ERROR reading: " + e.getMessage());
            server.removeClient(ID);
            stop();
			e.printStackTrace();
		}

	}

	/**
	 * Get ID du thread
	 * @return int
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Initiliaser les input/ouput du serveur
	 */
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
	/**
	 * Return le socket client
	 * @return socket
	 */
	public Socket getSocket() {
		return socket;
	}
	
	/**
	 * Fermer socket, input et ouput d'un client
	 * @throws IOException
	 */
	public void close() throws IOException {  
    	if (socket != null)    socket.close();
        if (input != null)  input.close();
        if (output != null) output.close();
    }

}
