package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.util.AppTools;

/**
 * Created by AiMr on 2017/9/21.
 */
public class UpLoadProgressDialog extends Dialog {
    private TextView textView;
    public UpLoadProgressDialog(Context context, OnCancelListener onCancelListener){
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_file_load);

        if(onCancelListener != null) {
            setCancelable(true);
            setOnCancelListener(onCancelListener);
        }else{
            setCancelable(false);
        }
    }

    public void setProgress(double progress){
        if(textView == null)
            textView = (TextView) findViewById(R.id.tip_Tv);
        textView.setText("正在上传..."+ AppTools.getScaleNumber(progress)+"%");
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
}