package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.wool.R;
import com.android.wool.model.entity.BannerEntity;
import com.android.wool.util.ImageUtils;
import com.android.wool.util.LogUtil;
import com.android.wool.view.activity.GoodsInfoActivity;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2018/1/25
 */
public class ActivityPagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> views;
    private List<String> mList;
    private ViewHolder viewHolder;

    public ActivityPagerAdapter(Context context,List<String> list) {
        views = new ArrayList<>();
        mList = new ArrayList<>();
        this.context = context;
        viewHolder = new ViewHolder();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
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
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if(views.size() > 0){
            view = views.get(0);
            views.remove(0);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.item_home_recommend,null);
            viewHolder.img1 = (ImageView) view.findViewById(R.id.pager1);
            viewHolder.img2 = (ImageView) view.findViewById(R.id.pager2);
            viewHolder.img3 = (ImageView) view.findViewById(R.id.pager3);
            viewHolder.img4 = (ImageView) view.findViewById(R.id.pager4);
            view.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        }
        container.addView(view);

        List<BannerEntity> list = getPosEntity(position);
        final BannerEntity entity1 = getEnttiy(0,list);
        if(entity1 == null){
            viewHolder.img1.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.img1.setVisibility(View.VISIBLE);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img1.getWidth(),
                    viewHolder.img1.getHeight(),
                    viewHolder.img1,
                    ImageView.ScaleType.FIT_XY,
                    entity1.getLogo(),
                    0,
                    0);
        }
        viewHolder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("id",entity1.getId());
                context.startActivity(intent);
            }
        });
        final BannerEntity entity2 = getEnttiy(1,list);
        if(entity2 == null){
            viewHolder.img2.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.img2.setVisibility(View.VISIBLE);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img2.getWidth(),
                    viewHolder.img2.getHeight(),
                    viewHolder.img2,
                    ImageView.ScaleType.FIT_XY,
                    entity2.getLogo(),
                    0,
                    0);
        }
        viewHolder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("id",entity2.getId());
                context.startActivity(intent);
            }
        });
        final BannerEntity entity3 = getEnttiy(2,list);
        if(entity3 == null){
            viewHolder.img3.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.img3.setVisibility(View.VISIBLE);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img3.getWidth(),
                    viewHolder.img3.getHeight(),
                    viewHolder.img3,
                    ImageView.ScaleType.FIT_XY,
                    entity3.getLogo(),
                    0,
                    0);
        }
        viewHolder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("id",entity3.getId());
                context.startActivity(intent);
            }
        });
        final BannerEntity entity4 = getEnttiy(3,list);
        if(entity4 == null){
            viewHolder.img4.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.img4.setVisibility(View.VISIBLE);
            ImageUtils.downloadWidthDefault(
                    viewHolder.img4.getWidth(),
                    viewHolder.img4.getHeight(),
                    viewHolder.img4,
                    ImageView.ScaleType.FIT_XY,
                    entity4.getLogo(),
                    0,
                    0);
        }
        viewHolder.img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GoodsInfoActivity.class);
                intent.putExtra("id",entity4.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }

    private BannerEntity getEnttiy(int pos,List<BannerEntity> list){
        try {
            return list.get(pos);
        }catch (Exception e){
            return null;
        }
    }

    class ViewHolder{
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView img4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        views.add((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    private List<BannerEntity> getPosEntity(int pos){
        String json = mList.get(pos);
        try {
            JSONArray jsonArray = new JSONArray(json);
            JSONArray jsonArrayObj = jsonArray.getJSONArray(pos);
            if(jsonArrayObj != null && jsonArrayObj.length()>0){
                List<BannerEntity> list = new ArrayList<>();
                for (int i = 0 ; i < jsonArrayObj.length(); i++){
                    JSONObject jsonObject = jsonArrayObj.getJSONObject(i);
                    BannerEntity entity = new BannerEntity();
                    entity.parsFromJson(jsonObject);
                    list.add(entity);
                }
                return list;
            }
        }catch (Exception e){
            LogUtil.d(e.getMessage());
            return null;
        }
        return null;
    }
}