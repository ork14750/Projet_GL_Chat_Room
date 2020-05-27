package com.kede.fileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.kede.ihm.ClientIhm;

public class Upload implements Runnable{
	
	private int port;
	private String addr;
	private Socket sckt;
	private File file;
	private FileInputStream input;
	private OutputStream output;
	private ClientIhm ihm;
	
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

	@Override
	public void run() {
		 try {       
	            byte[] buffer = new byte[1024];
	            int count;
	            
	            while((count = input.read(buffer)) >= 0){
	                output.write(buffer, 0, count);
	            }
	            output.flush();
	            
	            ihm.logIt("Whatsup", "Moi", "Le fichier a �t� upload�");
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
