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
import com.android.wool.model.entity.GoodsCartsEntity;
import com.android.wool.util.AppTools;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.SearchGoodsListActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/28
 */
public class NaTypeChildAdapter extends BaseAdapter {
    private List<GoodsCartsEntity> mList;
    private Context context;
    private int w;
    public NaTypeChildAdapter(Context context) {
        this.mList = new ArrayList<>();
        this.context = context;
        w = (context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(106, context)) / 3;
    }

    public NaTypeChildAdapter(Context context,List<GoodsCartsEntity> list) {
        this.mList = list;
        this.context = context;
        w = (context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(106, context)) / 3;
    }

    public void resetData(List<GoodsCartsEntity> list){
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_na_type_right, parent, false);
            viewHolder = new ViewHolder();
            int h = (int)(140f*w/180f);
            int tvH = (int)(40f*w/180f);
            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.tvName.setLayoutParams(new LinearLayout.LayoutParams(w,tvH));
            viewHolder.img.setLayoutParams(new LinearLayout.LayoutParams(w,h));
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        final GoodsCartsEntity entity = mList.get(position);
        ImageUtils.downloadWidthDefault(
                viewHolder.img.getWidth(),
                viewHolder.img.getHeight(),
                viewHolder.img,
                ImageView.ScaleType.FIT_CENTER,
                entity.getLogo(),
                R.drawable.home_default9,
                R.drawable.home_default9
        );
        viewHolder.tvName.setText(entity.getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchGoodsListActivity.class);
                intent.putExtra("id",entity.getId());
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
