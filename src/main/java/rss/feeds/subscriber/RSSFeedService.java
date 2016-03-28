package rss.feeds.subscriber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rss.common.Article;
import com.rss.common.DBDataProvider;

import rss.feeds.quartz.JobSchedulerBean;
import rss.feeds.stomp.SubscriptionMessage;
import rss.feeds.subscriber.dataprovider.ChannelObjectMapper;
import rss.feeds.subscriber.dataprovider.SubscriberObjectMapper;

@Configuration
@Component
@RestController("rssfeed")
public class RSSFeedService {
    
	@Autowired
    IChannelOrchestrator mChannelOrchestrator;
	@Autowired
    ISubscriberOrchestrator mSubscriberOrchestrator;

    @Autowired
    private JobSchedulerBean jobScheduler;

    @RequestMapping(value="/createSubscriber/{username}/{emailAddress}", 
    		method = RequestMethod.POST, 
    		produces = "application/json")
    public ResponseEntity<String> createSubscriber(@PathVariable("username") String username, @PathVariable("emailAddress") String emailAddress) {
    	// save it to the database and return a subscriber object
    	String subscriberId = mSubscriberOrchestrator.addSubscriber(username, emailAddress);
    	return new ResponseEntity<String>(subscriberId, HttpStatus.OK);
    }    

    @RequestMapping(value="/subscriber/{subscriberid}", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public ResponseEntity<SubscriberObjectMapper> getSubscriber(@PathVariable("subscriberid") String subscriberid) {
    	SubscriberObjectMapper mapper = mSubscriberOrchestrator.getSubscriber(subscriberid);
    	return new ResponseEntity<SubscriberObjectMapper>(mapper, HttpStatus.OK);
    }

    @RequestMapping(value="/subscriber", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public ResponseEntity<List<SubscriberObjectMapper>> getAllSubscribers() {
    	List<SubscriberObjectMapper> mapperList = mSubscriberOrchestrator.getSubscribers();
    	return new ResponseEntity<List<SubscriberObjectMapper>>(mapperList, HttpStatus.OK);
    }

    @RequestMapping(value="/channels/subscriber/{subscriberid}", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public List<ChannelObjectMapper> getChannels(@PathVariable("subscriberid") String subscriberid) {      
    	return mChannelOrchestrator.getChannels(subscriberid);
    }

    @RequestMapping(value="/channels/{channelid}", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public ChannelObjectMapper getChannel(@PathVariable("channelid") String channelid) {
    	return mChannelOrchestrator.getChannel(channelid);
    }

    @RequestMapping(value="/createChannel", 
	method = RequestMethod.POST,
	consumes = "application/json",
	produces = "application/json")
    public String createChannel(@RequestBody ChannelObjectMapper channel) {
    	 return mChannelOrchestrator.addChannel(channel);    	
    }    

    @RequestMapping(value="/feeds/{channelid}", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public List<Article> getLatestFeeds(@PathVariable("channelid") String channelid) {
    	return mChannelOrchestrator.fetchFeeds(channelid);    	
    }    

    /**
     * This will subscribe the user to the latest feeds for the given channelId
     * And a job scheduler will be kicked off
     * @param message
     * @throws Exception
     */
    @MessageMapping("/getfeeds")
    // message will contain the channelId
    public void subscribeToFeeds(SubscriptionMessage message) throws Exception {
		String stompEndPoint = "/topic/getfeeds/" + message.getChannelId();
		
		DBDataProvider.addWebsocketEndPoint(stompEndPoint, message.getChannelId());
		jobScheduler.scheduleJob(message.getChannelId());
    }
}