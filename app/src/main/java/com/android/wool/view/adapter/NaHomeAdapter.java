package com.android.wool.view.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.BannerEntity;
import com.android.wool.model.entity.HomeCourseEntity;
import com.android.wool.model.entity.HomeEntity;
import com.android.wool.model.entity.RecommendEntity;
import com.android.wool.util.AppTools;
import com.android.wool.util.BannerImageLoader;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.activity.SearchGoodsListActivity;
import com.android.wool.view.activity.SpecialListActivity;
import com.android.wool.view.constom.PointView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/27
 */
public class NaHomeAdapter extends BaseAdapter{
    private List<BannerEntity> topTypeList = new ArrayList<>();
    private List<BannerEntity> adImgLists = new ArrayList<>();
    private List<RecommendEntity> recommendEntityList = new ArrayList<>();
    private Context context;
    private int widthPixels;
    public NaHomeAdapter(Context context) {
        this.context = context;
        widthPixels = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void addAllData(List<RecommendEntity> list){
        if(list != null && list.size()>0){
            recommendEntityList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void resetData(HomeEntity entity){
        if(entity != null){
            topTypeList.clear();
            adImgLists.clear();
            recommendEntityList.clear();
            List<BannerEntity> adLists = entity.getAd_list();
            if(adLists != null && adLists.size() > 0){
                topTypeList.addAll(adLists);
                topViewHolder.banner.update(topTypeList);
            }
            List<BannerEntity> adImgList = entity.getAd_img();
            if(adImgList != null && adImgList.size() > 0){
                adImgLists.addAll(adImgList);
            }
            List<RecommendEntity> recommendEntities = entity.getRecommend();
            if(recommendEntities != null && recommendEntities.size() > 0){
                recommendEntityList.addAll(recommendEntities);
            }
            notifyDataSetChanged();
        }
    }

    private BannerEntity getImgAdvertEntity(int pos){
        try {
            return adImgLists.get(pos);
        }catch (Exception e){
            return null;
        }
    }
    /**
     * 广告图
     * @return
     */
    private void advert(CenterViewHolder viewHolder){
        final BannerEntity entity1 = getImgAdvertEntity(0);
        if(entity1 != null) {
            ImageUtils.downloadWidthDefault(
                    viewHolder.img1.getWidth(),
                    viewHolder.img1.getHeight(),
                    viewHolder.img1,
                    ImageView.ScaleType.FIT_XY,
                    entity1.getLogo(),
                    0,
                    0);
            viewHolder.tvTitle1.setText(entity1.getTitle());
            viewHolder.img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SpecialListActivity.class);
                    intent.putExtra("title", entity1.getTitle());
                    intent.putExtra("id", entity1.getContent());
                    intent.putExtra("desc", entity1.getDesc());
                    context.startActivity(intent);
                }
            });
        }
        final BannerEntity entity2 = getImgAdvertEntity(1);
        if(entity2 != null) {
            ImageUtils.downloadWidthDefault(
                    viewHolder.img2.getWidth(),
                    viewHolder.img2.getHeight(),
                    viewHolder.img2,
                    ImageView.ScaleType.FIT_XY,
                    entity2.getLogo(),
                    0,
                    0);
            viewHolder.tvTitle2.setText(entity2.getTitle());
            viewHolder.img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SpecialListActivity.class);
                    intent.putExtra("title", entity2.getTitle());
                    intent.putExtra("id", entity2.getContent());
                    intent.putExtra("desc", entity2.getDesc());
                    context.startActivity(intent);
                }
            });
        }
        final BannerEntity entity3 = getImgAdvertEntity(2);
        if(entity3 != null) {
            ImageUtils.downloadWidthDefault(
                    viewHolder.img3.getWidth(),
                    viewHolder.img3.getHeight(),
                    viewHolder.img3,
                    ImageView.ScaleType.FIT_XY,
                    entity3.getLogo(),
                    0,
                    0);
            viewHolder.tvTitle3.setText(entity3.getTitle());
            viewHolder.img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SpecialListActivity.class);
                    intent.putExtra("title", entity3.getTitle());
                    intent.putExtra("id", entity3.getContent());
                    intent.putExtra("desc", entity3.getDesc());
                    context.startActivity(intent);
                }
            });
        }
        viewHolder.imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchGoodsListActivity.class);
                intent.putExtra("type","hot");
                context.startActivity(intent);
            }
        });
        viewHolder.imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SearchGoodsListActivity.class);
                intent.putExtra("type","new");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        return recommendEntityList.size()+2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        if(position == 1)
            return 1;
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    TopViewHolder topViewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == 0) {
            View view;
            if (convertView != null) {
                view = convertView;
                topViewHolder = (TopViewHolder) view.getTag();
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.item_home_head, null);
                topViewHolder = new TopViewHolder();
                topViewHolder.banner = (Banner) view.findViewById(R.id.banner);
                topViewHolder.home_head = (RelativeLayout) view.findViewById(R.id.home_head);
                int height = (int) (widthPixels * 420f / 750f);
                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        height
                );
                topViewHolder.home_head.setLayoutParams(layoutParams);
                topViewHolder.banner.setImageLoader(new BannerImageLoader());
                topViewHolder.banner.setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        BannerEntity entity = topTypeList.get(position);
                        if ("0".equals(entity.getType())) {
                            Intent intent = new Intent(context, GoodsInfoActivity.class);
                            intent.putExtra("id", entity.getContent());
                            context.startActivity(intent);
                        } else if ("2".equals(entity.getType())) {
                            HomeCourseEntity homeCourseEntity = entity.getDetails();
                            if (homeCourseEntity != null) {
                                Intent intent = new Intent(context, SpecialListActivity.class);
                                intent.putExtra("id", entity.getContent());
                                intent.putExtra("title", homeCourseEntity.getTitle());
                                intent.putExtra("desc", homeCourseEntity.getDesc());
                                context.startActivity(intent);
                            }
                        }
                    }
                });
                view.setTag(topViewHolder);
            }
            return view;
        }else if(getItemViewType(position) == 1){
            View view ;
            CenterViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_home_wool_center,null);
                viewHolder = new CenterViewHolder();
                viewHolder.img1 = (ImageView) view.findViewById(R.id.img1);
                viewHolder.img2 = (ImageView) view.findViewById(R.id.img2);
                viewHolder.img3 = (ImageView) view.findViewById(R.id.img3);
                viewHolder.imgLeft = (ImageView) view.findViewById(R.id.img_left);
                viewHolder.imgRight = (ImageView) view.findViewById(R.id.img_right);
                viewHolder.imgLayout = (LinearLayout) view.findViewById(R.id.img_layout);
                viewHolder.layoutNone = (RelativeLayout) view.findViewById(R.id.layout_none);
                viewHolder.layoutTop = (LinearLayout) view.findViewById(R.id.layout_top);
                viewHolder.tvTitle1 = (TextView) view.findViewById(R.id.tv_title1);
                viewHolder.tvTitle2 = (TextView) view.findViewById(R.id.tv_title2);
                viewHolder.tvTitle3 = (TextView) view.findViewById(R.id.tv_title3);
                int w1 = (int)((widthPixels - AppTools.turnDipToPx(4,context)) / 3f);
                int height1 = (int)(w1 * 166f / 188f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        height1);
                viewHolder.layoutTop.setLayoutParams(layoutParams);

                int w2 = (int)((widthPixels - AppTools.turnDipToPx(15,context)) / 2f);
                int height2 = (int)(269f*w2/569f);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height2
                );
                layoutParams2.topMargin = AppTools.turnDipToPx(20,context);
                viewHolder.imgLayout.setLayoutParams(layoutParams2);

                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height2
                );
                viewHolder.layoutNone.setLayoutParams(layoutParams3);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (CenterViewHolder) view.getTag();
            }
            advert(viewHolder);
            return  view;
        }else {
            View view;
            BottomViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_home_wool_bottom,null);
                viewHolder = new BottomViewHolder();
                viewHolder.bottomHead = (ImageView) view.findViewById(R.id.bottom_head);
                viewHolder.viewPager = (ViewPager) view.findViewById(R.id.view_pager);
                viewHolder.pointView = (PointView) view.findViewById(R.id.point_view);

                viewHolder.pointView.setNormalColor(R.color.gray);
                viewHolder.pointView.setSelectedColor(R.color.app_black);

                int height = (int) (widthPixels * 420f / 750f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        height
                );
                int h = (int)((widthPixels - AppTools.turnDipToPx(25,context))/4f);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        h
                );
                layoutParams1.topMargin = AppTools.turnDipToPx(30,context);
                viewHolder.bottomHead.setLayoutParams(layoutParams);
                viewHolder.viewPager.setLayoutParams(layoutParams1);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (BottomViewHolder)view.getTag();
            }
            typeLists(viewHolder,position-2);
            return view;
        }
    }

    private void typeLists(final BottomViewHolder viewHolder,int pos){
        final RecommendEntity entity = recommendEntityList.get(pos);
        ImageUtils.downloadWidthDefault(
                viewHolder.bottomHead.getWidth(),
                viewHolder.bottomHead.getHeight(),
                viewHolder.bottomHead,
                ImageView.ScaleType.FIT_XY,
                entity.getLogo(),
                0,
                0
        );
        viewHolder.bottomHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SpecialListActivity.class);
                intent.putExtra("title",entity.getTitle());
                intent.putExtra("desc",entity.getDesc());
                intent.putExtra("id",entity.getId());
                context.startActivity(intent);
            }
        });
        final List<String> list = entity.getGoods_list();
        ActivityPagerAdapter adapter = new ActivityPagerAdapter(context,list);
        viewHolder.viewPager.setAdapter(adapter);
        if(list != null)
            viewHolder.pointView.setPosition(0,list.size());
        viewHolder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                viewHolder.pointView.setPosition(position,list.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class CenterViewHolder{
        ImageView img1;
        ImageView img2;
        ImageView img3;
        ImageView imgLeft;
        ImageView imgRight;
        LinearLayout imgLayout;
        RelativeLayout layoutNone;
        LinearLayout layoutTop;
        TextView tvTitle1;
        TextView tvTitle2;
        TextView tvTitle3;
    }

    class BottomViewHolder{
        ImageView bottomHead;
        ViewPager viewPager;
        PointView pointView;
    }

    class TopViewHolder{
        Banner banner;
        RelativeLayout home_head;
    }

    public void onStart(){
        if(topViewHolder != null && topTypeList.size() > 0){
            topViewHolder.banner.startAutoPlay();
        }
    }

    public void onStop(){
        if(topViewHolder != null){
            topViewHolder.banner.stopAutoPlay();
        }
    }
}