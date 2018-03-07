package com.android.wool.util;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.model.network.api.HttpConstant;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import java.util.List;
public class ShareUtils implements UMShareListener {
    private Context context;
    private String shareTitle;//分享标题
    private String shareUrl;//分享链接
    private String shareText;//分享内容
    private UMImage umImage;
    private OnShareResultListener onShareResultListener;

    public ShareUtils(Context context){
        this.context = context;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public UMImage getUmImage() {
        return umImage;
    }

    public void setUmImage(UMImage umImage) {
        this.umImage = umImage;
    }

    public void setOnShareResultListener(OnShareResultListener onShareResultListener) {
        this.onShareResultListener = onShareResultListener;
    }

    public void shareQQ(){
        if(isQQClientAvailable(context)) {
            UMWeb web = new UMWeb(HttpConstant.URL+shareUrl);
            web.setTitle(shareTitle);
            web.setDescription(shareText);
            web.setThumb(umImage);
            new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ)
                    .setCallback(this)
                    .withMedia(web)
                    .share();
        }else{
            Toast.makeText(context, R.string.uninstall_qq_mobile, Toast.LENGTH_SHORT).show();
        }
    }

    public void shareSina(){
        //不支持title
        UMWeb web = new UMWeb(HttpConstant.URL+shareUrl);
        web.setTitle(shareTitle);
        web.setDescription(shareText);
        web.setThumb(umImage);
        new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.SINA)
                .setCallback(this)
                .withMedia(web)
                .share();
    }

    public void shareWechat(){
        UMWeb web = new UMWeb(HttpConstant.URL+shareUrl);
        web.setThumb(umImage);
        web.setTitle(shareTitle);
        web.setDescription(shareText);
        new ShareAction((Activity)context).setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(this)
                .withMedia(web)
                .share();
    }

    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onResult(SHARE_MEDIA share_media) {
        LogUtil.d("onResult---->" + share_media.toString());
        Toast.makeText(context, R.string.share_success, Toast.LENGTH_SHORT).show();
        if(onShareResultListener != null){
            onShareResultListener.onShareSuccess();
        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, final Throwable throwable) {
        LogUtil.d("onError---->"+share_media.toString()+throwable.getMessage());
        Toast.makeText(context,throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {
        LogUtil.d("onCancel---->");
        Toast.makeText(context,R.string.on_cancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        LogUtil.d("onStart---->");
    }

    public interface OnShareResultListener{
        void onShareSuccess();
    }
}
