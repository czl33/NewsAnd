package com.newczl.czl1812114105.Bean;

import java.util.ArrayList;

public class NewsTabBean {
    public NewsTab data;
    public class NewsTab{
        public String more;
        public ArrayList<TopNews> topnews;
        public ArrayList<NewsData> news;
    }
    public class NewsData{//新闻列表
        public int id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;
    }
    public class TopNews{//顶部图片内容
        public int id;
        public String topimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;

        @Override
        public String toString() {
            return topimage;
        }
    }
}
