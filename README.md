This is one of the projects of RSSFeedsProject which enables clients to register for receiving latest feeds for various groups of urls at a preconfigured interval.
Clients register websocket endpoints and latest feeds are published to the endpoint.

RSSFeedSubscriber
==================

This is the subscriber(client) part of RSS FeedAggregator:

1) A client registers at first with the application.

2) Once registered, a client can create many channels.

3) Each channel will be attributed to a specific topic (Ex: News/ Sports/Travel etc.,) with a set of urls for which latest feeds need to be fetched.

4) Once channels are defined, a user can create a websocket endpoint one for each of the channelId.

5) The application is supposed to scheduling jobs inorder to fetch latest feeds for the urls associated with the channelId
    at a preconfigured interval.
    
    
Calling webservice endpoints:
1) Create user /rssfeeds/subscriber

2) Create channels for each user /rssfeeds/channel

3) Subscribe to websocketendpoint for a given channel

Work In Progress:
1) Sending notifications to the client websocket endpoints once latest feeds are retrieved.

TODO:
1) Enabling user to paginate through the feeds by fetching subsequent ranges.
2) Using caching to optimize fetches.
3) Configure separate job interval schedule for each of the feed url by retrieving the max time (from GET headers) before another GET can be sent again. 

