package rss.feeds.subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.rss.common.Article;
import com.rss.common.DBDataProvider;
import com.rss.common.ArticleObjectMapper;

public class CacheDatabaseReader {
	public static List<Article> fetchArticles(Set<String> urlSet) {
		List<Article> list = new ArrayList<Article>();
		for (String url : urlSet) {
			List<ArticleObjectMapper> tempList = DBDataProvider.getArticles(url);
			for (ArticleObjectMapper mapper : tempList) {
				list.add(new Article(mapper));
			}
		}
		return list;
	}
}