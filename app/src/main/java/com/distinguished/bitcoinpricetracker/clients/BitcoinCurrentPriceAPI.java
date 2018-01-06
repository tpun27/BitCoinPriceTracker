package com.distinguished.bitcoinpricetracker.clients;

import com.distinguished.bitcoinpricetracker.pojos.BitcoinCurrentPrice;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BitcoinCurrentPriceAPI {

    @GET("v1/bpi/currentprice.json")
    Call<BitcoinCurrentPrice> loadBitcoinCurrentPrices();
}
