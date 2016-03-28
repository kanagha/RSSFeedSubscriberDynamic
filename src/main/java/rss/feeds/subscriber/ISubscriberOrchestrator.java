package rss.feeds.subscriber;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import rss.feeds.subscriber.dataprovider.SubscriberObjectMapper;

@Component
public interface ISubscriberOrchestrator {
	public String addSubscriber(String username, String emailAddress);
	public SubscriberObjectMapper getSubscriber(String subscriberId);
	public List<SubscriberObjectMapper> getSubscribers();
}
