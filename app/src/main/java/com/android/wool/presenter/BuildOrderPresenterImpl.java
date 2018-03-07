package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.BuildOrderEntity;
import com.android.wool.model.interactor.GoToBalanceInteractor;
import com.android.wool.model.interactor.GoToBalanceInteractorImpl;
import com.android.wool.model.interactor.OrderSubmitInteractor;
import com.android.wool.model.interactor.OrderSubmitInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.BuildOrderView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/10/19
 */
public class BuildOrderPresenterImpl extends DetachController<BuildOrderView> implements BuildOrderPresenter{
    private GoToBalanceInteractor balanceInteractor;
    private OrderSubmitInteractor submitInteractor;
    public BuildOrderPresenterImpl(BuildOrderView mView) {
        super(mView);
        balanceInteractor = new GoToBalanceInteractorImpl();
        submitInteractor = new OrderSubmitInteractorImpl();
    }

    @Override
    public void buildOrder(final Context context) {
        showLoading();
        balanceInteractor.getConfirm(context, new GoToBalanceInteractor.GoToBalanceListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    BuildOrderEntity entity = response.getEntity("list", new BuildOrderEntity());
                    mView.getBuildEntity(entity);
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
    public void submitOrder(final Context context, String addr_id, String logistics_id, String activity_id, String comment) {
        showLoading();
        submitInteractor.submitOrder(context, addr_id, logistics_id, activity_id, comment,
                new OrderSubmitInteractor.OrderSubmitListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            String total = response.getStringForData("total");
                            String order_no = response.getStringForData("order_no");
                            String orderId = response.getStringForData("order_id");
                            mView.startPayActivity(total,order_no,orderId);
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
    public void setPrice(String price, boolean add) {
        if(isAttach())
            mView.setPrice(price,add);
    }
}
