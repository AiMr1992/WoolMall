package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.wool.R;
/**
 * 弹出对话框，有提示和输入密码两种形式
 */
public class ShareDialog extends Dialog implements View.OnClickListener{
    private TextView tvQq;
    private TextView tvWx;
    private TextView tvSina;
    private TextView tvCancel;
    public ShareDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_share);

        tvQq = (TextView) findViewById(R.id.tv_qq);
        tvWx = (TextView) findViewById(R.id.tv_wx);
        tvSina = (TextView) findViewById(R.id.tv_sina);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        tvQq.setOnClickListener(this);
        tvWx.setOnClickListener(this);
        tvSina.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setWindowAnimations(R.style.pop_anim_style);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_qq:
                if(listener != null){
                    listener.onclick(QQ,this);
                }
                break;
            case R.id.tv_wx:
                if(listener != null){
                    listener.onclick(WX,this);
                }
                break;
            case R.id.tv_sina:
                if(listener != null){
                    listener.onclick(SINA,this);
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public void setShareListener(OnShareListener listener) {
        this.listener = listener;
    }
    public OnShareListener listener;
    public static final int QQ = 0;
    public static final int WX = 1;
    public static final int SINA = 2;
    public interface OnShareListener{
        void onclick(int type,Dialog dialog);
    }
}