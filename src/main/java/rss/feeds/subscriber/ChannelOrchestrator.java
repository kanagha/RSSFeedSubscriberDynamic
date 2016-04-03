package rss.feeds.subscriber;

import static com.rss.common.aws.AWSDetails.DYNAMODB_MAPPER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.rss.common.Article;
import com.rss.common.Channel;
import com.rss.common.RSSFeedQueueRequest;
import com.rss.common.aws.AWSDetails;
import com.rss.common.dataprovider.ChannelObjectMapper;
import com.rss.common.dataprovider.IChannelDBProvider;

// This will save/retrieve from database and fetch feeds
@Component
@Scope("prototype")
public class ChannelOrchestrator implements IChannelOrchestrator {

    ArticleFetcher mCacheDatabaseReader = null;
    @Autowired
    IChannelDBProvider channelDBProvider = null;

    public ChannelOrchestrator() {
        mCacheDatabaseReader = new ArticleFetcher();
    }

    /**
     * Retrieves the existing feeds from the db
     * For those urls not present, it sends a message to SQS queue
     * and when the message is processed,
     * the user gets notified with the latest feeds, if a websocket
     * endpoint is configured for the channel
     */
    @Override
    public List<Article> fetchFeeds(String channelId) {

        ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, channelId);
        if (channel == null) {
            return new ArrayList<Article>();
        }

        // First retrieve if any of the values are present in db
        // and only for those urls which are not present in db,
        // send a queue request

        Map<String, List<Article>> mapOfArticles = mCacheDatabaseReader.fetchArticles(channel.getUrlSet());
        List<Article> existingFetchedArticles = new ArrayList<Article>();

        Set<String> toFetchUrls = new HashSet<String>();

        // evaluate urls to fetch
        for (String url : channel.getUrlSet()) {
            if (!mapOfArticles.keySet().contains(url)) {
                toFetchUrls.add(url);
            }
        }

        // collect all available articles
        for (String url : mapOfArticles.keySet()) {
            existingFetchedArticles.addAll(mapOfArticles.get(url));
        }

        if (!toFetchUrls.isEmpty()) {
            String queueUrl = AWSDetails.SQS.getQueueUrl(new GetQueueUrlRequest(AWSDetails.SQS_ADDJOB_GETFEEDS_QUEUE)).getQueueUrl();
            RSSFeedQueueRequest request = new RSSFeedQueueRequest();

            request.URLlist.addAll(toFetchUrls);
            request.channelId = channelId;
            AWSDetails.SQS.sendMessage(new SendMessageRequest(queueUrl, request.serializeToJSON()));
            System.out.println("Sending message --> "+ request + " channelId :" + channelId);
        }
        return existingFetchedArticles;
    }

    @Override
    public String addChannel(ChannelObjectMapper channel) {
        return channelDBProvider.addChannel(channel);
    }

    @Override
    public Channel getChannel(String channelId) {
        return channelDBProvider.getChannel(channelId);
    }

    @Override
    public void addURL(String channelId, String url) {
        channelDBProvider.addURL(channelId, url);
    }

    @Override
    public List<Channel> getChannels(String subscriberid) {
        return channelDBProvider.getChannels(subscriberid);
    }

    @Override
    public void deleteURL(String channelId, String url) {
        channelDBProvider.deleteURL(channelId, url);
    }
}
