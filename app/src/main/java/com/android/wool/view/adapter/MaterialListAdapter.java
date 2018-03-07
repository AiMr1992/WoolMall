package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.MaterialEntity;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.HtmlActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/11/17
 * 素材
 */
public class MaterialListAdapter extends BaseAdapter{
    private List<MaterialEntity> mList;
    private Context context;
    private boolean firstVisible;

    public MaterialListAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void resetData(List<MaterialEntity> list){
        firstVisible = true;
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAllData(List<MaterialEntity> list){
        if(list != null && list.size() > 0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
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

    @Override
    public MaterialEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(getItemViewType(position) == 0){
            if(convertView != null){
                view = convertView;
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.view_null,null);
                view.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(),
                        parent.getHeight()));
                ((TextView)view.findViewById(R.id.tv_none)).setText(R.string.special_none);
                return view;
            }
        }else {
            ViewHolder viewHolder;
            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_material, null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) view.findViewById(R.id.img);
                viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
                viewHolder.tv_label = (TextView) view.findViewById(R.id.tv_label);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final MaterialEntity entity = mList.get(position);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img,
                    ImageView.ScaleType.FIT_XY,
                    entity.getLogo(), R.drawable.home_default12, R.drawable.home_default12
            );
            viewHolder.tv_title.setText(entity.getTitle());
            viewHolder.tv_label.setText(entity.getDescription());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HtmlActivity.class);
                    intent.putExtra("url",entity.getContent());
                    intent.putExtra("share","true");
                    intent.putExtra("type","data");
                    intent.putExtra("share_url",HttpConstant.HTML_SHARE_SOURCE+entity.getId());
                    intent.putExtra("share_desc",entity.getDescription());
                    intent.putExtra("share_img",entity.getLogo());
                    intent.putExtra("title",entity.getTitle());
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    class ViewHolder{
        ImageView img;
        TextView tv_title;
        TextView tv_label;
    }
}