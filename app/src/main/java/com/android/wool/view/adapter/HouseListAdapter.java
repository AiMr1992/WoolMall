package com.android.wool.view.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.HouseListEntity;
import com.android.wool.presenter.HouseListPresenter;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.SlideView;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/25
 */
public class HouseListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<HouseListEntity> mList;
    private HouseListPresenter presenter;
    private boolean firstVisible;
    public HouseListAdapter(Context context, HouseListPresenter presenter) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
        this.presenter = presenter;
    }

    public void resetData(List<HouseListEntity> list){
        firstVisible = true;
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void addAllData(List<HouseListEntity> list){
        if(list != null && list.size() > 0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void remove(int position){
        if(mList.size() > 0){
            mList.remove(position);
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
    public HouseListEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if(getItemViewType(position) == 0){
            if(convertView != null){
                view = convertView;
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.view_null,null);
                view.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(),
                        parent.getHeight()));
                ((TextView)view.findViewById(R.id.tv_none)).setText(R.string.house_none);
                return view;
            }
        }else {
            ViewHolder viewHolder;
            if (convertView == null) {
                View itemView = layoutInflater.inflate(R.layout.view_list_house, null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) itemView.findViewById(R.id.img);
                viewHolder.tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                viewHolder.tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
                viewHolder.tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
                viewHolder.tvOldPrice = (TextView) itemView.findViewById(R.id.tv_old_price);
                SlideView slideView = new SlideView(context);
                slideView.setLayoutParams(new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.WRAP_CONTENT
                ));
                slideView.setContentView(itemView);
                slideView.setTag(viewHolder);
                view = slideView;
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final HouseListEntity entity = mList.get(position);
            ((SlideView) view).showSlideButton(true, false, new SlideView.OnSlideButtonClick() {
                @Override
                public void onDeleteClick() {
                    MyAlertDialog dialog = new MyAlertDialog(context);
                    dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setRightButton(R.string.enter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            presenter.update(context, entity, "del", position);
                        }
                    });
                    dialog.setMsg("是否取消收藏?");
                    dialog.show();
                }

                @Override
                public void onEditClick() {

                }
            });
            ImageUtils.downloadWidthDefault(viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img, ImageView.ScaleType.FIT_XY,
                    entity.getLogo(),
                    R.drawable.home_default10,
                    R.drawable.home_default10);
            viewHolder.tvTitle.setText(entity.getTitle());
            viewHolder.tvPrice.setText(
                    String.format(context.getResources().getString(R.string.rmb), entity.getPrice()));
            viewHolder.tvOldPrice.setText(
                    String.format(context.getResources().getString(R.string.rmb), entity.getMarket_price()));
            TextView textView = (TextView) view.findViewById(R.id.tv_old_price);
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra("id", entity.getId());
                    context.startActivity(intent);
                }
            });
        }
        return view;
    }

    class ViewHolder{
        ImageView img;
        TextView tvTitle;
        TextView tvLocation;
        TextView tvPrice;
        TextView tvOldPrice;
    }
}
