package rss.feeds.subscriber;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import rss.feeds.controller.RssFeedsSubscriberApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RssFeedsSubscriberApplication.class)
@WebAppConfiguration
public class RssFeedsSubscriberApplicationTests {

	@Test
	public void contextLoads() {
	}

}
