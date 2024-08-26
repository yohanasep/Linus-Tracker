package JDBC;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBC {
    private String url = "jdbc:mysql://localhost:3306/linusTracker";
    private String user = "root";
    private String pass = "";
    Connection connect;
    
    public JDBC(){
        createConnection();    
    }

    public void createConnection(){
        try {
            connect = DriverManager.getConnection(url, user, pass);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public PreparedStatement createStatement(String sql) throws SQLException{
        if (connect == null || connect.isClosed()) {
            createConnection();
        }
        return connect.prepareStatement(sql);
    }
}
