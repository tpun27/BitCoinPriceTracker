package com.distinguished.bitcoinpricetracker;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BitcoinAPI {

    @GET("v1/bpi/historical/close.json")
        Call<BitcoinPriceHistory> loadBitcoinPriceHistory();
}
