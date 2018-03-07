package com.android.wool.eventbus;
/**
 * Created by AiMr on 2017/11/13
 */
public class TapeMsgEvent extends MessageEvent{
    private boolean isPlay;
    private String path;

    public TapeMsgEvent(boolean isPlay, String path) {
        this.isPlay = isPlay;
        this.path = path;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
