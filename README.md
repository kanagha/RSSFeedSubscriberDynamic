RSSFeedSubscriber
========================

This is the subscriber(client) part of RSS FeedAggregator:

1) A client registers at first with the application.
2) Once registered, a client can create many channels.
3) Each channel will be attributed to a specific topic (Ex: News/ Sports/Travel etc.,) with a set of urls for which latest feeds need to be fetched.
4) Once channels are defined, a user can create a websocket endpoint one for each of the channelId.
5) The application starts scheduling jobs inorder to fetch latest feeds for the urls associated with the channelId
    at a preconfigured interval.


