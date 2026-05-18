package model;

public class User {
    private String name;
    private String email;
    private int id;

    public User(){
    }

    public User(int id,String name,String email){
        this.name= name;
        this.email =email;
        this.id = id;
    }
    public void setName(String name){
        this.name =name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
    @Override
    public String toString(){
        return " id: "+id + " name: "+name+" email: "+email;
    }
}
