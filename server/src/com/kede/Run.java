package com.kede;

import com.kede.database.Database;

public class Run {

	public static void main(String[] args) {
		// new Wserver();
		// new ServerUI();
		Database db = Database.getInstance();
		db.addUser("chri", "kede");
		System.out.println(db.checkLogin("chridd", "kede"));
	}

}
