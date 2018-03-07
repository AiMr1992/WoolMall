package com.android.wool.view.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.android.wool.MyApplication;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.presenter.MsgPresenterImpl;
import com.android.wool.view.adapter.MsgListAdapter;
import com.android.wool.view.constom.RefreshListView;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.pop.OnSessionListChangedListener;
import com.qiyukf.unicorn.api.pop.POPManager;
import com.qiyukf.unicorn.api.pop.Session;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/25
 */
public class MsgListActivity extends BaseActivity<MsgPresenterImpl> implements MsgListView{
    @BindView(R.id.list_view)
    RefreshListView listView;

    private MsgListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MsgPresenterImpl(this);
        initView();
    }

    private void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.mine_msg);
        adapter = new MsgListAdapter(this,presenter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MyApplication.inMainProcess(MsgListActivity.this)) {
                    ConsultSource source = new ConsultSource("", "个人主页", "");
                    Unicorn.openServiceActivity(MsgListActivity.this, "客服", source);
                }
            }
        });
        POPManager.addOnSessionListChangedListener(new OnSessionListChangedListener() {
            @Override
            public void onSessionUpdate(List<Session> updateSessionList) {
                adapter.resetData(updateSessionList);
            }

            @Override
            public void onSessionDelete(String shopId) {
                disMissProgress();
                adapter.delete(shopId);
            }
        },MyApplication.inMainProcess(this));
        listView.setRefreshOn(false,false);
        listView.setOpenSlide(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_msg_list;
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
    }
}