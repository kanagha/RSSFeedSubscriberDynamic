package rss.feeds.subscriber;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rss.common.DBDataProvider;

import rss.feeds.quartz.JobSchedulerBean;

@Component
@Scope("prototype")
public class WebsocketEndPointConfigOrchestrator implements IWebsocketEndPointConfigOrchestrator {

	@Autowired
    private JobSchedulerBean jobScheduler;

	@Override
	public void addEndpoint(String channelId, String endPoint) throws SchedulerException {
		// TODO: if endpoint not found for channel, then
		// add a new endpoint
		if (!isEndPointConfigured(channelId)) {
			DBDataProvider.addWebsocketEndPoint(endPoint, channelId);
			jobScheduler.scheduleJob(channelId);	
		}
	}
	
	private boolean isEndPointConfigured(String channelId) {
		// TODO: check in db, if an endpoint is already configured
		return false;
	}
}
