package com.db.asgnmt.databasebuilder.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.db.asgnmt.databasebuilder.manager.QueryManager;

public class QueryService {
	
	static boolean transaction_mode = false;
	
	public static void getQueries() throws IOException, SQLException {
		String query;
		Scanner sc = new Scanner(System.in);
		do {
		System.out.print(">>");
		query = sc.nextLine();
		
		queryProcessor(query);
		
		}while(!query.equals("q"));
		sc.close();
		
	}
	
	public static void queryProcessor(String query) throws IOException, SQLException {
		boolean res = true;
		
		QueryManager queryManager = new QueryManager();
		if(query.toLowerCase().startsWith("create")) {
			res = queryManager.createHandler(query);
		}
		
		else if(query.toLowerCase().startsWith("insert")) {
			res = queryManager.insertHandler(query);
		}
		
		else if(query.toLowerCase().startsWith("select")) {
			if(query.contains("where")) {
				res = queryManager.selectHandler(query);
			}
			else {
				res = queryManager.selectAll(query);
				
			}
		}
		else if(query.toLowerCase().startsWith("start transaction;")) {
			ArrayList<String> backlog = new ArrayList<>();
			Scanner sc = new Scanner(System.in);
			String s;
			do {
				System.out.print(">>");
				s = sc.nextLine();
				if(s.equals("commit;")) {
					while(!backlog.isEmpty()) {
						queryProcessor(backlog.get(0));
						backlog.remove(0);
					}
					return;
				}
				else if(s.toLowerCase().startsWith("select")){
					queryProcessor(s);					
				}
				else {
					backlog.add(s);
				}
			}while(!s.equalsIgnoreCase("rollback;"));
		}
		
		if(!res) System.out.println("ERROR!! Please check your Syntax");
	}
}
