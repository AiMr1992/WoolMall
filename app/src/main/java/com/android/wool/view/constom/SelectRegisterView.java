package com.android.wool.view.constom;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.presenter.LoginPresenterImpl;
import com.android.wool.util.AppTools;
/**
 * Created by AiMr on 2017/9/22
 * 注册
 */
public class SelectRegisterView extends LinearLayout implements View.OnClickListener{
    private MultiEditTextView registerPhone;
    private MultiEditTextView registerCode;
    private TextView registerSend;
    private EditText registerPwd;
    private TextView tvRegister;
    private Context context;
    private LoginPresenterImpl loginPresenter;
    private Handler handler;
    private int seconds = 60;
    public SelectRegisterView(Context context) {
        this(context,null);
    }

    public SelectRegisterView(Context context,AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        handler = new Handler();
    }

    public void setLoginPresenter(LoginPresenterImpl loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(context).inflate(R.layout.layout_register,this);
        initView();
    }

    private void initView() {
        registerPhone = (MultiEditTextView) findViewById(R.id.register_phone);
        registerCode = (MultiEditTextView) findViewById(R.id.register_code);
        registerSend = (TextView) findViewById(R.id.register_send);
        registerPwd = (EditText) findViewById(R.id.register_pwd);
        tvRegister = (TextView) findViewById(R.id.tv_register);
        registerSend.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_send:
                if(TextUtils.isEmpty(registerPhone.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_phone_toast),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(registerPhone.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_phone_toast2),Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPresenter.sendMsg(registerPhone.getText().toString(),context,this);
                break;
            case R.id.tv_register:
                if(TextUtils.isEmpty(registerPhone.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_phone_toast),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(registerPhone.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_phone_toast2),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(registerCode.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.msg_hint),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(registerPwd.getText().toString())){
                    Toast.makeText(context,context.getString(R.string.login_pwd_toast),Toast.LENGTH_SHORT).show();
                    return;
                }
                loginPresenter.register(registerPhone.getText().toString(),
                        context,
                        registerCode.getText().toString(),
                        registerPwd.getText().toString());
                break;
        }
    }

    public void messageCountDown(){
        registerSend.setEnabled(false);
        seconds = 60;
        handler.post(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if(seconds >= 0){
                registerSend.setText(seconds+"s后重发");
                seconds --;
                handler.postDelayed(this,1000);
            }else{
                registerSend.setText(R.string.msg_code);
                registerSend.setEnabled(true);
            }
        }
    };

    public void remove(){
        if(handler != null)
            handler.removeCallbacks(run);
    }
}
