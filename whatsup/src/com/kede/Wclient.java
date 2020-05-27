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
				System.out.println("Incoming : " + msg.toString() + " "+msg.type.equals("MESSAGE"));
				

				if (msg.type.equals("LOGIN")) {
					if (msg.body.equals("OK")) {
						this.ihm.btnNewButton_1.setEnabled(false);
						this.ihm.btnNewButton_3.setEnabled(true);
						this.ihm.logIt("SERVER", "MOI", "CONNECTION REUSSITE, VOUS POUVEZ COMMENCER A CHATTER");

					}else {
						this.ihm.logIt("SERVER", "MOI", "CONNECTION ECHOUEE, IDENTIFIANTS INCORRECTES");
					}
				}else if(msg.type.equals("CONNECTION")) {
					if(msg.body.equals("OK")) {
						this.ihm.btnNewButton.setEnabled(false);
						this.ihm.btnNewButton_1.setEnabled(true);
						this.ihm.btnNewButton_2.setEnabled(true);
						
						this.ihm.logIt("SERVER", "MOI", "VOUS AVEZ REJOINT LE SERVEUR AVEC SUCCES, VEUILLEZ VOUS LOGGER");

					}
				}else if(msg.type.equals("NEW_USER")){
		            if(!msg.body.equals(this.ihm.getLogin())){
		                boolean exists = false;
		                for(int i = 0; i < this.ihm.modelUser.getSize(); i++){
		                    if(this.ihm.modelUser.getElementAt(i).equals(msg.body)){
		                        exists = true; break;
		                    }
		                }
		                if(!exists){ this.ihm.modelUser.addElement(msg.body);
		                
		                }
		            }
		        }else if(msg.type.equals("MESSAGE")){
                    if(msg.recipient.equals(this.ihm.getLogin())){
                    	System.out.println("message for me");
                       this.ihm.logIt(msg.sender, msg.recipient, msg.body);
                    }
                    else{
                    	this.ihm.logIt(msg.sender, msg.recipient, msg.body);
                    	System.out.println("message for me");
                    }
                                            
              
                } else if(msg.type.equals("SIGNOUT")){
                    if(msg.body.equals(this.ihm.getLogin())){
                        this.ihm.logIt("SERVER", "MOI", "Au revoir !");
                        for(int i = 1; i < this.ihm.modelUser.size(); i++){
                            this.ihm.modelUser.removeElementAt(i);
                        }
                        
                        this.ihm.clientThr.stop();
                    }
                    else{
                    	this.ihm.modelUser.removeElement(msg.body);
                        this.ihm.logIt(msg.sender, "TOUT LE MONDE", msg.body +"s'est déconnecté");
                    }
                }else if (msg.type.equals("SIGNUP")) {
					if (msg.body.equals("OK")) {
						this.ihm.btnNewButton_1.setEnabled(false);
						this.ihm.btnNewButton_3.setEnabled(true);
						this.ihm.btnNewButton_2.setEnabled(false);
						this.ihm.logIt("SERVER", "MOI", "LOGIN en cours...");

					}else {
						this.ihm.logIt("SERVER", "MOI", "LOGIN déjà pris!");
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
