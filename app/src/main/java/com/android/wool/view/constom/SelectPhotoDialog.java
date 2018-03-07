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
public class SelectPhotoDialog extends Dialog{
    private TextView tvPz;
    private TextView tvXc;
    private TextView tvCancel;
    public static final int PHOTO_PX = 0 ;
    public static final int PHOTO_XC = 1 ;
    public static final int PHOTO_NOT_SELECT = -1 ;
    private Window window;
    private OnCloseClickListener listener;
    public SelectPhotoDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_selected_photo);
        tvPz = (TextView) findViewById(R.id.tv_pz);
        tvXc = (TextView) findViewById(R.id.tv_location);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onCloseClick(false,PHOTO_NOT_SELECT,SelectPhotoDialog.this);
                dismiss();
            }
        });
        tvPz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onCloseClick(true,PHOTO_PX,SelectPhotoDialog.this);
            }
        });
        tvXc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onCloseClick(true,PHOTO_XC,SelectPhotoDialog.this);
            }
        });
        setCanceledOnTouchOutside(true);
    }


    public interface OnCloseClickListener{
        void onCloseClick(boolean flag, int selectType, Dialog dialog);
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
