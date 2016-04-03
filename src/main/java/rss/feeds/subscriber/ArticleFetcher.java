package rss.feeds.subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rss.common.Article;
import com.rss.common.DBDataProvider;
import com.rss.common.dataprovider.ArticleObjectMapper;

@Component
@Scope("prototype")
public class ArticleFetcher {

    // Since reading from redis is not completely configured,
    // it reads from database currently

    public Map<String, List<Article>> fetchArticles(Set<String> urlSet) {
        // If cache contains articles, read from the cache.
        // Else, read from the database.
        // Cache expiry timer is set to 10 minutes by default if not explicity set
        Map<String, List<Article>> mapOfArticles = new HashMap<String, List<Article>>();

        for (String url : urlSet) {

            List<ArticleObjectMapper> listFromDB = DBDataProvider.getArticles(url);
            List<Article> articlesListFromDB = new ArrayList<Article>();
            for (ArticleObjectMapper mapper : listFromDB) {
                articlesListFromDB.add(new Article(mapper));
            }
            mapOfArticles.put(url, articlesListFromDB);
        }
        return mapOfArticles;
    }
}