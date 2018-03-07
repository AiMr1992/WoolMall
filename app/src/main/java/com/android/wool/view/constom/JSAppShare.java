package com.android.wool.view.constom;
import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.JavascriptInterface;
import com.android.wool.util.LogUtil;
import com.android.wool.view.activity.HtmlActivity;
/**
 * Created by AiMr on 2017/12/4
 */
@SuppressLint("JavascriptInterface")
public class JSAppShare {
    private Context context;

    public JSAppShare(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void share(String imgUrl,String title,String desc,String url){
        LogUtil.d(imgUrl+" "+title+" "+desc+" "+url);
        ((HtmlActivity)context).share(title,url,desc,imgUrl);
    }
}