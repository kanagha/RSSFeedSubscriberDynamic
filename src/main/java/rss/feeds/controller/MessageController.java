package rss.feeds.controller;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Responsible for configuring the endpoint for receiving latest feeds
 * @author Kanagha
 */
@Controller
public class MessageController {
  
  private SimpMessagingTemplate template;
  @Inject
  public MessageController(SimpMessagingTemplate template) {
    this.template = template;
  }
  
  /**
   * This will subscribe the user to the latest feeds for the given channelId
   * And a job scheduler will be kicked off
   * @param message
   */
  @MessageMapping("/getfeeds")
  public void greeting(Principal principal) throws Exception {
	  //Principal principal = message.getHeaders().get(SimpMessageHeaderAccessor.USER_HEADER, Principal.class);
		String reply =  principal.getName();
		System.out.println("sending " + reply);
		template.convertAndSendToUser(principal.getName(), "/queue/messages", reply);
  }
}