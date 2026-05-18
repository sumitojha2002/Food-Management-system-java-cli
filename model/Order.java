package model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int usersId;
    private Timestamp timestamp;

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    
    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public int getId() {
        return id;
    }

    public int getUsersId() {
        return usersId;
    }

    @Override
    public String toString() {
        return "order_id: "+id+" user_id: "+usersId+" timestamp: "+timestamp.toString();
    }
}
