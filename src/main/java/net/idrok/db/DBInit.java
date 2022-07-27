package net.idrok.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBInit {

    public static boolean test(){
        String sql = "SELECT 1;";
        try {
            return DataSource.ps(sql).execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS tuser(id INT PRIMARY KEY, first_name VARCHAR(255)); ";

        try { PreparedStatement ps = DataSource.ps(sql);
            if(ps.execute()){
                System.out.println("Successfully creat tables");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insert() {
        String sql = "insert into tuser(id , first_name) values(1, 'salom'); ";

        try { PreparedStatement ps = DataSource.ps(sql);
            if(ps.executeUpdate()>0){
                System.out.println("Successfully inserted ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
