package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import com.android.wool.R;
/**
 * Created by AiMr on 2017/9/21.
 */
public class ProgressDialog extends Dialog {
    public ProgressDialog(Context context, OnCancelListener onCancelListener){
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_loading);

        if(onCancelListener != null) {
            setCancelable(true);
            setOnCancelListener(onCancelListener);
        }else{
            setCancelable(false);
        }
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
