package rss.feeds.subscriber;

import javax.xml.bind.annotation.XmlRootElement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/*
 * User table: Subscriber table:

UserId
UserName
Address
EmailAddress
Info
 */

@XmlRootElement
@DynamoDBTable(tableName = "subscriber")
public class SubscriberObjectMapper {
	
	@DynamoDBHashKey
	String id;
	String emailAddress;
	String name;
	
	
	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	public String getId(){
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBAttribute
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	@DynamoDBAttribute
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*@DynamoDBAttribute
	public Set<Integer> getChannelIds() {
		return mChannelIds;
	}

	public void setChannels(Set<Channel> channels) {
		mChannels.addAll(channels);
	}*/
}