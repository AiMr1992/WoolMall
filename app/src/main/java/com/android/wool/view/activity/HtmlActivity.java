package com.android.wool.view.activity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.util.HtmlFormat;
import com.android.wool.util.ShowShareUtil;
import com.android.wool.view.constom.JSAppJumpPage;
import com.android.wool.view.constom.JSAppShare;
import com.android.wool.util.LogUtil;
import com.android.wool.view.constom.MyAlertDialog;
import com.umeng.socialize.UMShareAPI;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/11/10
 */
public class HtmlActivity extends BaseActivity{
    @BindView(R.id.web_view)
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack(R.drawable.back,"",true);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("share")))
            setRightNext(R.drawable.course_share,"",true);
        webView.getSettings().setJavaScriptEnabled(true);
        MyChromeClient myChromeClient = new MyChromeClient();
        webView.setWebChromeClient(myChromeClient);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!TextUtils.isEmpty(getIntent().getStringExtra("pay"))){
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
        webView.addJavascriptInterface(new JSAppJumpPage(this),"JSAppJumpPage");
        webView.addJavascriptInterface(new JSAppShare(this),"JSAppShare");
        /**
         * type url h5链接
         *      data htme标签
         */
        if("data".equals(getIntent().getStringExtra("type"))){
            webView.loadData(HtmlFormat.getNewContent(getIntent().getStringExtra("url")),"text/html; charset=UTF-8", null);
        }else {
            webView.loadUrl(getIntent().getStringExtra("url"));
        }
        LogUtil.d(getIntent().getStringExtra("url"));
        showLoadingProgressDialog(null);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_fodder;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
            case R.id.layout_right:
                share(getIntent().getStringExtra("title"),
                        getIntent().getStringExtra("share_url"),
                        getIntent().getStringExtra("share_desc"),
                        getIntent().getStringExtra("share_img")
                );
                break;
        }
    }

    public void share(String title,String url,String desc,String imgUrl){
        if (Build.VERSION.SDK_INT >= 23){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                ShowShareUtil shareUtil = new ShowShareUtil(this);
                shareUtil.setShareContent(title,
                        url,
                        desc,
                        imgUrl
                );
                shareUtil.show();
            }else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        101);
            }
        }else {
            ShowShareUtil shareUtil = new ShowShareUtil(this);
            shareUtil.setShareContent(getIntent().getStringExtra("title"),
                    getIntent().getStringExtra("share_url"),
                    getIntent().getStringExtra("share_desc"),
                    getIntent().getStringExtra("share_img")
            );
            shareUtil.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    MyAlertDialog dialog = new MyAlertDialog(this);
                    dialog.setMsg(getString(R.string.permissions_wr));
                    dialog.setCenterButton(R.string.center_y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
            if (requestCode == 101) {
                ShowShareUtil shareUtil = new ShowShareUtil(this);
                shareUtil.setShareContent(getIntent().getStringExtra("title"),
                        getIntent().getStringExtra("share_url"),
                        getIntent().getStringExtra("share_desc"),
                        getIntent().getStringExtra("share_img")
                );
                shareUtil.show();
            }
        }
    }

    class MyChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if(!TextUtils.isEmpty(getIntent().getStringExtra("title"))){
                setTitle(getIntent().getStringExtra("title"));
            }else {
                setTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100) {
                webView.loadUrl("javascript:hide()");
                dismissProgressDialog();
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!TextUtils.isEmpty(getIntent().getStringExtra("pay")))
            finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成
    }
}