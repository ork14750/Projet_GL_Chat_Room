package com.kede;

import java.io.Serializable;

public class SMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	public String type, sender, body, recipient;

	public SMessage(String type, String sender, String body, String recipient) {
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