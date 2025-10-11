package com.atmd.library.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    //数据库配置信息
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "heu19530901";

    public static Connection getConnection() throws SQLException{
        try{
            //加载PostgreSQL JDBC驱动
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.err.println("PostgreSQL JDBC Driver not found.Make sure it's your classpath");
            throw new RuntimeException(e);
        }
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
