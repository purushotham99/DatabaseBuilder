package com.db.asgnmt.databasebuilder.entities;

import java.io.Serializable;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
	
	String username;
	String password;
	
	public String toString() {
		return username+"||"+password;
	}
	
	public String getHeaders() {
		return "username||password";
	}
	
	public static String getCaptcha() {
		String chars = "0123456789:;<>?abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder sb = new StringBuilder(5);
		Random random = new Random();
		for(int i=4;i>=0;i--) {            // change i to increase captcha length.
			int ind = random.nextInt(67);
			sb.append(chars.charAt(ind));
		}
		
		return sb.toString();
	}
}
