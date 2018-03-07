package com.android.wool.view.constom;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.util.LogUtil;
import com.android.wool.view.activity.HtmlActivity;
/**
 * Created by AiMr on 2017/11/10
 */
@SuppressLint("JavascriptInterface")
public class JSAppJumpPage {
    private Context context;
    public JSAppJumpPage(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void onItemClick(String url,String share){
        LogUtil.d(HttpConstant.URL+url);
        Intent intent = new Intent(context, HtmlActivity.class);
        intent.putExtra("url", HttpConstant.URL+url);
        intent.putExtra("share",share);
        context.startActivity(intent);
    }

    @JavascriptInterface
    public void onPageClick(String className){
        try {
            Class clz = Class.forName(className);
            context.startActivity(new Intent(context, clz));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
