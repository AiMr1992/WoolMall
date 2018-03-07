package com.android.wool.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.model.entity.LogisticsTimeEntity;
import com.android.wool.presenter.LogisticsPresenterImpl;
import com.android.wool.view.adapter.LogisticsListAdapter;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/27
 */
public class LogisticsListActivity extends BaseActivity<LogisticsPresenterImpl> implements LogisticsListView{
    @BindView(R.id.list_view)
    ListView listView;
    private LogisticsListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LogisticsPresenterImpl(this);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_logistics_list;
    }

    @Override
    public void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.logistics_info);
        adapter = new LogisticsListAdapter(this,getIntent().getStringExtra("exp_time"));
        listView.setAdapter(adapter);
        presenter.getLogistics(this,getIntent().getStringExtra("order_no"));
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void getLogistics(List<LogisticsTimeEntity> list) {
        adapter.resetData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }
}
