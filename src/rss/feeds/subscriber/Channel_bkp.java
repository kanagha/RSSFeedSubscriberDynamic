package rss.feeds.subscriber;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

class Frequency{
	int hour;
	int minutes;
	int seconds;
}

@XmlRootElement
public class Channel_bkp { //implements IChannel {
	
	Set<String> mURLList = new HashSet<String>();
	List<String> mMatchingKeywords = new ArrayList<String>();
	Frequency mFrequency;
	Topic mTopic;
	
	public Channel_bkp() {
		
	}
	
	public Channel_bkp(Topic topic, Set<String> urlList){
		mTopic = topic;
		mURLList = urlList;
	}
	
	public Topic getTopic() {
		return mTopic;
	}
	
	public Frequency getFrequency() {
		return mFrequency;
	}
	
	public Set<String> getURLList() {
		return mURLList;
	}
	
	public List<String> getMatchingKeywords() {
		return mMatchingKeywords;
	}	
		
	/*public void subscribe(List<String> matchingKeywords, Frequency frequency) {
	   mMatchingKeywords.addAll(matchingKeywords);	
	   mFrequency = frequency;	   
	}
	
	public void unsubscribe(List<String> matchingKeywords){
		mMatchingKeywords.removeAll(matchingKeywords);		
	}
	
	public void changeFreqency(Frequency frequency){
		mFrequency = frequency;
	}
	
	public void addFeedURLS(List<String> urlList){
		mURLList.addAll(urlList);
	}*/
}
