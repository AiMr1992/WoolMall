package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.common.MyPreference;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.presenter.SearchKeyPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.adapter.FlowLayoutAdapter;
import com.android.wool.view.adapter.HistoryAdapter;
import com.android.wool.view.constom.MultiEditTextView;
import com.android.wool.view.constom.flowlayout.FlowTagLayout;
import com.android.wool.view.constom.flowlayout.OnTagClickListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/12
 * 热搜关键字
 */
public class SearchKeyActivity extends BaseActivity<SearchKeyPresenterImpl> implements SearchKeyView{
    @BindView(R.id.edit_search)
    MultiEditTextView editSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.listView)
    ListView listView;
    private View view;
    private HistoryAdapter historyAdapter;
    private FlowTagLayout flowTagLayout;
    private FlowLayoutAdapter flowLayoutAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchKeyPresenterImpl(this);
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_key;
    }

    private void history(){
        String history = MyPreference.getInstance().getPreferenceData(this,MyPreference.SEARCH_HISTORY);
        if(!TextUtils.isEmpty(history)){
            String[] str = history.split(",");
            List list = Arrays.asList(str);
            historyAdapter.resetData(list);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        history();
    }

    private void initView() {
        flowLayoutAdapter = new FlowLayoutAdapter(this);
        historyAdapter = new HistoryAdapter(this);
        view = LayoutInflater.from(this).inflate(R.layout.view_search_head,null);
        flowTagLayout = (FlowTagLayout) view.findViewById(R.id.flowTagLayout);
        flowTagLayout.setAdapter(flowLayoutAdapter);
        listView.addHeaderView(view);
        listView.setAdapter(historyAdapter);
        flowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                Intent intent = new Intent(SearchKeyActivity.this,SearchGoodsListActivity.class);
                intent.putExtra("key",flowLayoutAdapter.getItem(position));
                startActivity(intent);
            }
        });

        tvCancel.setOnClickListener(this);
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(!TextUtils.isEmpty(editSearch.getText().toString())) {
                        Intent intent = new Intent(SearchKeyActivity.this, SearchGoodsListActivity.class);
                        intent.putExtra("key",editSearch.getText().toString());
                        startActivity(intent);
                        // 当按了搜索之后关闭软键盘
                        AppTools.closeKeyBord(editSearch,SearchKeyActivity.this);
                    }else {
                        Toast.makeText(SearchKeyActivity.this,"请输入关键字",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
        presenter.getHotSearch(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void getHotList(List<String> list) {
        if(list != null && list.size() > 0){
            view.setVisibility(View.VISIBLE);
            flowLayoutAdapter.setData(list.toArray(new String[list.size()]));
        }
        history();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEven(IoMessage event){
        finish();
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
