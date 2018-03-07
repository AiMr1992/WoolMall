package com.android.wool.view.activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.MyApplication;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.common.MyPreference;
import com.android.wool.presenter.MainPresenterImpl;
import com.android.wool.util.ImageUtils;
import com.android.wool.util.LogUtil;
import com.android.wool.util.SystemUtils;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.fragment.NaCartFragment;
import com.android.wool.view.fragment.FindFragment;
import com.android.wool.view.fragment.HomeFragment;
import com.android.wool.view.fragment.MineFragment;
import com.android.wool.view.fragment.NaTypeFragment;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnreadCountChangeListener;
import org.json.JSONObject;
import butterknife.BindView;
public class MainActivity extends BaseActivity<MainPresenterImpl> implements MainView{
    @BindView(R.id.frame_content)
    FrameLayout frameLayout;
    @BindView(R.id.layout_mine)
    RelativeLayout layoutMine;
    @BindView(R.id.na_layout)
    LinearLayout naLayout;
    @BindView(R.id.tv_point_order)
    TextView tvPoint;
    @BindView(R.id.layout_boot)
    LinearLayout layoutBoot;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_find)
    TextView tvFind;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.tv_mine)
    TextView tvMine;
    private HomeFragment homeFragment;
    private NaTypeFragment typeFragment;
    private FindFragment findFragment;
    private NaCartFragment cartFragment;
    private MineFragment mineFragment;

    public static final String TAG_EXIT = "exit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainPresenterImpl(this);
        initView();
    }

    @Override
    public void initView() {
        ImageUtils.clearCacheMemory();
        homeFragment = new HomeFragment();
        typeFragment = new NaTypeFragment();
        findFragment = new FindFragment();
        cartFragment = new NaCartFragment();
        mineFragment = new MineFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,homeFragment);
        fragmentTransaction.add(R.id.frame_content,typeFragment);
        fragmentTransaction.add(R.id.frame_content,findFragment);
        fragmentTransaction.add(R.id.frame_content,cartFragment);
        fragmentTransaction.add(R.id.frame_content,mineFragment);
        fragmentTransaction.commit();
        replaceFragment(homeFragment);

        tvHome.setOnClickListener(this);
        tvType.setOnClickListener(this);
        tvFind.setOnClickListener(this);
        tvCart.setOnClickListener(this);
        layoutMine.setOnClickListener(this);

        presenter.initCityData(this);
        addUnreadCountChangeListener(MyApplication.inMainProcess(this));
        PgyUpdateManager.register(this, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {
            }

            @Override
            public void onUpdateAvailable(String result) {
                LogUtil.d(result);
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject != null && jsonObject.has("data")){
                        JSONObject dataJson = jsonObject.getJSONObject("data");
                        if(dataJson != null && dataJson.has("downloadURL")){
                            //下载地址
                            final String downloadURL = dataJson.getString("downloadURL");
                            //替换内容
                            String releaseNote = dataJson.getString("releaseNote");
                            MyAlertDialog dialog = new MyAlertDialog(MainActivity.this);
                            dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            dialog.setRightButton(R.string.update_goto, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    Uri uri = Uri.parse(downloadURL);
                                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(it);
                                }
                            });
                            dialog.setTitle(R.string.update_app);
                            dialog.setMsg(releaseNote);
                            dialog.show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        paddingTop();
        SystemUtils.setStatusBarTransLight(this);

        tvHome.setSelected(true);
        tvType.setSelected(false);
        tvFind.setSelected(false);
        tvCart.setSelected(false);
        tvMine.setSelected(false);
    }

    private void paddingTop(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int height = SystemUtils.getStatusBarHeight(this);
            layoutBoot.setPadding(0,height,0,0);
        }
    }

    private void clearPaddingTop(){
        layoutBoot.setPadding(0,0,0,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        LogUtil.d("code:"+requestCode);
    }

    private void addUnreadCountChangeListener(boolean add) {
        Unicorn.addUnreadCountChangeListener(listener, add);
    }

    private UnreadCountChangeListener listener = new UnreadCountChangeListener() {
        @Override
        public void onUnreadCountChange(int count) {
            mineFragment.setTvPos(count);
            if(count == 0){
                tvPoint.setVisibility(View.GONE);
            }else {
                tvPoint.setVisibility(View.VISIBLE);
                if(count > 99)
                    tvPoint.setText("99+");
                else
                    tvPoint.setText(count+"");
            }
        }
    };

    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(homeFragment);
        fragmentTransaction.hide(typeFragment);
        fragmentTransaction.hide(findFragment);
        fragmentTransaction.hide(cartFragment);
        fragmentTransaction.hide(mineFragment);
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_home:
                replaceFragment(homeFragment);
                switchNaBottom(0);
                paddingTop();
                tvHome.setSelected(true);
                tvType.setSelected(false);
                tvFind.setSelected(false);
                tvCart.setSelected(false);
                tvMine.setSelected(false);
                break;
            case R.id.tv_type:
                replaceFragment(typeFragment);
                switchNaBottom(1);
                paddingTop();
                tvHome.setSelected(false);
                tvType.setSelected(true);
                tvFind.setSelected(false);
                tvCart.setSelected(false);
                tvMine.setSelected(false);
                break;
            case R.id.tv_find:
                replaceFragment(findFragment);
                switchNaBottom(2);
                paddingTop();
                tvHome.setSelected(false);
                tvType.setSelected(false);
                tvFind.setSelected(true);
                tvCart.setSelected(false);
                tvMine.setSelected(false);
                break;
            case R.id.tv_cart:
                replaceFragment(cartFragment);
                switchNaBottom(3);
                paddingTop();
                tvHome.setSelected(false);
                tvType.setSelected(false);
                tvFind.setSelected(false);
                tvCart.setSelected(true);
                tvMine.setSelected(false);
                break;
            case R.id.layout_mine:
                if(!TextUtils.isEmpty(MyPreference.getInstance().getUid(this))) {
                    replaceFragment(mineFragment);
                    switchNaBottom(4);
                    clearPaddingTop();
                    tvHome.setSelected(false);
                    tvType.setSelected(false);
                    tvFind.setSelected(false);
                    tvCart.setSelected(false);
                    tvMine.setSelected(true);
                }else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    intent.putExtra("login","false");
                    intent.putExtra("type","4");
                    startActivityForResult(intent,REQUEST_CODE);
                }
                break;
        }
    }

    @Override
    public void switchNaBottom(int pos) {
        SystemUtils.setStatusBarTransLight(this);
        if(pos == 4 && !TextUtils.isEmpty(MyPreference.getInstance().getUid(this))){
            tvPoint.setVisibility(View.INVISIBLE);
        }else {
            if(MyApplication.inMainProcess(this)){
                int count = Unicorn.getUnreadCount();
                if(count != 0){
                    tvPoint.setVisibility(View.VISIBLE);
                    if(count > 99)
                        tvPoint.setText("99+");
                    else
                        tvPoint.setText(count+"");
                }else {
                    tvPoint.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public final static int REQUEST_CODE = 500;
    public final static int RESULT_CODE = 200;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE == requestCode){
            if(RESULT_CODE == resultCode){
                String type = data.getStringExtra("type");
                if("4".equals(type) && !TextUtils.isEmpty(MyPreference.getInstance().getUid(this))){
                    replaceFragment(mineFragment);
                    switchNaBottom(4);
                    tvHome.setSelected(false);
                    tvType.setSelected(false);
                    tvFind.setSelected(false);
                    tvCart.setSelected(false);
                    tvMine.setSelected(true);
                    clearPaddingTop();
                }
            }
        }
    }

    private long exitTime = 0;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
                toast.show();
                exitTime = System.currentTimeMillis();
            } else {
                // 退出代码
                super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            boolean isExit = intent.getBooleanExtra(TAG_EXIT, false);
            String cart = intent.getStringExtra("cart");
            if (isExit) {
                this.finish();
            }else {
                if(!TextUtils.isEmpty(cart)){
                    replaceFragment(cartFragment);
                    switchNaBottom(3);
                    paddingTop();
                    tvHome.setSelected(false);
                    tvType.setSelected(false);
                    tvFind.setSelected(true);
                    tvCart.setSelected(false);
                    tvMine.setSelected(false);
                }else {
                    replaceFragment(homeFragment);
                    switchNaBottom(0);
                    paddingTop();
                    tvHome.setSelected(true);
                    tvType.setSelected(false);
                    tvFind.setSelected(false);
                    tvCart.setSelected(false);
                    tvMine.setSelected(false);
                    mineFragment.setMineImg();
                }
            }
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disMissProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopDBThread();
        PgyUpdateManager.unregister();
    }
}