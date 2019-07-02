package com.newczl.czl1812114105.Bean;

import java.util.List;

public class GovNews {

    /**
     * showapi_res_error :
     * showapi_res_id : 38013de6aaf041e884ddd369e64a9049
     * showapi_res_code : 0
     * showapi_res_body : {"ret_code":0,"pagebean":{"allPages":207257,"contentlist":[{"pubDate":"2019-06-01 21:37:24","channelName":"国内最新","desc":"　　中新网永州6月1日电 (盘林 黄潜 何红福)6月1日至4日，湖南永州市道县举行传统龙船赛，共有144艘龙船、3400余名运动员在潇水河上展开竞渡，预计吸引前来观看的观众达40万人次。","channelId":"5572a109b3cdc86cf39001db","link":"http://www.chinanews.com/sh/2019/06-01/8853768.shtml","id":"b5da3c01c2245c3401c36d8718910cf4","havePic":true,"title":"湖南道县举行传统龙船赛 百余艘龙船竞渡","imageurls":[{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268120.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268130.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268140.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268150.jpg"}],"source":"中国新闻网"}],"currentPage":1,"allNum":207257,"maxResult":1}}
     */

    private String showapi_res_error;
    private String showapi_res_id;
    private int showapi_res_code;
    private ShowapiResBodyBean showapi_res_body;

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public String getShowapi_res_id() {
        return showapi_res_id;
    }

    public void setShowapi_res_id(String showapi_res_id) {
        this.showapi_res_id = showapi_res_id;
    }

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * ret_code : 0
         * pagebean : {"allPages":207257,"contentlist":[{"pubDate":"2019-06-01 21:37:24","channelName":"国内最新","desc":"　　中新网永州6月1日电 (盘林 黄潜 何红福)6月1日至4日，湖南永州市道县举行传统龙船赛，共有144艘龙船、3400余名运动员在潇水河上展开竞渡，预计吸引前来观看的观众达40万人次。","channelId":"5572a109b3cdc86cf39001db","link":"http://www.chinanews.com/sh/2019/06-01/8853768.shtml","id":"b5da3c01c2245c3401c36d8718910cf4","havePic":true,"title":"湖南道县举行传统龙船赛 百余艘龙船竞渡","imageurls":[{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268120.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268130.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268140.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268150.jpg"}],"source":"中国新闻网"}],"currentPage":1,"allNum":207257,"maxResult":1}
         */

        private int ret_code;
        private PagebeanBean pagebean;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public PagebeanBean getPagebean() {
            return pagebean;
        }

        public void setPagebean(PagebeanBean pagebean) {
            this.pagebean = pagebean;
        }

        public static class PagebeanBean {
            /**
             * allPages : 207257
             * contentlist : [{"pubDate":"2019-06-01 21:37:24","channelName":"国内最新","desc":"　　中新网永州6月1日电 (盘林 黄潜 何红福)6月1日至4日，湖南永州市道县举行传统龙船赛，共有144艘龙船、3400余名运动员在潇水河上展开竞渡，预计吸引前来观看的观众达40万人次。","channelId":"5572a109b3cdc86cf39001db","link":"http://www.chinanews.com/sh/2019/06-01/8853768.shtml","id":"b5da3c01c2245c3401c36d8718910cf4","havePic":true,"title":"湖南道县举行传统龙船赛 百余艘龙船竞渡","imageurls":[{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268120.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268130.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268140.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268150.jpg"}],"source":"中国新闻网"}]
             * currentPage : 1
             * allNum : 207257
             * maxResult : 1
             */

            private int allPages;
            private int currentPage;
            private int allNum;
            private int maxResult;
            private List<ContentlistBean> contentlist;

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<ContentlistBean> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentlistBean> contentlist) {
                this.contentlist = contentlist;
            }

            public static class ContentlistBean {
                /**
                 * pubDate : 2019-06-01 21:37:24
                 * channelName : 国内最新
                 * desc : 　　中新网永州6月1日电 (盘林 黄潜 何红福)6月1日至4日，湖南永州市道县举行传统龙船赛，共有144艘龙船、3400余名运动员在潇水河上展开竞渡，预计吸引前来观看的观众达40万人次。
                 * channelId : 5572a109b3cdc86cf39001db
                 * link : http://www.chinanews.com/sh/2019/06-01/8853768.shtml
                 * id : b5da3c01c2245c3401c36d8718910cf4
                 * havePic : true
                 * title : 湖南道县举行传统龙船赛 百余艘龙船竞渡
                 * imageurls : [{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268120.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268130.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268140.jpg"},{"height":0,"width":0,"url":"http://i2.chinanews.com/simg/2019/190601//100268150.jpg"}]
                 * source : 中国新闻网
                 */

                private String pubDate;
                private String channelName;
                private String desc;
                private String channelId;
                private String link;
                private String id;
                private boolean havePic;
                private String title;
                private String source;
                private List<ImageurlsBean> imageurls;

                public String getPubDate() {
                    return pubDate;
                }

                public void setPubDate(String pubDate) {
                    this.pubDate = pubDate;
                }

                public String getChannelName() {
                    return channelName;
                }

                public void setChannelName(String channelName) {
                    this.channelName = channelName;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getChannelId() {
                    return channelId;
                }

                public void setChannelId(String channelId) {
                    this.channelId = channelId;
                }

                public String getLink() {
                    return link;
                }

                public void setLink(String link) {
                    this.link = link;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isHavePic() {
                    return havePic;
                }

                public void setHavePic(boolean havePic) {
                    this.havePic = havePic;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getSource() {
                    return source;
                }

                public void setSource(String source) {
                    this.source = source;
                }

                public List<ImageurlsBean> getImageurls() {
                    return imageurls;
                }

                public void setImageurls(List<ImageurlsBean> imageurls) {
                    this.imageurls = imageurls;
                }

                public static class ImageurlsBean {
                    /**
                     * height : 0
                     * width : 0
                     * url : http://i2.chinanews.com/simg/2019/190601//100268120.jpg
                     */

                    private int height;
                    private int width;
                    private String url;

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }
    }
}
