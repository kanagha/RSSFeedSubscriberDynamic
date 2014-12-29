package rss.feeds.subscriber;

import java.util.List;

public interface ISubscriberOrchestrator {
	public String addSubscriber(String username, String emailAddress);
	public SubscriberObjectMapper getSubscriber(String subscriberId);
	public List<SubscriberObjectMapper> getSubscribers();
}
