package com.android.wool.view.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.eventbus.OrderMsgEvent;
import com.android.wool.model.entity.LogisticsTimeEntity;
import com.android.wool.model.entity.OrderDetailEntity;
import com.android.wool.model.entity.OrderListEntity;
import com.android.wool.presenter.OrderDetailPresenterImpl;
import com.android.wool.util.SystemUtils;
import com.android.wool.view.adapter.OrderDetailAdapter;
import com.android.wool.view.constom.MyAlertDialog;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/16
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenterImpl> implements OrderDetailView{
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    private OrderDetailAdapter adapter;
    private String orderId;
    private OrderDetailEntity detailEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new OrderDetailPresenterImpl(this);
        initView();
    }

    private void UpdaterPager(String type) {
        if (ORDER_NONE_PLAY.equals(type)) {
            tvLeft.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
            tvLeft.setBackgroundResource(R.drawable.btn_order);
            tvLeft.setTextColor(getResources().getColor(R.color.app_black));
            tvLeft.setText(R.string.cancel);
            tvRight.setText(R.string.pay);
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.editOrder(
                            OrderDetailActivity.this,
                            orderId,
                            ORDER_CANCEL,
                            getIntent().getIntExtra("position",-1));
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailEntity != null) {
                        OrderListEntity orderListEntity = detailEntity.getOrder_info();
                        if(orderListEntity != null) {
                            Intent intent = new Intent(OrderDetailActivity.this, PayTypeActivity.class);
                            intent.putExtra("order_no", orderListEntity.getOrder_no());
                            intent.putExtra("total", orderListEntity.getTotal_order());
                            startActivity(intent);
                        }
                    }
                }
            });
        } else if (ORDER_TACK_N.equals(type)){
            tvLeft.setVisibility(View.GONE);
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(R.string.take_btn);
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(OrderDetailActivity.this,R.string.take_label,Toast.LENGTH_SHORT).show();
                }
            });
        }else if(ORDER_TACK_Y.equals(type)){
            tvLeft.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
            tvLeft.setText(R.string.enter_tack);
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyAlertDialog dialog = new MyAlertDialog(OrderDetailActivity.this);
                    dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setRightButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            presenter.editOrder(
                                    OrderDetailActivity.this,
                                    orderId,
                                    ORDER_TAKE,
                                    getIntent().getIntExtra("position",-1));
                        }
                    });
                    dialog.setMsg("是否确认收货？");
                    dialog.show();
                }
            });
            tvRight.setText(R.string.look_logistics);
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(detailEntity != null) {
                        OrderListEntity orderListEntity = detailEntity.getOrder_info();
                        if (orderListEntity != null) {
                            Intent intent = new Intent(OrderDetailActivity.this, LogisticsListActivity.class);
                            intent.putExtra("order_no", orderListEntity.getOrder_no());
                            intent.putExtra("exp_time",orderListEntity.getAdd_time());
                            startActivity(intent);
                        }
                    }
                }
            });
        }else if(ORDER_FINISH.equals(type)){
            tvLeft.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.VISIBLE);
            tvLeft.setBackgroundResource(R.drawable.btn_order);
            tvLeft.setTextColor(getResources().getColor(R.color.black));
            tvLeft.setText(R.string.delete);
            tvRight.setText(R.string.reset_buy);
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyAlertDialog dialog = new MyAlertDialog(OrderDetailActivity.this);
                    dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setRightButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            presenter.editOrder(
                                    OrderDetailActivity.this,
                                    orderId,
                                    ORDER_DELETE,
                                    getIntent().getIntExtra("position",-1));
                        }
                    });
                    dialog.setMsg(R.string.or_delete);
                    dialog.show();
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.resetOrder(OrderDetailActivity.this,orderId);
                }
            });
        }else if(ORDER_TACK.equals(type)){
            tvLeft.setVisibility(View.VISIBLE);
            tvRight.setVisibility(View.GONE);
            tvLeft.setText(R.string.delete);
            tvLeft.setBackgroundResource(R.drawable.btn_order);
            tvLeft.setTextColor(getResources().getColor(R.color.black));
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyAlertDialog dialog = new MyAlertDialog(OrderDetailActivity.this);
                    dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setRightButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            presenter.editOrder(
                                    OrderDetailActivity.this,
                                    orderId,
                                    ORDER_DELETE,
                                    getIntent().getIntExtra("position",-1));
                        }
                    });
                    dialog.setMsg(R.string.or_delete);
                    dialog.show();
                }
            });
        }
    }

    @Override
    public void initView() {
        SystemUtils.setStatusBarTransDark(this);
        setTitle(R.string.order_detail);
        setHeadViewColor(R.color.white);
        setLeftBack(R.drawable.black_back,"",true);
        setTitleTextColor(R.color.black);
        orderId = getIntent().getStringExtra("id");

        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();
        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = i_getvalue.getData();
            if(uri != null){
                orderId = uri.getQueryParameter("orderId");
            }
        }

        adapter = new OrderDetailAdapter(this);
        listView.setAdapter(adapter);
        listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        tvRight.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getOrderDetail(this,orderId);
    }

    @Override
    public void editSuccess(int position,String type) {
        finish();
        EventBus.getDefault().post(new OrderMsgEvent(getIntent().getStringExtra("type"),type,position));
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
    public void getOrderDetailEntity(OrderDetailEntity entity) {
        this.detailEntity = entity;
        adapter.resetData(entity);
        if(entity != null){
            OrderListEntity orderListEntity = entity.getOrder_info();
            if(orderListEntity != null)
                UpdaterPager(orderListEntity.getStatus());
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void getLogistics(LogisticsTimeEntity entity) {
    }
}