package com.kede.fileManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.kede.ihm.ClientIhm;

public class Download implements Runnable {

	private Socket sckt;
	private ServerSocket server;
	private int port;
	public int getPort() {
		return port;
	}


	private String to ="";
	public InputStream input;
	public FileOutputStream output;
	
	private ClientIhm ihm;
	
	public Download(String to, ClientIhm ihm) {
		this.to = to; 
		this.ihm = ihm;
		try {
			server = new ServerSocket(0);
			port = server.getLocalPort();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		try {
			sckt = server.accept();
			input = sckt.getInputStream();
			output = new FileOutputStream(to);
			  
			byte[] buffer = new byte[1024];
            int count;
            while((count = input.read(buffer)) >= 0){
                output.write(buffer, 0, count);
            }
            
            output.flush();
            
            ihm.logIt("Whatsup", "Moi", "Téléchargement fini !");
            
            if(output != null){ output.close(); }
            if(input != null){ input.close(); }
            if(sckt != null){ sckt.close(); }
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

}
