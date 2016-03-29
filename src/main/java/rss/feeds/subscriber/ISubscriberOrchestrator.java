package rss.feeds.subscriber;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public interface ISubscriberOrchestrator {

	public String addSubscriber(String username, String emailAddress);

	public Subscriber getSubscriber(String subscriberId);

	public List<Subscriber> getSubscribers();
}
