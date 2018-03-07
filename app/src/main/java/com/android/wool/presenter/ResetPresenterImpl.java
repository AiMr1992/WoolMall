package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.interactor.ResetInteractor;
import com.android.wool.model.interactor.ResetInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.ResetPwdView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/9/22
 */
public class ResetPresenterImpl extends DetachController<ResetPwdView> implements ResetPresenter{
    private ResetInteractor resetInteractor;
    public ResetPresenterImpl(ResetPwdView mView) {
        super(mView);
        resetInteractor = new ResetInteractorImpl();
    }

    @Override
    public void commit(String Phone, String Pwd, String Pwd2,final Context context) {
        showLoading();
        resetInteractor.commit(Phone, Pwd, Pwd2, new ResetInteractor.ResetListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                disMissProgress();
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.commitSuccess();
                }
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