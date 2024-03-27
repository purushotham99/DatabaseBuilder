package com.db.asgnmt.databasebuilder.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import com.db.asgnmt.databasebuilder.entities.User;
import com.db.asgnmt.databasebuilder.logo.Logo;
import com.db.asgnmt.databasebuilder.manager.UserManager;

public class UserService {
	
	Scanner sc = new Scanner(System.in);
	static UserManager userManager = new UserManager();
	
	public void register() throws IOException {
		Logo.displayLogo();
		
		boolean status;
		do {
		System.out.println("Select your username");
		String uname = sc.next();
		System.out.println("Select your password");
		Integer pass1 = sc.next().hashCode();	
		String pass = pass1.toString();
		
		status = userManager.saveUser(new User(uname, pass));
		if(!status) {
			System.out.println("The selected username Exists, please enter another username");
		}
		
		
		}while(!status);
	}
	
	public void login() throws IOException, InterruptedException, SQLException {
		
		System.out.println("Enter username");
		String uname = sc.next();
		System.out.println("Enter password");
		Integer pass1 = sc.next().hashCode();	
		String pass = pass1.toString();
		String captcha = User.getCaptcha();
		System.out.println("Enter the Captcha: "+ captcha);
		String answer = sc.next();
		
		String status = userManager.login(uname,pass,captcha,answer);
		
		if(status.equals("Success")) {
			System.out.println("\n\n");
			System.out.println("------------------Entered Dashboard--------------------");
			QueryService.getQueries();
			
		}
		else if(status.equals("try again")) {
			
			
		}
		
	}

	public void controller() throws IOException, InterruptedException, SQLException {
		int inp = -1;
		
		System.out.println("--------LOGIN MENU----------");
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("0. Exit");

		inp = sc.nextInt();
		
		switch(inp) {
			case 2: this.register();
			
			case 1:System.out.println("-------------LOGIN--------------"); 
				this.login();
					break;
					
			case 0: break;                      //TBD
			
			default:System.out.println("please enter a valid response");
			}
		
		
	}
}
