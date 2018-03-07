package com.android.wool.view.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.ProductsEntity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/17
 */
public class ProductAdapter extends BaseAdapter{
    private Context context;
    private List<ProductsEntity> mList;

    public ProductAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    public void resetData(List<ProductsEntity> list){
        mList.clear();
        if(list != null && list.size()>0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public Object getItem(int position) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_goods_info,null);
            viewHolder = new ViewHolder();
            viewHolder.tv_right = (TextView) view.findViewById(R.id.tv_right);
            viewHolder.tv_left = (TextView) view.findViewById(R.id.tv_left);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        ProductsEntity entity = mList.get(position);
        viewHolder.tv_left.setText(entity.getName());
        viewHolder.tv_right.setText(entity.getValue());
        return view;
    }

    class ViewHolder{
        TextView tv_right;
        TextView tv_left;
    }
}
