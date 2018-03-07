package com.android.wool.view.fragment;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.wool.R;
import com.android.wool.base.BaseFragment;
import com.android.wool.model.entity.HomeEntity;
import com.android.wool.model.entity.RecommendEntity;
import com.android.wool.presenter.NaHomePresenterImpl;
import com.android.wool.view.activity.ScanQcActivity;
import com.android.wool.view.activity.SearchKeyActivity;
import com.android.wool.view.adapter.NaHomeAdapter;
import com.android.wool.view.constom.RefreshListView;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class HomeFragment extends BaseFragment<NaHomePresenterImpl> implements HomeView{
    @BindView(R.id.list_view)
    RefreshListView listView;
    @BindView(R.id.img_scan)
    ImageView imageView;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    private NaHomeAdapter naHomeAdapter;
    private int page = 1;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disMissProgress() {
        listView.setRefreshFinish();
    }

    @Override
    public void initView() {
        listView.setRefreshOn(true,true);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                page = 2;
                presenter.getHomeList(getActivity());
            }

            @Override
            public void onUpRefresh() {
                if(!flag)
                    presenter.getLoadHomeList(page+"");
            }
        });
        naHomeAdapter = new NaHomeAdapter(getActivity());
        listView.setAdapter(naHomeAdapter);
        imageView.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
    }

    private boolean first = true;
    @Override
    public void onStart() {
        super.onStart();
        if(first) {
            presenter.getHomeList(getActivity());
            first = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_scan:
                startActivity(new Intent(getActivity(), ScanQcActivity.class));
                break;
            case R.id.layout_search:
                startActivity(new Intent(getActivity(), SearchKeyActivity.class));
                break;
        }
    }

    @Override
    public void init() {
        presenter = new NaHomePresenterImpl(this);
    }

    @Override
    public void getHome(HomeEntity entity) {
        naHomeAdapter.resetData(entity);
        this.flag = false;
        listView.setLoadFinish(flag);
    }

    private boolean flag;
    @Override
    public void getHomeList(List<RecommendEntity> list,boolean flag) {
        naHomeAdapter.addAllData(list);
        this.flag = flag;
        listView.setLoadFinish(flag);
        if(list != null && list.size() > 0){
            page ++;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden) {
            naHomeAdapter.onStop();
        }else{
            naHomeAdapter.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        naHomeAdapter.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isHidden())
            return;
        naHomeAdapter.onStart();
    }
}