package rss.feeds.subscriber;

import static com.rss.common.AWSDetails.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
/*import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;*/

public class SubscriberOrchestrator implements ISubscriberOrchestrator {

	public String addSubscriber(String username, String emailAddress) {
		SubscriberObjectMapper subscriber = new SubscriberObjectMapper();
		subscriber.setEmailAddress(emailAddress);
		subscriber.setName(username);
    	DYNAMODB_MAPPER.save(subscriber);
    	return subscriber.getId();
	}

	@Override
	public SubscriberObjectMapper getSubscriber(String subscriberId) {
		return DYNAMODB_MAPPER.load(SubscriberObjectMapper.class, subscriberId);
	}

	@Override
	public List<SubscriberObjectMapper> getSubscribers() {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> filter = new HashMap<String, Condition>();
		filter.put("id", new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL));
		scanExpression.setScanFilter(filter);
		return DYNAMODB_MAPPER.scan(SubscriberObjectMapper.class, scanExpression);
	}
}
