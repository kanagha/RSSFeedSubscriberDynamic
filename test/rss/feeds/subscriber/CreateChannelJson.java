package rss.feeds.subscriber;

import com.google.gson.Gson;
import com.rss.common.RSSFeedQueueRequest;

import static com.rss.common.AWSDetails.*;

public class CreateChannelJson {
	public static void main(String args[]) {
		Gson gson = new Gson();
		ChannelObjectMapper mapper = new ChannelObjectMapper();
		mapper.topic = Topic.NEWS.toString();
		mapper.urlSet.add("http://rss.cnn.com/rss/cnn_topstories.rss");
		mapper.setUserId("270dfe6b-c4e1-4b7b-9f8c-d8884bc32e31");
		System.out.println(gson.toJson(mapper));
		
		ChannelObjectMapper channel = DYNAMODB_MAPPER.load(ChannelObjectMapper.class, "3fcf3f32-1d5e-433c-a1fd-699dc439c74c");
		RSSFeedQueueRequest request = new RSSFeedQueueRequest();
		request.URLlist.addAll(channel.getUrlSet());
		request.jobId = 123;
		System.out.println(gson.toJson(request));
	}
}
