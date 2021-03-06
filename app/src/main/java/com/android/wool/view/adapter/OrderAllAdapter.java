package com.android.wool.view.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.model.entity.OrderGoodsEntity;
import com.android.wool.model.entity.OrderListEntity;
import com.android.wool.presenter.OrderListPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.activity.LogisticsListActivity;
import com.android.wool.view.activity.OrderDetailView;
import com.android.wool.view.activity.PayTypeActivity;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.MyListView;
import com.android.wool.view.fragment.OrderView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/12
 */
public class OrderAllAdapter extends BaseAdapter{
    private List<OrderListEntity> mList;
    private Context context;
    private String[] arrays;
    private int height;
    private OrderListPresenterImpl presenter;
    private boolean firstVisible;
    public OrderAllAdapter(Context context,OrderListPresenterImpl presenter) {
        this.mList = new ArrayList<>();
        this.context = context;
        this.presenter = presenter;
        arrays = context.getResources().getStringArray(R.array.order_states);
        height = AppTools.turnDipToPx(90,context);
    }

    @Override
    public int getCount() {
        if(!firstVisible){
            return 0;
        }
        return mList.size() == 0 ? 1:mList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(!firstVisible)
            return 1;
        return mList.size()==0?0:1;
    }

    public void resetData(List<OrderListEntity> list){
        firstVisible = true;
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAllData(List<OrderListEntity> list){
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /**取消成功*/
    public void cancelSuccess(int position){
        OrderListEntity entity = mList.get(position);
        entity.setStatus("5");
        notifyDataSetChanged();
    }

    /** 删除成功*/
    public void delSuccess(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }

    /** 收货成功*/
    public void tackSuccess(int position){
        OrderListEntity entity = mList.get(position);
        entity.setStatus("4");
        notifyDataSetChanged();
    }

    @Override
    public OrderListEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if(getItemViewType(position) == 0){
            if(convertView != null){
                view = convertView;
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.view_null,null);
                view.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(),
                        parent.getHeight()));
                ((TextView)view.findViewById(R.id.tv_none)).setText(R.string.order_none);
                return view;
            }
        }else {
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_order, null);
                viewHolder = new ViewHolder();
                viewHolder.tvState = (TextView) view.findViewById(R.id.tv_state);
                viewHolder.listView = (MyListView) view.findViewById(R.id.list_view);
                viewHolder.tvLeft = (TextView) view.findViewById(R.id.tv_left);
                viewHolder.tvRight = (TextView) view.findViewById(R.id.tv_right);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final OrderListEntity entity = mList.get(position);
            if ("1".equals(entity.getStatus())) {
                viewHolder.tvState.setText(arrays[0]);
                viewHolder.tvLeft.setVisibility(View.VISIBLE);
                viewHolder.tvRight.setVisibility(View.VISIBLE);
                viewHolder.tvLeft.setText(R.string.cancel);
                viewHolder.tvRight.setText(R.string.pay);
                viewHolder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消订单
                        presenter.editOrder(context, entity.getId(), OrderView.ORDER_CANCEL, position);
                    }
                });
                viewHolder.tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //支付
                        Intent intent = new Intent(context, PayTypeActivity.class);
                        intent.putExtra("order_no", entity.getOrder_no());
                        intent.putExtra("total", entity.getTotal_order());
                        context.startActivity(intent);
                    }
                });
            } else if ("2".equals(entity.getStatus())) {
                viewHolder.tvState.setText(arrays[1]);
                viewHolder.tvLeft.setVisibility(View.GONE);
                viewHolder.tvRight.setVisibility(View.VISIBLE);
                viewHolder.tvRight.setText(R.string.take_btn);
                viewHolder.tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,R.string.take_label,Toast.LENGTH_SHORT).show();
                    }
                });
            } else if ("3".equals(entity.getStatus())) {
                viewHolder.tvState.setText(arrays[2]);
                viewHolder.tvLeft.setVisibility(View.VISIBLE);
                viewHolder.tvRight.setVisibility(View.VISIBLE);
                viewHolder.tvLeft.setText(R.string.look_logistics);
                viewHolder.tvRight.setText(R.string.enter_tack);
                viewHolder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //查看物流
                        Intent intent = new Intent(context, LogisticsListActivity.class);
                        intent.putExtra("order_no", entity.getOrder_no());
                        intent.putExtra("exp_time",entity.getExp_time());
                        context.startActivity(intent);
                    }
                });
                viewHolder.tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //确认收货
                        MyAlertDialog dialog = new MyAlertDialog(context);
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
                                presenter.editOrder(context, entity.getId(), OrderView.ORDER_TAKE, position);
                            }
                        });
                        dialog.setMsg("是否确认收货？");
                        dialog.show();
                    }
                });
            } else if ("4".equals(entity.getStatus())) {
                viewHolder.tvState.setText(arrays[3]);
                viewHolder.tvLeft.setVisibility(View.VISIBLE);
                viewHolder.tvRight.setVisibility(View.VISIBLE);
                viewHolder.tvLeft.setText(R.string.delete);
                viewHolder.tvRight.setText(R.string.reset_buy);
                viewHolder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除订单
                        MyAlertDialog dialog = new MyAlertDialog(context);
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
                                presenter.editOrder(context, entity.getId(), OrderView.ORDER_DELETE, position);
                            }
                        });
                        dialog.setMsg(R.string.or_delete);
                        dialog.show();
                    }
                });
                viewHolder.tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //再次购买
                        MyAlertDialog dialog = new MyAlertDialog(context);
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
                                presenter.resetOrder(context, entity.getId());
                            }
                        });
                        dialog.setMsg("您是否要再次购买？");
                        dialog.show();
                    }
                });
            } else {
                viewHolder.tvState.setText(arrays[4]);
                viewHolder.tvLeft.setVisibility(View.VISIBLE);
                viewHolder.tvRight.setVisibility(View.GONE);
                viewHolder.tvLeft.setText(R.string.delete);
                viewHolder.tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除订单
                        MyAlertDialog dialog = new MyAlertDialog(context);
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
                                presenter.editOrder(context, entity.getId(), OrderView.ORDER_DELETE, position);
                            }
                        });
                        dialog.setMsg(R.string.or_delete);
                        dialog.show();
                    }
                });
            }
            List<OrderGoodsEntity> goodsEntities = entity.getGoods_list();
            if (goodsEntities != null && goodsEntities.size() > 0) {
                OrderGoodsAdapter adapter = new OrderGoodsAdapter(
                        context, entity.getGoods_list(), entity.getId(), OrderDetailView.ORDER_PAGER_ALL, position, entity.getOrder_no());
                viewHolder.listView.setAdapter(adapter);
                viewHolder.listView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height * entity.getGoods_list().size()));
            }
        }
        return view;
    }

    class ViewHolder{
        TextView tvState;
        MyListView listView;
        TextView tvLeft;
        TextView tvRight;
    }
}