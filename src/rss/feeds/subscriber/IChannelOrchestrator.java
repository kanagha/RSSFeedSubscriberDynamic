package rss.feeds.subscriber;

import java.util.List;
import java.util.Map;

import rss.feeds.subscriber.ChannelOrchestrator.QueueMessage;

import com.rss.common.Article;

public interface IChannelOrchestrator {
	
	public List<Article> fetchFeeds(String channelId);

	public String addChannel(ChannelObjectMapper channel);
	
	public void addURL(String channelId, String url);

	public ChannelObjectMapper getChannel(String channelId);

	public Map<String, QueueMessage> getQueueMessages();

	public List<ChannelObjectMapper> getChannels(String subscriberid);
}
