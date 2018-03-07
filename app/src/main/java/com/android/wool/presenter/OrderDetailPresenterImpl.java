package com.android.wool.presenter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.LogisticsEntity;
import com.android.wool.model.entity.OrderDetailEntity;
import com.android.wool.model.interactor.LogisticsInteractor;
import com.android.wool.model.interactor.LogisticsInteractorImpl;
import com.android.wool.model.interactor.OrderDetailInteractor;
import com.android.wool.model.interactor.OrderDetailInteractorImpl;
import com.android.wool.model.interactor.OrderEditInteractor;
import com.android.wool.model.interactor.OrderEditInteractorImpl;
import com.android.wool.model.interactor.OrderResetInteractor;
import com.android.wool.model.interactor.OrderResetInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.MainActivity;
import com.android.wool.view.activity.OrderDetailActivity;
import com.android.wool.view.activity.OrderDetailView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/10/16
 */
public class OrderDetailPresenterImpl extends DetachController<OrderDetailView> implements OrderDetailPresenter{
    private OrderDetailInteractor detailInteractor;
    private OrderEditInteractor editInteractor;
    private LogisticsInteractor logisticsInteractor;
    private OrderResetInteractor resetInteractor;
    public OrderDetailPresenterImpl(OrderDetailView mView) {
        super(mView);
        detailInteractor = new OrderDetailInteractorImpl();
        editInteractor = new OrderEditInteractorImpl();
        logisticsInteractor = new LogisticsInteractorImpl();
        resetInteractor = new OrderResetInteractorImpl();
    }

    @Override
    public void getOrderDetail(final Context context,String orderId) {
        showLoading();
        detailInteractor.getOrderDetail(context, orderId,
                new OrderDetailInteractor.OrderDetailListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            OrderDetailEntity entity = response.getEntity("list",new OrderDetailEntity());
                            mView.getOrderDetailEntity(entity);
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
    public void editOrder(final Context context, String order_id, final String type, final int position) {
        showLoading();
        editInteractor.editOrder(context, order_id, type,
                new OrderEditInteractor.OrderEditListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.editSuccess(position,type);
                        }
                        mView.disMissProgress();
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
    public void getLogistics(final Context context, String order_no) {
        logisticsInteractor.getLogisticsInfo(order_no,
                new LogisticsInteractor.LogisticsListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            LogisticsEntity entity = response.getEntity("list",new LogisticsEntity());

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
    public void resetOrder(final Context context, String order_id) {
        resetInteractor.resetOrder(context, order_id,
                new OrderResetInteractor.OrderResetListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("cart","true");
                            context.startActivity(intent);
                            ((OrderDetailActivity)context).finish();
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