package dao;
import java.sql.*;
import java.util.*;

import model.Order;
import model.OrderItem;
import model.Product;
import model.User;

import util.DBConnection;

public class OrderDAO {
    private DBConnection db = new DBConnection();
    
    // always create order before inserting order item
    public void createOrder(User user){
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("INSERT INTO orders (user_id) VALUES (?)");   
            pstmt.setInt(1,user.getId());
            int exe = pstmt.executeUpdate();
            if(exe ==1){
                System.out.println("Order has been created.");
            }else{
                System.out.println("Order has been failed to be created.");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // get the Order by id
    public List<Order> getOrderById(User user){
        List<Order> orders = new ArrayList<>();
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("SELECT * FROM orders WHERE user_id = ?");
            int usersId = user.getId();
            pstmt.setInt(1,usersId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                Timestamp ts = rs.getTimestamp("order_date");
                Order order = new Order();
                order.setId(id);
                order.setUsersId(userId);
                order.setTimestamp(ts);
                orders.add(order);
            }   
            db.closeConnection();
            return orders;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // using order and the Product to add the product into the oderItems
    public void addProductInOrderItem(Product product,int quntity,Order order){
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("INSERT INTO order_items (order_id,product_id,quantity) VALUES (?,?,?)");
            
            int productId = product.getId();
            int orderId = order.getId();
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quntity);
            int exe = pstmt.executeUpdate();
            if(exe == 1){
                System.out.println("item added successfully");
            }else{
                System.out.println("failed to add the item  in the orderItem");
            }
            pstmt.close();
            db.closeConnection();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // using the orderId to get the orderItem 
    public List<OrderItem> getOrderItemById(Order order){
        List<OrderItem> orderItems = new ArrayList<>();
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("SELECT * FROM order_items WHERE order_id = ?");
            pstmt.setInt(1,order.getId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int orderId = rs.getInt("order_id");
                int quantity = rs.getInt("quantity");
                orderItems.add(new OrderItem(id, orderId, productId, quantity));
            }
        pstmt.close();
        rs.close();
        db.closeConnection();
        return orderItems;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return null;
    }

    public List<OrderItem> getAllOrderItem(Order order){
        List<OrderItem> orderItem = new ArrayList<>();
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("SELECT * FROM order_items WHERE order_id = ?");
            pstmt.setInt(1, order.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int orderId = rs.getInt("order_id");
                int prouductId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                orderItem.add(new OrderItem(id, orderId, prouductId, quantity));
            }
            rs.close();
            pstmt.close();
            db.closeConnection();
            return orderItem;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    // want something that can be 
    // used by the restaurant manager 
    // to see all the orders of the users
    // i think of using the inner JOIN query

    public List<String> getUsersOrderForResManager(User user,Order order){
        List<String> usersInfoList = new ArrayList<>();
        double total  = 0.0;
        try{
            db.establishConnection();   
            PreparedStatement pstmt = db.getConn().prepareStatement("SELECT p.name, oi.quantity, p.price, (oi.quantity * p.price) AS tol_price FROM orders o INNER JOIN order_items oi ON o.id = oi.order_id INNER JOIN products p ON oi.product_id = p.id WHERE user_id = ? AND o.id = ?");
            pstmt.setInt(1, user.getId());
            pstmt.setInt(2,order.getId());
            ResultSet rs = pstmt.executeQuery();
            String ordDetail = "id: "+order.getId()+" user: "+user.getName()+" date and time: "+order.getTimestamp()+"\n";
            usersInfoList.add(ordDetail);
            while(rs.next()){
               String nameOfFood =  rs.getString("name");
               int quantity =  rs.getInt("quantity");
               double priceOfFood = rs.getDouble("price");
               double totalPriceOfFood =  rs.getDouble("tol_price");
               total += totalPriceOfFood; 
               String  result  = "name of food: "+nameOfFood+" quntity: "+quantity+" price of food: "+priceOfFood+" tol price of food: "+totalPriceOfFood;
                usersInfoList.add(result);
            }
            String breaker = "--------------------------------------";
            String value = "\tTotal bill: "+total;
            usersInfoList.add(breaker);
            usersInfoList.add(value);
            return usersInfoList;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return null;
    }
}
