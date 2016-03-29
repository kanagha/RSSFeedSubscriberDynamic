package rss.feeds.subscriber;

import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import rss.feeds.subscriber.dataprovider.SubscriberObjectMapper;

@Component
@Scope("prototype")
public class Subscriber {
	String mId;
	String mEmailAddress;
	String mName;
	Set<String> mChannelIds;
	
	public Subscriber(SubscriberObjectMapper mapper) {
		this.mId = mapper.getId();
		this.mEmailAddress = mapper.getEmailAddress();
		this.mName = mapper.getName();
		this.mChannelIds = mapper.getChannelIds();
	}

	public String getId(){
		return mId;
	}

	public String getEmailAddress() {
		return mEmailAddress;
	}
	
	public String getName() {
		return mName;
	}
	
	public Set<String> getChannelIds() {
		return mChannelIds;
	}
}
