package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.model.entity.ActivityEntity;
import com.android.wool.model.entity.BuildOrderEntity;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.model.entity.LogisticsInfoEntity;
import com.android.wool.presenter.BuildOrderPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.adapter.BuildOrderAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/19
 */
public class BuildOrderActivity extends BaseActivity<BuildOrderPresenterImpl> implements BuildOrderView{
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    private BuildOrderAdapter adapter;
    private String totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BuildOrderPresenterImpl(this);
        initView();
    }

    @Override
    public void initView() {
        adapter = new BuildOrderAdapter(this,presenter);
        listView.setAdapter(adapter);
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.enter_order);
        totalPrice = getIntent().getStringExtra("total_price");
        tvPrice.setText(String.format(getString(R.string.rmb_none),totalPrice));
        presenter.buildOrder(this);
        EventBus.getDefault().register(this);
        tvCommit.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_build_order;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
            case R.id.tv_commit:
                LocationEntity locationEntity = adapter.getLocationEntity();
                String logisticsId = "";
                String activityId = "";
                if(locationEntity == null){
                    Toast.makeText(this,R.string.location_select,Toast.LENGTH_SHORT).show();
                }else {
                    LogisticsInfoEntity logisticsInfoEntity = adapter.getLogisticsInfoEntity();
                    ActivityEntity activityEntity = adapter.getActivityEntity();
                    if(logisticsInfoEntity != null){
                        logisticsId = logisticsInfoEntity.getId();
                    }
                    if(activityEntity != null){
                        activityId = activityEntity.getId();
                    }
                    presenter.submitOrder(this,locationEntity.getId(),logisticsId,activityId,adapter.getRemark());
                }
                break;
        }
    }

    @Override
    public void getBuildEntity(BuildOrderEntity entity) {
        adapter.resetData(entity);
        if(entity != null){
            List<ActivityEntity> list = entity.getActivity_list();
            if(list != null && list.size()>0){
                tvDiscount.setText(String.format(getString(R.string.preferential_rmb),list.get(0).getDiscount()));
            }
            String countPrice = "0";
            List<LogisticsInfoEntity> logistics = entity.getLogistics_info();
            if(logistics != null && logistics.size()>0)
                yfPrice = logistics.get(0).getPrice();
            List<ActivityEntity> activityEntityList = entity.getActivity_list();
            if(activityEntityList != null && activityEntityList.size()>0)
                activityPrice = activityEntityList.get(0).getDiscount();
            countPrice = AppTools.addition(totalPrice,yfPrice);
            countPrice = AppTools.subtract(countPrice,activityPrice);
            tvPrice.setText(countPrice);
        }
    }

    private String yfPrice = "0";
    private String activityPrice = "0";
    @Override
    public void setPrice(String price, boolean add) {
        if(!TextUtils.isEmpty(price)) {
            if(add){
                yfPrice = price;
            }else {
                activityPrice = price;
            }
            String countPrice = AppTools.addition(totalPrice,yfPrice);
            countPrice = AppTools.subtract(countPrice,activityPrice);
            tvPrice.setText(countPrice);
        }
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEven(IoMessage<LocationEntity> event){
        LocationEntity entity = event.getT();
        adapter.resetLocation(entity);
    }

    @Override
    public void startPayActivity(String total,String order_no,String orderId) {
        EventBus.getDefault().post(new IoMessage<>());
        Intent intent = new Intent(this,PayTypeActivity.class);
        intent.putExtra("total",total);
        intent.putExtra("order_no",order_no);
        intent.putExtra("order_id",orderId);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}