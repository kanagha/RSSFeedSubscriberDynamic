package rss.feeds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rss.common.Article;

import rss.feeds.subscriber.Channel;
import rss.feeds.subscriber.IChannelOrchestrator;
import rss.feeds.subscriber.ISubscriberOrchestrator;
import rss.feeds.subscriber.Subscriber;
import rss.feeds.subscriber.dataprovider.ChannelObjectMapper;

@Configuration
@Component
@RestController

public class RSSFeedService {
    
	@Autowired
    IChannelOrchestrator mChannelOrchestrator;
	@Autowired
    ISubscriberOrchestrator mSubscriberOrchestrator;

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
    public ResponseEntity<Subscriber> getSubscriber(@PathVariable("subscriberid") String subscriberid) {
    	Subscriber subscriber = mSubscriberOrchestrator.getSubscriber(subscriberid);
    	return new ResponseEntity<Subscriber>(subscriber, HttpStatus.OK);
    }

    @RequestMapping(value="/subscriber", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public ResponseEntity<List<Subscriber>> getAllSubscribers() {
    	List<Subscriber> mapperList = mSubscriberOrchestrator.getSubscribers();
    	return new ResponseEntity<List<Subscriber>>(mapperList, HttpStatus.OK);
    }

    @RequestMapping(value="/channels/subscriber/{subscriberid}", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public List<Channel> getChannels(@PathVariable("subscriberid") String subscriberid) {      
    	return mChannelOrchestrator.getChannels(subscriberid);
    }

    @RequestMapping(value="/channels/{channelid}", 
    		method = RequestMethod.GET,
    		produces = "application/json")
    public Channel getChannel(@PathVariable("channelid") String channelid) {
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
}