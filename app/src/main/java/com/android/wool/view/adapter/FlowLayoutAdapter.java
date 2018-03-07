package com.android.wool.view.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.wool.R;
import java.util.ArrayList;
import java.util.List;
public class FlowLayoutAdapter extends BaseAdapter{
    private Context context;
    private List<String> tips;
    public FlowLayoutAdapter(Context context){
        this.context = context;
        tips = new ArrayList<>();
    }

    public void setData(String[] list){
        if(list != null && list.length > 0) {
            for(int i = 0; i < list.length; i ++) {
                tips.add(list[i]);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return tips.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public String getItem(int position) {
        if(position < 0 || position > tips.size() - 1){
            return "";
        }
        return tips.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        TextView textView;
        if(convertView != null){
            view = convertView;
            textView = (TextView)view.getTag();
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.tag_item,null);
            textView = (TextView)view.findViewById(R.id.tv_tag);
            view.setTag(textView);
        }
        textView.setText(tips.get(position));
        return view;
    }
}
