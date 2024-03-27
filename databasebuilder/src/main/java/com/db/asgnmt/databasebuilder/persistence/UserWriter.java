package com.db.asgnmt.databasebuilder.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class UserWriter {
		
	public void insertData(String path, String data) throws IOException {
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		writer.write("\n"+data);
		
		writer.close();
		System.out.println("Registration Successfull!!");
	}
	
	public void createFile(String path, String headers) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
		writer.write(headers);
		
		writer.close();
	}
	
	public String[] readFileHeaders(String path) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String header = reader.readLine();
		reader.close();
		
		return header.split("\\|\\|");
	}
	
	public HashMap<String, String[]> loadUsers(String path) throws IOException {
		HashMap<String, String[]> users = new HashMap<>(); 
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
        String row;
        
		while ((row = reader.readLine())!= null) {
            String[] temp = row.split("\\|\\|");
			users.put(temp[0], temp);
        }
		reader.close();
		return users;
	}
}
