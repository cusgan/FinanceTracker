package com.example.financetrackerapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main() {
        Connection c = SQLInterface.getConnection();
        String query1 = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "email VARCHAR(50) UNIQUE not null, " +
                "password VARCHAR(50) not null)";
        String query2 = "CREATE TABLE IF NOT EXISTS accounts (" +
                "aid INT PRIMARY KEY AUTO_INCREMENT, " +
                "userid INT not null, " +
                "balance FLOAT default 0, " +
                "FOREIGN KEY (userid) REFERENCES users(id) ON DELETE CASCADE)";
        String query3 = "CREATE TABLE IF NOT EXISTS wallets (" +
                "walletid INT PRIMARY KEY AUTO_INCREMENT, " +
                "accs INT[] ," +//arrays
                "wtitle VARCHAR(100) not null, " +
                "balance FLOAT default 0, " +
                ")";//not finished yet ggrggrgrgr ERD pa
        String query4 = "CREATE TABLE IF NOT EXISTS transactions (" +
                "tid INT PRIMARY KEY AUTO_INCREMENT, " +
                "accid INT not null, " +
                "tdate DATETIME not null, " +
                "tdesc VARCHAR(100) not null, " +
                "content VARCHAR(500) not null, " +
                "FOREIGN KEY (accid) REFERENCES accounts(aid) ON DELETE CASCADE)";
        try {
            Statement statement = c.createStatement();
            statement.execute(query1);
            statement.execute(query2);
            System.out.println("Tables created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}