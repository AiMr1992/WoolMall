package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.model.entity.HouseListEntity;
import com.android.wool.presenter.HouseListPresenterImpl;
import com.android.wool.view.adapter.HouseListAdapter;
import com.android.wool.view.constom.RefreshListView;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/25
 */
public class HouseListActivity extends BaseActivity<HouseListPresenterImpl> implements HouseListView{
    @BindView(R.id.list_view)
    RefreshListView listView;
    private HouseListAdapter adapter;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new HouseListPresenterImpl(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getHouseList(this,page+"");
    }

    @Override
    public void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.mine_collection);
        listView.setRefreshOn(true,true);
        listView.setOpenSlide(true);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                page = 1;
                presenter.getHouseList(HouseListActivity.this,page+"");
            }

            @Override
            public void onUpRefresh() {
                if(!flag){
                    presenter.getHouseList(HouseListActivity.this,page+"");
                }
            }
        });
        adapter = new HouseListAdapter(this,presenter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int position, long id) {
                if(adapter.getItemViewType(position) != 0) {
                    HouseListEntity entity = adapter.getItem(position - 1);
                    Intent intent = new Intent(HouseListActivity.this, GoodsInfoActivity.class);
                    intent.putExtra("id", entity.getId());
                    startActivity(intent);
                }
            }
        });
        showLoading();
    }

    private boolean flag;
    @Override
    public void getHouseList(List<HouseListEntity> list,boolean flag) {
        this.flag = flag;
        listView.setLoadFinish(flag);
        if(page == 1){
            adapter.resetData(list);
        }else {
            adapter.addAllData(list);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_house_list;
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

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
        listView.setRefreshFinish();
    }

    @Override
    public void remove(int position) {
        adapter.remove(position);
    }
}