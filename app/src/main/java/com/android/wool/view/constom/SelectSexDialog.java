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
 * Created by AiMr on 2017/9/23
 */
public class SelectSexDialog extends Dialog{
    private TextView tvName;
    private TextView tvNv;
    private TextView tvCancel;
    private OnCloseClickListener listener;
    private Window window;
    public SelectSexDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_selected_sex);
        tvName = (TextView) findViewById(R.id.tv_nan);
        tvNv = (TextView) findViewById(R.id.tv_nv);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onCloseClick(false,"",SelectSexDialog.this);
                dismiss();
            }
        });
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onCloseClick(true,"0",SelectSexDialog.this);
                dismiss();
            }
        });
        tvNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onCloseClick(true,"1",SelectSexDialog.this);
                dismiss();
            }
        });
        setCanceledOnTouchOutside(true);
    }


    public interface OnCloseClickListener{
        void onCloseClick(boolean flag,String text,Dialog dialog);
    }

    public void setOnCloseClickListener(OnCloseClickListener onCloseClickListener) {
        this.listener = onCloseClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        window = getWindow();
        window.setWindowAnimations(R.style.pop_anim_style);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }
}
