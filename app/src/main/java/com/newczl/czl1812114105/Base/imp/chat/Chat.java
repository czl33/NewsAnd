package com.newczl.czl1812114105.Base.imp.chat;

public class Chat {
    public static int SELT_CHAT=1;
    public static int ROBOT_CHAT=2;
    private int ImageIds;
    private String mess;
    private int type;

    public Chat(int imageIds, String mess, int type) {
        ImageIds = imageIds;
        this.mess = mess;
        this.type = type;
    }

    public int getImageIds() {
        return ImageIds;
    }

    public void setImageIds(int imageIds) {
        ImageIds = imageIds;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
