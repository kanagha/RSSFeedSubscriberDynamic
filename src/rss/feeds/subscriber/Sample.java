package rss.feeds.subscriber;
/*import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

import com.rss.common.Article;

interface RssFeedParser{
	public String parseURL(String urlString);
}

class RssFeedParserTopics {
	
	RssFeedParser parser;
	
	public RssFeedParserTopics(RssFeedParser parser){
		this.parser = parser;
	}

	public void parseURL(String urlString) {
		String content = parser.parseURL(urlString);
		
	}
	
	private List<Article> parseArticles(String content){
		List<Article> articleList = new ArrayList<Article>();
		
		return articleList;
	}
	
}

class RssFeedParserImpl implements RssFeedParser {
	public String parseURL(String urlString){
		try{
			URL url = new URL(urlString);
			  HttpURLConnection conn =
			      (HttpURLConnection) url.openConnection();

			  if (conn.getResponseCode() != 200) {
			    throw new IOException(conn.getResponseMessage());
			  }
			  System.out.println("responsecode :" + conn.getResponseCode());

			  // Buffer the result into a string
			  BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    sb.append(line);
			  }
			  rd.close();

			  conn.disconnect();		
			  System.out.println(sb);
			  return sb.toString();
		}
		catch(Exception ex){
			System.out.println("Exception occurred " + ex);			
		}
		return null;
	}
}


public class Sample {
	public static void main(String args[]){
		RssFeedParser parser = new RssFeedParserImpl();
		String content = parser.parseURL("http://rss.cnn.com/rss/edition.rss");	

		

		
		
		URLConnection conn;
		try {
	        URL a = new URL(args[0]);
	        conn = a.openConnection();
	        InputStream is = conn.getInputStream();
	        int ret = 0;
	        while ((ret = is.read(buf)) > 0) {
	          processBuf(buf);
	        }
	        // close the inputstream
	        is.close();
	} catch (IOException e) {
	        try {
	                respCode = ((HttpURLConnection)conn).getResponseCode();
	                es = ((URLConnection)conn).getErrorStream();
	                int ret = 0;
	                // read the response body
	                while ((ret = es.read(buf)) > 0) {
	                        processBuf(buf);
	                }
	                // close the errorstream
	                es.close();
	        } catch(IOException ex) {
	                // deal with the exception
	        }		
		
		
		Jedis jedis = new Jedis("localhost");
		  //adding a new key
		  jedis.set("key", "value");
		  //getting the key value
		  System.out.println(jedis.get("key"));
	}
}
*/