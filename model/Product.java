package model;

public class Product {
    private int id;
    private String name;
    private double price;

    public Product(){}

    public Product(String name,double price){
        this.name = name;
        this.price = price;
    }

    public Product(int id,String name,double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id: "+id+" name: "+name+" price: "+price;
    }
}
