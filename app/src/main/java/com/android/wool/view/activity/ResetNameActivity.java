package com.android.wool.view.activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.view.constom.MultiEditTextView;

import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class ResetNameActivity extends BaseActivity{
    @BindView(R.id.edit_pwd)
    MultiEditTextView editPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.update_name);
        tvLogin.setOnClickListener(this);
        editPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(editPwd.getText().toString())){
                    tvLogin.setEnabled(false);
                }else {
                    tvLogin.setEnabled(true);
                }
            }
        });
        editPwd.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
            case R.id.tv_login:
                EventBus.getDefault().post(new IoMessage(editPwd.getText().toString()));
                finish();
                break;
        }
    }
}
