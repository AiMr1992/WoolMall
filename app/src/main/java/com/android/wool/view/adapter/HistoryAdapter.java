package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.common.MyPreference;
import com.android.wool.view.activity.SearchGoodsListActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/12
 */
public class HistoryAdapter extends BaseAdapter{
    private Context context;
    private List<String> mList;

    public HistoryAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    public void resetData(List<String> list){
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size()+1;
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.size() == 0) {
            return 0;
        }else if(position == 0){
            return 1;
        }else
            return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == 0){
            View view = new View(context);
            view.setBackgroundColor(R.color.home_bg_color);
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);
            return view;
        }else if(getItemViewType(position) == 1){
            View view = LayoutInflater.from(context).inflate(R.layout.view_search_history,null);
            view.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyPreference.getInstance().savePreferenceData(context,MyPreference.SEARCH_HISTORY,"");
                    Toast.makeText(context,"清除成功",Toast.LENGTH_SHORT).show();
                    resetData(null);
                }
            });
            return view;
        }else {
            View view;
            ViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.view_history,null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView.setText(mList.get(position-1));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SearchGoodsListActivity.class);
                    intent.putExtra("key",mList.get(position-1));
                    context.startActivity(intent);
                }
            });
            return view;
        }
    }

    class ViewHolder{
        TextView textView;
    }
}
