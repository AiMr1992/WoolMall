package com.android.wool.presenter;
import android.content.Context;
import com.android.wool.view.constom.SelectRegisterView;
/**
 * Created by AiMr on 2017/9/22
 */
public interface LoginPresenter {
    void login(String phone,String pwd,Context context);
    void sendMsg(String phone,Context context,SelectRegisterView view);
    void register(String Phone,Context context,String Code, String Pwd);
}
