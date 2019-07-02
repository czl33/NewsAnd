package com.newczl.czl1812114105.Bean;

import java.util.ArrayList;

public class RobotMsg {//这是对象接收的类
    public Intent intent;
    public ArrayList<Results> results;


    public class Intent{
        public int code;//最后状态码
    }

    public class Results{
        public int groupType;
        public String resultType;//返回的类型
        public Values values;
    }

    public class Values{
        public String text;
    }
}
