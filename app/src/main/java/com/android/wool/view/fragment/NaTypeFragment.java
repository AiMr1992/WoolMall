package com.android.wool.view.fragment;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.android.wool.R;
import com.android.wool.base.BaseFragment;
import com.android.wool.model.entity.GoodsCartsEntity;
import com.android.wool.model.entity.NaTypeEntity;
import com.android.wool.presenter.NaTypePresenterImpl;
import com.android.wool.view.activity.SearchKeyActivity;
import com.android.wool.view.adapter.NaTypeLayoutLeftAdapter;
import com.android.wool.view.adapter.NaTypeMainAdapter;
import com.android.wool.view.constom.RefreshListView;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class NaTypeFragment extends BaseFragment<NaTypePresenterImpl> implements NaTypeView{
    @BindView(R.id.layout_head)
    LinearLayout layoutHead;
    @BindView(R.id.list_left)
    ListView listViewLeft;
    @BindView(R.id.list_right)
    RefreshListView listViewRight;

    private NaTypeLayoutLeftAdapter typeLeftAdapter;
    private NaTypeMainAdapter typeMainAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_type;
    }

    @Override
    public void initView() {
        listViewRight.setRefreshOn(true,false);
        listViewRight.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                presenter.getTypeGoodsList(getActivity());
            }

            @Override
            public void onUpRefresh() {

            }
        });
        listViewLeft.setAdapter(typeLeftAdapter);
        listViewRight.setAdapter(typeMainAdapter);

        layoutHead.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden && naTypeEntity == null) {
            showLoading();
            presenter.getTypeGoodsList(getActivity());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_head:
                startActivity(new Intent(getActivity(), SearchKeyActivity.class));
                break;
        }
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        listViewRight.setRefreshFinish();
        dismissProgressDialog();
    }

    @Override
    public void init() {
        presenter = new NaTypePresenterImpl(this);
        typeLeftAdapter = new NaTypeLayoutLeftAdapter(getActivity(),presenter);
        typeMainAdapter = new NaTypeMainAdapter(getActivity());
    }

    private NaTypeEntity naTypeEntity;
    @Override
    public void getTypeGoodsList(NaTypeEntity entity) {
        this.naTypeEntity = entity;
        typeLeftAdapter.resetData(entity.getGoods_cats());
        refreshRight(entity.getGoods_cats().get(typeLeftAdapter.getPos()).getChild());
    }

    @Override
    public void refreshRight(List<GoodsCartsEntity> bottomList) {
        typeMainAdapter.resetData(bottomList,naTypeEntity.getHot_brand());
    }
}