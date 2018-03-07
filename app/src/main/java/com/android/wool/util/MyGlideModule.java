package com.android.wool.util;
import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;
/**
 * Created by AiMr on 2017/11/9
 */
public class MyGlideModule implements GlideModule {
    @Override public void applyOptions(Context context, GlideBuilder builder) {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int memoryCacheSize = maxMemory / 8;
        //设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));
        int diskCacheSize = 1024 * 1024 * 30;
        //设置磁盘缓存大小
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", diskCacheSize));
        //设置BitmapPool缓存内存大小
        builder.setBitmapPool(new LruBitmapPool(memoryCacheSize));
    }

    @Override public void registerComponents(Context context, Glide glide) {
    }
}