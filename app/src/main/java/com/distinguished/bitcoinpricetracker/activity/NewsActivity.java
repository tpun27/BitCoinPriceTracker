package com.distinguished.bitcoinpricetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.distinguished.bitcoinpricetracker.ArticleAdapter;
import com.distinguished.bitcoinpricetracker.R;
import com.distinguished.bitcoinpricetracker.clients.GoogleFinanceAPI;
import com.distinguished.bitcoinpricetracker.pojos.Article;
import com.distinguished.bitcoinpricetracker.pojos.RSSFeed;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class NewsActivity extends AppCompatActivity {

    static final String BASE_URL = "https://finance.google.com/";
    RecyclerView articleRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.newsToolbar);
        setSupportActionBar(myToolbar);

        populateApp();
    }

    public void populateApp() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).build();

        GoogleFinanceAPI googleNewsAPI = retrofit.create(GoogleFinanceAPI.class);

        Call<RSSFeed> call = googleNewsAPI.loadRSSFeed();
        call.enqueue(new Callback<RSSFeed>() {
            @Override
            public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
                if (response.isSuccessful()) {
                    RSSFeed rss = response.body();

                    List<Article> articleList = rss.getArticleList();

                    articleRecyclerView = (RecyclerView) findViewById(R.id.articleRecyclerView);
                    articleRecyclerView.setHasFixedSize(true);
                    articleRecyclerView.setLayoutManager(new LinearLayoutManager(NewsActivity.this));

                    ArticleAdapter adapter = new ArticleAdapter(rss.getArticleList());
                    articleRecyclerView.setAdapter(adapter);
                }
                else {
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<RSSFeed> call, Throwable t) {
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
