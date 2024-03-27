package com.db.asgnmt.databasebuilder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.db.asgnmt.databasebuilder.gdc.JDBCUtils;
import com.db.asgnmt.databasebuilder.gdc.JsonHandler;
import com.db.asgnmt.databasebuilder.gdc.Table;
import com.db.asgnmt.databasebuilder.services.UserService;


public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, SQLException {
        UserService userService = new UserService();
        userService.controller();

    }
}
