package com.newczl.czl1812114105.Bean;

import java.util.ArrayList;

/**
 * 新闻中心对象，
 * 遇到{}类，[]数组
 */
public class NewsMenu {
    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData{
        public int id;
        public String title;
        public int type;
        public ArrayList<NewsDataChildren> children;
    }
    public class NewsDataChildren{
        public int id;
        public String title;
        public int type;
        public String url;

    }

    @Override
    public String toString() {
        return "NewsMenu{" +
                "retcode=" + retcode +
                ", extend=" + extend +
                ", data=" + data +
                '}';
    }
}
