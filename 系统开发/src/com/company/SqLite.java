package com.company;
import java.sql.*;


public class SqLite {

    public void create(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
         //   System.out.println("Opened database successfully");
            stmt = c.createStatement();
         //  String sql9 = "DROP TABLE IF EXISTS USER";
         //  stmt.executeUpdate(sql9);
            String sql = "CREATE TABLE  IF NOT EXISTS USER " +
                    "(ID    TEXT  PRIMARY KEY  NOT NULL," +
                    " NAME  TEXT       NOT NULL," +
                    " PASSWORD   TEXT    NOT NULL," +
                    " TYPE     INT      NOT NULL," +
                    " LEVEL    INT      NOT NULL," +
                    " RETIME    TEXT    NOT NULL," +
                    " MONEY     INT     NOT NULL," +
                    " PHONE     TEXT     NOT NULL," +
                    " MAILBOX   TEXT     NOT NULL)";
            stmt.executeUpdate(sql);


          //  String sql99 = "DROP TABLE IF EXISTS COMMODITY";
           // stmt.executeUpdate(sql99);

            String sql1 = "CREATE TABLE  IF NOT EXISTS COMMODITY " +
                    "(ID TEXT PRIMARY KEY  NOT NULL," +
                    "PRODUCT TEXT   NOT NULL," +
                    "FACTORY  TEXT  NOT NULL," +
                    "DATE   TEXT  NOT NULL," +
                    "TYPE  TEXT  NOT NULL," +
                    "INPRICE INT NOT NULL," +
                    "OUTPRICE   INT  NOT NULL," +
                    "NUMS   INT   NOT NULL)";

            stmt.executeUpdate(sql1);



            String sql2 = "CREATE TABLE  IF NOT EXISTS SHOPCART " +
                    "(USERNAME TEXT    NOT NULL," +
                    " PRODUCT  TEXT    NOT NULL," +
                    " PRICE    INT     NOT NULL," +
                    " AMOUNT   INT     NOT NULL)";

            stmt.executeUpdate(sql2);


            String sql3 = "CREATE TABLE  IF NOT EXISTS HISTORY " +
                    "(USERNAME TEXT    NOT NULL," +
                    " PRODUCT TEXT   NOT NULL," +
                    " AMOUNT    INT     NOT NULL," +
                    " TOTALPRICE   INT  NOT NULL," +
                    " TIME    TEXT     NOT NULL)";

            stmt.executeUpdate(sql3);

         //   String sql9 = "DELETE FROM SHOPCART";
          //  stmt.executeUpdate(sql9);
          //  String deleteQuery10 = "DELETE FROM HISTORY";
          //  stmt.executeUpdate(deleteQuery10);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
      //  System.out.println("Table created successfully");
    }








}



