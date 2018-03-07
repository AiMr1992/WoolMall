package com.android.wool.view.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.presenter.LocationAddPresenter;
import com.android.wool.presenter.LocationListPresenterImpl;
import com.android.wool.view.activity.LocationAddActivity;
import com.android.wool.view.constom.MyAlertDialog;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/25
 */
public class LocationListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<LocationEntity> mList;
    private Drawable drawableLeft;
    private Drawable unDrawableLeft;
    private LocationListPresenterImpl presenter;
    private boolean flag;
    private boolean firstVisible;
    public LocationListAdapter(Context context, LocationListPresenterImpl presenter,boolean flag) {
        this.context = context;
        this.flag = flag;
        layoutInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
        this.presenter = presenter;
        drawableLeft = context.getResources().getDrawable(R.drawable.default_location);
        unDrawableLeft = context.getResources().getDrawable(R.drawable.un_default_location);
        drawableLeft.setBounds(0,0,drawableLeft.getIntrinsicWidth(),drawableLeft.getIntrinsicHeight());
        unDrawableLeft.setBounds(0,0,unDrawableLeft.getIntrinsicWidth(),unDrawableLeft.getIntrinsicHeight());
    }

    public int getItemCount(){
        return mList.size();
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

    /** 重置数据  */
    public void resetData(List<LocationEntity> list){
        firstVisible = true;
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAllData(List<LocationEntity> list){
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    /** 选择默认地址 */
    public void defaultLocation(int position){
        if(position == 0){
            LocationEntity entity = mList.get(position);
            entity.setIs_default("1");
        }else {
            LocationEntity entity = mList.get(position);
            entity.setIs_default("1");
            LocationEntity entity1 = mList.get(0);
            entity1.setIs_default("0");
            mList.set(0, entity);
            mList.set(position, entity1);
        }
        notifyDataSetChanged();
    }

    /** 删除地址 */
    public void removeLocation(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void addLocation(LocationEntity entity){
        if("1".equals(entity.getIs_default())){
            if(mList.size() > 0)
                mList.get(0).setIs_default("0");
            mList.add(0,entity);
        }else {
            mList.add(entity);
        }
        notifyDataSetChanged();
    }

    public void updateLocation(LocationEntity entity,int position){
        if("1".equals(entity.getIs_default())){
            if(mList.size() > 1) {
                LocationEntity entity1 = mList.get(0);
                entity1.setIs_default("0");
                mList.set(position,entity1);
            }
            mList.set(0,entity);
        }else {
            mList.set(position,entity);
        }
        notifyDataSetChanged();
    }

    @Override
    public LocationEntity getItem(int position) {
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
                view = layoutInflater.inflate(R.layout.view_null,null);
                view.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(),
                        parent.getHeight()));
                ((TextView)view.findViewById(R.id.tv_none)).setText(R.string.location_none);
                return view;
            }
        }else {
            ViewHolder viewHolder;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.view_list_location, null);
                viewHolder = new ViewHolder();
                viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.tvPhone = (TextView) view.findViewById(R.id.tv_phone);
                viewHolder.tvAddress = (TextView) view.findViewById(R.id.tv_address);
                viewHolder.tvDefault = (TextView) view.findViewById(R.id.tv_default);
                viewHolder.tvEdit = (TextView) view.findViewById(R.id.tv_edit);
                viewHolder.tvDel = (TextView) view.findViewById(R.id.tv_del);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final LocationEntity entity = mList.get(position);
            viewHolder.tvName.setText(entity.getContact_name());
            viewHolder.tvPhone.setText(entity.getContact_phone());
            viewHolder.tvAddress.setText(entity.getCity());
            viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LocationAddActivity.class);
                    intent.putExtra("type", LocationAddPresenter.LOCATION_UPDATE);
                    intent.putExtra("position", position);
                    intent.putExtra("entity", entity);
                    context.startActivity(intent);
                }
            });
            viewHolder.tvDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                            presenter.delLocation(entity.getId(), context, position);
                        }
                    });
                    dialog.setMsg(R.string.or_delete);
                    dialog.show();
                }
            });
            if ("1".equals(entity.getIs_default())) {
                viewHolder.tvDefault.setCompoundDrawables(drawableLeft, null, null, null);
            } else {
                viewHolder.tvDefault.setCompoundDrawables(unDrawableLeft, null, null, null);
            }

            viewHolder.tvDefault.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("0".equals(entity.getIs_default()))
                        presenter.updateDefault(context, entity, position);
                }
            });
            if (flag)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new IoMessage<LocationEntity>(entity));
                        presenter.finish();
                    }
                });
        }
        return view;
    }

    class ViewHolder{
        TextView tvName;
        TextView tvPhone;
        TextView tvAddress;
        TextView tvDefault;
        TextView tvEdit;
        TextView tvDel;
    }
}
