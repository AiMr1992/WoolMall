package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.presenter.RetrievePresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.constom.MultiEditTextView;

import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/22
 */
public class RetrieveActivity extends BaseActivity<RetrievePresenterImpl> implements RetrieveView{
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.edit_phone)
    MultiEditTextView editPhone;
    @BindView(R.id.edit_msg)
    MultiEditTextView editMsg;
    private Handler handler;
    private int seconds = 60;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new RetrievePresenterImpl(this);
        initView();
    }

    @Override
    public void initView() {
        setLeftBack(R.drawable.close,"",true);
        setTitle(R.string.retrieve_pwd);
        String phone = getIntent().getStringExtra("phone");
        editPhone.setText(TextUtils.isEmpty(phone)?"":phone);
        handler = new Handler();

        tvCommit.setOnClickListener(this);
        tvMsg.setOnClickListener(this);
    }

    @Override
    public void messageCountDown(){
        tvMsg.setEnabled(false);
        seconds = 60;
        handler.post(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if(seconds >= 0){
                tvMsg.setText(seconds+"s后重发");
                seconds --;
                handler.postDelayed(this,1000);
            }else{
                tvMsg.setText(R.string.msg_code);
                tvMsg.setEnabled(true);
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_retrieve;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:
                if(TextUtils.isEmpty(editPhone.getText().toString())){
                    Toast.makeText(this,R.string.login_phone_toast,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(editPhone.getText().toString())){
                    Toast.makeText(this,R.string.login_phone_toast2,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editMsg.getText().toString())){
                    Toast.makeText(this,R.string.msg_hint,Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.commit(editPhone.getText().toString(),editMsg.getText().toString(),this);
                break;
            case R.id.layout_left:
                finish();
                break;
            case R.id.tv_msg:
                if(TextUtils.isEmpty(editPhone.getText().toString())){
                    Toast.makeText(this,R.string.login_phone_toast,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(editPhone.getText().toString())){
                    Toast.makeText(this,R.string.login_phone_toast2,Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.sendMsg(editPhone.getText().toString(),"forget",this);
                break;
        }
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null)
            handler.removeCallbacks(null);
    }

    @Override
    public void startResetActivity() {
        Intent intent = new Intent(this,ResetPwdActivity.class);
        intent.putExtra("phone",editPhone.getText().toString());
        startActivity(intent);
        finish();
    }
}
