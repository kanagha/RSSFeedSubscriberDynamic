package rss.feeds.subscriber;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChannelPayload {
	Set<String> mURLSet;
	Topic mTopic;
	int mSubscriberId;
	
	public ChannelPayload() {
		mURLSet = new HashSet<String>();
	}

	public Set<String> getURLSet(){
		return mURLSet;
	}
	
	public Topic getTopic() {
		return mTopic;
	}
	
	public int getSubscriberId() {
		return mSubscriberId;
	}
	
	public void setTopic(Topic topic) {
		mTopic = topic;
	}
	
	public void setURLSet(Set<String> urlList){
		mURLSet.addAll(urlList);
	}
	
	public void setSubscriberId(int id) {
		mSubscriberId = id;
	}
}