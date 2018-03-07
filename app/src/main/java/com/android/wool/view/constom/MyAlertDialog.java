package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
/**
 * Created by AiMr on 2017/12/14.
 */
public class MyAlertDialog extends Dialog {
    private TextView tvLeft,tvRight,tvCenter,tvMsg,tvTitle;
    private LinearLayout layoutSelect;
    public MyAlertDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_alert);

        tvLeft = (TextView) findViewById(R.id.tv_left);
        tvRight = (TextView) findViewById(R.id.tv_right);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        layoutSelect = (LinearLayout) findViewById(R.id.layout_select);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        tvTitle = (TextView) findViewById(R.id.tv_title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        setCanceledOnTouchOutside(false);
    }

    public void setLeftButton(String text, final OnClickListener listener){
        if(listener != null){
            layoutSelect.setVisibility(View.VISIBLE);
            tvLeft.setText(text);
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(MyAlertDialog.this,0);
                }
            });
        }
    }

    public void setLeftButton(int textId,final OnClickListener listener){
        this.setLeftButton(getContext().getString(textId),listener);
    }

    public void setRightButton(String text, final OnClickListener listener){
        if(listener != null){
            layoutSelect.setVisibility(View.VISIBLE);
            tvRight.setText(text);
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(MyAlertDialog.this,0);
                }
            });
        }
    }

    public void setRightButton(int textId,final OnClickListener listener){
        this.setRightButton(getContext().getString(textId),listener);
    }

    public void setCenterButton(String text, final OnClickListener listener){
        if(listener != null){
            tvCenter.setVisibility(View.VISIBLE);
            tvCenter.setText(text);
            tvCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(MyAlertDialog.this,0);
                }
            });
        }
    }

    public void setCenterButton(int textId, final OnClickListener listener){
        this.setCenterButton(getContext().getString(textId),listener);
    }

    public void setTitle(String text){
        if(!TextUtils.isEmpty(text)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(text);
        }
    }

    public void setTitle(int textId){
        this.setTitle(getContext().getString(textId));
    }

    public void setMsg(String text){
        if(!TextUtils.isEmpty(text)) {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(text);
        }
    }

    public void setMsg(int textId){
        this.setMsg(getContext().getString(textId));
    }
}
