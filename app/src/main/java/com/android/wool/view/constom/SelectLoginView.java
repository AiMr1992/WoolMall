package com.android.wool.view.constom;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.common.MyPreference;
import com.android.wool.presenter.LoginPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.activity.RetrieveActivity;
/**
 * Created by AiMr on 2017/9/22
 * 登陆页面
 */
public class SelectLoginView extends LinearLayout implements View.OnClickListener{
    private MultiEditTextView loginPhone;
    private MultiEditTextView loginPwd;
    private TextView tvLogin;
    private TextView tvPwd;
    private Context context;
    private LoginPresenterImpl loginPresenter;
    public SelectLoginView(Context context) {
        this(context,null);
    }

    public SelectLoginView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setLoginPresenter(LoginPresenterImpl loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    private void initView(){
        loginPhone = (MultiEditTextView) findViewById(R.id.login_phone);
        loginPwd = (MultiEditTextView) findViewById(R.id.login_pwd);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        tvPwd = (TextView) findViewById(R.id.tv_pwd);
        tvLogin.setOnClickListener(this);
        tvPwd.setOnClickListener(this);

        String phone = MyPreference.getInstance().getPreferenceData(getContext(),MyPreference.PHONE);
        if(!TextUtils.isEmpty(phone))
            loginPhone.setText(phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                if(TextUtils.isEmpty(loginPhone.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_phone_toast),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(loginPhone.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_phone_toast),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(loginPwd.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_pwd_toast),Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPresenter.login(loginPhone.getText().toString(),loginPwd.getText().toString(),context);
                break;
            case R.id.tv_pwd:
                Intent intent = new Intent(context,RetrieveActivity.class);
                intent.putExtra("phone",loginPhone.getText().toString());
                context.startActivity(intent);
                break;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(context).inflate(R.layout.layout_login,this);
        initView();
    }
}