package rss.feeds.subscriber;

import static com.rss.common.AWSDetails.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.rss.common.Article;

@Path("/rssfeed")
public class RSSFeedService {
	
	// Allows to insert contextual objects into the class
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    
    IChannelOrchestrator mChannelOrchestrator = new ChannelOrchestrator();
    ISubscriberOrchestrator mSubscriberOrchestrator = new SubscriberOrchestrator();
    
    @GET @Path("/createSubscriber/{username}/{emailAddress}")
    public String createSubscriber(@PathParam("username") String username, @PathParam("emailAddress") String emailAddress) {
    	// save it to the database and return a subscriber object
    	return String.valueOf(mSubscriberOrchestrator.addSubscriber(username, emailAddress));    	
    }
    
    @GET @Path("/subscriber/user")
    public String createUser() {
    	User user = new User();
    	user.setId(1);
    	user.setStatus("HELLO");
    	DYNAMODB_MAPPER.save(user);    	
    	return "HELLO";
    }    
       
    @GET @Path("/subscriber/{subscriberid}")
    @Produces({MediaType.APPLICATION_JSON})
    public SubscriberObjectMapper getSubscriber(@PathParam("subscriberid") String subscriberid) {
    	SubscriberObjectMapper mapper = mSubscriberOrchestrator.getSubscriber(subscriberid);
    	return mapper;
    }

    @GET @Path("/subscriber")
    @Produces({MediaType.APPLICATION_JSON})
    public List<SubscriberObjectMapper> getAllSubscribers() {
    	return mSubscriberOrchestrator.getSubscribers();
    }
    
    @GET @Path("/channels/subscriber/{subscriberid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ChannelObjectMapper> getChannels(@PathParam("subscriberid") String subscriberid) {      
    	return mChannelOrchestrator.getChannels(subscriberid);
    }

    @GET @Path("/channels/{channelid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ChannelObjectMapper getChannel(@PathParam("channelid") String channelid) {
    	return mChannelOrchestrator.getChannel(channelid);
    }

    @POST @Path("/createChannel/")
    @Consumes("application/json")
    public String createChannel(ChannelObjectMapper channel) {
    	 return mChannelOrchestrator.addChannel(channel);    	
    }
   
    @GET @Path("/addFeedURLS/{channelid}/{url}")
    public String addFeedURLS(@PathParam("channelid") String channelid, @PathParam("url") String url){
		mChannelOrchestrator.addURL(channelid, url);
		return channelid;
	}
    
    @GET @Path("/feeds/{channelid}")
    @Consumes("application/json")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Article> getLatestFeeds(@PathParam("channelid") String channelid) {
    	return mChannelOrchestrator.fetchFeeds(channelid);    	
    }
}