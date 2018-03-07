package com.android.wool.view.activity;
import android.os.Bundle;
import android.view.View;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
/**
 * Created by AiMr on 2017/9/25
 */
public class AboutActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.about);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activy_about;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
        }
    }
}
