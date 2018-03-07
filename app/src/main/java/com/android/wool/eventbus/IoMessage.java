package com.android.wool.eventbus;
/**
 * Created by AiMr on 2017/9/21.
 */
public class IoMessage<T> extends MessageEvent{
    public IoMessage() {
    }

    public IoMessage(T t) {
        this.t = t;
    }

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
