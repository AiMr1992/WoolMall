package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/9/22
 */
public interface RetrievePresenter {
    void sendMsg(String phone, String type, Context context);
    void commit(String Phone,String Code,Context context);
}
