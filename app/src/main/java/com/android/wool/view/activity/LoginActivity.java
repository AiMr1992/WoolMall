package com.android.wool.view.activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.common.MyPreference;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.presenter.LoginPresenterImpl;
import com.android.wool.util.SystemUtils;
import com.android.wool.view.constom.SelectLoginView;
import com.android.wool.view.constom.SelectRegisterView;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/22
 */
public class LoginActivity extends BaseActivity<LoginPresenterImpl> implements LoginView{
    @BindView(R.id.head_left)
    RelativeLayout headLeft;
    @BindView(R.id.head_right)
    RelativeLayout headRight;
    @BindView(R.id.view_left)
    View viewLeft;
    @BindView(R.id.view_right)
    View viewRight;
    @BindView(R.id.selectLoginView)
    SelectLoginView selectLoginView;
    @BindView(R.id.selectRegisterView)
    SelectRegisterView selectRegisterView;
    @BindView(R.id.tv_browse)
    TextView tvBrowse;
    @BindView(R.id.register_label)
    TextView registerLabel;
    @BindView(R.id.login_left)
    TextView loginLeft;
    @BindView(R.id.login_right)
    TextView loginRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LoginPresenterImpl(this);
        initView();
    }

    @Override
    public void startMainActivity() {
        String login = getIntent().getStringExtra("login");
        if(!TextUtils.isEmpty(login)){
            if(!TextUtils.isEmpty(MyPreference.getInstance().getUid(this)) && "true".equals(login)) {
                EventBus.getDefault().post(new IoMessage());
            }
            String type = getIntent().getStringExtra("type");
            if(!TextUtils.isEmpty(type)){
                Intent intent = new Intent();
                intent.putExtra("type",type);
                setResult(MainActivity.RESULT_CODE,intent);
            }
            finish();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.TAG_EXIT, false);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void initView() {
        SystemUtils.setStatusBarTransDark(this);
        ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.app_black));
        SpannableString spannableString = new SpannableString(getString(R.string.register_label));
        spannableString.setSpan(fcs,6,spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        registerLabel.setText(spannableString);

        selectLoginView.setLoginPresenter(presenter);
        selectRegisterView.setLoginPresenter(presenter);
        headLeft.setOnClickListener(this);
        headRight.setOnClickListener(this);
        tvBrowse.setOnClickListener(this);
        showLoginView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                showLoginView();
                break;
            case R.id.head_right:
                showRegisterView();
                break;
            case R.id.tv_browse:
                startMainActivity();
                break;
        }
    }

    @Override
    public void showLoginView() {
        viewLeft.setVisibility(View.VISIBLE);
        viewRight.setVisibility(View.GONE);
        selectLoginView.setVisibility(View.VISIBLE);
        selectRegisterView.setVisibility(View.GONE);
        viewLeft.setEnabled(false);
        viewRight.setEnabled(true);
        tvBrowse.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRegisterView() {
        viewLeft.setVisibility(View.GONE);
        viewRight.setVisibility(View.VISIBLE);
        selectLoginView.setVisibility(View.GONE);
        selectRegisterView.setVisibility(View.VISIBLE);
        viewLeft.setEnabled(true);
        viewRight.setEnabled(false);
        tvBrowse.setVisibility(View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if("close".equals(getIntent().getStringExtra("close"))){
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra(MainActivity.TAG_EXIT, true);
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
