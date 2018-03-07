package com.android.wool.view.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.common.MyPreference;
import com.android.wool.util.GlideCacheUtil;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.constom.MyAlertDialog;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/25
 */
public class SettingActivity extends BaseActivity{
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_clear)
    LinearLayout tvClear;
    @BindView(R.id.tv_out)
    TextView tvOut;
    @BindView(R.id.tv_text)
    TextView tvText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.mine_install);
        tvAbout.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvOut.setOnClickListener(this);
        CacheSize();
    }

    private void CacheSize(){
        try {
            tvText.setText(GlideCacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void delete(){
        new Thread() {
            public void run() {
                Message msg = new Message();
                try {
                    GlideCacheUtil.clearAllCache(SettingActivity.this);
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ImageUtils.clearCacheMemory();
                Toast.makeText(SettingActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
                CacheSize();
            } else {
                Toast.makeText(SettingActivity.this,"清除失败",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
            case R.id.tv_clear:
                MyAlertDialog dialog = new MyAlertDialog(this);
                dialog.setMsg("是否清除缓存?");
                dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setRightButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        delete();
                    }
                });
                dialog.show();
                break;
            case R.id.tv_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.tv_out:
                Intent intent = new Intent(this,LoginActivity.class);
                intent.putExtra("close","close");
                startActivity(intent);
                MyPreference.getInstance().savePreferenceData(this,MyPreference.UID,"");
                MyPreference.getInstance().savePreferenceData(this,MyPreference.USER_TYPE,"");
                finish();
                break;
        }
    }
}