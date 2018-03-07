package com.android.wool.view.activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.presenter.ResetPresenterImpl;
import com.android.wool.view.constom.MultiEditTextView;

import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/22
 */
public class ResetPwdActivity extends BaseActivity<ResetPresenterImpl> implements ResetPwdView{
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.edit_pwd)
    MultiEditTextView editPwd;
    @BindView(R.id.edit_pwd2)
    MultiEditTextView editPwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ResetPresenterImpl(this);
        initView();
    }

    @Override
    public void initView() {
        setLeftBack(R.drawable.close,"",true);
        setTitle(R.string.reset_password);
        tvCommit.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_commit:
                if(TextUtils.isEmpty(editPwd.getText().toString()) || TextUtils.isEmpty(editPwd2.getText().toString())){
                    Toast.makeText(this,R.string.login_pwd_toast,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!TextUtils.equals(editPwd.getText().toString(),editPwd2.getText().toString())){
                    Toast.makeText(this,R.string.pwd_not,Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.commit(
                        getIntent().getStringExtra("phone"),
                        editPwd.getText().toString(),
                        editPwd2.getText().toString(),
                        this);
                break;
            case R.id.layout_left:
                finish();
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
    public void commitSuccess() {
        finish();
    }
}
