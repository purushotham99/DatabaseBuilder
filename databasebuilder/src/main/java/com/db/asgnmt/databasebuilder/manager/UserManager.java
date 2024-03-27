package com.db.asgnmt.databasebuilder.manager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.db.asgnmt.databasebuilder.entities.User;
import com.db.asgnmt.databasebuilder.persistence.UserWriter;


public class UserManager {
	
	static UserWriter wr = new UserWriter();
	static String user_path = "./database/user.txt";
	
	
	public boolean saveUser(User user) throws IOException {
		File f = new File(user_path);
		if(!f.exists() && !f.isDirectory()) { 
		    wr.createFile(user_path, user.getHeaders());
		}
		HashMap<String, String[]> users = wr.loadUsers(user_path);
		
		if(users.containsKey(user.getUsername())) {
			return false;
		}
		wr.insertData(user_path, user.toString());
		return true;
	}
	
	public String login(String uname, String pass, String captcha, String userInput) throws IOException, InterruptedException {
		
		HashMap<String, String[]> users = wr.loadUsers(user_path);
		
		if(users.containsKey(uname) && (users.get(uname)[1].equals(pass)) && (captcha.equals(userInput))) {
			System.out.println("Login Successfull");
			return "Success";
		}
		else {
			System.out.println("Login Failed, please try again!");
			return "Failed";
		}
		
	}
}
