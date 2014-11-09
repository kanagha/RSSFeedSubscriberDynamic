package rss.feeds.subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.rss.common.AWSDetails;
import com.rss.common.Article;

// This will save/retrieve from database and fetch feeds
public class ChannelOrchestrator implements IChannelOrchestrator {
	
	class QueueMessage {
		Lock lock;
		Condition condition;
		List<Article> articles;
		
		QueueMessage() {
			articles = new ArrayList<Article>();
		}		
	}
	/*final Lock mLock = new ReentrantLock();
	List<Condition> mConditions = new LinkedList<Condition>();*/
	
	public Map<String, QueueMessage> mQueueMessages= new ConcurrentHashMap<String, QueueMessage>();	
	
	public void CreateChannel(ChannelPayload channelPayload) {
		
	}
	
	public Map<String, QueueMessage> getQueueMessages() {
		return mQueueMessages;
	}
	
	public List<Article> fetchFeeds() {
		Lock lock = new ReentrantLock();
		
		Condition condition = lock.newCondition();
		QueueMessage message = new QueueMessage();
		message.lock = lock;
		message.condition = condition;
		
		String randomUuid = UUID.randomUUID().toString();
		mQueueMessages.put(randomUuid, message);
		
		// add it to the job queue;
		
		
		lock.lock();
		
		String queueUrl = AWSDetails.SQS.getQueueUrl(new GetQueueUrlRequest(AWSDetails.SQS_PUBLISHER_QUEUE)).getQueueUrl();
		
        AWSDetails.SQS.sendMessage(new SendMessageRequest(queueUrl, randomUuid));
        System.out.println("Sending message --> "+ randomUuid);
        
		try {
			condition.await();
			if (mQueueMessages.containsKey(message)) {
				return mQueueMessages.get(randomUuid).articles;
			}
			
		} catch (InterruptedException e) {
			System.out.println("Caught interrupted exception: " + e);
		}
		return new ArrayList<Article>();
	}

	@Override
	public void addChannel(Channel channel) {
		
	}
}
