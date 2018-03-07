package com.android.wool;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.android.wool.common.Constant;
import com.android.wool.model.entity.CityInfoEntity;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.activity.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.pgyersdk.crash.PgyCrashManager;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.SavePowerConfig;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import org.xutils.x;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
/**
 * Created by AiMr on 2017/9/21
 */
public class MyApplication extends Application{
    private static MyApplication context;
    public static List<CityInfoEntity> cityList;
    @Override
    public void onCreate() {
        super.onCreate();
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH);
        x.Ext.init(this);
        x.Ext.setDebug(true);
        context = this;
        Unicorn.init(this, Constant.QIYU_APPKEY, options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {
                ImageUtils.downloadUrl(getApplicationContext(),width,height,uri,listener);
            }
        });
        Config.DEBUG = true;
        initYM();
        PgyCrashManager.register(this);
    }

    private void initYM(){
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx967daebe835fbeac","5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("1106503885","I4LzCsLzK6dZMnUi");
        PlatformConfig.setSinaWeibo("2904850582","c909a91b5ff3441037da03bc4cbd8c08","http://sns.whalecloud.com");
        UMShareConfig config = new UMShareConfig();
        config.isOpenShareEditActivity(true);
        UMShareAPI.get(this).setShareConfig(config);

        //用户行为分析
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_DUM_NORMAL);
        UMConfigure.setEncryptEnabled(true);
    }

    public static void setCityList(List<CityInfoEntity> cityEntityList) {
        MyApplication.cityList = cityEntityList;
    }

    public static boolean inMainProcess(Context context) {
        String mainProcessName = context.getApplicationInfo().processName;
        String processName = getProcessName();
        return TextUtils.equals(mainProcessName, processName);
    }

    /** 获取当前进程名 */
    private static String getProcessName() {
        BufferedReader reader = null;
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            reader = new BufferedReader(new FileReader(file));
            return reader.readLine().trim();
        } catch (IOException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class;
        options.statusBarNotificationConfig = config;
        options.savePowerConfig = new SavePowerConfig();
        return options;
    }

    public static MyApplication getInstance() {
        return context;
    }
}
