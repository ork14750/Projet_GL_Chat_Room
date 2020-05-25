package server;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void MessageType() {
		Message message = new Message("TEST", "Chris", "salut", "Jean Bapt");

		assertEquals("{type='TEST', sender='Chris', body='salut', recipient='Jean Bapt'}", message.toString());
	}

}
