package com.db.asgnmt.databasebuilder.manager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSet;
import com.db.asgnmt.databasebuilder.gdc.JDBCUtils;
import com.db.asgnmt.databasebuilder.gdc.JsonHandler;
import com.db.asgnmt.databasebuilder.gdc.Table;
import com.db.asgnmt.databasebuilder.logo.Logo;
import com.db.asgnmt.databasebuilder.persistence.QueryWriter;

public class QueryManager {

    public JsonHandler jsonHandler;

    public QueryManager() throws SQLException {
        this.jsonHandler = new JsonHandler();
    }

    //select * from table_name;
    public boolean selectAll(String query) throws IOException {

        String tableName = query.split(" ")[3].replace(";", "");

        // Rerouting to remote DB based on the table name
        if (jsonHandler.getTableNames().contains(tableName)) {
            Table table = jsonHandler.getTable(tableName);
            System.out.println("Connecting to remote Server in: "+ table.getServerLocation());
            Connection connection = JDBCUtils.getConnection(table.getServerUrl(), table.getServerUname(), table.getServerPass());

            try {

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        if (i > 1) {
                            row.append(" || ");
                        }
                        row.append(resultSet.getString(i));
                    }
                    System.out.println(row.toString());
                }
                System.out.println("Fetched from remote DB.");
                resultSet.close();
                statement.close();
                JDBCUtils.closeConnection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            QueryWriter queryWriter = new QueryWriter();

            HashMap<Integer, HashMap<String, String>> table = queryWriter.loadTable(tableName);
            String headers = queryWriter.getHeaders(tableName);
            System.out.println(headers);
            String header_arr[] = headers.split("\\|\\|");
            for (int i = 0; i < table.size(); i++) {


                System.out.print(table.get(i).get(header_arr[0]));

                for (int j = 1; j < header_arr.length; j++) {
                    System.out.print(" | " + table.get(i).get(header_arr[j]));

                }
                System.out.println("");

            }
            System.out.println();
        }
        return true;

    }

    //create table CSCI5408 ( assignmentName varchar(255), studentName varchar(255), grade varchar(255));
    public boolean createHandler(String query) throws IOException {

        String tableName = query.substring(0, query.indexOf('(')).trim().split(" ")[2];

        // Rerouting to remote DB based on the table name
        if (jsonHandler.getTableNames().contains(tableName)) {
            Table table = jsonHandler.getTable(tableName);
            System.out.println("Connecting to remote Server in: "+ table.getServerLocation());
            Connection connection = JDBCUtils.getConnection(table.getServerUrl(), table.getServerUname(), table.getServerPass());

            try {

                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                statement.close();
                JDBCUtils.closeConnection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            ArrayList<String> col = new ArrayList<>();

            String columnDefinitions = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")"));

            for (String s : columnDefinitions.split(",")) {
                String[] temp = s.trim().split(" ");
                col.add(temp[0]);                                  //use temp[1] for datatypes.
            }


            QueryWriter queryWriter = new QueryWriter();

            queryWriter.createTable(tableName, col);
        }
        return true;
    }

    //SELECT * FROM Persons where FirstName = purushotham;
    public boolean selectHandler(String query) throws IOException {
        QueryWriter queryWriter = new QueryWriter();

        String tableName = query.split(" ")[3];
        String columnName = query.split(" ")[5];
        String value = query.substring(query.indexOf("=") + 1, query.indexOf(";")).trim();

        // Rerouting to remote DB based on the table name
        if (jsonHandler.getTableNames().contains(tableName)) {
            Table table = jsonHandler.getTable(tableName);
            System.out.println("Connecting to remote Server in: "+ table.getServerLocation());
            Connection connection = JDBCUtils.getConnection(table.getServerUrl(), table.getServerUname(), table.getServerPass());

            try {

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    StringBuilder row = new StringBuilder();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        if (i > 1) {
                            row.append(" || ");
                        }
                        row.append(resultSet.getString(i));
                    }
                    System.out.println(row.toString());
                }
                System.out.println("Fetched from remote DB.");
                resultSet.close();
                statement.close();
                JDBCUtils.closeConnection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {


            String headers = queryWriter.getHeaders(tableName);
            String header_arr[] = headers.split("\\|\\|");

            HashMap<Integer, HashMap<String, String>> table = queryWriter.loadTable(tableName);
            if (table == null) {
                return false;
            }

            System.out.println(headers);

            if (!table.get(0).containsKey(columnName)) {
                System.out.println("ERROR! Column name not found!");
                return false;
            }

            for (int i = 0; i < table.size(); i++) {

                if (table.get(i).get(columnName).equals(value)) {
                    System.out.print(table.get(i).get(header_arr[0]));

                    for (int j = 1; j < header_arr.length; j++) {
                        System.out.print(" | " + table.get(i).get(header_arr[j]));

                    }
                    System.out.println("");

                }
            }

        }
        return true;
    }

    //insert into CSCI5408 values(Assignment1, Purushotham, A+);
    public boolean insertHandler(String query) throws IOException {
        String tableName = query.substring(0, query.indexOf('(')).trim().split(" ")[2];

        // Rerouting to remote DB based on the table name
        if (jsonHandler.getTableNames().contains(tableName)) {
            Table table = jsonHandler.getTable(tableName);
            System.out.println("Connecting to remote Server in: "+ table.getServerLocation());

            Connection connection = JDBCUtils.getConnection(table.getServerUrl(), table.getServerUname(), table.getServerPass());

            try {

                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                statement.close();
                JDBCUtils.closeConnection();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            ArrayList<String> col = new ArrayList<>();

//          insert into table values("","","","","");
            String columnValues = query.substring(query.indexOf("(") + 1, query.lastIndexOf(")"));

            for (String s : columnValues.split(",")) {
                col.add(s.trim());
            }

            QueryWriter queryWriter = new QueryWriter();

            queryWriter.insertIntoTable(tableName, col);
        }
        return true;
    }
}


