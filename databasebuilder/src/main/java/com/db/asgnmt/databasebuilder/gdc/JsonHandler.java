package com.db.asgnmt.databasebuilder.gdc;
import com.google.gson.Gson;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
public class JsonHandler {

    private GDC gdc;

    public JsonHandler() throws SQLException {
        this.gdc = new GDC();
        this.getJson();
    }
    private void getJson() throws SQLException {

        Connection connection = JDBCUtils.getConnection("jdbc:mysql://34.143.250.187:3306/thailand_server", "root", "raj123");

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT json_data FROM gdc WHERE id = 1;");

        String jsonData = null;

        while (resultSet.next()) {
            jsonData = resultSet.getString("json_data");
        }

        Gson gson = new Gson();

        this.gdc = gson.fromJson(jsonData, GDC.class);
    }

    public Table getTable(String tableName){
        return this.gdc.getGDC().get(tableName);
    }

    public Set<String> getTableNames(){
        return this.gdc.getGDC().keySet();
    }

}
