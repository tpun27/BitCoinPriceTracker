package com.distinguished.bitcoinpricetracker.pojos;

import com.distinguished.bitcoinpricetracker.pojos.Article;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class RSSFeed {

    @ElementList(name = "item", inline = true)
    @Path("channel")
    private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
