package util;
import java.sql.*;

public class DBConnection {
    
    private String url = "jdbc:mysql://localhost:3306/foodpanda";
    private String username = "root";
    private String password = "Slayer@King1234";
    private Connection conn;

    public void establishConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password); 
            System.out.println("Connection established successfully");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Connection getConn(){
        return conn;
    }

    public void closeConnection(){
        try{
            conn.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
