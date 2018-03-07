package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.ActivityEntity;
import com.android.wool.model.entity.BuildOrderEntity;
import com.android.wool.model.entity.CartEntity;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.model.entity.LogisticsInfoEntity;
import com.android.wool.presenter.BuildOrderPresenterImpl;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.LocationListActivity;
import com.android.wool.view.constom.SelectActivityTypeDialog;
import com.android.wool.view.constom.SelectLogisticsDialog;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/23
 */
public class BuildOrderAdapter extends BaseAdapter{
    private Context context;
    private BuildOrderEntity orderEntity;
    //商品
    private List<CartEntity> mList;
    //收货地址信息
    private List<LocationEntity> location_list;
    //活动列表
    private List<ActivityEntity> activity_list;
    //物流信息
    private List<LogisticsInfoEntity> logistics_list;
    private int mPos1;
    private int mPos2;
    private Drawable drawable;
    private int index = -1;
    private StringBuilder sb;

    private LogisticsInfoEntity logisticsInfoEntity;
    private ActivityEntity activityEntity;
    private LocationEntity locationEntity;
    private BuildOrderPresenterImpl presenter;
    public LogisticsInfoEntity getLogisticsInfoEntity() {
        return logisticsInfoEntity;
    }
    public ActivityEntity getActivityEntity() {
        return activityEntity;
    }
    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    public String getRemark() {
        return sb.toString();
    }

    public BuildOrderAdapter(Context context,BuildOrderPresenterImpl presenter) {
        this.presenter = presenter;
        this.context = context;
        mList = new ArrayList<>();
        activity_list = new ArrayList<>();
        location_list = new ArrayList<>();
        logistics_list = new ArrayList<>();
        drawable = context.getResources().getDrawable(R.drawable.next);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        this.sb = new StringBuilder();
    }

