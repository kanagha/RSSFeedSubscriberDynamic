package rss.feeds.quartz;

import static com.rss.common.aws.AWSDetails.DYNAMODB_MAPPER;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.rss.common.RSSFeedQueueRequest;
import com.rss.common.aws.AWSDetails;

import rss.feeds.subscriber.dataprovider.ChannelObjectMapper;

/**
 * Defines RSS Feed queue job attributes
 *
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
@Scope("prototype")
public class RSSFeedsQueueJob implements Job {

    private static Logger _log = LoggerFactory.getLogger(RSSFeedsQueueJob.class);
    
    // parameter specific to this job
    public static final String CHANNEL_ID = "channelId";
    public static final String EXECUTION_COUNT = "count";

    public RSSFeedsQueueJob() {
    }

    /**
     * Called when a scheduler fires a trigger
     * associated with the job.
     * This sends a message to the SQS queue inorder to
     * fetch latest feeds for the url associated with the channelId
     * 
     * @throws JobExecutionException
     *             if there is an exception while executing the job.
     */
    public void execute(JobExecutionContext context)
        throws JobExecutionException {

        // This job simply prints out its job name and the
        // date and time that it is running
        JobKey jobKey = context.getJobDetail().getKey();
        
        // Grab and print passed parameters
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String channelId = data.getString(CHANNEL_ID);
        int count = data.getInt(EXECUTION_COUNT);
        _log.info("RSSFeedsQueueJob: " + jobKey + " executing at " + new Date() + "\n" +
            "  channelId is " + channelId + "\n" + 
            "  execution count (from job map) is " + count + "\n");
        sendMessage(channelId);

        // increment the count and store it back into the 
        // job map so that job state can be properly maintained
        count++;
        data.put(EXECUTION_COUNT, count);
    }

    private void sendMessage(String channelId) {
    	ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
		if (channel != null) {
			String queueUrl = AWSDetails.SQS.getQueueUrl(new GetQueueUrlRequest(AWSDetails.SQS_ADDJOB_GETFEEDS_QUEUE)).getQueueUrl();
			RSSFeedQueueRequest request = new RSSFeedQueueRequest();		
			
			request.URLlist.addAll(channel.getUrlSet());
			request.channelId = channelId;
	        AWSDetails.SQS.sendMessage(new SendMessageRequest(queueUrl, request.serializeToJSON()));
	        System.out.println("Sending message:"+ request + " channelId :" + channelId);
		}
    }
}
