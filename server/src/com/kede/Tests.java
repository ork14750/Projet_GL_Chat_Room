package com.kede;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void MessageType() {
		SMessage message = new SMessage("TEST", "Chris", "salut", "Jean Bapt");

		assertEquals("{type='TEST', sender='Chris', body='salut', recipient='Jean Bapt'}", message.toString());
	}

}
