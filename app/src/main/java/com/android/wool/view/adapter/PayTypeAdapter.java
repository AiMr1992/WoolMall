package com.android.wool.view.adapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.PayTypeEntity;
import com.android.wool.util.ImageUtils;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/24
 */
public class PayTypeAdapter extends BaseAdapter{
    private Context context;
    private List<PayTypeEntity> mList;
    private int mPos;
    private Drawable defaultDrawable;
    private Drawable drawable;

    public PayTypeAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        defaultDrawable = context.getResources().getDrawable(R.drawable.default_location);
        defaultDrawable.setBounds(0,0,defaultDrawable.getIntrinsicWidth(),defaultDrawable.getIntrinsicHeight());
        drawable = context.getResources().getDrawable(R.drawable.un_default_location);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
    }

    public void resetData(List<PayTypeEntity> list){
        mList.clear();
        if(list != null && list.size()>0){
            mList.addAll(list);
            mList.get(mPos).setShow(true);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public PayTypeEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public PayTypeEntity getItemEntity(){
        return getItem(mPos);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_pay_type,null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        final PayTypeEntity entity = mList.get(position);
        viewHolder.tvName.setText(entity.getTitle());
        ImageUtils.downloadWidthDefault(
                viewHolder.img.getWidth(),
                viewHolder.img.getHeight(),
                viewHolder.img,
                ImageView.ScaleType.FIT_XY,
                entity.getLogo(),
                0,
                0);
        if(entity.isShow()){
            viewHolder.tvName.setCompoundDrawables(null,null,defaultDrawable,null);
        }else {
            viewHolder.tvName.setCompoundDrawables(null,null,drawable,null);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!entity.isShow()){
                    mList.get(mPos).setShow(false);
                    mList.get(position).setShow(true);
                    mPos = position;
                    notifyDataSetChanged();
                }
            }
        });
        return view;
    }

    class ViewHolder{
        ImageView img;
        TextView tvName;
    }
}
