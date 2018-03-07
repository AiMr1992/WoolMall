package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.interactor.RetrieveInteractor;
import com.android.wool.model.interactor.RetrieveInteractorImpl;
import com.android.wool.model.interactor.SendMsgInteractor;
import com.android.wool.model.interactor.SendMsgInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.RetrieveView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/9/22
 */
public class RetrievePresenterImpl extends DetachController<RetrieveView> implements RetrievePresenter{
    private SendMsgInteractor sendMsgInteractor;
    private RetrieveInteractor retrieveInteractor;
    public RetrievePresenterImpl(RetrieveView mView) {
        super(mView);
        sendMsgInteractor = new SendMsgInteractorImpl();
        retrieveInteractor = new RetrieveInteractorImpl();
    }

    @Override
    public void sendMsg(String phone, String type, final Context context) {
        showLoading();
        sendMsgInteractor.sendMsg(phone, type, new SendMsgInteractor.RegisterListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.messageCountDown();
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
    public void commit(String Phone, String Code,final Context context) {
        showLoading();
        retrieveInteractor.commit(Phone, Code, new RetrieveInteractor.RetrieveListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.startResetActivity();
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
