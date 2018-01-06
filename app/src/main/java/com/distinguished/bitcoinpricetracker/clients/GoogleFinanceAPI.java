package com.distinguished.bitcoinpricetracker.clients;

import com.distinguished.bitcoinpricetracker.pojos.RSSFeed;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleFinanceAPI {

    @GET("finance/company_news?q=CURRENCY:BTC&ei=M9lQWunxMMXpmAHGxrGACg&output=rss")
    Call<RSSFeed> loadRSSFeed();
}