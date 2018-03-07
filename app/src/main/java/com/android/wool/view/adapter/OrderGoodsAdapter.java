package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.OrderGoodsEntity;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.OrderDetailActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/13
 */
public class OrderGoodsAdapter extends BaseAdapter{
    private List<OrderGoodsEntity> mList;
    private Context context;
    private String orderNo;
    private String type;
    private int position;
    private String order_no;
    public OrderGoodsAdapter(Context context,String orderNo) {
        this.context = context;
        this.orderNo = orderNo;
        mList = new ArrayList<>();
    }

    public OrderGoodsAdapter(Context context,List<OrderGoodsEntity> list,String orderNo,String type,int position,String order_no) {
        this.context = context;
        mList = new ArrayList<>();
        if(list != null && list.size() > 0)
            mList.addAll(list);
        this.orderNo = orderNo;
        this.type = type;
        this.position = position;
        this.order_no = order_no;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public OrderGoodsEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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
            viewHolder = (ViewHolder)view.getTag();
        }
        final OrderGoodsEntity entity = mList.get(position);
        viewHolder.tvName.setText(entity.getGoods_title());
        viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb_none),entity.getGoods_price()));
        viewHolder.tvOldPrice.setText(String.format(context.getString(R.string.rmb_none),entity.getPrice_original()));
        viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(TextUtils.isEmpty(entity.getPrice_original()) || "0.00".equals(entity.getPrice_original()))
            viewHolder.tvOldPrice.setVisibility(View.GONE);
        viewHolder.tvNumber.setText("x"+entity.getGoods_num());
        ImageUtils.downloadWidthDefault(
                viewHolder.img.getWidth(),
                viewHolder.img.getHeight(),
                viewHolder.img,
                ImageView.ScaleType.FIT_XY,
                entity.getGoods_logo(),
                R.drawable.home_default10,
                R.drawable.home_default10);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("id",orderNo);
                intent.putExtra("type",type);
                intent.putExtra("position",position);
                intent.putExtra("order_no",order_no);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class  ViewHolder{
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvOldPrice;
        TextView tvNumber;
    }
}
