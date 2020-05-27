package com.kede;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.kede.database.Database;
import com.kede.ihm.ServerIhm;

public class Wserver implements Runnable {

	private Wthread clients[];
	private ServerSocket server = null;
	private Thread thread = null;
	private int nbClients = 0;
	private int sPort = 3000;
	private ServerIhm ihm;

	public Wserver(ServerIhm ihm) {
		this.ihm = ihm;
		clients = new Wthread[10];

		try {
			server = new ServerSocket(sPort);
			sPort = server.getLocalPort();

			ihm.textArea_1
					.append("Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());

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
				if (this.nbClients == 0) {
					ihm.textArea_1.append("\nAttente de connection client ...");
				}

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
		if (msg.type.equals("CONNECTION")) {
			 clients[this.findClientByID(ID)].sendMessage(new Message("CONNECTION", "SERVER", "OK", msg.sender));
			 ihm.textArea_1.append("\nUn nouveau client s'est connecté au serveur !");
		
		}else if (msg.type.equals("LOGIN")) {
			System.out.println(msg.sender+" "+msg.body);
			if(Database.getInstance().chckLogin(msg.sender, msg.body)) {
				clients[this.findClientByID(ID)].setClientLogin(msg.sender);
				clients[this.findClientByID(ID)].sendMessage(new Message("LOGIN", "SERVER", "OK", msg.sender));
				this.diffuseMessage("NEW_USER", "SERVER", msg.sender);
				this.sendClientList(msg.sender);
			}else {
				clients[this.findClientByID(ID)].sendMessage(new Message("LOGIN", "SERVER", "KO", msg.sender));

			}
			

		} else if(msg.type.equals("MESSAGE")){
			System.out.println(msg);
            if(msg.recipient.equals("TOUT LE MONDE")){
                diffuseMessage("MESSAGE", msg.sender, msg.body);
            }
            else{
                findClientByLogin(msg.recipient).sendMessage(new Message(msg.type, msg.sender, msg.body, msg.recipient));
                clients[findClientByID(ID)].sendMessage(new Message(msg.type, msg.sender, msg.body, msg.recipient));
            }
        }else if (msg.type.equals("WINDOW_CLOSED")){
            this.diffuseMessage("SIGNOUT", "SERVER", msg.sender);
            removeClient(ID); 
        }else if (msg.type.equals("SIGNUP")) {
			if(!Database.getInstance().chckLogin(msg.sender, msg.body)) {
				Database.getInstance().addUser(msg.sender, msg.body);
				
				clients[this.findClientByID(ID)].setClientLogin(msg.sender);
				clients[this.findClientByID(ID)].sendMessage(new Message("SIGNUP", "SERVER", "OK", msg.sender));
				clients[this.findClientByID(ID)].sendMessage(new Message("LOGIN", "SERVER", "OK", msg.sender));
				this.diffuseMessage("NEW_USER", "SERVER", msg.sender);
				this.sendClientList(msg.sender);
			}else {
				clients[this.findClientByID(ID)].sendMessage(new Message("SIGNUP", "SERVER", "KO", msg.sender));

			}
			

		}
	
	
	
	}

	public ServerIhm getIhm() {
		return this.ihm;   
	}

	public int findClientByID(int ID) {
		for (int i = 0; i < this.nbClients; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public Wthread findClientByLogin(String login) {
		for (int i = 0; i < this.nbClients; i++) {
			if (clients[i].getClientLogin().equals(login)) {
				return clients[i];
			}
		}
		return null;
	}

	public void diffuseMessage(String type, String sender, String body) {
		Message msg = new Message(type, sender, body, "TOUT LE MONDE");
		for (int i = 0; i < this.nbClients; i++) {
			clients[i].sendMessage(msg);
		}
	}
	
	public synchronized void removeClient(int ID) {
		int position = this.findClientByID(ID);
		if (position >= 0){  
			Wthread clientToRemove = clients[position];
            this.ihm.textArea_1.append("\nRemoving client thread " + ID + " at " + position);
	    if (position < this.nbClients-1){
                for (int i = position+1; i < this.nbClients; i++){
                    clients[i-1] = clients[i];
	        }
	    }
	    this.nbClients--;
	    try{  
	      	clientToRemove.close();
	    }
	    catch(IOException ioe){  
	      	this.ihm.textArea_1.append("\nError closing thread: " + ioe); 
	    }
	    clientToRemove.stop(); 
	}
		
		
	}
	
	public void sendClientList(String user){
		 for(int i = 0; i < nbClients; i++){
	            findClientByLogin(user).sendMessage(new Message("NEW_USER", "SERVER", clients[i].getClientLogin(), user));
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
