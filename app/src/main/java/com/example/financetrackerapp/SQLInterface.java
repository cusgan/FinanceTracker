package com.example.financetrackerapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SQLInterface {
    public static final String IP = "10.0.2.2";
    public static final String URL = "jdbc:mysql://"+IP+":3306/dbspendsmart";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

    static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("DB Connection Success!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
    }
    public static boolean createTables(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicBoolean success = new AtomicBoolean(false);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute("" +
                        "CREATE TABLE IF NOT EXISTS `dbspendsmart`.`tblaccount` " +
                        "(`accid` INT NOT NULL AUTO_INCREMENT , `userid` INT NOT NULL , `email` VARCHAR(50) NOT NULL " +
                        ", `password` VARCHAR(100) NOT NULL , PRIMARY KEY (`accid`));");
                success.set(true);
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        return success.get();
    }
    public static boolean signup(final String email, final String password, final String name){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicBoolean success = new AtomicBoolean(false);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                        "INSERT INTO " +
                        "tblaccount (email, password)" +
                        "values ('"+email+"','"+password+"')"
                );
                success.set(true);
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        return success.get();
    }
}