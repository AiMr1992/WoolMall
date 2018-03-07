package com.android.wool.view.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.OrderListEntity;
import com.android.wool.presenter.OrderListPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.activity.OrderDetailView;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.MyListView;
import com.android.wool.view.fragment.OrderView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/12
 */
public class OrderFinishAdapter extends BaseAdapter{
    private List<OrderListEntity> mList;
    private Context context;
    private String[] arrays;
    private int height;
    private OrderListPresenterImpl presenter;
    private boolean firstVisible;
    public OrderFinishAdapter(Context context, OrderListPresenterImpl presenter) {
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

    /** 删除成功*/
    public void delSuccess(int position){
        mList.remove(position);
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
                public void onClick(View view) {
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
            OrderGoodsAdapter adapter = new OrderGoodsAdapter(
                    context, entity.getGoods_list(), entity.getId(), OrderDetailView.ORDER_PAGER_FINISH, position, entity.getOrder_no());
            viewHolder.listView.setAdapter(adapter);
            viewHolder.listView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height * entity.getGoods_list().size()));
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