package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.GoodsTypeBrandEntity;
import com.android.wool.util.AppTools;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.SearchGoodsListActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/29
 */
public class NaHotAdapter extends BaseAdapter{
    private List<GoodsTypeBrandEntity> mList;
    private Context context;
    private int w;
    public NaHotAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.context = context;
    }
    public NaHotAdapter(Context context,List<GoodsTypeBrandEntity> list) {
        this.mList = list;
        this.context = context;
        w = (context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(106, context)) / 3;
    }

    public void resetData(List<GoodsTypeBrandEntity> list){
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GoodsTypeBrandEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_na_type_right,null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            int h = (int)(140f*w/180f);
            int tvH = (int)(40f*w/180f);
            viewHolder.img.setLayoutParams(new LinearLayout.LayoutParams(w,h));
            viewHolder.tvName.setLayoutParams(new LinearLayout.LayoutParams(w,tvH));
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        final GoodsTypeBrandEntity entity = mList.get(position);
        viewHolder.tvName.setText(entity.getTitle());
        ImageUtils.downloadWidthDefault(
                viewHolder.img.getWidth(),
                viewHolder.img.getHeight(),
                viewHolder.img,
                ImageView.ScaleType.FIT_CENTER,
                entity.getLogo(),
                R.drawable.home_default9,
                R.drawable.home_default9);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchGoodsListActivity.class);
                intent.putExtra("key",entity.getTitle());
                intent.putExtra("type","true");
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder{
        ImageView img;
        TextView tvName;
    }
}
