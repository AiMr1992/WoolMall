package com.android.wool.util;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.ImageView;
import com.android.wool.MyApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import org.xutils.x;
import java.io.File;
/**
 * Created by AiMr on 2017/9/25
 */
public class ImageUtils {
    public static void downloadWidthDefault(int width,int height,
                                                   ImageView imageView,ImageView.ScaleType scaleType,String url,
                                                   int defaultImg,int failedImg){
        if(TextUtils.isEmpty(url)){
            return;
        }
        LogUtil.d("imgUrl---->" +url);
        imageView.setScaleType(scaleType);
        Glide
                .with(x.app())
                .load(url)
//                .load(HttpConstant.IMG_URL+url)
                .dontAnimate()
                .placeholder(defaultImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(failedImg)
                .into(imageView);
    }

    // 清除Glide内存缓存
    public static boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(MyApplication.getInstance().getApplicationContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void downloadWidthCropCircle(int width,int height,
                                            ImageView imageView,ImageView.ScaleType scaleType,String url,
                                            int defaultImg,int failedImg){
        if(TextUtils.isEmpty(url)){
            return;
        }
        LogUtil.d("imgUrl---->" + url);
        imageView.setScaleType(scaleType);
        Glide.with(x.app())
                .load(url)
//                .load(HttpConstant.IMG_URL+url)
                .placeholder(defaultImg)
                .error(failedImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(x.app()))
                .into(imageView);
    }

    public static void downloadUrl(Context context,int width, int height,
                                   String url,final ImageLoaderListener listener){
        Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>(width, height) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if (listener != null) {
                    listener.onLoadComplete(resource);
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                if (listener != null) {
                    listener.onLoadFailed(e);
                }
            }
        });
    }

    /** 加载七牛圆图 */
    public static void downloadQiNiuCropCircle(int width,int height,
                                            ImageView imageView,ImageView.ScaleType scaleType,String url,
                                            int defaultImg,int failedImg){
        if(TextUtils.isEmpty(url)){
            return;
        }
        LogUtil.d("imgUrl---->" + url);
        imageView.setScaleType(scaleType);
        Glide.with(x.app())
                .load(url)
                .placeholder(defaultImg)
                .error(failedImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(x.app()))
                .into(imageView);
    }

    /** 七牛路径名 */
    public static void downloadQiNiu(int width,int height,
                                               ImageView imageView,ImageView.ScaleType scaleType,String url,
                                               int defaultImg,int failedImg){
        if(TextUtils.isEmpty(url)){
            return;
        }
        LogUtil.d("imgUrl---->" + url);
        imageView.setScaleType(scaleType);
        Glide.with(x.app())
                .load(url)
//                .load(Constant.SECRET_HOST + url)
                .placeholder(defaultImg)
                .error(failedImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /** 本地图片  */
    public static void downloadWidthLocationCropCircle(int width,int height,
                                               ImageView imageView,ImageView.ScaleType scaleType,File file,
                                               int defaultImg,int failedImg){
        if(file == null || !file.exists() ){
            return;
        }
        imageView.setScaleType(scaleType);
        Glide.with(x.app())
                .load(file)
                .placeholder(defaultImg)
                .error(failedImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new GlideCircleTransform(x.app()))
                .into(imageView);
    }

    /** 本地图片  */
    public static void downloadWidthLocation(int width,int height,
                                                       ImageView imageView,ImageView.ScaleType scaleType,File file,
                                                       int defaultImg,int failedImg){
        if(file == null || !file.exists() ){
            return;
        }
        imageView.setScaleType(scaleType);
        Glide.with(x.app())
                .load(file)
                .placeholder(defaultImg)
                .error(failedImg)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}