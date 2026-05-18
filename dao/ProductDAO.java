package dao;
import java.sql.*;
import java.util.*;

import model.Product;
import util.DBConnection;

public class ProductDAO {
    DBConnection db = new DBConnection();

    // add product in the db.
    public void addProduct(Product product){
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("INSERT INTO products (name,price) VALUES (?,?)");
            pstmt.setString(1,product.getName());
            pstmt.setDouble(2, product.getPrice());
            int execute = pstmt.executeUpdate();
            if(execute == 1){
                System.out.println("Product added successfully.");
            }else{
                System.out.println("Failed to add the product.");
            }
            pstmt.close();
            db.closeConnection();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    // get product by id.
    public Product getProductById(int id){
         Product product;
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("SELECT * FROM products WHERE id = ?");
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int ProId = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                product = new Product(ProId,name,price);
                rs.close();
                pstmt.close();
                return product;
            }
        return null;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return null;
    }

    // get all product.
    public List<Product> getAllProducts(){
        List<Product> products = new ArrayList<>();
        try{
            db.establishConnection();
            Statement stmt = db.getConn().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM products");

            while(rs.next()){
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                int id = rs.getInt("id");
                products.add(new Product(id,name,price));
            }
            rs.close();
            stmt.close();
            return products;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    // update product price.
    public void updateProduct(int productId,double price){
        Product product = getProductById(productId);
        if(product != null){
            System.out.println("Product found");
            product.setPrice(price);
            try{
                db.establishConnection();
                PreparedStatement pstmt = db.getConn().prepareStatement("UPDATE products SET price = ? WHERE id = ?");
                pstmt.setDouble(1, price);
                pstmt.setInt(2, productId);
                int execute = pstmt.executeUpdate();
                if(execute == 1){
                    System.out.println("Has been updated successfully");
                }else{
                    System.out.println("Unable to update the product");
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            System.out.println("Unable to find the product");
        }
    }

    // delete product by id
    public void deleteProductById(int id){
        try{
            db.establishConnection();
            PreparedStatement pstmt = db.getConn().prepareStatement("DELETE FROM products WHERE id = ?");
            pstmt.setInt(1, id);
            int exe = pstmt.executeUpdate();
            if(exe == 1){
                System.out.println("The item has been deleted successfully");
            }else{
                System.out.println("Unable to delete the item");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
