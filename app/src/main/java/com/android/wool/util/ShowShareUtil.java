package com.android.wool.util;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import com.android.wool.view.constom.ShareDialog;
import com.umeng.socialize.media.UMImage;
public class ShowShareUtil{
    private ShareDialog dialog;
    private Context context;
    private ShareUtils shareUtils;
    public ShowShareUtil(Context context){
        this.context = context;
        init();
    }

    private void init(){
        shareUtils = new ShareUtils(context);
        dialog = new ShareDialog(context);
        dialog.setShareListener(new ShareDialog.OnShareListener() {
            @Override
            public void onclick(int type, Dialog dialog) {
                if(type == ShareDialog.QQ){
                    shareUtils.shareQQ();
                    dialog.dismiss();
                }else if(type == ShareDialog.WX){
                    shareUtils.shareWechat();
                    dialog.dismiss();
                }else if(type == ShareDialog.SINA){
                    shareUtils.shareSina();
                    dialog.dismiss();
                }
            }
        });
    }

    public void setShareContent(String shareTitle, String shareUrl, String shareText, String shareIcon){
        LogUtil.d("分享:"+shareTitle+" shareUrl:"+shareUrl+" shareText:"+shareText+" shareIcon:"+shareIcon);
        shareUtils.setShareTitle(shareTitle);
        shareUtils.setShareUrl(shareUrl);
        shareUtils.setShareText(shareText);
        if(!TextUtils.isEmpty(shareIcon)) {
            shareUtils.setUmImage(new UMImage(context,
                    shareIcon));
        }
    }

    public void show(){
        dialog.show();
    }
}
