package com.kede;

import com.kede.database.Database;

public class Run {

	public static void main(String[] args) {
		// new Wserver();
		// new ServerUI();
		Database db = Database.getInstance();
		String[] groupUser = {"toto","juttonior", "ketotkde"};
		
		//System.out.println(db.chckLogin("chris", "kede"));
		//db.addGroup("test2", "chris",groupUser );
		//db.addGroup("test5", "chris",groupUser );
		//System.out.println(db.getUserGroups("toto"));
		//System.out.println(db.getUserGroups("junior"));
		System.out.println(db.getUsersByGroup("ff"));


		
		
	}

}
