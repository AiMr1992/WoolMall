package com.android.wool.view.fragment;
import com.android.wool.R;
import com.android.wool.base.BaseFragment;
import com.android.wool.model.entity.MaterialEntity;
import com.android.wool.presenter.NaFindPresenterImpl;
import com.android.wool.view.adapter.MaterialListAdapter;
import com.android.wool.view.constom.RefreshListView;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class FindFragment extends BaseFragment<NaFindPresenterImpl> implements NaFindView{
    @BindView(R.id.list_view)
    RefreshListView listView;
    private MaterialListAdapter materialListAdapter;
    private int page = 1;
    private boolean isFirst = true;
    @Override
    public void init() {
        presenter = new NaFindPresenterImpl(this);
    }

    private boolean flag;
    @Override
    public void getMaterialList(List<MaterialEntity> list,boolean flag) {
        this.flag = flag;
        listView.setLoadFinish(flag);
        if(page == 1){
            materialListAdapter.resetData(list);
        }else {
            materialListAdapter.addAllData(list);
        }
        if(list != null && list.size() > 0){
            page ++;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void initView() {
        materialListAdapter = new MaterialListAdapter(getActivity());
        listView.setAdapter(materialListAdapter);
        listView.setRefreshOn(true,true);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                page = 1;
                presenter.getMaterialList(page+"");
            }

            @Override
            public void onUpRefresh() {
                if(!flag)
                    presenter.getMaterialList(page+"");
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden && isFirst){
            isFirst = false;
            showLoading();
            presenter.getMaterialList(page+"");
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
}
