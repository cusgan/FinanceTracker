package com.example.financetrackerapp;

import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SQLInterface {
    public static final String IP = "10.0.2.2";
    public static final String URL = "jdbc:mysql://"+IP+":3306/dbspendsmart";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    private static final long TIMEOUT = 120;

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
                        "(`accid` INT NOT NULL AUTO_INCREMENT ," +
                        "`email` VARCHAR(50) UNIQUE NOT NULL ," +
                        "`password` VARCHAR(100) NOT NULL ," +
                        " PRIMARY KEY (`accid`));");
                Statement statement2 = c.createStatement();
                statement2.execute("" +
                        "CREATE TABLE IF NOT EXISTS `dbspendsmart`.`tbluser` " +
                        "(`userid` INT NOT NULL AUTO_INCREMENT , " +
                        "`accid` INT NOT NULL , `name` VARCHAR(50) NOT NULL ," +
                        "`budget` FLOAT NOT NULL DEFAULT '0'," +
                        "`spent` FLOAT NOT NULL DEFAULT '0'," +
                        " PRIMARY KEY (`userid`));");
                Statement statement3 = c.createStatement();
                statement3.execute("" +
                        "CREATE TABLE IF NOT EXISTS`dbspendsmart`.`tblwallet` " +
                        "(`walletid` INT NOT NULL AUTO_INCREMENT , " +
                        "`walletname` VARCHAR(30) NOT NULL , "+
                        "`balance` FLOAT NOT NULL DEFAULT '0' , " +
                        "`isdeleted` BOOLEAN NOT NULL DEFAULT FALSE , " +
                        "`userid1` INT NOT NULL , `userid2` INT NULL , " +
                        " PRIMARY KEY (`walletid`))");
                Statement statement4 = c.createStatement();
                statement4.execute("" +
                        "CREATE TABLE IF NOT EXISTS`dbspendsmart`.`tblgoal` " +
                        "(`goalid` INT NOT NULL AUTO_INCREMENT , " +
                        "`goalname` VARCHAR(30) NOT NULL , "+
                        "`goalamount` FLOAT NOT NULL DEFAULT '100' , " +
                        "`balance` FLOAT NOT NULL DEFAULT '0' , " +
                        "`isdeleted` BOOLEAN NOT NULL DEFAULT FALSE , " +
                        "`userid1` INT NOT NULL , `userid2` INT NULL , " +
                        " PRIMARY KEY (`goalid`))");

                Statement statement5 = c.createStatement();
                statement5.execute("" +
                        "CREATE TABLE IF NOT EXISTS`dbspendsmart`.`tbltransaction` " +
                        "(`transactionid` INT NOT NULL AUTO_INCREMENT , " +
                        "`userid` INT NOT NULL , "+
                        "`walletid` INT NOT NULL , "+
                        "`transactiondesc` VARCHAR(20) NOT NULL DEFAULT 'Transaction' , " +
                        "`category` VARCHAR(20) NULL , " +
                        "`isdeleted` BOOLEAN NOT NULL DEFAULT FALSE , " +
                        "`amount` FLOAT NOT NULL ," +
                        "`transactiondate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "+
                        " PRIMARY KEY (`transactionid`))");
                Statement statement6 = c.createStatement();
                statement6.execute("" +
                        "CREATE TABLE IF NOT EXISTS `dbspendsmart`.`tblnotif` (" +
                        "`notifid` INT NOT NULL AUTO_INCREMENT , " +
                        "`text` VARCHAR(60) NOT NULL , " +
                        "`userid` INT NOT NULL , PRIMARY KEY (`notifid`)); ");
                Statement statement7 = c.createStatement();
                statement7.execute("" +
                        "CREATE TABLE IF NOT EXISTS `dbspendsmart`.`tblinvite` (" +
                        "`inviteid` INT NOT NULL AUTO_INCREMENT , " +
                        "`text` VARCHAR(60) NOT NULL , " +
                        "`walletid` INT NOT NULL , " +
                        "`userid` INT NOT NULL , " +
                        "`isaccept` BOOLEAN NOT NULL DEFAULT FALSE ,"+
                        "`isanswer` BOOLEAN NOT NULL DEFAULT FALSE ,"+
                        " PRIMARY KEY (`inviteid`)); ");
                success.set(true);
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return success.get();
    }
    public static boolean signupAccount(final String email, final String password, final String name){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicBoolean success = new AtomicBoolean(false);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                        "INSERT INTO " +
                        "tblaccount (email, password)" +
                        "values ('"+email+"','"+password.hashCode()+"')"
                ,Statement.RETURN_GENERATED_KEYS);
                int accid = -1;
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next())
                    accid = generatedKeys.getInt(1);
                else
                    throw new SQLException("No Key Obtained");

                Statement statement2 = c.createStatement();
                statement2.execute(""+
                        "INSERT INTO " +
                        "tbluser (accid, name)" +
                        "values ("+accid+",'"+name+"')"
                ,Statement.RETURN_GENERATED_KEYS);
                Statement statement3 = c.createStatement();
                generatedKeys = statement2.getGeneratedKeys();
                int userid = -1;
                if(generatedKeys.next())
                    userid = generatedKeys.getInt(1);
                statement3.execute(""+
                                "INSERT INTO " +
                                "tblwallet (walletname, balance, userid1)" +
                                "values ('Main Wallet',0,"+userid+")"
                        ,Statement.RETURN_GENERATED_KEYS);
                success.set(true);
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return success.get();
    }
    public static int login(final String email, final String password){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger accid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                ResultSet res = statement.executeQuery(""+
                        "SELECT * " +
                        "FROM tblaccount " +
                        "WHERE email='"+email+"' " +
                        "AND password='"+password.hashCode()+"';"
                );
                while(res.next()) {
                    accid.set(res.getInt("accid"));
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return accid.get();
    }
    public static int accIdToUserId(int accid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger userid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                ResultSet res = statement.executeQuery(""+
                        "SELECT userid " +
                        "FROM tbluser " +
                        "WHERE accid="+accid+";"
                );
                if(res.next())
                    userid.set(res.getInt("userid"));
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return userid.get();
    }
    public static int newWallet(final String walletname, final float balance, final int userid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger walletid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                                "INSERT INTO " +
                                "tblwallet (walletname, balance, userid1)" +
                                "values ('"+walletname+"',"+balance+","+userid+")"
                        ,Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next())
                    walletid.set(generatedKeys.getInt(1));
                else
                    throw new SQLException("No Key Obtained");
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return walletid.get();
    }
    public static int newGoal(final String goalname, final float goalamount, final float balance, final int userid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger goalid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                                "INSERT INTO " +
                                "tblgoal (goalname, goalamount, balance, userid1)" +
                                "values ('"+goalname+"',"+goalamount+","+balance+","+userid+")"
                        ,Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next())
                    goalid.set(generatedKeys.getInt(1));
                else
                    throw new SQLException("No Key Obtained");
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return goalid.get();
    }
    public static int newTransaction(final String description, final String category, final float amount, final int walletid, final int userid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger transid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                                "INSERT INTO " +
                                "tbltransaction (transactiondesc, category, amount, walletid,userid)" +
                                "values ('"+description+"','"+category+"',"+amount+","+walletid+","+userid+")"
                        ,Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next())
                    transid.set(generatedKeys.getInt(1));
                else
                    throw new SQLException("No Key Obtained");
                Statement statement2 = c.createStatement();
                statement2.execute("" +
                        "UPDATE tblwallet SET balance = (balance + "+amount+") " +
                        "WHERE walletid = "+walletid+";");
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return transid.get();
    }
    public static void getUserData(int userid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                ResultSet res = statement.executeQuery(""+
                        "SELECT * " +
                        "FROM tbluser " +
                        "WHERE userid="+userid+";"
                );
                if(res.next()) {
                    UserData.name = res.getString("name");
                    try{
                        UserData.monthlyBudget = res.getFloat("budget");
                        UserData.budgetSpent = res.getFloat("spent");
                    } catch (Exception e){e.printStackTrace();}
                }
                float totalBalance = 0;
                Statement statement2 = c.createStatement();
                res = statement2.executeQuery(""+
                        "SELECT * " +
                        "FROM tblwallet " +
                        "WHERE userid1="+userid+" OR userid2="+userid+";"
                );
                UserData.wallets = new ArrayList<>();
                while(res.next()) {
                    if(res.getBoolean("isdeleted")) continue;
                    float balance = res.getFloat("balance");
                    UserData.wallets.add(new Wallet(
                       res.getInt("walletid"),
                       res.getString("walletname"),
                       balance,
                       res.getInt("userid1"),
                            res.getInt("userid2")
                    ));
                    totalBalance += balance;
                }
                UserData.totalBalance = totalBalance;

                Statement statement3 = c.createStatement();
                res = statement3.executeQuery(""+
                        "SELECT * " +
                        "FROM tbltransaction " +
                        "WHERE userid="+userid+" ;"
                );
                UserData.transactions = new ArrayList<>();
                float income = 0;
                float expenses = 0;
                while(res.next()){
                    if(res.getBoolean("isdeleted")) continue;
                    Transaction t = new Transaction();
                    t.amount = res.getFloat("amount");
                    t.id = res.getInt("transactionid");
                    t.walletid = res.getInt("walletid");
                    t.userid = res.getInt("userid");
                    t.category = res.getString("category");
                    t.description = res.getString("transactiondesc");
                    t.datetime = res.getTimestamp("transactiondate");
                    UserData.transactions.add(t);
                    if(t.amount > 0) income += t.amount;
                    else expenses -=t.amount;
                }
                UserData.totalExpenses = expenses;
                UserData.totalIncome = income;
                UserData.goals = new ArrayList<>();
                Statement statement4 = c.createStatement();
                res = statement4.executeQuery(""+
                        "SELECT * " +
                        "FROM tblgoal " +
                        "WHERE userid1="+userid+" OR userid2 ="+userid+";"
                );
                while(res.next()){
                    Goal g = new Goal();
                    g.id = res.getInt("goalid");
                    g.userid1 = res.getInt("userid1");
                    g.userid2 = res.getInt("userid2");
                    g.amount = res.getFloat("goalamount");
                    g.balance= res.getFloat("balance");
                    g.name = res.getString("goalname");
                    UserData.goals.add(g);
                }
                UserData.invites = new ArrayList<>();
                Statement statement5 = c.createStatement();
                res = statement5.executeQuery(""+
                        "SELECT * " +
                        "FROM tblinvite " +
                        "WHERE userid="+userid+";"
                );
                while(res.next()){
                    Invite invite = new Invite();
                    invite.id = res.getInt("inviteid");
                    invite.text = res.getString("text");
                    invite.walletid = res.getInt("walletid");
                    invite.userid = res.getInt("userid");
                    UserData.invites.add(invite);
                }
                UserData.notifs = new ArrayList<>();
                Statement statement6 = c.createStatement();
                res = statement6.executeQuery(""+
                        "SELECT * " +
                        "FROM tblnotif " +
                        "WHERE userid="+userid+";"
                );
                while(res.next()){
                    Notif invite = new Notif();
                    invite.id = res.getInt("notifid");
                    invite.text = res.getString("text");
                    invite.userid = res.getInt("userid");
                    UserData.notifs.add(invite);
                }
                for(int i=0;i<UserData.recyclers.size(); i++){
                    UserData.recyclers.get(i).notifyDataSetChanged();
                }

            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT+1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
    }
    public static int transactGoal(int goalid, String goalname, int walletid, float amount, int userid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger transid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                                "INSERT INTO " +
                                "tbltransaction (transactiondesc, category, amount, walletid,userid)" +
                                "values ('Goal: "+goalname+"','Goals',"+(-amount)+","+walletid+","+userid+")"
                        ,Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next())
                    transid.set(generatedKeys.getInt(1));
                else
                    throw new SQLException("No Key Obtained");
                Statement statement2 = c.createStatement();
                statement2.execute("" +
                        "UPDATE tblwallet SET balance = (balance - "+amount+") " +
                        "WHERE walletid = "+walletid+";");
                Statement statement3 = c.createStatement();
                statement3.execute("" +
                        "UPDATE tblgoal SET balance = (balance + "+amount+") " +
                        "WHERE goalid = "+goalid+";");
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return transid.get();
    }
    public static void deleteGoal(int goalid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                                "DELETE FROM tblgoal WHERE goalid="+goalid+";"
                );
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
    }
    public static void deleteNotif(int notifid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                        "DELETE FROM tblnotif WHERE notifid="+notifid+";"
                );
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
    }
    public static void deleteWallet(int walletid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute(""+
                        "UPDATE tblwallet SET isdeleted = 1 WHERE walletid="+walletid+";"
                );
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
    }
    public static int sendInvite(int walletid, String targetEmail, String senderName, String walletName){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicInteger inviteid = new AtomicInteger(-1);
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement st = c.createStatement();
                int ue=-1;
                ResultSet r= st.executeQuery("SELECT userid FROM tbluser,tblaccount WHERE tblaccount.email='"+targetEmail+"' AND tbluser.accid=tblaccount.accid");
                if(r.next())
                    ue=r.getInt(1);
                Statement statement = c.createStatement();
                statement.execute(""+
                                "INSERT INTO " +
                                "tblinvite (text, walletid, userid)" +
                                "values ('"+senderName+" invited you to their Wallet : "+walletName+"',"+walletid+","+ue+")"
                        ,Statement.RETURN_GENERATED_KEYS);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next())
                    inviteid.set(generatedKeys.getInt(1));
                else
                    throw new SQLException("No Key Obtained");
            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
        return inviteid.get();
    }
    public static void acceptInvite(int inviteid){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(()->{
            try(Connection c = getConnection()){
                Statement statement = c.createStatement();
                statement.execute("" +
                                "UPDATE tblinvite SET isaccept = 1 WHERE inviteid="+inviteid+";"
                        ,Statement.RETURN_GENERATED_KEYS);
                Statement statement2 = c.createStatement();
                ResultSet res = statement2.executeQuery("" +
                        "SELECT * FROM tblinvite WHERE inviteid ="+inviteid);
                int userid=-1, walletid=-1;
                if(res.next()){
                    userid = res.getInt("userid");
                    walletid = res.getInt("walletid");
                }
                Statement statement3 = c.createStatement();
                statement3.execute("" +
                        "UPDATE tblwallet SET userid2 = "+userid+" " +
                        "WHERE walletid="+walletid+";");

            } catch(SQLException e){
                e.printStackTrace();
            }
        });
        try { executor.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) { e.printStackTrace();}
    }
}