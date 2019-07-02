package com.newczl.czl1812114105.Bean;

import java.util.ArrayList;

/**
 * 组图的实体类
 */
public class PhotosBean {
    public PhotosData data;
    public class PhotosData{
        public ArrayList<PhotoNews> news;
    }
    public class PhotoNews{
        public int id;
        public String listimage;
        public String title;
    }
}
