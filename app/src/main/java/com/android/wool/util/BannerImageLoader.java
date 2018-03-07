package com.android.wool.util;
import android.content.Context;
import android.widget.ImageView;
import com.android.wool.R;
import com.android.wool.model.entity.BannerEntity;
import com.android.wool.model.entity.HomeCourseEntity;
import com.youth.banner.loader.ImageLoader;
/**
 * Created by AiMr on 2017/10/31
 */
public class BannerImageLoader extends ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        String imgPath = "";
        if(path instanceof String){
            imgPath = (String) path;
        }else if(path instanceof BannerEntity){
            imgPath = ((BannerEntity) path).getLogo();
        }else if(path instanceof HomeCourseEntity){
            imgPath = ((HomeCourseEntity) path).getLogo();
        }
        ImageUtils.downloadWidthDefault(
                imageView.getWidth(),
                imageView.getHeight(),
                imageView,
                ImageView.ScaleType.FIT_XY,
                imgPath,
                R.drawable.home_default1,
                R.drawable.home_default1);
    }
}
