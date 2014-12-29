package rss.feeds.subscriber;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "users")
public class User {
    
    private int id;
    private String status;
    
    @DynamoDBHashKey
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    @DynamoDBAttribute
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}