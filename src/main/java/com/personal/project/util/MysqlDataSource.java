package com.personal.project.util;

import java.sql.*;

public class MysqlDataSource {

    private static final String url = ConfigProperties.load("mysql.url");
    private static final String uname=ConfigProperties.load("mysql.username");
    private static final String pass=ConfigProperties.load("mysql.password");

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url,uname,pass);
        return conn;
    }


}
