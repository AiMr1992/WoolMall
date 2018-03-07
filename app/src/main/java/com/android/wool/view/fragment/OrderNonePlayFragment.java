package com.android.wool.view.fragment;
import com.android.wool.R;
import com.android.wool.base.VBaseFragment;
import com.android.wool.eventbus.OrderMsgEvent;
import com.android.wool.model.entity.OrderListEntity;
import com.android.wool.presenter.OrderListPresenterImpl;
import com.android.wool.view.activity.OrderDetailView;
import com.android.wool.view.adapter.OrderNonePlayAdapter;
import com.android.wool.view.constom.RefreshListView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/12
 * 待付款
 */
public class OrderNonePlayFragment extends VBaseFragment<OrderListPresenterImpl> implements OrderView{
    @BindView(R.id.list_view)
    RefreshListView listView;
    private int page = 1;
    private OrderNonePlayAdapter adapter;

    public void data(){
        showLoading();
        page = 1;
        presenter.getOrderList(getActivity(),"1",page+"");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {
        presenter = new OrderListPresenterImpl(this);
        adapter = new OrderNonePlayAdapter(getActivity(),presenter);
        listView.setRefreshOn(true,true);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                page = 1;
                presenter.getOrderList(getActivity(),"1",page+"");
            }

            @Override
            public void onUpRefresh() {
                if(!flag)
                    presenter.getOrderList(getActivity(),"1",page+"");
            }
        });
        listView.setAdapter(adapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEven(OrderMsgEvent event){
        if(event.getPager_type().equals(OrderDetailView.ORDER_NONE_PAGER_PLAY)){
            if(event.getEdit_type().equals(OrderDetailView.ORDER_CANCEL)){
                orderCancelSuccess(event.getPosition());
            }
        }
    }

    private boolean flag;
    @Override
    public void getOrderList(List<OrderListEntity> list,boolean flag) {
        this.flag = flag;
        listView.setLoadFinish(flag);
        if(page == 1){
            adapter.resetData(list);
        }else {
            adapter.addAllData(list);
        }
        if(list != null && list.size() > 0){
            page ++;
        }
    }

    @Override
    public void init() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
        listView.setRefreshFinish();
    }

    @Override
    public void orderCancelSuccess(int position) {
        adapter.cancelSuccess(position);
    }

    @Override
    public void orderDelSuccess(int position) {

    }

    @Override
    public void orderTackSuccess(int position) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}