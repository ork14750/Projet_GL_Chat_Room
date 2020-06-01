package com.kede;

import java.io.Serializable;

/**
 * La classe permettant de definir la strucuture d'un message échangé entre 
 * Le client et le serveur 
 * @author Christophe
 *
 */


public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	public String type, sender, body, recipient;
	
	/**
	 * Le constructor nous permettant de créer un nouveau message
	 * 
	 * @param type
	 * @param sender
	 * @param body
	 * @param recipient
	 */

	public Message(String type, String sender, String body, String recipient) {
		this.type = type;
		this.sender = sender;
		this.body = body;
		this.recipient = recipient;
	}

	@Override
	public String toString() {
		return "{type='" + type + "', sender='" + sender + "', body='" + body + "', recipient='" + recipient + "'}";
	}
}