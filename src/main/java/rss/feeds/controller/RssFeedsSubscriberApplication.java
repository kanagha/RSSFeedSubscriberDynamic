package rss.feeds.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = {"rss.feeds", "com"},
excludeFilters = @ComponentScan.Filter(value = Service.class)
)
@EnableAutoConfiguration
@ImportResource("classpath:Beans.xml")
public class RssFeedsSubscriberApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RssFeedsSubscriberApplication.class, args);
    }
}
