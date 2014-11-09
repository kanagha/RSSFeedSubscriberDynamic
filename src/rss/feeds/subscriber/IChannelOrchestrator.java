package rss.feeds.subscriber;

import java.util.List;
import java.util.Map;

import rss.feeds.subscriber.ChannelOrchestrator.QueueMessage;

import com.rss.common.Article;

public interface IChannelOrchestrator {
	
	public List<Article> fetchFeeds();

	public void addChannel(Channel channel);

	public Map<String, QueueMessage> getQueueMessages();
}
