package com.android.wool.eventbus;
/**
 * Created by AiMr on 2017/11/13
 */
public class TapeMsgEvent2 extends MessageEvent{
    private String path;
    private long time;

    public TapeMsgEvent2(String path,long time) {
        this.path = path;
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
