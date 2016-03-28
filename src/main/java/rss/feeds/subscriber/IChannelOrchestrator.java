package rss.feeds.subscriber;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.rss.common.Article;

import rss.feeds.subscriber.dataprovider.ChannelObjectMapper;

@Component
public interface IChannelOrchestrator {
	
	public List<Article> fetchFeeds(String channelId);

	public String addChannel(ChannelObjectMapper channel);
	
	public void addURL(String channelId, String url);
	
	public void deleteURL(String channelId, String url);

	public ChannelObjectMapper getChannel(String channelId);

	public List<ChannelObjectMapper> getChannels(String subscriberid);
}
