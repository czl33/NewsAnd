package com.newczl.czl1812114105.Global;

import org.xutils.http.RequestParams;

public class requestParams {//全局使用的请求地址
    public static final String SERVER_URL="https://www.newczl.cn/zhbj/";//服务器地址
    public static final RequestParams CATEGORY_URL=
            new RequestParams(SERVER_URL+"categories.json");//这是新闻的请求地址
    public static final RequestParams PHOTO_URL=
            new RequestParams(SERVER_URL+"photos/photos_1.json");//组图的请求地址

}
