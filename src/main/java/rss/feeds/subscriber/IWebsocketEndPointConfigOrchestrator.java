package rss.feeds.subscriber;

import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

/**
 * Takes care of configuring web socket endpoints
 * and scheduling jobs for each of the end points.
 */
@Component
public interface IWebsocketEndPointConfigOrchestrator {
	
	// TODO: Replace SchedulerException with websocketconfig exception (custom)
	public void addEndpoint(String channelId, String endPoint) throws SchedulerException;
}
