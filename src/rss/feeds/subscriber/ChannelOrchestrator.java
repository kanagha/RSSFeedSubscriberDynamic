package rss.feeds.subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.rss.common.AWSDetails;
import com.rss.common.Article;
import com.rss.common.RSSFeedQueueRequest;

import static com.rss.common.AWSDetails.*;

// This will save/retrieve from database and fetch feeds
public class ChannelOrchestrator implements IChannelOrchestrator {
	
	class QueueMessage {
		Lock lock;
		java.util.concurrent.locks.Condition condition;				
	}
	/*final Lock mLock = new ReentrantLock();
	List<Condition> mConditions = new LinkedList<Condition>();*/
	
	public Map<String, QueueMessage> mQueueMessages= new ConcurrentHashMap<String, QueueMessage>();

	public Map<String, QueueMessage> getQueueMessages() {
		return mQueueMessages;
	}
	
	public List<Article> fetchFeeds(String channelId) {
		
		ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
		if (channel == null) {
			return new ArrayList<Article>();
		}
		Lock lock = new ReentrantLock();		
		java.util.concurrent.locks.Condition condition = lock.newCondition();
		QueueMessage message = new QueueMessage();
		message.lock = lock;
		message.condition = condition;
		
		//String randomUuid = UUID.randomUUID().toString();
		int randomId = (int)(Math.random()*100);
		String randomIdString = String.valueOf(randomId);
		
		// add it to the job queue;
		mQueueMessages.put(randomIdString, message);
		
		String queueUrl = AWSDetails.SQS.getQueueUrl(new GetQueueUrlRequest(AWSDetails.SQS_ADDJOB_GETFEEDS_QUEUE)).getQueueUrl();
		RSSFeedQueueRequest request = new RSSFeedQueueRequest();		
		
		request.URLlist.addAll(channel.getUrlSet());
		request.jobId = randomId;
        AWSDetails.SQS.sendMessage(new SendMessageRequest(queueUrl, request.serializeToJSON()));
        System.out.println("Sending message --> "+ request + " job id :" + randomIdString);
        
		try {
			lock.lock();
			condition.await();			
			lock.unlock();			
			// Now fetch it from cache or from db
			CacheDatabaseReader.fetchArticles(channel.urlSet);
			return new ArrayList<Article>();

		} catch (InterruptedException e) {
			System.out.println("Caught interrupted exception: " + e);
		} finally {
			lock.unlock();
		}
		return new ArrayList<Article>();
	}

	@Override
	public String addChannel(ChannelObjectMapper channel) {
		DYNAMODB_MAPPER.save(channel);
		return channel.getId();
	}

	@Override
	public ChannelObjectMapper getChannel(String channelId) {
		return DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
	}

	@Override
	public void addURL(String channelId, String url) {
		ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
		channel.getUrlSet().add(url);
		DYNAMODB_MAPPER.save(channel);
	}

	@Override
	public List<ChannelObjectMapper> getChannels(String subscriberid) {
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
		Map<String, Condition> filter = new HashMap<String, Condition>();
		filter.put("userId", new Condition().withComparisonOperator(ComparisonOperator.NOT_NULL));
		scanExpression.setScanFilter(filter);
		return DYNAMODB_MAPPER.scan(ChannelObjectMapper.class, scanExpression);
	}
}
