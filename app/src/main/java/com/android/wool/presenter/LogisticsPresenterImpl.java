package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.LogisticsTimeEntity;
import com.android.wool.model.interactor.LogisticsInteractor;
import com.android.wool.model.interactor.LogisticsInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.LogisticsListView;

import org.xutils.x;

import java.util.List;

/**
 * Created by AiMr on 2017/10/27
 */
public class LogisticsPresenterImpl extends DetachController<LogisticsListView> implements LogisticsPresenter{
    private LogisticsInteractor logisticsInteractor;
    public LogisticsPresenterImpl(LogisticsListView mView) {
        super(mView);
        logisticsInteractor = new LogisticsInteractorImpl();
    }

    @Override
    public void getLogistics(final Context context, String order_no) {
        logisticsInteractor.getLogisticsInfo(order_no,
                new LogisticsInteractor.LogisticsListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<LogisticsTimeEntity> entity = response.parsFromData("list",new LogisticsTimeEntity());
                            mView.getLogistics(entity);
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
