package rss.feeds.subscriber;
import java.net.URL;
import java.util.List;

public interface IChannel {
	
	void subscribe(List<String> matchingKeywords, Frequency frequency);
	
	void unsubscribe(List<String> matchingKeywords);
	
	void changeFreqency(Frequency frequency);

	void addFeedURLS(List<String> urlList);

}
