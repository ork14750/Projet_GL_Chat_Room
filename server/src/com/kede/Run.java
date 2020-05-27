package com.kede;

import com.kede.database.Database;

public class Run {

	public static void main(String[] args) {
		// new Wserver();
		// new ServerUI();
		Database db = new Database("hello");
		db.addUser("chri", "kede");
	}

}
