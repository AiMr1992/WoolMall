package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.GoodsHomeEntity;
import com.android.wool.model.entity.SpecialEntity;
import com.android.wool.model.entity.SpecialListEntity;
import com.android.wool.util.AppTools;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.GoodsInfoActivity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/28
 */
public class SpecialListAdapter extends RecyclerView.Adapter{
    private SpecialEntity specialEntity;
    private List<GoodsHomeEntity> mList;
    private Context context;
    private int w;
    private int h;
    private int width;
    private String desc;
    public SpecialListAdapter(Context context,String desc) {
        this.context = context;
        this.desc = desc;
        mList = new ArrayList<>();
        w = context.getResources().getDisplayMetrics().widthPixels;
//        w = context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(8,context);
        h = w*380/740;
        width = (context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(5,context))/2;
    }

    public void resetData(SpecialListEntity entity){
        this.specialEntity = null;
        mList.clear();
        if(entity != null){
            List<GoodsHomeEntity> list = entity.getGoods_list();
            if(list != null && list.size()>0){
                mList.addAll(list);
            }
            SpecialEntity specialEntity = entity.getSpecial_info();
            if(specialEntity != null){
                this.specialEntity = specialEntity;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_head, parent, false);
            return new TopViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special, parent, false);
            return new GridViewHolder(view,width);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        return 1;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof TopViewHolder){
            TopViewHolder viewHolder = (TopViewHolder) holder;
            if(this.specialEntity != null){
                ImageUtils.downloadWidthDefault(
                        viewHolder.img.getWidth(),
                        viewHolder.img.getHeight(),
                        viewHolder.img,
                        ImageView.ScaleType.FIT_XY,
                        this.specialEntity.getLogo(),
                        R.drawable.home_default7,
                        R.drawable.home_default7
                );
                viewHolder.tv_desc.setText(specialEntity.getDesc());
            }
        }else {
            GridViewHolder viewHolder = (GridViewHolder) holder;
            if(mList.size() > 0) {
                GoodsHomeEntity entity = mList.get(position - 1);
                viewHolder.tvName.setText(entity.getTitle());
                viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb), entity.getPrice()));
                viewHolder.tvNum.setText(String.format(context.getString(R.string.num), entity.getSales_num()));
                ImageUtils.downloadWidthDefault(
                        viewHolder.img.getWidth(),
                        viewHolder.img.getHeight(),
                        viewHolder.img,
                        ImageView.ScaleType.FIT_XY,
                        entity.getLogo(),
                        R.drawable.home_default10,
                        R.drawable.home_default10
                );
                if(position % 2 != 0){
                    viewHolder.viewLeft.setVisibility(View.GONE);
                    viewHolder.viewRight.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.viewLeft.setVisibility(View.VISIBLE);
                    viewHolder.viewRight.setVisibility(View.GONE);
                }
                viewHolder.setListener(new GridViewHolder.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(context, GoodsInfoActivity.class);
                        intent.putExtra("id",mList.get(position).getId());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    class TopViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView tv_desc;
        public TopViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            img.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, h));
        }

    }

    public static class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvNum;
        View viewLeft;
        View viewRight;
        public GridViewHolder(View itemView,int width) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            viewLeft = (View) itemView.findViewById(R.id.view_left);
            viewRight = (View) itemView.findViewById(R.id.view_right);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,width);
            img.setLayoutParams(layoutParams);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition()-1;
            if(listener != null){
                listener.onItemClick(view,position);
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