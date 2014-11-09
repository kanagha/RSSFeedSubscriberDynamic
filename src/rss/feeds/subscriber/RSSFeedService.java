package rss.feeds.subscriber;
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
    
    /*// Return the list of orders for applications with json or xml formats
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Channel> getChannels() {
      List<Channel> orders = new ArrayList<Channel>();
      Set<String> newSet = new HashSet<String>();
      newSet.add("BasicUrl");
      
      Channel channel = new Channel(Topic.MUSIC, newSet);
      orders.add(channel);
      return orders;
    }*/

    /*@POST
    @Consumes("application/json")
    public Subscriber createSubscriber(String username, String emailAddress) {
    	// save it to the database and return a subscriber object
    	Subscriber subscriber = new Subscriber();
    	subscriber.setSubscriberId(1);
    	return subscriber;
    }*/
    
    // Start of process
    
    @GET @Path("/subscriber/{subscriberid}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Subscriber getSubscriber(@PathParam("subscriberid") int subscriberid) {
    	Subscriber subscriber = new Subscriber();
    	//subscriber.setSubscriberId(subscriberid);
    	return subscriber;
    }
    
    @GET //@Path("/subscribers")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Subscriber getSubscriber() {
    	Subscriber subscriber = new Subscriber();
    	subscriber.setSubscriberId(1);
    	return subscriber;
    }
    
    
    // Return the list of orders for applications with json or xml formats
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Channel> getChannels(int subscriberId) {
      List<Channel> orders = new ArrayList<Channel>();
      Set<String> URLList = new HashSet<String>();
      URLList.add("Hello");
      
      Channel channel = new Channel();
      channel.setTopic(Topic.MUSIC);
      channel.setURLList(URLList);
      orders.add(channel);
      return orders;
    }
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Channel create(ChannelPayload channelPayload) {
		Channel channel = new Channel(channelPayload);
		mChannelOrchestrator.addChannel(channel);
		return channel;
    }
    
    @POST
    @Consumes("application/json")
    public void addFeedURLS(int subscriberId, int channelId, List<String> urlList){
		//mURLList.addAll(urlList);
	}
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Article> getLatestFeeds(Channel channel) {
    	return mChannelOrchestrator.fetchFeeds();
    	
    }
  
    // End of current
    

    /*@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Order> getChannels() {
      List<Order> orders = new ArrayList<Order>();
      Order newOrder = new Order();
      newOrder.setId("Id");
      newOrder.setDescription("Description");
      orders.add(newOrder);
      return orders;
    }*/
    
    @GET
    @Produces("text/plain")
    public int getChannels() {
      return 1;
    }
}
