package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.SearchGoodsEntity;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.activity.SearchGoodsListView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/21
 */
public class SearchTypeListAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<SearchGoodsEntity> mList;
    private int type;
    private int w;

    public SearchTypeListAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        w = context.getResources().getDisplayMetrics().widthPixels / 2;
    }

    public void setListType(int type){
        this.type = type;
    }

    @Override
    public int getItemViewType(int position) {
        return type = type ==
                SearchGoodsListView.LINEAR_LAYOUT ?
                SearchGoodsListView.LINEAR_LAYOUT
                :SearchGoodsListView.GRID_LAYOUT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == SearchGoodsListView.LINEAR_LAYOUT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_search_goods, parent, false);
            return new LinearViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_search, parent, false);
            return new GridViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchGoodsEntity entity = mList.get(position);
        if(holder instanceof LinearViewHolder){
            LinearViewHolder viewHolder = (LinearViewHolder) holder;
            viewHolder.tvName.setText(entity.getTitle());
            viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb),entity.getPrice()));
            viewHolder.tvNum.setText(String.format(context.getString(R.string.num),entity.getSales_num()));
            viewHolder.tvOldPrice.setText(String.format(context.getString(R.string.rmb),entity.getMarket_price()));
            viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img,
                    ImageView.ScaleType.FIT_XY,
                    entity.getLogo(),
                    R.drawable.home_default11,
                    R.drawable.home_default11
            );
            viewHolder.setListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("id",mList.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }else if(holder instanceof  GridViewHolder){
            GridViewHolder viewHolder = (GridViewHolder) holder;
            viewHolder.tvName.setText(entity.getTitle());
            viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb),entity.getPrice()));
            viewHolder.tvNum.setText(String.format(context.getString(R.string.num),entity.getSales_num()));
            viewHolder.tvOldPrice.setText(String.format(context.getString(R.string.rmb),entity.getMarket_price()));
            viewHolder.tvOldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
            ImageUtils.downloadWidthDefault(
                    viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img,
                    ImageView.ScaleType.FIT_XY,
                    entity.getLogo(),
                    R.drawable.home_default10,
                    R.drawable.home_default10
            );
            viewHolder.setListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("id",mList.get(position).getId());
                    context.startActivity(intent);
                }
            });
            if(position % 2 == 0){
                viewHolder.viewLeft.setVisibility(View.GONE);
                viewHolder.viewRight.setVisibility(View.VISIBLE);
            }else {
                viewHolder.viewLeft.setVisibility(View.VISIBLE);
                viewHolder.viewRight.setVisibility(View.GONE);
            }
        }
    }

    public void addAllData(List<SearchGoodsEntity> list){
        if(list != null && list.size()>0) {
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void resetData(List<SearchGoodsEntity> list){
        mList.clear();
        if(list != null && list.size()>0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvOldPrice;
        TextView tvNum;
        public LinearViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tv_old_price);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
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
    }

    class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvOldPrice;
        TextView tvNum;
        View viewLeft;
        View viewRight;
        public GridViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvOldPrice = (TextView) itemView.findViewById(R.id.tv_old_price);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            viewLeft = (View) itemView.findViewById(R.id.view_left);
            viewRight = (View) itemView.findViewById(R.id.view_right);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w,w);
            img.setLayoutParams(layoutParams);
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
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}