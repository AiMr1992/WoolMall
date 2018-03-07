package com.android.wool.view.adapter;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.LogisticsTimeEntity;
import com.android.wool.util.AppTools;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/29
 */
public class LogisticsListAdapter extends BaseAdapter{
    private Context context;
    private List<LogisticsTimeEntity> mList;
    private String exp_time;

    public LogisticsListAdapter(Context context,String exp_time) {
        this.context = context;
        mList = new ArrayList<>();
        this.exp_time = exp_time;
    }

    public void resetData(List<LogisticsTimeEntity> list){
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        LogisticsTimeEntity entity = new LogisticsTimeEntity();
        entity.setContext("卖家已发货");
        if(!TextUtils.isEmpty(exp_time))
            entity.setTime(AppTools.parseLongDate(exp_time));
        mList.add(entity);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public LogisticsTimeEntity getItem(int position) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_logistics,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_context = (TextView) view.findViewById(R.id.tv_content);
            viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        LogisticsTimeEntity entity = mList.get(position);
        viewHolder.tv_context.setText(entity.getContext());
        viewHolder.tv_time.setText(entity.getTime().replace(" ","\n"));
        if(position == 0){
            viewHolder.tv_context.setTextColor(context.getResources().getColor(R.color.order_head));
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.order_head));
        }else {
            viewHolder.tv_context.setTextColor(context.getResources().getColor(R.color.edit_phone));
            viewHolder.tv_time.setTextColor(context.getResources().getColor(R.color.edit_phone));
        }
        return view;
    }

    class ViewHolder{
        TextView tv_context;
        TextView tv_time;
    }
}