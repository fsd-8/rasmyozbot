package net.idrok.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSource {
    private static  Connection connection;

    public static boolean connect(){
        String url = "jdbc:postgresql://localhost:5432/rasmyozbot";
        String username = "postgres";
        String password = "123";

//        String url = "jdbc:h2:./botbaza;";
//        String username = "sa";
//        String password = "filepwd userpwd";
        try {
//            Class.forName("org.h2.Driver");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static PreparedStatement ps(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
