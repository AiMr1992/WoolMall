package com.android.wool.view.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.WindowManager;
import com.android.wool.R;
import com.android.wool.common.MyPreference;
/**
 * Created by AiMr on 2017/9/22
 */
public class SplashActivity extends Activity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler.post(runnable);
    }

    private int time = 2;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(time > 0){
                time --;
                handler.postDelayed(this,1000);
            }else {
                splashActivity();
                finish();
            }
        }
    };

    private void splashActivity(){
        if(TextUtils.isEmpty(MyPreference.getInstance().getUid(this))) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}