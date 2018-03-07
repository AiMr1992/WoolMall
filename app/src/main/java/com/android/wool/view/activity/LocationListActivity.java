package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.eventbus.LocationMessage;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.presenter.LocationAddPresenter;
import com.android.wool.presenter.LocationListPresenterImpl;
import com.android.wool.view.adapter.LocationListAdapter;
import com.android.wool.view.constom.RefreshListView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/25
 */
public class LocationListActivity extends BaseActivity<LocationListPresenterImpl> implements LocationListView{
    @BindView(R.id.list_view)
    RefreshListView listView;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    private LocationListAdapter adapter;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LocationListPresenterImpl(this);
        initView();
    }

    private void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.manager_location);
        adapter = new LocationListAdapter(this,presenter, TextUtils.isEmpty(getIntent().getStringExtra("no_location"))?false:true);
        listView.setRefreshOn(true,true);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                page = 1;
                presenter.getLocationList(LocationListActivity.this,page+"");
            }

            @Override
            public void onUpRefresh() {
                if(!flag)
                    presenter.getLocationList(LocationListActivity.this,page+"");
            }
        });
        listView.setAdapter(adapter);
        tvAdd.setOnClickListener(this);
        showLoading();
        presenter.getLocationList(this,page+"");
        EventBus.getDefault().register(this);
    }

    private boolean flag;
    @Override
    public void getLocationList(List<LocationEntity> list,boolean flag) {
        this.flag = flag;
        listView.setLoadFinish(flag);
        if(page == 1) {
            adapter.resetData(list);
        }else {
            adapter.addAllData(list);
        }
        if(list != null && list.size() > 0){
            page++;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_location_list;
    }

    @Override
    public void disMissProgress() {
        listView.setRefreshFinish();
        dismissProgressDialog();
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                if(adapter.getCount() == 0){
                    EventBus.getDefault().post(new IoMessage<LocationEntity>(null));
                }
                finish();
                break;
            case R.id.tv_add:
                if(adapter.getItemCount() < 21) {
                    Intent intent = new Intent(this, LocationAddActivity.class);
                    intent.putExtra("type", LocationAddPresenter.LOCATION_ADD);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,R.string.location_length,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void deleteLocation(int position) {
        Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
        adapter.removeLocation(position);
    }

    @Override
    public void updateLocationDefault(int position) {
        adapter.defaultLocation(position);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LocationMessage<LocationEntity> event) {
        LocationEntity entity = event.getT();
        if(event.isActivity() == LocationMessage.INSER) {
            adapter.addLocation(entity);
        }else if(event.isActivity() == LocationMessage.UPDATE){
            adapter.updateLocation(entity,event.getPosition());
        }else {
            adapter.removeLocation(event.getPosition());
        }
    }

    @Override
    public void finishPager() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
