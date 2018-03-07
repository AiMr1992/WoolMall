package com.android.wool.eventbus;
/**
 * Created by AiMr on 2017/10/11
 */
public class LocationMessage<T> extends MessageEvent{
    public static final int INSER = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;

    private int activity;
    private int position;
    private T t;

    public LocationMessage(int activity,int position, T t) {
        this.activity = activity;
        this.t = t;
        this.position = position;
    }

    public T getT() {
        return t;
    }
    public void setT(T t) {
        this.t = t;
    }

    public int isActivity() {
        return activity;
    }

    public int getPosition() {
        return position;
    }
}
