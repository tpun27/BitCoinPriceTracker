package com.distinguished.bitcoinpricetracker.pojos;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class BitcoinPriceHistory {

    @SerializedName("bpi")
    public JsonElement priceHistory;

    public String[] dates;
    public Float[] prices;

    public void parsePriceHistory() {
        int numEntries = priceHistory.getAsJsonObject().entrySet().size();
        dates = new String[numEntries];
        prices = new Float[numEntries];

        int entryIndex = 0;
        for (Map.Entry<String, JsonElement> entry : priceHistory.getAsJsonObject().entrySet()) {
            dates[entryIndex] = entry.getKey();
            prices[entryIndex] = Float.parseFloat(entry.getValue().toString());
            entryIndex++;
        }
    }

    public String[] getDates() {
        return dates;
    }

    public Float[] getPrices() {
        return prices;
    }
}
