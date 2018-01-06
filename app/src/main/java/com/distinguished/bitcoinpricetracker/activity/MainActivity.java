package com.distinguished.bitcoinpricetracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.distinguished.bitcoinpricetracker.R;
import com.distinguished.bitcoinpricetracker.clients.BitcoinCurrentPriceAPI;
import com.distinguished.bitcoinpricetracker.pojos.BitcoinCurrentPrice;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String BASE_URL = "https://api.coindesk.com/";

    TextView USDPriceTextView, GBPPriceTextView, EURPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(myToolbar);

        USDPriceTextView = (TextView) findViewById(R.id.USDPriceTextView);
        GBPPriceTextView = (TextView) findViewById(R.id.GBPPriceTextView);
        EURPriceTextView = (TextView) findViewById(R.id.EURPriceTextView);

        populateCurrentPrices();
    }

    public void populateCurrentPrices() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        BitcoinCurrentPriceAPI bitcoinCurrentPriceAPI = retrofit.create(BitcoinCurrentPriceAPI.class);

        Call<BitcoinCurrentPrice> call = bitcoinCurrentPriceAPI.loadBitcoinCurrentPrices();

        call.enqueue(new Callback<BitcoinCurrentPrice>() {
            @Override
            public void onResponse(Call<BitcoinCurrentPrice> call, Response<BitcoinCurrentPrice> response) {
                if (response.isSuccessful()) {
                    BitcoinCurrentPrice currentPriceObj = response.body();

                    currentPriceObj.parseCurrentPrices();
                    String[] currencySymbols = currentPriceObj.getCurrencySymbols();
                    Float[] prices = currentPriceObj.getPrices();

                    TextView[] textViewObjects = { USDPriceTextView, GBPPriceTextView, EURPriceTextView };

                    for (int i = 0; i < textViewObjects.length; i++) {
                        String priceText = currencySymbols[i] + ": " + prices[i];
                        textViewObjects[i].setText(priceText);
                    }
                }
                else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BitcoinCurrentPrice> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.actionGraph:
                intent = new Intent(this, HistoricalPriceActivity.class);
                startActivity(intent);
                return true;

            case R.id.actionNews:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                return true;

            case R.id.actionCurrentPrice:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
