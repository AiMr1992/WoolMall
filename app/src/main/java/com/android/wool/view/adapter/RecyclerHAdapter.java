package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.GoodsHomeEntity;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.GoodsInfoActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/27
 */
public class RecyclerHAdapter extends RecyclerView.Adapter<RecyclerHAdapter.MyViewHolder>{
    private List<GoodsHomeEntity> mList;
    private Context context;
    public RecyclerHAdapter(final Context context) {
        this.mList = new ArrayList<>();
        this.context = context;
    }

    public void resetData(List<GoodsHomeEntity> list){
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GoodsHomeEntity entity = mList.get(position);
        holder.tvName.setText(entity.getTitle());
        holder.tvPrice.setText(String.format(context.getString(R.string.rmb_none),entity.getPrice()));
        ImageUtils.downloadWidthDefault(
                holder.imageView.getWidth(),
                holder.imageView.getHeight(),
                holder.imageView,
                ImageView.ScaleType.FIT_CENTER,
                entity.getLogo(),
                R.drawable.home_default8,
                R.drawable.home_default8
        );
        holder.setListener(new MyViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("id",mList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_center, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView tvName;
        TextView tvPrice;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_home_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            if(listener != null){
                listener.onItemClick(v,position);
            }
        }

        private OnItemClickListener listener;

        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }
        public interface OnItemClickListener{
            void onItemClick(View view,int position);
        }
    }
}
