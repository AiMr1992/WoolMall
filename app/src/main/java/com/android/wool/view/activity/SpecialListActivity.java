package com.android.wool.view.activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.model.entity.SpecialListEntity;
import com.android.wool.presenter.SpecialListPresenterImpl;
import com.android.wool.view.adapter.SpecialListAdapter;
import com.android.wool.view.constom.refresh.PtrClassicFrameLayout;
import com.android.wool.view.constom.refresh.PtrDefaultHandler;
import com.android.wool.view.constom.refresh.PtrFrameLayout;
import com.android.wool.view.constom.refresh.PtrHandler2;
import com.android.wool.view.constom.refresh.PtrRecyclerView;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/28
 */
public class SpecialListActivity extends BaseActivity<SpecialListPresenterImpl> implements SpecialListView{
    @BindView(R.id.refresh_layout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.recycler_view)
    PtrRecyclerView recyclerView;
    private int page = 1;
    private SpecialListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SpecialListPresenterImpl(this);
        initView();
    }

    @Override
    public void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(getIntent().getStringExtra("title"));

        gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position == 0)
                    return gridLayoutManager.getSpanCount();
                return 1;
            }
        });
        adapter = new SpecialListAdapter(this,getIntent().getStringExtra("desc"));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return false;
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                presenter.getSpecialList(SpecialListActivity.this,getIntent().getStringExtra("id"),page+"");
            }
        });
        presenter.getSpecialList(this,getIntent().getStringExtra("id"),page+"");
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
    public void getEntity(SpecialListEntity entity) {
        adapter.resetData(entity);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_special;
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
        refreshLayout.refreshComplete();
    }
}