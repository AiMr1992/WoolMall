package com.android.wool.presenter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.OrderListEntity;
import com.android.wool.model.interactor.OrderEditInteractor;
import com.android.wool.model.interactor.OrderEditInteractorImpl;
import com.android.wool.model.interactor.OrderListInteractor;
import com.android.wool.model.interactor.OrderListInteractorImpl;
import com.android.wool.model.interactor.OrderResetInteractor;
import com.android.wool.model.interactor.OrderResetInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.MainActivity;
import com.android.wool.view.activity.OrderDetailActivity;
import com.android.wool.view.activity.OrderListActivity;
import com.android.wool.view.fragment.OrderView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/10/12
 */
public class OrderListPresenterImpl extends DetachController<OrderView> implements OrderListPresenter {
    private OrderListInteractor interactor;
    private OrderEditInteractor editInteractor;
    private OrderResetInteractor resetInteractor;
    public OrderListPresenterImpl(OrderView mView) {
        super(mView);
        interactor = new OrderListInteractorImpl();
        editInteractor = new OrderEditInteractorImpl();
        resetInteractor = new OrderResetInteractorImpl();
    }

    @Override
    public void getOrderList(final Context context, String type,String pageSize) {
        interactor.getOrderList(context, type,pageSize,
                new OrderListInteractor.OrderListListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<OrderListEntity> mList = response.parsFromData("list",new OrderListEntity());
                            mView.getOrderList(mList,response.isLoad());
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
                        mView.getOrderList(null,false);
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
                            if(mView.ORDER_CANCEL.equals(type)) {
                                mView.orderCancelSuccess(position);
                            }else if(mView.ORDER_DELETE.equals(type)){
                                mView.orderDelSuccess(position);
                            }else {
                                mView.orderTackSuccess(position);
                            }
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
                            ((OrderListActivity)context).finish();
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
