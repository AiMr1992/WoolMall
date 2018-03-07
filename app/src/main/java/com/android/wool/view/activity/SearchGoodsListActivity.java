package com.android.wool.view.activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.common.MyPreference;
import com.android.wool.common.UMEventUtils;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.model.entity.SearchGoodsEntity;
import com.android.wool.presenter.SearchGoodsListPresenterImpl;
import com.android.wool.view.adapter.SearchTypeListAdapter;
import com.android.wool.view.constom.refresh.PtrClassicFrameLayout;
import com.android.wool.view.constom.refresh.PtrDefaultHandler;
import com.android.wool.view.constom.refresh.PtrFrameLayout;
import com.android.wool.view.constom.refresh.PtrHandler2;
import com.android.wool.view.constom.refresh.PtrRecyclerView;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/12
 * 检索商品
 */
public class SearchGoodsListActivity extends BaseActivity<SearchGoodsListPresenterImpl> implements SearchGoodsListView{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.layout_search)
    RelativeLayout layoutSearch;
    @BindView(R.id.tv_switch)
    ImageView tvSwitch;
    @BindView(R.id.refresh_layout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.recycler_view)
    PtrRecyclerView recyclerView;
    @BindView(R.id.tv_key)
    TextView tvKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.layout_select)
    LinearLayout layoutSelect;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.btn_num)
    LinearLayout btnNum;
    @BindView(R.id.btn_price)
    LinearLayout btnPrice;
    @BindView(R.id.close)
    View close;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    private SearchTypeListAdapter adapter;
    private int page;
    private int currentLayout = GRID_LAYOUT;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private boolean open;
    private Drawable drawable;

    private String defaultPrice = "asc";
    private String defaultNum = "";
    private String defaultTime = "";
    private Drawable drawableTop;
    private Drawable drawableBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchGoodsListPresenterImpl(this);
        showLoading();
        initView();
    }

    @Override
    public void refresh() {
        page = 1;
        if(!TextUtils.isEmpty(getIntent().getStringExtra("key"))) {
            presenter.getSearchGoodsList(this, getIntent().getStringExtra("key"), defaultNum, defaultPrice, defaultTime, page + "");
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            presenter.getIndexOrder(getIntent().getStringExtra("type"),page+"");
        }else {
            presenter.getTypeListId(this,getIntent().getStringExtra("id"),defaultNum,defaultPrice,defaultTime,page+"");
        }
    }

    @Override
    public void loading() {
        if(!flag) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("key"))) {
                presenter.getSearchGoodsList(this, getIntent().getStringExtra("key"), "", defaultPrice, "", page + "");
            }else if(!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
                presenter.getIndexOrder(getIntent().getStringExtra("type"),page+"");
            }else {
                presenter.getTypeListId(this, getIntent().getStringExtra("id"), defaultNum, defaultPrice, defaultTime, page + "");
            }
        }else {
            refreshLayout.refreshComplete();
        }
    }

    @Override
    public void edit() {
        if(TextUtils.isEmpty(getIntent().getStringExtra("key"))){
            tvKey.setVisibility(View.GONE);
            tvContent.setVisibility(View.VISIBLE);
        }else {
            tvKey.setVisibility(View.VISIBLE);
            tvKey.setText(getIntent().getStringExtra("key"));
            String history = MyPreference.getInstance().getPreferenceData(this,MyPreference.SEARCH_HISTORY);
            StringBuilder stringBuilder = new StringBuilder();
            if(!TextUtils.isEmpty(history)){
                String[] str = history.split(",");
                if(str.length > 0){
                    List<String> list = new ArrayList<>();
                    String key = getIntent().getStringExtra("key");
                    list.add(key);
                    for (String text:str){
                        if(!text.equals(key))
                            list.add(text);
                    }
                    for (String text:list) {
                        stringBuilder.append(text);
                        stringBuilder.append(",");
                    }
                    stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
                    MyPreference.getInstance().savePreferenceData(
                            SearchGoodsListActivity.this,MyPreference.SEARCH_HISTORY,stringBuilder.toString());
                }
            }else {
                stringBuilder.append(getIntent().getStringExtra("key"));
                stringBuilder.append(",");
                MyPreference.getInstance().savePreferenceData(
                        SearchGoodsListActivity.this,MyPreference.SEARCH_HISTORY,stringBuilder.toString());
            }
            tvContent.setVisibility(View.GONE);
        }
    }

    private void initView() {
        if(!TextUtils.isEmpty(getIntent().getStringExtra("type"))){
            layoutTop.setVisibility(View.GONE);
        }
        drawable = getResources().getDrawable(R.drawable.default_location);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawableTop = getResources().getDrawable(R.drawable.list_top);
        drawableTop.setBounds(0,0,drawableTop.getIntrinsicWidth(),drawableTop.getIntrinsicHeight());
        drawableBottom = getResources().getDrawable(R.drawable.list_bottom);
        drawableBottom.setBounds(0,0,drawableBottom.getIntrinsicWidth(),drawableBottom.getIntrinsicHeight());
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        gridLayoutManager = new GridLayoutManager(this,2);
        adapter = new SearchTypeListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        isLayoutType();
        edit();
        refresh();
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return PtrDefaultHandler.checkContentCanBePulledUp(frame, content, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                loading();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
        back.setOnClickListener(this);
        tvSwitch.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
        btnNum.setOnClickListener(this);
        btnPrice.setOnClickListener(this);
        layoutSelect.setOnClickListener(this);
        close.setOnClickListener(this);
        btnPrice.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvNum.setOnClickListener(this);

        if(!TextUtils.isEmpty(getIntent().getStringExtra("key"))){
            Map<String,String> map = new HashMap();
            UMEventUtils.pushEvent(this,getString(R.string.search_shop_name),map);
        }
    }

    @Override
    public void isLayoutType() {
        if(currentLayout == GRID_LAYOUT){
            showGridLayout();
        }else {
            showLinearLayout();
        }
    }

    @Override
    public void showLinearLayout() {
        tvSwitch.setImageResource(R.drawable.wg);
        adapter.setListType(LINEAR_LAYOUT);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showGridLayout() {
        tvSwitch.setImageResource(R.drawable.lb);
        adapter.setListType(GRID_LAYOUT);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_goods_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                if(open){
                    closeWind();
                }else {
                    EventBus.getDefault().post(new IoMessage<>());
                    finish();
                }
                break;
            case R.id.tv_switch:
                if(open){
                    closeWind();
                }else {
                    currentLayout = currentLayout == LINEAR_LAYOUT ? GRID_LAYOUT : LINEAR_LAYOUT;
                    isLayoutType();
                }
                break;
            case R.id.layout_search:
                closeWind();
                if(TextUtils.isEmpty(getIntent().getStringExtra("key"))
                        || !TextUtils.isEmpty(getIntent().getStringExtra("type"))){
                    startActivity(new Intent(this,SearchKeyActivity.class));
                    finish();
                }else {
                    finish();
                }
                break;
            case R.id.btn_num:
                if(!open){
                    layoutSelect.setVisibility(View.VISIBLE);
                    open = true;
                }else {
                    layoutSelect.setVisibility(View.GONE);
                    open = false;
                }
                break;
            case R.id.close:
                closeWind();
                break;
            case R.id.btn_price:
                if(open){
                    closeWind();
                }else {
                    if(TextUtils.equals(defaultPrice,"asc")){
                        defaultPrice = "desc";
                        tvPrice.setCompoundDrawables(null,null,drawableBottom,null);
                    }else {
                        defaultPrice = "asc";
                        tvPrice.setCompoundDrawables(null,null,drawableTop,null);
                    }
                    showLoading();
                    refresh();
                }
                break;
            case R.id.tv_time:
                layoutSelect.setVisibility(View.GONE);
                open = false;
                if(TextUtils.isEmpty(defaultTime)){
                    defaultTime = "desc";
                    defaultNum = "";
                    tvTime.setCompoundDrawables(null,null,drawable,null);
                    tvNum.setCompoundDrawables(null,null,null,null);
                }
                refresh();
                break;
            case R.id.tv_num:
                layoutSelect.setVisibility(View.GONE);
                open = false;
                if(TextUtils.isEmpty(defaultNum)){
                    defaultNum = "desc";
                    defaultTime = "";
                    tvTime.setCompoundDrawables(null,null,null,null);
                    tvNum.setCompoundDrawables(null,null,drawable,null);
                }
                refresh();
                break;
        }
    }

    private void closeWind(){
        layoutSelect.setVisibility(View.GONE);
        open = false;
    }

    private boolean flag;
    @Override
    public void getSearchGoodsList(List<SearchGoodsEntity> list,boolean flag) {
        this.flag = flag;
        refreshLayout.setLoadFinish(flag);
        if(page == 1)
            adapter.resetData(list);
        else
            adapter.addAllData(list);
        if(list != null && list.size() > 0)
            page++;
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