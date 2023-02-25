package com.example.feedview

class FeedAPI(
    val status: String,
    val feed: FeedInfoAPI,
    val items: ArrayList<FeedItemAPI>
)

class FeedInfoAPI(
    val title: String,
    val link: String,
    val image: String
)

class FeedItemAPI(
    val title: String,
    val link: String,
    val enclosure: EnclosureAPI,
    val description: String,
)

class EnclosureAPI(
    val link: String,
    val type: String
)