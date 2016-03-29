package rss.feeds.subscriber;

import java.util.List;
import org.springframework.stereotype.Component;

import com.rss.common.Article;

import rss.feeds.subscriber.dataprovider.ChannelObjectMapper;

@Component
public interface IChannelOrchestrator {
	
	/**
	 * Retrieves the existing feeds from the db
	 * For those urls not present, it sends a message to SQS queue
	 * and when the message is processed,
	 * the user gets notified with the latest feeds, if a websocket
	 * endpoint is configured for the channel
	 */
	public List<Article> fetchFeeds(String channelId);

	public String addChannel(ChannelObjectMapper channel);
	
	public void addURL(String channelId, String url);
	
	public void deleteURL(String channelId, String url);

	public Channel getChannel(String channelId);

	public List<Channel> getChannels(String subscriberid);
}
