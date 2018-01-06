package com.distinguished.bitcoinpricetracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.distinguished.bitcoinpricetracker.R;
import com.distinguished.bitcoinpricetracker.pojos.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private List<Article> articleList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitleTextView, articleDateTextView;

        public ViewHolder(View v) {
            super(v);
            articleTitleTextView = (TextView) v.findViewById(R.id.articleTitleTextView);
            articleDateTextView = (TextView) v.findViewById(R.id.articleDateTextView);
        }
    }

    public ArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Article article = ((Article) articleList.get(position));
        holder.articleDateTextView.setText(article.getPubDate());
        holder.articleTitleTextView.setText(article.getTitle());

        holder.articleTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getArticleLink()));
                context.startActivity(browserIntent);
            }
        });

        holder.articleDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getArticleLink()));
                context.startActivity(browserIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
