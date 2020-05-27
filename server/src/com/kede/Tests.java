package com.kede;

class Tests {

	@Test
	void MessageType() {
		SMessage message = new SMessage("TEST", "Chris", "salut", "Jean Bapt");

		assertEquals("{type='TEST', sender='Chris', body='salut', recipient='Jean Bapt'}", message.toString());
	}

}
