package com.kede;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.kede.fileManager.Download;
import com.kede.fileManager.Upload;
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
			
		} catch (IOException e) {
			
		}

	}

	public void stopThread(Thread th) {
		th = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		boolean running = true;
		while (running) {
			Message msg;
			try {
				msg = (Message) input.readObject();
				System.out.println("Incoming : " + msg.toString() + " "+msg.type.equals("MESSAGE"));
				

				if (msg.type.equals("LOGIN")) {
					if (!msg.body.equals("KO")) {
						this.ihm.btnNewButton_1.setEnabled(false);
						this.ihm.btnNewButton_2.setEnabled(false);
						this.ihm.btnNewButton_3.setEnabled(true);
						this.ihm.btnNewButton_4.setEnabled(true);
						this.ihm.btnNewButton_6.setEnabled(true);
						
						this.ihm.logIt("SERVER", "MOI", "CONNECTION REUSSITE, VOUS POUVEZ COMMENCER A CHATTER");
						String[] groups = msg.body.split(";");
						for(String s: groups) {
							this.ihm.modelGroup.addElement(s);
						}
						System.out.print("Model "+this.ihm.modelGroup+"  ");


					}else {
						this.ihm.logIt("SERVER", "MOI", "CONNECTION ECHOUEE, IDENTIFIANTS INCORRECTES OU VOUS ETES DEJA CONNECTES!");
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
                }else if(msg.type.equals("REQ_UPLOAD")){
                    
                    if(JOptionPane.showConfirmDialog(ihm.getFrame(), ("Accepter '"+msg.body+"' de "+msg.sender+" ?")) == 0){
                        
                        JFileChooser jf = new JFileChooser();
                        jf.setSelectedFile(new File(msg.body));
                        int dialVal = jf.showSaveDialog(ihm.getFrame());
                        
                        String to = jf.getSelectedFile().getPath();
                        
                        if(to != null && dialVal == JFileChooser.APPROVE_OPTION) {
                        	Download dwn = new Download(to, ihm);
                        	Thread thr = new Thread(dwn);
                        	thr.start();
                            sendMessage(new Message("RES_UPLOAD", ihm.getLogin(), (""+dwn.getPort()), msg.sender));

                        }else {
                            sendMessage(new Message("RES_UPLOAD", ihm.getLogin(), "KO", msg.sender));

                        }
                       
                        
                    }else{
                        	sendMessage(new Message("RES_UPLOAD", ihm.getLogin(), "KO", msg.sender));

                    }
                }else if(msg.type.equals("RES_UPLOAD")){
                    if(!msg.body.equals("NO")){
                        int port  = Integer.parseInt(msg.body);
                        String addr = msg.sender;
                        
                        Upload upl = new Upload(addr, port, ihm.getFile(), ihm);
                        Thread t = new Thread(upl);
                        t.start();
                        this.ihm.btnNewButton_4.setEnabled(true);
                    }
                    else{
                        ihm.logIt("SERVER", "MOI",  msg.sender+" a réfusé(e) le fichier");
                    }
                }else if(msg.type.equals("MESSAGE_GROUP")){
                	
                	
                	String from = msg.sender;
                	if(msg.body.equals(this.ihm.getLogin())) from = "moi";
                	ihm.logIt("["+msg.recipient+"] "+from, msg.recipient, msg.body);
                	
                }else if(msg.type.equals("ADDED_IN_GROUP")) {
                	DefaultListModel  list = this.ihm.modelGroup;
                	list.addElement(msg.recipient);
						this.ihm.list.setModel(list);
					
					
					if(msg.sender.equals(this.ihm.getLogin())) {
							try {
								this.ihm.gIhm.getFrame().dispose();
								 ihm.logIt("SERVER", "MOI",  "Votre groupe a été créé : "+msg.recipient);
							}catch(Exception e) {
								 ihm.logIt("SERVER", "MOI",  "Votre groupe a été créé : "+msg.recipient);
							}
						 
					}else {
						System.out.println("addd in grou");
						 ihm.logIt("SERVER", "MOI",  "Vous avez été rajouté dans un nouveau groupe : "+msg.recipient);
					}
					

                	
                	
                }else{
                    ihm.logIt("SERVER", "MOI",  "COMMANDE NON RECONUE");
                }
			} catch (ClassNotFoundException | IOException e) {
				ihm.logIt("ERREUR", "MOI",  "LE SERVER SEMBLE INDISPONIBLE !");
				running = false;
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
