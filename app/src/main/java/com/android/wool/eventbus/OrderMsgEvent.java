package com.android.wool.eventbus;
/**
 * Created by AiMr on 2017/10/24
 */
public class OrderMsgEvent extends MessageEvent{
    private String pager_type;
    private String edit_type;
    private int position;

    public OrderMsgEvent(String pager_type, String edit_type,int position) {
        this.pager_type = pager_type;
        this.edit_type = edit_type;
        this.position = position;
    }

    public String getPager_type() {
        return pager_type;
    }

    public String getEdit_type() {
        return edit_type;
    }

    public int getPosition() {
        return position;
    }
}
