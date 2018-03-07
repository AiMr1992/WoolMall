package com.android.wool.base;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.util.SystemUtils;
import com.android.wool.view.constom.ProgressDialog;
import com.umeng.analytics.MobclickAgent;
import butterknife.ButterKnife;
/**
 * Created by AiMr on 2017/9/21
 */
public abstract class BaseActivity<T extends DetachController> extends AppCompatActivity implements View.OnClickListener{
    public TextView tvTitle;
    public TextView tvBack;
    public TextView tvRight;
    public RelativeLayout layoutTitle;
    public T presenter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_base,null);
        FrameLayout actBase = (FrameLayout)view.findViewById(R.id.act_base);
        View actLayout = LayoutInflater.from(this).inflate(getLayoutId(),null);
        actBase.addView(actLayout);
        SystemUtils.setStatusBarTransLight(this);
        setContentView(view);
        ButterKnife.bind(this);
    }

    public abstract int getLayoutId();

    public void setTitle(int textId){
        this.setTitle(getString(textId));
    }

    public void setTitle(CharSequence text){
        if(tvTitle == null)
            tvTitle = (TextView) findViewById(R.id.head_title);
        tvTitle.setText(text);
    }

    public void setHeadViewColor(int colorId){
        if(layoutTitle == null)
            layoutTitle = (RelativeLayout) findViewById(R.id.layout_title);
        layoutTitle.setBackgroundColor(getResources().getColor(colorId));
    }

    public void setTitleTextColor(int colorId){
        if(tvTitle == null)
            tvTitle = (TextView) findViewById(R.id.head_title);
        tvTitle.setTextColor(getResources().getColor(colorId));
    }

    public void setLeftBack(int res,CharSequence text,boolean listener){
        LinearLayout layoutLeft = (LinearLayout) findViewById(R.id.layout_left);
        ImageView imgBack = (ImageView) findViewById(R.id.head_img_back);
        if(imgBack != null)
            imgBack.setImageResource(res);
        if(tvBack == null)
            tvBack = (TextView) findViewById(R.id.head_tv_back);
        tvBack.setText(text);
        if(listener)
            layoutLeft.setOnClickListener(this);
    }

    public void setLeftBack(int res,int textId,boolean listener){
        this.setLeftBack(res,getString(textId),listener);
    }

    public void setLeftTextColor(int colorId){
        if(tvBack != null)
            tvBack.setTextColor(colorId);
    }

    public void setRightNext(int res,CharSequence text,boolean listener){
        LinearLayout layoutRight = (LinearLayout) findViewById(R.id.layout_right);
        ImageView imgRight = (ImageView) findViewById(R.id.head_img_right);
        if(imgRight != null)
            imgRight.setImageResource(res);
        if(tvRight == null)
            tvRight = (TextView) findViewById(R.id.head_tv_right);
        tvRight.setText(text);
        if(listener)
            layoutRight.setOnClickListener(this);
    }

    public void setRightNext(int res,int textId,boolean listener){
        this.setRightNext(res,getString(textId),listener);
    }

    public void setRightTextColor(int colorId){
        if(tvRight != null)
            tvRight.setTextColor(colorId);
    }

    public void showLoadingProgressDialog(DialogInterface.OnCancelListener onCancelListener){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this,onCancelListener);
        }
        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tvTitle != null)
            MobclickAgent.onPageStart(tvTitle.getText().toString());
        else
            MobclickAgent.onPageStart(getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(tvTitle != null)
            MobclickAgent.onPageEnd(tvTitle.getText().toString());
        else
            MobclickAgent.onPageEnd(getClass().getSimpleName());
        MobclickAgent.onPause(this);
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null)
            presenter.detachView();
    }
}