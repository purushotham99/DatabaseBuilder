package com.db.asgnmt.databasebuilder.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryWriter {
	
	static String table_path = "./database/tables/";
	
	public boolean createTable(String name, ArrayList<String> column) throws IOException {
		
		boolean status = this.checkTable(name);
		if(status!=true) {
		String header = column.get(0);
		for(int i=1;i<column.size();i++)
			header += "||"+column.get(i);

		BufferedWriter writer = new BufferedWriter(new FileWriter(table_path+name+".txt"));
		writer.write(header);
		
		writer.close();
		return true;
		}
		else {
			System.out.println("Another table found with the same name, Please try again!");
			return false;
		}
	}
	
	public boolean checkTable(String name) {
		File f = new File(table_path+name+".txt");
		if(!f.exists() && !f.isDirectory()) { 
		    return false;
		}
		return true; // true if exists
	}

	public boolean insertIntoTable(String name, ArrayList<String> column) throws IOException {
		
		boolean status = this.checkTable(name);
		if(status==true) {
		String data = column.get(0);
		for(int i=1;i<column.size();i++)
			data += "||"+column.get(i);

		BufferedWriter writer = new BufferedWriter(new FileWriter(table_path+name+".txt", true));
		writer.write("\n"+data);
		
		writer.close();
		return true;
		}
		else {
			System.out.println("Please enter a valid table name.");
			return false;
		}
	}

	public HashMap<Integer, HashMap<String, String>> loadTable(String name) throws IOException {
		boolean status = this.checkTable(name);
		if(status==true) {
		
			HashMap<Integer, HashMap<String, String>> table = new HashMap<>(); 
			
			BufferedReader reader = new BufferedReader(new FileReader(table_path+name+".txt"));
	        String header[] = reader.readLine().split("\\|\\|");
	        
			String row = reader.readLine();
			int count = 0;
			while (row!= null) {
//				System.out.println(row);
	            String[] temp = row.split("\\|\\|");
	            HashMap<String, String> tempMap = new HashMap<>();
	            for(int i=0;i<temp.length;i++){
	            	
	            	tempMap.put(header[i],temp[i]);
	            }	           
	            table.put(count, tempMap );
	            row = reader.readLine();
	            count++;
			}
			reader.close();
			return table;
		}
		else {
			System.out.println("Please enter a valid table name.");
		}
		
		return null;
	}

	public String getHeaders(String name) throws IOException {
		boolean status = this.checkTable(name);
		if(status==true) {
			BufferedReader reader = new BufferedReader(new FileReader(table_path+name+".txt"));
	        return reader.readLine();
	        
			
		}
		else {
			System.out.println("Please enter a valid table name.");
		}
		return null;
	}
}
