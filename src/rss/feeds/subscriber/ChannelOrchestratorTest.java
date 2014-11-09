package rss.feeds.subscriber;

import rss.feeds.subscriber.ChannelOrchestrator.QueueMessage;

import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.google.gson.Gson;

import static com.rss.common.AWSDetails.SQS;
import static com.rss.common.AWSDetails.SQS_PUBLISHER_QUEUE;

class FetchFeedsRunnable implements Runnable {
	IChannelOrchestrator mChannelOrchestrator;
	
	FetchFeedsRunnable(IChannelOrchestrator channelOrchestrator) {
		mChannelOrchestrator = channelOrchestrator;
	}

	@Override
	public void run() {
		mChannelOrchestrator.fetchFeeds();		
	}
}

class PollQueueRunnable implements Runnable {
	IChannelOrchestrator mChannelOrchestrator;
	
	PollQueueRunnable(IChannelOrchestrator channelOrchestrator) {
		mChannelOrchestrator = channelOrchestrator;
	}

	@Override
	public void run() {
        System.out.println("Listening for work");
        String queueUrl = SQS.getQueueUrl(new GetQueueUrlRequest(SQS_PUBLISHER_QUEUE)).getQueueUrl();

        while (true) {
            try {
                ReceiveMessageResult result = SQS.receiveMessage(
                        new ReceiveMessageRequest(queueUrl).withMaxNumberOfMessages(1));
                for (Message msg : result.getMessages()) {
                    System.out.println("Received message" + msg.getBody());
                    QueueMessage message = mChannelOrchestrator.getQueueMessages().get(msg.getBody());
                    if (message != null) {
                    	message.lock.lock();
                    	System.out.println("Signalling right now :");
                    	message.condition.signal();
                    }
                    SQS.deleteMessage(new DeleteMessageRequest(queueUrl, msg.getReceiptHandle()));
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Caught exception :" + e);
            }
        }
	}	
}


public class ChannelOrchestratorTest {
	public static void main(String args[]) {
		IChannelOrchestrator channelOrchestrator = new ChannelOrchestrator();
		
		String publisherQueueUrl = SQS.createQueue(new CreateQueueRequest(SQS_PUBLISHER_QUEUE)).getQueueUrl();
        System.out.println("Using Amazon SQS Queue: " + publisherQueueUrl);       
                
		
		Thread thread1 = new Thread(new FetchFeedsRunnable(channelOrchestrator));
		thread1.start();
		Thread thread2 = new Thread(new PollQueueRunnable(channelOrchestrator));
		thread2.start();
	}	
}
