package rss.feeds.subscriber;

public class SubscriberOrchestrator implements ISubscriberOrchestrator {
	public void addSubscriber(String username, String emailAddress) {
		Subscriber subscriber = new Subscriber();
		subscriber.setName(username);
		subscriber.setEmailAddress(emailAddress);
		
		// save it to database
		
	}

}
