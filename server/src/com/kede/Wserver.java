package com.kede;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.kede.database.Database;
import com.kede.ihm.ServerIhm;
/**
 * La classe Serveur, qui va gérer les échanges entres clients 
 * @author Christophe kede
 * 
 *
 */

public class Wserver implements Runnable {

	private Wthread clients[];
	private ServerSocket server = null;
	private Thread thread = null;
	private int nbClients = 0;
	private int sPort;
	private ServerIhm ihm;
	
	/**
	 * Le constructeur prend parametre
	 * @param ihm  l'interface utilisateur d'administration du serveur
	 * @param sPort Le port d'ecoute du serveur
	 */
	public Wserver(ServerIhm ihm, int  sPort) {
		new Database(ihm.filePath);
		System.out.println(ihm.filePath);
		this.sPort = sPort;
		this.ihm = ihm;
		clients = new Wthread[10];

		try {
			server = new ServerSocket(sPort);
			sPort = server.getLocalPort();

			ihm.textArea_1
					.append("Serveur lancer. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort()+"\n");
			ihm.btnNewButton_1.setEnabled(false);

			this.startTheard();
		} catch (IOException ioe) {
			ihm.textArea_1.append("Le port : " + sPort + " est déjà utilisé !\n");
			
		}
	}



	/**
	 * Lancement du thread Serveur
	 * Le serveur Attend la connexion d'un client
	 */
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

	/**
	 * Méthode permettant de lancer un Thread
	 */
	public void startTheard() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Methode permettant de lancer un nouveau Wthread, pour les clients qui se connectent
	 * au serveur
	 * @param socket, socket du client
	 * @throws IOException
	 */
	public void addClientThread(Socket socket) throws IOException {

		this.clients[this.nbClients] = new Wthread(this, socket);

		Wthread currentClient = this.clients[this.nbClients];

		currentClient.openConnection();
		currentClient.start();

		System.out.println("Hello new client " + currentClient.getName());
		this.nbClients++;

	}
	
	/**
	 * Methode qui permet d'ecouter les inputs client recus par le serveur
	 * @param ID, ID du client
	 * @param msg Message 
	 */
	public synchronized void messageHandler(int ID, Message msg) {
		if (msg.type.equals("CONNECTION")) {
			 clients[this.findClientByID(ID)].sendMessage(new Message("CONNECTION", "SERVER", "OK", msg.sender));
			 ihm.textArea_1.append("\nUn nouveau client s'est connecté au serveur !");
			 this.ihm.lblNewLabel_4.setText(Integer.toString(nbClients));
		
		}else if (msg.type.equals("LOGIN")) {
			System.out.println(msg.sender+" "+msg.body);
			if(this.findClientByLogin(msg.sender) == null) {
				if(Database.getInstance(this.ihm.filePath).chckLogin(msg.sender, msg.body)) {
					String clientGroup = Database.getInstance(this.ihm.filePath).getUserGroups(msg.sender);
					
					clients[this.findClientByID(ID)].setClientLogin(msg.sender);
					clients[this.findClientByID(ID)].sendMessage(new Message("LOGIN", "SERVER", clientGroup, msg.sender));
					
					this.diffuseMessage("NEW_USER", "SERVER", msg.sender);
					this.sendClientList(msg.sender);
				}else {
					clients[this.findClientByID(ID)].sendMessage(new Message("LOGIN", "SERVER", "KO", msg.sender));

				}
				
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
			if(!Database.getInstance(this.ihm.filePath).chckLogin(msg.sender, msg.body)) {
				Database.getInstance(this.ihm.filePath).addUser(msg.sender, msg.body);
				
				clients[this.findClientByID(ID)].setClientLogin(msg.sender);
				clients[this.findClientByID(ID)].sendMessage(new Message("SIGNUP", "SERVER", "OK", msg.sender));
				clients[this.findClientByID(ID)].sendMessage(new Message("LOGIN", "SERVER", "OK", msg.sender));
				this.diffuseMessage("NEW_USER", "SERVER", msg.sender);
				this.sendClientList(msg.sender);
			}else {
				clients[this.findClientByID(ID)].sendMessage(new Message("SIGNUP", "SERVER", "KO", msg.sender));

			}
			

		}else if(msg.type.equals("REQ_UPLOAD")) {
			this.findClientByLogin(msg.recipient).sendMessage(new Message("REQ_UPLOAD", msg.sender, msg.body, msg.recipient));
			System.out.println(msg);
		
		}else if(msg.type.equals("RES_UPLOAD")) {
			if(!msg.body.equals("KO")) {
				String IP = findClientByLogin(msg.sender).getSocket().getInetAddress().getHostAddress();
				this.findClientByLogin(msg.recipient).sendMessage(new Message("RES_UPLOAD", IP, msg.body, msg.recipient));

			}else {
				this.findClientByLogin(msg.recipient).sendMessage(new Message("RES_UPLOAD", msg.body, msg.body, msg.recipient));

			}
		}else if(msg.type.equals("MESSAGE_GROUP")){
				
				List<String> users = Database.getInstance(this.ihm.filePath).getUsersByGroup(msg.recipient);
				
				for(int i = 0; i<users.size(); i++) {
					System.out.println("User login "+ users.get(i));
					Wthread curr_user = findClientByLogin(users.get(i));
					if(curr_user != null) {
						System.out.println("User find "+ i);
						curr_user.sendMessage(new Message(msg.type, msg.sender, msg.body, msg.recipient));
					}
				}
                
          
            
        }else if(msg.type.equals("NEW_GROUP")) {
        	String[] groupUser = msg.body.split(";");
        	Database.getInstance(this.ihm.filePath).addGroup(msg.recipient, msg.sender, groupUser);
        	
        	List<String> users = Database.getInstance(this.ihm.filePath).getUsersByGroup(msg.recipient);
        	String clientGroup = Database.getInstance(this.ihm.filePath).getUserGroups(msg.recipient);
			
			
			for(int i = 0; i<users.size(); i++) {
				System.out.println("User login "+ users.get(i));
				Wthread curr_user = findClientByLogin(users.get(i));
				if(curr_user != null) {
					System.out.println("User find "+ i);
					curr_user.sendMessage(new Message("ADDED_IN_GROUP", msg.sender, clientGroup, msg.recipient));
				}
			}
        	
        	
        	
        }
	
	
	
	}
	/**
	 * 
	 * @return ServerIhm 
	 */
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

	/**
	 * Trouver le Wthread d'un client grace à son login
	 * @param login client
	 * @return Wthread
	 */
	public Wthread findClientByLogin(String login) {
		for (int i = 0; i < this.nbClients; i++) {
			if (clients[i].getClientLogin().equals(login)) {
				return clients[i];
			}
		}
		return null;
	}

	/**
	 * Envoie de message à tout client loggé au serveur
	 * @param type Type du message
	 * @param sender autheur du message
	 * @param body   contenu du message
	 */
	public void diffuseMessage(String type, String sender, String body) {
		Message msg = new Message(type, sender, body, "TOUT LE MONDE");
		for (int i = 0; i < this.nbClients; i++) {
			clients[i].sendMessage(msg);
		}
	}
	
	/**
	 * Fermer le Wthread d'un client
	 * @param ID id du client
	 */
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
        this.ihm.lblNewLabel_4.setText(Integer.toString(nbClients));

	    try{  
	      	clientToRemove.close();
	    }
	    catch(IOException ioe){  
	      	this.ihm.textArea_1.append("\nError closing thread: " + ioe); 
	    }
	    clientToRemove.stop(); 
	}
		
		
	}
	
	/**
	 * Envoyer la liste d'utilisateur connecté au un client
	 * @param user destinataire
	 */
	public void sendClientList(String user){
		 for(int i = 0; i < nbClients; i++){
	            findClientByLogin(user).sendMessage(new Message("NEW_USER", "SERVER", clients[i].getClientLogin(), user));
	        }
	}
	/**
	 * Convertir une List<String> en chaine de caractère
	 * @param l la liste
	 * @return chaine de caractère
	 */
	public String arrToString(List<String> l) {
		String res ="";
		for(int i =0; i<l.size(); i++) {
			res+=l.get(i)+";";
		}
		return res.substring(0, res.length() -1);
	}

	/**
	 * Arreter le thread du serveur
	 */
	@SuppressWarnings("deprecation")
	public void stopTheard() {
		if (thread == null) {
			thread.stop();
			thread = null;
		}
	}

}
