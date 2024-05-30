package com.example.financetrackerapp;

import java.sql.Timestamp;
import java.util.ArrayList;

public class UserData {
    public static int selectedwallet=0;
    public static int selectedGoal=0;
    public static boolean debug = false;
    public static int accid,userid;
    public static float totalBalance,totalIncome,totalExpenses, monthlyBudget, budgetSpent;
    public static String name = "User", email = "email";
    public static ArrayList<Wallet> wallets = new ArrayList<>();
    public static ArrayList<Transaction> transactions = new ArrayList<>();
    public static ArrayList<Goal> goals = new ArrayList<>();
    public static ArrayList<Notif> notifs = new ArrayList<>();
    public static ArrayList<Invite> invites = new ArrayList<>();
    public static Wallet getWallet(int walletid){
        for(int i=0; i<wallets.size(); i++){
            if(wallets.get(i).id == walletid)
                return wallets.get(i);
        }
        return null;
    }
    public static Budget budget = new Budget();
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
class Goal{
    public int id,userid1, userid2;
    public String name;
    public float amount, balance;
}
class Notif{
    public int id,userid;
    public String text;
}
class Invite{
    public int id,walletid,userid;
    public String text;
}
class Budget{
    public float total=0;
    public float[] category = new float[8];
}