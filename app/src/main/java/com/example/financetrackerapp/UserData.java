package com.example.financetrackerapp;

import java.sql.Timestamp;
import java.util.ArrayList;

public class UserData {
    public static boolean debug = false;
    public static int accid,userid;
    public static float totalBalance,totalIncome,totalExpenses;
    public static String name = "User", email = "email";
    public static ArrayList<Wallet> wallets = new ArrayList<>();
    public static ArrayList<Transaction> transactions = new ArrayList<>();
}
class Wallet{
    public Wallet(int id, String name, float balance, int userid1,int userid2){
        this.id=id;
        this.name=name;
        this.balance=balance;
        this.userid1=userid1;
        this.userid2=userid2;
    }
    public int id, userid1, userid2;
    public String name;
    public float balance;
}
class Transaction{
    public int id,walletid,userid;
    public String description,category;
    public float amount;
    public Timestamp datetime;
}