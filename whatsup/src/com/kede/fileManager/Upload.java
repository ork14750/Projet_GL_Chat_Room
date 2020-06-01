package com.kede.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.kede.ihm.ClientIhm;

/**
 * Classe pour la Gestion des uploads
 * @author Christophe Kede
 *
 */
public class Upload implements Runnable{
	
	private int port;
	private String addr;
	private Socket sckt;
	private File file;
	private FileInputStream input;
	private OutputStream output;
	private ClientIhm ihm;
	
	/**
	 * Initialisation d'un upload
	 * @param addr Destination
	 * @param port Port
	 * @param fpath  Fichier
	 * @param ihm  ClientIhm
	 */
	public Upload(String addr, int port, File fpath, ClientIhm ihm) {
		super();
		
		
		try {
			this.file = fpath;
			this.ihm = ihm;
			sckt = new Socket(InetAddress.getByName(addr), port);
			output = sckt.getOutputStream();
			input = new FileInputStream(fpath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * Processus d'upload
	 */
	@Override
	public void run() {
		 try {       
	            byte[] buffer = new byte[1024];
	            int count;
	            
	            while((count = input.read(buffer)) >= 0){
	                output.write(buffer, 0, count);
	            }
	            output.flush();
	            
	            ihm.logIt("Whatsup", "Moi", "Le fichier a été uploadé");
	            //ui.jButton5.setEnabled(true); ui.jButton6.setEnabled(true);
	            //ui.jTextField5.setVisible(true);
	            
	            if(input != null){ input.close(); }
	            if(output != null){ output.close(); }
	            if(sckt != null){ sckt.close(); }
	        }
	        catch (Exception ex) {
	            System.out.println("erreur upload "+ex);
	            ex.printStackTrace();
	        }
		
	}
	
	
	
}
