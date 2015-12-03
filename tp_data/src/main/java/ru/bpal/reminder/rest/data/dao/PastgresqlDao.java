package ru.bpal.reminder.rest.data.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by serg on 26.10.15.
 */
public class PastgresqlDao {

    public Connection conn;
    public Statement stmt;

    String user = "tpuser";
    String passwd = "tppasswd";
    String uri = "jdbc:postgresql://127.0.0.1:5432/bpaldb3?useUnicode=yes&characterEncoding=UTF-8";

    private static PastgresqlDao instance = null;

    public PastgresqlDao() {
        createConnection();
    }

//    public static PastgresqlDao getInstance() {
//        if(instance == null) {
//            instance = new PastgresqlDao();
//        }
//        return instance;
//    }

    protected void createConnection() {

        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(uri, user, passwd);
            stmt = conn.createStatement();
            stmt.execute("select  now()");

            System.out.println("Connecting to PostgreSQL database");
        } catch (Exception e) {
            System.out.println(e.getMessage());

            try {
                if (null != stmt) {
                    stmt.close();
                }
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return conn != null;
    }
}
