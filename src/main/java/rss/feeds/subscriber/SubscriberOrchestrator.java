package rss.feeds.subscriber;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rss.common.Subscriber;
import com.rss.common.dataprovider.SubscriberDBProvider;

@Component
@Scope("prototype")
public class SubscriberOrchestrator implements ISubscriberOrchestrator {
    
    @Autowired
    SubscriberDBProvider subscriberDBProvider;

    public String addSubscriber(String username, String emailAddress) {
        return subscriberDBProvider.addSubscriber(username, emailAddress);
    }

    @Override
    public Subscriber getSubscriber(String subscriberId) {
        return subscriberDBProvider.getSubscriber(subscriberId);
    }

    @Override
    public List<Subscriber> getSubscribers() {
        return subscriberDBProvider.getSubscribers();
    }
}