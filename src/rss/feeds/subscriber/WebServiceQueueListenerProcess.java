package rss.feeds.subscriber;

import static com.rss.common.AWSDetails.SQS;
import static com.rss.common.AWSDetails.*;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.rss.common.Article;

public class WebServiceQueueListenerProcess implements Runnable, CallbackHandler {
	
private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(50);	
	
	public void run() {
		
		String webServiceQueueUrl = SQS.getQueueUrl(new GetQueueUrlRequest(SQS_GETFEEDS_QUEUE)).getQueueUrl();
		while (true) {
            try {
                ReceiveMessageResult result = SQS.receiveMessage(
                        new ReceiveMessageRequest(webServiceQueueUrl).withMaxNumberOfMessages(1));
                for (Message msg : result.getMessages()) {
                    
                	// Now do the processing here
                	// put it in the other job queue
                	// now wait till we get message in the publisher queue
                    
                    // delete the message
                    SQS.deleteMessage(new DeleteMessageRequest(webServiceQueueUrl, msg.getReceiptHandle()));
                }                
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                throw new RuntimeException("Worker interrupted");
            } catch (Exception e) {
                System.out.println("Exception occurred in WebServiceQueueListenerProcess : " + e);
            }
        }
	}

	@Override
	public void onFetchingLatestFeeds(List<Article> articles) {
		

	}
}
