package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.model.entity.PayTypeEntity;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.presenter.PayTypePresenterImpl;
import com.android.wool.view.adapter.PayTypeAdapter;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/24
 */
public class PayTypeActivity extends BaseActivity<PayTypePresenterImpl> implements PayTypeView{
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_pay)
    TextView tvPay;
    private PayTypeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PayTypePresenterImpl(this);
        initView();
    }

    @Override
    public void initView() {
        adapter = new PayTypeAdapter(this);
        listView.setAdapter(adapter);

        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.checkout_counter);
        tvPrice.setText(String.format(getString(R.string.rmb_none),getIntent().getStringExtra("total")));
        presenter.payTypeList(this);
        tvPay.setOnClickListener(this);
    }

    @Override
    public void payTypeList(List<PayTypeEntity> list) {
        adapter.resetData(list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
            case R.id.tv_pay:
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(HttpConstant.BASE_URL + HttpConstant.HTML_PAY);
                stringBuilder.append("?type="+adapter.getItemEntity().getType());
                stringBuilder.append("&trade_no="+getIntent().getStringExtra("order_no"));
                stringBuilder.append("&subject="+"商品");
                stringBuilder.append("&total_amount="+getIntent().getStringExtra("total"));
                Intent intent = new Intent(this,HtmlActivity.class);
                intent.putExtra("url",stringBuilder.toString());
                intent.putExtra("pay","true");
                intent.putExtra("order_id",getIntent().getStringExtra("order_id"));
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_list;
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