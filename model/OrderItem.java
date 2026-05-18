package model;

public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private int quantity;

    public OrderItem(){};

    public OrderItem(int id,int orderId,int productId, int quantity){
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.orderId = orderId;
    }
    
    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "product id: "+productId+" quantity: "+quantity;
    }
}
