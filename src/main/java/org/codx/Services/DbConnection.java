package org.codx.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static String host = "ec2-3-230-61-252.compute-1.amazonaws.com";
    private static String port = "5432";
    private static String dbName = "d3sdt5h4vak3pv";
    private static String user = "rioewehkzwlxxa";
    private static String pass = "505f047f5e68c036b7e180e325e49ea43b1335012aa60f8dd7b01b9cf11b2540";
    private static String ssl = "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

    public static Connection connectDb(){
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://"+host+":"+port+"/"+dbName,user,pass);

        }catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            System.out.println("[ERROR]:Connection Failed!");
            return null;
        }
        System.out.println("[INFO]: Successfully");
        return conn;
    }
}
