package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.PayTypeEntity;
import com.android.wool.model.interactor.PayTypeInteractor;
import com.android.wool.model.interactor.PayTypeInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.PayTypeView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/10/24
 */
public class PayTypePresenterImpl extends DetachController<PayTypeView> implements PayTypePresenter{
    private PayTypeInteractor interactor;
    public PayTypePresenterImpl(PayTypeView mView) {
        super(mView);
        interactor = new PayTypeInteractorImpl();
    }

    @Override
    public void payTypeList(final Context context) {
        showLoading();
        interactor.payTypeList(new PayTypeInteractor.PayTypeListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    List<PayTypeEntity> mList = response.parsFromData("list",new PayTypeEntity());
                    mView.payTypeList(mList);
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