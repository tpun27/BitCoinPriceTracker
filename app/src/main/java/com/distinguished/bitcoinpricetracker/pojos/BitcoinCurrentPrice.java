package com.distinguished.bitcoinpricetracker.pojos;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class BitcoinCurrentPrice {

    @SerializedName("bpi")
    private JsonElement currentPricesObj;

    private String[] currencySymbols;
    private Float[] prices;

    public void parseCurrentPrices() {

        int numEntries = currentPricesObj.getAsJsonObject().entrySet().size();
        currencySymbols = new String[numEntries];
        prices = new Float[numEntries];

        int entryIndex = 0;
        for (Map.Entry<String, JsonElement> entry : currentPricesObj.getAsJsonObject().entrySet()) {
            JsonElement priceElement = entry.getValue();
            currencySymbols[entryIndex] = getCurrencySymbol(priceElement).substring(1,4);
            prices[entryIndex] = getPrice(priceElement);
            entryIndex++;
        }
    }

    private String getCurrencySymbol(JsonElement priceElement) {
        return priceElement.getAsJsonObject().get("code").toString();
    }

    private float getPrice(JsonElement priceElement) {
        return Float.parseFloat(priceElement.getAsJsonObject().get("rate_float").toString());
    }

    public String[] getCurrencySymbols() {
        return currencySymbols;
    }

    public Float[] getPrices() {
        return prices;
    }
}
