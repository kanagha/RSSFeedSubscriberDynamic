package rss.feeds.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import rss.feeds.quartz.JobSchedulerBean;
import rss.feeds.stomp.SubscriptionMessage;

/**
 * Responsible for configuring the endpoint for receiving latest feeds
 * @author Kanagha
 */
@Controller
public class MessageController {

    enum JobStatus {
        START,
        PROGRESS,
        STOP,
    }

    private SimpMessagingTemplate template;
    @Inject
    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    JobSchedulerBean jobScheduler;
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    /**
     * This will subscribe the user to the latest feeds for the given channelId
     * And a job scheduler will be kicked off
     * @param message
     */
    @MessageMapping("/getfeeds")
    public void scheduleLatestFeedsJob(Principal principal, SubscriptionMessage message) throws Exception {
        //Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);

        if (message.getCommandOrMsg().compareToIgnoreCase("connect") == 0) {
            logger.info("Starting job scheduler for user :" + principal.getName());
            doScheduleJob(principal.getName(), JobStatus.START);
        } else if(message.getCommandOrMsg().compareToIgnoreCase("disconnect") == 0) {
            logger.info("Disconnecting job scheduler for user :" + principal.getName());
            doScheduleJob(principal.getName(), JobStatus.STOP);
        }
        template.convertAndSendToUser(principal.getName(), "/queue/messages", message.getCommandOrMsg());
    }

    private void doScheduleJob(String userId, JobStatus jobStatus) throws SchedulerException {
        switch(jobStatus) {
            case START: {
                // fetch all the channels for a given userId
                List<String> channelIds = new ArrayList<String>();
                for (String channelId : channelIds) {
                    jobScheduler.scheduleJob(channelId);
                }
            }
            break;
            case STOP: {
                // fetch all the channels for a given userId
                List<String> channelIds = new ArrayList<String>();
                for (String channelId : channelIds) {
                    jobScheduler.unscheduleJob(channelId);
                }
            }
            break;
        }
    }
}