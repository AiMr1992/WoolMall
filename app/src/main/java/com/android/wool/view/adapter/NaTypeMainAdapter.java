package com.android.wool.view.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.GoodsCartsEntity;
import com.android.wool.model.entity.GoodsTypeBrandEntity;
import com.android.wool.util.AppTools;
import com.android.wool.view.constom.GrapeGridView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/28
 */
public class NaTypeMainAdapter extends BaseAdapter{
    private List<GoodsCartsEntity> bottomLists;
    private List<GoodsTypeBrandEntity> listHot;
    private Context context;
    private List<NaTypeChildAdapter> listAdapters;
    private int w;
    public NaTypeMainAdapter(Context context) {
        bottomLists = new ArrayList<>();
        listHot = new ArrayList<>();
        this.context = context;
        listAdapters = new ArrayList<>();
        w = (int)((context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(106, context)) / 3f);

    }

    public void resetData(List<GoodsCartsEntity> list, List<GoodsTypeBrandEntity> list2){
        bottomLists.clear();
        listHot.clear();
        if(list != null && list.size() > 0){
            bottomLists.addAll(list);
        }
        if(list2 != null && list2.size()>0){
            listHot.addAll(list2);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(listHot.size()>0 && position == 0)
            return 0;
        return 1;
    }

    @Override
    public int getCount() {
        return listHot.size()>0?bottomLists.size()+1:bottomLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == 0){
            View view;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.view_type_hot, null);
                topviewHolder = new TopViewHolder();
                topviewHolder.customGridVie =  (GrapeGridView) view.findViewById(R.id.grid_view);
                topviewHolder.layoutView = (LinearLayout) view.findViewById(R.id.layout_view);
                view.setTag(topviewHolder);
            }else {
                view = convertView;
                topviewHolder = (TopViewHolder) view.getTag();
            }
            NaHotAdapter adapter = new NaHotAdapter(context,listHot);
            topviewHolder.customGridVie.setAdapter(adapter);
            int size = 0;
            if (listHot != null && listHot.size() > 0) {
                size = AppTools.getRow(listHot.size());
                topviewHolder.layoutView.setVisibility(View.VISIBLE);
            } else {
                topviewHolder.layoutView.setVisibility(View.GONE);
            }
            int tvH = (int)(40f*w/180f);
            int h = (int) (140f*w/180f + tvH);
            topviewHolder.customGridVie.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size * h));
            return view;
        }else {
            View view;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.view_type_goods, null);
                viewHolder = new ViewHolder();
                viewHolder.customGridVie = (GrapeGridView) view.findViewById(R.id.grid_view);
                viewHolder.tvName = ((TextView)view.findViewById(R.id.tv_title));
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            GoodsCartsEntity entity = bottomLists.get(position-1);
            List<GoodsCartsEntity> list = entity.getChild();
            NaTypeChildAdapter adapter = new NaTypeChildAdapter(context,list);
            viewHolder.tvName.setText(entity.getTitle());
            viewHolder.customGridVie.setAdapter(adapter);
            int size = 0;
            if (list != null && list.size() > 0) {
                size = AppTools.getRow(list.size());
                view.findViewById(R.id.layout_view).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.layout_view).setVisibility(View.GONE);
            }
            int tvH = (int)(40f*w/180f);
            int h = (int)(140f*w/180f + tvH);
            viewHolder.customGridVie.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, size * h));
            return view;
        }
    }

    private TopViewHolder topviewHolder;
    class TopViewHolder{
        GrapeGridView customGridVie;
        LinearLayout layoutView;
    }

    private ViewHolder viewHolder;
    class ViewHolder{
        GrapeGridView customGridVie;
        TextView tvName;
    }
}
