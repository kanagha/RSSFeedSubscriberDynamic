package rss.feeds.quartz;

import static com.rss.common.aws.AWSDetails.DYNAMODB_MAPPER;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rss.common.dataprovider.ChannelObjectMapper;

import rss.feeds.controller.MessageController;

@Component
@Scope(value = "singleton")
public class JobSchedulerBean {

    SchedulerFactory sf = null;
    Scheduler sched = null;

    // This will be later configured by the user through UI
    // where he can enter how long he wants to receive the feeds
    private static final int TOTAL_COUNT=100;
    private static final int DEFAULT_JOB_INTERVAL = 20;
    private static final String JOB_PREFIX = "job";
    private static final String TRIGGER_PREFIX = "trigger";
    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @PostConstruct
    public void startScheduler() throws SchedulerException {
        logger.info("Starting scheduler now!");
        sf = new StdSchedulerFactory();
        sched = sf.getScheduler();
    }

    @PreDestroy
    public void stopScheduler() throws SchedulerException {
        sched.shutdown(true);
        logger.info( "Stopped scheduler now!");
    }

    public void scheduleJob(String channelId) throws SchedulerException {
        scheduleJob(channelId, DEFAULT_JOB_INTERVAL);
    }

    public void scheduleJob(String channelId, int scheduleIntervalInMinutes) throws SchedulerException {
        // It will check the database, if already a job scheduler is kicked off
        // for the channelId, then another job is not kicked off
        ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
        if (channel == null) {
            logger.error("Invalid channelId, it is null");
            return;
        }
        String jobName = JOB_PREFIX + channelId;
        String triggerName = TRIGGER_PREFIX + channelId;
        JobDetail job = newJob(RSSFeedsQueueJob.class).withIdentity(jobName, jobName + "group1").build();

        SimpleTrigger trigger = newTrigger().withIdentity(triggerName, jobName + "group1").startAt(new Date())
            .withSchedule(simpleSchedule().withIntervalInMinutes(scheduleIntervalInMinutes).withRepeatCount(TOTAL_COUNT)).build();

        job.getJobDataMap().put(RSSFeedsQueueJob.CHANNEL_ID, channelId);
        job.getJobDataMap().put(RSSFeedsQueueJob.EXECUTION_COUNT, 1);

        // schedule the job to run
        Date scheduleTime1 = sched.scheduleJob(job, trigger);
        logger.info(job.getKey() + " will run at: " + scheduleTime1 + " and repeat: " + trigger.getRepeatCount()
                 + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");
    }

    public void unscheduleJob(String channelId) throws SchedulerException {
        Set<JobKey> jobKeys = sched.getJobKeys(GroupMatcher.jobGroupEquals(channelId));
        logger.info("Found no.of jobs for the following channelId : " + jobKeys.size());
        for (JobKey jobKey : jobKeys) {
            logger.info("Deleting job with name : " + jobKey.getName() + " , group name: " + jobKey.getGroup());
            sched.deleteJob(jobKey);
        }
    }
}