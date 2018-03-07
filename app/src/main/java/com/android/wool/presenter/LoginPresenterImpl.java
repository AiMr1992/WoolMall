package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;

import com.android.wool.R;
import com.android.wool.base.DetachController;
import com.android.wool.common.MyPreference;
import com.android.wool.common.UMEventUtils;
import com.android.wool.model.entity.UserEntity;
import com.android.wool.model.interactor.LoginInteractor;
import com.android.wool.model.interactor.LoginInteractorImpl;
import com.android.wool.model.interactor.RegisterInteractor;
import com.android.wool.model.interactor.RegisterInteractorImpl;
import com.android.wool.model.interactor.SendMsgInteractor;
import com.android.wool.model.interactor.SendMsgInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.LoginView;
import com.android.wool.view.constom.SelectRegisterView;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by AiMr on 2017/9/22
 */
public class LoginPresenterImpl extends DetachController<LoginView> implements LoginPresenter{
    private LoginInteractor loginInteractor;
    private SendMsgInteractor sendMsgInteractor;
    private RegisterInteractor registerInteractor;
    public LoginPresenterImpl(LoginView mView) {
        super(mView);
        loginInteractor = new LoginInteractorImpl();
        sendMsgInteractor = new SendMsgInteractorImpl();
        registerInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void login(String phone, String pwd, final Context context) {
        showLoading();
        loginInteractor.login(phone, pwd, new LoginInteractor.LoginListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    UserEntity userEntity = response.getEntity("list",new UserEntity());
                    MyPreference.getInstance().savePreferenceData(context,MyPreference.UID,userEntity.getId());
                    MyPreference.getInstance().savePreferenceData(context,MyPreference.PHONE,userEntity.getPhone());
                    MyPreference.getInstance().savePreferenceData(context,MyPreference.USER_TYPE,userEntity.getRoles());
                    mView.startMainActivity();

                    Map<String,String> map = new HashMap();
                    map.put(context.getString(R.string.login),context.getString(R.string.login));
                    UMEventUtils.pushEvent(context,context.getString(R.string.login),map);
                }
                disMissProgress();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                disMissProgress();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }

    @Override
    public void sendMsg(String phone, final Context context, final SelectRegisterView view) {
        showLoading();
        sendMsgInteractor.sendMsg(phone, "reg", new SendMsgInteractor.RegisterListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    view.messageCountDown();
                }
                disMissProgress();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                disMissProgress();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }

    @Override
    public void register(String Phone,final Context context,String Code, String Pwd) {
        showLoading();
        registerInteractor.register(Phone, Code, Pwd, new RegisterInteractor.RegisterListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.showLoginView();
                }
                disMissProgress();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                disMissProgress();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }
}