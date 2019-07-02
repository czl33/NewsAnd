package com.newczl.czl1812114105.Bean;

public class sendRobot{
    public  int reqType;//类型
    public Perception perception;//内容
    public User userInfo;//用户信息

    public sendRobot( Perception perception) {
        this.reqType = 0;
        this.perception = perception;
        this.userInfo=new User();
    }

    public static   class Perception{
        public Perception(String text) {
            this.inputText =new Input(text);
        }
        public Input inputText;

    }
    public static  class Input{
        public Input(String text) {
            this.text = text;
        }

        public String text;
    }
    public class User{
        public final String apiKey="b1e1893d03624bfd9cf742e47635b5fc";
        public final String userId="123";
    }

}
