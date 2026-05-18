package dao;
import java.sql.*;
import java.util.*;

import model.User;
import util.DBConnection;

public class UserDAO {
    private DBConnection db = new DBConnection();
    
    public void addUser(User user){
        try{
            db.establishConnection();
            
            // prepared statment
            PreparedStatement pstatement = db.getConn().prepareStatement("INSERT INTO users (name,email) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
    
            // set values
            pstatement.setString(1, user.getName());
            pstatement.setString(2, user.getEmail());
            
            // execute query
            int executeQuery = pstatement.executeUpdate();
            
            // get generated keys
            ResultSet generatedKeys = pstatement.getGeneratedKeys();
            
            // set id into user object
            if(generatedKeys.next()){
                int newId = generatedKeys.getInt(1);
                user.setID(newId);
            }
            
            if(executeQuery == 1){
                System.out.println("Successfully added a user");
            }else{
                System.out.println("Failed to added the user");
            }
            pstatement.close();
            db.closeConnection();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // get users by id
    public User getUserFromId(int id){
        try{
            User  user = new User();
            db.establishConnection();
            PreparedStatement pstat = db.getConn().prepareStatement("SELECT * FROM users WHERE id = ?");
            pstat.setInt(1,id);
            ResultSet rs = pstat.executeQuery();
            
            while(rs.next()){
                String name = rs.getString("name");
                String email = rs.getString("email");
                user.setEmail(email);
                user.setID(id);
                user.setName(name);
                return user;
            }
            rs.close();
            pstat.close();
            db.closeConnection();   
            return user;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return null;
    }

    public List<User> getAllUser(){
        List<User> userList = new ArrayList<>();
        try{
            db.establishConnection();
            Statement stmt = db.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                userList.add(new User(id,name,email));
            }
            rs.close();
            stmt.close();
            db.closeConnection();
            return userList;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return null;
    }

    // update user's email
    public void updateUserEmail(int id,String email){
        User user = getUserFromId(id);
        if(user != null){
            user.setEmail(email);
            try{
                db.establishConnection();
                PreparedStatement pstmt = db.getConn().prepareStatement("UPDATE users SET email = ? WHERE id = ?");
                pstmt.setString(1, user.getEmail());
                pstmt.setInt(2, id);
                int execute = pstmt.executeUpdate();
                if(execute == 1){
                    System.out.println("User added successfully.");
                }else{
                    System.out.println("Unable to update the user.");
                }
                pstmt.close();
                db.closeConnection();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Unable to find the users");
        }
    }

}
