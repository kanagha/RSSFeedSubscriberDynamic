package rss.feeds.subscriber;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@XmlRootElement
@DynamoDBTable(tableName = "channel")
public class Channel {

	private int mChannelId;
	Set<String> mURLSet = new HashSet<String>();
	//List<String> mMatchingKeywords = new ArrayList<String>();
	//Frequency mFrequency;
	Topic mTopic;
	
	// TODO: Necessary to provide a default constructor. Need to investigate
	public Channel() {
		mURLSet = new HashSet<String>();
	}
	
	public Channel(ChannelPayload payload) {
		mURLSet.addAll(payload.mURLSet);
		mTopic = payload.mTopic;
	}
	
	/*public Channel(Topic topic, Set<String> urlList){
		mTopic = topic;
		mURLList = urlList;
	}*/
	
	/*public Channel1(Topic topic) {
		mTopic = topic;		
	}*/
	
	@DynamoDBAttribute
	public int getChannelId() {
		return mChannelId;
	}
	
	public void setChannelId(int channelId) {
		mChannelId = channelId;
	}
	
	@DynamoDBAttribute
	public Topic getTopic() {
		return mTopic;
	}
	
	public void setTopic(Topic topic) {
		mTopic = topic;
	}
	
	@DynamoDBAttribute
	public Set<String> getURLList() {
		return mURLSet;
	}
	
	public void setURLList(Set<String> urlList) {
		mURLSet = urlList;
	}
	
	/*public Frequency getFrequency() {
		return mFrequency;
	}
	
	
	
	public List<String> getMatchingKeywords() {
		return mMatchingKeywords;
	}*/
		
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