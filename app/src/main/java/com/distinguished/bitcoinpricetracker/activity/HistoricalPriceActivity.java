package com.distinguished.bitcoinpricetracker.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.distinguished.bitcoinpricetracker.R;
import com.distinguished.bitcoinpricetracker.clients.BitcoinAPI;
import com.distinguished.bitcoinpricetracker.pojos.BitcoinPriceHistory;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoricalPriceActivity extends AppCompatActivity {

    static final String BASE_URL = "https://api.coindesk.com/";

    LineChart historicalMonthChart;
    String[] dates;
    Float[] prices;
    List<Entry> dataEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_price_graph);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.priceGraphToolbar);
        setSupportActionBar(myToolbar);

        historicalMonthChart = (LineChart) findViewById(R.id.historicalMonthChart);

        generateHistoricalMonthChart();
    }

    public void generateHistoricalMonthChart() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BitcoinAPI bitcoinAPI = retrofit.create(BitcoinAPI.class);

        Call<BitcoinPriceHistory> call = bitcoinAPI.loadBitcoinPriceHistory();
        call.enqueue(new Callback<BitcoinPriceHistory>() {
            @Override
            public void onResponse(Call<BitcoinPriceHistory> call, Response<BitcoinPriceHistory> response) {
                if (response.isSuccessful()) {
                    BitcoinPriceHistory bitcoinPriceHistory = response.body();

                    bitcoinPriceHistory.parsePriceHistory();
                    dates = bitcoinPriceHistory.getDates();
                    prices = bitcoinPriceHistory.getPrices();

                    populateChart();
                }
                else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<BitcoinPriceHistory> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void populateChart() {
        dataEntries = new ArrayList<>();

        int entryIndex = 0;
        for (float price : prices) {
            dataEntries.add(new Entry(entryIndex, price));
            entryIndex++;
        }

        LineDataSet dataSet = new LineDataSet(dataEntries, "BitCoin Prices");
        LineData lineData = new LineData(dataSet);
        historicalMonthChart.setData(lineData);
        historicalMonthChart.invalidate();
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
