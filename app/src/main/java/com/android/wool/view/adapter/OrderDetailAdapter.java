package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.ActivityEntity;
import com.android.wool.model.entity.ExpressEntity;
import com.android.wool.model.entity.ExpressInfoEntity;
import com.android.wool.model.entity.ExpressListEntity;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.model.entity.OrderDetailEntity;
import com.android.wool.model.entity.OrderGoodsEntity;
import com.android.wool.model.entity.OrderListEntity;
import com.android.wool.util.AppTools;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.activity.LogisticsListActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/16
 */
public class OrderDetailAdapter extends BaseAdapter{
    private Context context;
    //订单详情实体
    private OrderDetailEntity entity;
    //订单详情
    private OrderListEntity orderListEntity;
    //商品列表
    private List<OrderGoodsEntity> goodsList;
    //收货地址实体
    private LocationEntity locationEntity;
    //订单物流状态列表
    private List<ExpressListEntity> expressListEntities;
    //物流信息详情
    private ExpressInfoEntity expressInfoEntity;
    //活动详情
    private ActivityEntity activityEntitys;
    private String[] array;

    public OrderDetailAdapter(Context context) {
        this.context = context;
        goodsList = new ArrayList<>();
        expressListEntities = new ArrayList<>();
        array = context.getResources().getStringArray(R.array.order_states);
    }

    public void resetData(OrderDetailEntity entity){
        this.entity = null;
        this.goodsList.clear();
        this.expressListEntities.clear();
        this.orderListEntity = null;
        this.locationEntity = null;
        this.expressInfoEntity = null;
        this.activityEntitys = null;
        if(entity != null){
            this.entity = entity;
            //订单详情
            OrderListEntity listEntity = entity.getOrder_info();
            if(listEntity != null){
                orderListEntity = listEntity;
            }
            //商品列表
            List<OrderGoodsEntity> goodsEntities = entity.getGoods_list();
            if(goodsEntities != null && goodsEntities.size()>0){
                goodsList.addAll(goodsEntities);
            }
            //收货地址
            LocationEntity locationEntity1 = entity.getAddr_info();
            if(locationEntity1 != null){
                locationEntity = locationEntity1;
            }
            //活动
            ActivityEntity activityEntity = entity.getActivity();
            if(activityEntity != null){
                activityEntitys = activityEntity;
            }
            //物流
            ExpressEntity expressEntity = entity.getExpress();
            if(expressEntity != null){
                ExpressInfoEntity expressInfoEntity1 = expressEntity.getInfo();
                if(expressInfoEntity1 != null){
                    expressInfoEntity = expressInfoEntity1;
                }
                List<ExpressListEntity> expressListEntities1 = expressEntity.getLsit();
                if(expressListEntities1 != null && expressListEntities1.size() > 0){
                    expressListEntities.addAll(expressListEntities1);
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return goodsList.size()+2;
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        if(position == goodsList.size()+1)
            return 2;
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail_head,null);
            LinearLayout layoutLogistics = (LinearLayout) view.findViewById(R.id.layout_logistics);
            layoutLogistics.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LogisticsListActivity.class);
                    intent.putExtra("order_no",orderListEntity.getOrder_no());
                    context.startActivity(intent);
                }
            });
            TextView tvState = (TextView) view.findViewById(R.id.order_state);
            TextView express_time = (TextView) view.findViewById(R.id.express_time);
            if(orderListEntity != null){
                if("1".equals(orderListEntity.getStatus())){
                    tvState.setText(array[0]);
                }else if("2".equals(orderListEntity.getStatus())){
                    tvState.setText(array[1]);
                }else if("3".equals(orderListEntity.getStatus())){
                    tvState.setText(array[2]);
                }else if("4".equals(orderListEntity.getStatus())){
                    tvState.setText(array[3]);
                }else if("5".equals(orderListEntity.getStatus())){
                    tvState.setText("订单已取消");
                }
            }
            TextView expressName = (TextView) view.findViewById(R.id.express_name);
            if(expressListEntities.size() > 0) {
                ExpressListEntity expressListEntity = expressListEntities.get(0);
                if (expressListEntity != null) {
                    expressName.setText(expressListEntity.getContext());
                    express_time.setText(expressListEntity.getTime());
                }
            }else {
                layoutLogistics.setVisibility(View.GONE);
            }
            TextView receiverName = (TextView) view.findViewById(R.id.receiver_name);
            TextView receiverAddress = (TextView) view.findViewById(R.id.receiver_address);
            if(locationEntity != null){
                receiverName.setText("收货人:"+locationEntity.getContact_name());
                receiverAddress.setText("收货地址:"+locationEntity.getCity()+locationEntity.getAddress());
            }
            return view;
        }else if(getItemViewType(position) == 2){
            View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail_foot,null);
            TextView tvFreight = (TextView) view.findViewById(R.id.tv_freight);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tv_order_no = (TextView) view.findViewById(R.id.tv_order_no);
            TextView tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
            if (orderListEntity != null){
                tvFreight.setText("运费:  "+String.format(context.getString(R.string.rmb),orderListEntity.getCost_carry()));
                tv_order_no.setText("订单号:   "+orderListEntity.getOrder_no());
                tv_order_time.setText("创建时间:    "+AppTools.parseLongDate(orderListEntity.getAdd_time()));
                tv_price.setText("实付款:  "+String.format(context.getString(R.string.rmb),orderListEntity.getTotal_order()));
            }
            return view;
        }else {
            View view = null;
            ViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_order_goods,null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) view.findViewById(R.id.img);
                viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.tvPrice = (TextView) view.findViewById(R.id.tv_price);
                viewHolder.tvOldPrice = (TextView) view.findViewById(R.id.tv_old_price);
                viewHolder.tvNumber = (TextView) view.findViewById(R.id.tv_number);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final OrderGoodsEntity entity = goodsList.get(position-1);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img,
                    ImageView.ScaleType.FIT_XY,
                    entity.getGoods_logo(),
                    R.drawable.home_default10,
                    R.drawable.home_default10
            );
            viewHolder.tvName.setText(entity.getGoods_title());
            viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb_none),entity.getGoods_price()));
            viewHolder.tvOldPrice.setText(String.format(context.getString(R.string.rmb_none),entity.getPrice_original()));
            viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvNumber.setText("x"+entity.getGoods_num());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,GoodsInfoActivity.class);
                    intent.putExtra("id",entity.getGoods_id());
                    context.startActivity(intent);
                }
            });
            return view;
        }
    }

    class ViewHolder{
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvOldPrice;
        TextView tvNumber;
    }
}