package rss.feeds.subscriber;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import rss.feeds.subscriber.dataprovider.ChannelObjectMapper;

@Component
@Scope("prototype")
public class Channel {

	private String id;
	private String subscriberId;
	Set<String> urlSet = new HashSet<String>();
	String topic;	
	
	public Channel(ChannelObjectMapper channelMapper) {
		this.id = channelMapper.getId();
		this.subscriberId = channelMapper.getSubscriberId();
		this.urlSet = channelMapper.getUrlSet();
		this.topic = channelMapper.getTopic();
	}

	public String getId() {
		return id;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public String getTopic() {
		return topic;
	}
	
	public Set<String> getUrlSet() {
		return urlSet;
	}

	public boolean equals(Object arg){
		if (!(arg instanceof Channel)) {
			return false;
		}
		Channel channel = (Channel)arg;
		if (id.compareToIgnoreCase(channel.id) == 0) 
	    {
		    return true;
	    }
		return false;
	}
	
	public int hashCode() {
		return (13 * id.toLowerCase().hashCode());
	}
}