    public void resetData(BuildOrderEntity entity){
        mList.clear();
        activity_list.clear();
        location_list.clear();
        logistics_list.clear();
        locationEntity = null;
        if(entity != null){
            this.orderEntity = entity;
            List<CartEntity> list = orderEntity.getGoods_list();
            if(list != null && list.size() > 0){
                mList.addAll(list);
            }
            List<ActivityEntity> activityEntities = orderEntity.getActivity_list();
            if(activityEntities != null && activityEntities.size()>0){
                activity_list.addAll(activityEntities);
                ActivityEntity firstEntity = activityEntities.get(0);
                activity_list.add(firstEntity.newNoneEntity());
                activityEntity = firstEntity;
            }
            List<LocationEntity> locationEntityList = orderEntity.getAddr_info();
            if(locationEntityList != null && locationEntityList.size()>0){
                location_list.addAll(locationEntityList);
                locationEntity = locationEntityList.get(0);
            }
            List<LogisticsInfoEntity> logisticsInfoEntities = orderEntity.getLogistics_info();
            if(logisticsInfoEntities != null && logisticsInfoEntities.size()>0){
                logistics_list.addAll(logisticsInfoEntities);
                logisticsInfoEntity = logisticsInfoEntities.get(0);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size()+2;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        if(position == mList.size()+1)
            return 2;
        return 1;
    }

    public void resetLocation(LocationEntity locationEntity){
        location_list.clear();
        if(locationEntity != null){
            location_list.add(locationEntity);
            this.locationEntity = locationEntity;
        }
        notifyDataSetChanged();
    }

    @Override
    public CartEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == 0){
            View view = LayoutInflater.from(context).inflate(R.layout.item_order_location_head,null);
            LinearLayout layoutLocation = (LinearLayout) view.findViewById(R.id.layout_location);
            RelativeLayout layoutAdd = (RelativeLayout) view.findViewById(R.id.layout_add);
            TextView tv_receiver = (TextView) view.findViewById(R.id.tv_receiver);
            TextView tv_location = (TextView) view.findViewById(R.id.tv_location);
            if(location_list.size()>0){
                layoutLocation.setVisibility(View.VISIBLE);
                layoutAdd.setVisibility(View.GONE);
                LocationEntity entity = location_list.get(0);
                tv_receiver.setText(entity.getContact_name());
                tv_location.setText(entity.getCity());
                layoutLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LocationListActivity.class);
                        intent.putExtra("no_location","true");
                        context.startActivity(intent);
                    }
                });
            }else {
                layoutLocation.setVisibility(View.GONE);
                layoutAdd.setVisibility(View.VISIBLE);
                layoutAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LocationListActivity.class);
                        intent.putExtra("no_location","true");
                        context.startActivity(intent);
                    }
                });
            }
            return view;
        }else if(getItemViewType(position) == 2){
            View view = LayoutInflater.from(context).inflate(R.layout.item_order_create_foot,null);
            TextView tvType = (TextView) view.findViewById(R.id.tv_type);
            TextView tv_activity = (TextView) view.findViewById(R.id.tv_activity);
            TextView tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            final EditText edit_remark = (EditText) view.findViewById(R.id.edit_remark);
            edit_remark.addTextChangedListener(new TextSwitcher());
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tv_order_no = (TextView) view.findViewById(R.id.tv_order_no);
            TextView tv_order_time = (TextView) view.findViewById(R.id.tv_order_time);
            LinearLayout layoutTotalPrice = (LinearLayout) view.findViewById(R.id.tv_total_price);
            LinearLayout layout_activity = (LinearLayout) view.findViewById(R.id.layout_activity);
            LinearLayout layout_type = (LinearLayout) view.findViewById(R.id.layout_type);
            tv_order_no.setVisibility(View.GONE);
            tv_order_time.setVisibility(View.GONE);
            layoutTotalPrice.setVisibility(View.GONE);
            tvType.setCompoundDrawables(null,null,drawable,null);
            tv_activity.setCompoundDrawables(null,null,drawable,null);
            if(activityEntity != null){
                tv_activity.setText(activityEntity.getTitle());
            }
            if(activity_list.size() > 0){
                layout_activity.setVisibility(View.VISIBLE);
                layout_activity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelectActivityTypeDialog dialog = new SelectActivityTypeDialog(context);
                        dialog.setTvTitle(R.string.activity_yh);
                        dialog.setData(activity_list,mPos2);
                        dialog.setOnSelectListener(new SelectActivityTypeDialog.OnSelectListener() {
                            @Override
                            public void select(ActivityEntity entity,int Pos2) {
                                activityEntity = entity;
                                mPos2 = Pos2;
                                presenter.setPrice(activityEntity.getDiscount(),false);
                                notifyDataSetChanged();
                            }
                        });
                        dialog.show();
                    }
                });
            }else {
                layout_activity.setVisibility(View.GONE);
            }
            if(logisticsInfoEntity != null){
                tvType.setText(String.format(context.getString(R.string.logistics_price),
                        logisticsInfoEntity.getName(),logisticsInfoEntity.getPrice()));
            }
            if(logistics_list.size()>0){
                layout_type.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SelectLogisticsDialog dialog = new SelectLogisticsDialog(context);
                        dialog.setTvTitle(R.string.dispatching_type);
                        dialog.setData(logistics_list,mPos1);
                        dialog.setOnSelectListener(new SelectLogisticsDialog.OnSelectListener() {
                            @Override
                            public void select(LogisticsInfoEntity entity,int mPos) {
                                logisticsInfoEntity = entity;
                                mPos1 = mPos;
                                presenter.setPrice(logisticsInfoEntity.getPrice(),true);
                                notifyDataSetChanged();
                            }
                        });
                        dialog.show();
                    }
                });
            }
            edit_remark.setText(sb.toString());
            edit_remark.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_UP){
                        index = position;
                    }
                    return false;
                }
            });
            edit_remark.clearFocus();
            if (index != -1 && index == position) {
                // 如果当前的行下标和点击事件中保存的index一致，手动为EditText设置焦点。
                edit_remark.requestFocus();
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
            CartEntity entity = mList.get(position-1);
            viewHolder.tvName.setText(entity.getTitle());
            ImageUtils.downloadWidthDefault(
                    viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img,
                    ImageView.ScaleType.FIT_XY,
                    entity.getLogo(),
                    R.drawable.home_default10,
                    R.drawable.home_default10
            );
            viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb_none),entity.getPrice()));
            if(!TextUtils.isEmpty(entity.getMarket_price()) && !"0.00".equals(entity.getMarket_price())) {
                viewHolder.tvOldPrice.setText(String.format(context.getString(R.string.rmb_none), entity.getMarket_price()));
                viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            viewHolder.tvNumber.setText("x"+entity.getGoods_num());
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

    class TextSwitcher implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            sb.setLength(0);
            sb.append(s.toString());
        }
    }
}
