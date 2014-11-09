package rss.feeds.subscriber;

import java.util.List;

import com.rss.common.Article;

public interface CallbackHandler {
	
	public void onFetchingLatestFeeds(List<Article> articles);
}
