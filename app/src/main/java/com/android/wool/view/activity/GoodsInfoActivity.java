package com.android.wool.view.activity;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.MyApplication;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.common.MyPreference;
import com.android.wool.common.UMEventUtils;
import com.android.wool.model.entity.GoodsInfoEntity;
import com.android.wool.model.entity.SpecEntity;
import com.android.wool.presenter.GoodsInfoPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.util.BannerImageLoader;
import com.android.wool.util.HtmlFormat;
import com.android.wool.util.ShowShareUtil;
import com.android.wool.util.SystemUtils;
import com.android.wool.view.adapter.IntroAdapter;
import com.android.wool.view.constom.GoodsInfoDialog;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.SelectSpecDialog;
import com.lzy.widget.vertical.VerticalWebView;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.umeng.socialize.UMShareAPI;
import com.youth.banner.Banner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/10/13
 */
public class GoodsInfoActivity extends BaseActivity<GoodsInfoPresenterImpl> implements GoodsInfoView{
    @BindView(R.id.web_view)
    VerticalWebView webView;
    @BindView(R.id.grid_view)
    GridView gridView;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_old_price)
    TextView tvOldPrice;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.back_img)
    ImageView layoutBack;
    @BindView(R.id.tv_house)
    ImageView tvHouse;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_cart)
    TextView tvCart;
    @BindView(R.id.layout_cart)
    RelativeLayout layoutCart;
    @BindView(R.id.tv_spec)
    TextView tvSpec;
    @BindView(R.id.tv_share)
    ImageView ImgShare;
    @BindView(R.id.tv_parameter)
    TextView tvParameter;
    @BindView(R.id.add_house)
    TextView addHouse;
    @BindView(R.id.layout_im)
    LinearLayout layoutIm;
    private SelectSpecDialog dialog;

    private boolean house;
    private IntroAdapter adapter;
    private GoodsInfoEntity entity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GoodsInfoPresenterImpl(this);

        int w = getResources().getDisplayMetrics().widthPixels;
        int h = w*562/750;
        LinearLayout.LayoutParams layoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,h);
        banner.setLayoutParams(layoutParams);
        banner.isAutoPlay(false);
        banner.setImageLoader(new BannerImageLoader());
        adapter = new IntroAdapter(this);

        gridView.setAdapter(adapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        layoutBack.setOnClickListener(this);
        layoutCart.setOnClickListener(this);
        tvSpec.setOnClickListener(this);
        ImgShare.setOnClickListener(this);
        tvParameter.setOnClickListener(this);
        tvHouse.setOnClickListener(this);
        addHouse.setOnClickListener(this);
        layoutIm.setOnClickListener(this);
        ImgShare.setOnClickListener(this);
        SystemUtils.setStatusBarTransLight(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getGoodsInfo(this,getIntent().getStringExtra("id"));
    }

    private String user_code = "";
    @Override
    public void getGoodsInfo(final GoodsInfoEntity entity, String cart_num,String user_code) {
        this.user_code = user_code;
        if(entity != null) {
            this.entity = entity;
            if("0".equals(entity.getIs_collection())){
                house = false;
                houseGoods(house);
            }else {
                house = true;
                houseGoods(house);
            }
            adapter.resetData(entity.getIntro());
            tvName.setText(entity.getTitle());
            tvPrice.setText(String.format(getString(R.string.rmb_none), entity.getPrice()));
            tvOldPrice.setText(String.format(getString(R.string.rmb_none), entity.getMarket_price()));
            tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvNum.setText(String.format(getString(R.string.num), entity.getSales_num()));
            String number = AppTools.getPotNumber(cart_num);
            if (TextUtils.isEmpty(number)) {
                tvCart.setVisibility(View.GONE);
            } else {
                tvCart.setText(number);
                tvCart.setVisibility(View.VISIBLE);
            }
            if(entity.getChild() != null) {
                banner.update(entity.getChild());
            }
            webView.loadData(HtmlFormat.getNewContent(entity.getContent()), "text/html; charset=UTF-8", null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_img:
                finish();
                break;
            case R.id.layout_cart:
                if(!TextUtils.isEmpty(MyPreference.getInstance().getUid(this))) {
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("cart","true");
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("login","false");
                    startActivity(intent);
                }
                break;
            case R.id.tv_spec:
            case R.id.add_house:
                if(entity != null) {
                    if (dialog == null) {
                        dialog = new SelectSpecDialog(this);
                        dialog.setButtonCenterListener(new SelectSpecDialog.OnButtonCenterListener() {
                            @Override
                            public void selectGoods(String goodsId, String goodsNum, String specArray) {
                                dialog.dismiss();
                                if (!TextUtils.isEmpty(MyPreference.getInstance().getUid(GoodsInfoActivity.this))) {
                                    showLoading();
                                    presenter.joinCart(GoodsInfoActivity.this, goodsId, goodsNum, specArray);

                                    Map<String,String> map = new HashMap();
                                    map.put(entity.getTitle(),"商品id:"+goodsId+" 数量:"+goodsNum+" 规格:"+specArray);
                                    UMEventUtils.pushEvent(GoodsInfoActivity.this,"加入购物车",map);
                                } else {
                                    Intent intent = new Intent(GoodsInfoActivity.this, LoginActivity.class);
                                    intent.putExtra("login", "false");
                                    startActivity(intent);
                                }
                            }
                        });
                        dialog.setData(entity,presenter,entity.getId());
                    }
                }
                if(!dialog.isShowing())
                    dialog.show();
                break;
            case R.id.tv_parameter:
                if(entity != null){
                    GoodsInfoDialog dialog = new GoodsInfoDialog(this);
                    dialog.setData(entity.getProducts());
                    dialog.show();
                }
                break;
            case R.id.tv_house:
                if(!TextUtils.isEmpty(MyPreference.getInstance().getUid(this))){
                    if(entity != null) {
                        if(house)
                            presenter.update(this,entity.getId(),"del");
                        else
                            presenter.update(this,entity.getId(),"add");
                    }
                }else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("login","false");
                    startActivity(intent);
                }
                break;
            case R.id.layout_im:
                if(!TextUtils.isEmpty(MyPreference.getInstance().getUid(this))){
                    if (MyApplication.inMainProcess(GoodsInfoActivity.this)) {
                        ConsultSource source = new ConsultSource("", "商品详情", "");
                        Unicorn.openServiceActivity(GoodsInfoActivity.this, "客服", source);
                    }
                }else {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("login","false");
                    startActivity(intent);
                }
                break;
            case R.id.tv_share:
                if (Build.VERSION.SDK_INT >= 23){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED){
                        if (entity != null) {
                            ShowShareUtil shareUtil = new ShowShareUtil(this);
                            shareUtil.setShareContent(entity.getTitle(), user_code, " ", entity.getChild().get(0));
                            shareUtil.show();
                        }
                    }else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                101);
                    }
                }else {
                    if (entity != null) {
                        ShowShareUtil shareUtil = new ShowShareUtil(this);
                        shareUtil.setShareContent(entity.getTitle(), user_code, " ", entity.getChild().get(0));
                        shareUtil.show();
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    MyAlertDialog dialog = new MyAlertDialog(this);
                    dialog.setMsg(getString(R.string.permissions_wr));
                    dialog.setCenterButton(R.string.center_y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
            if (requestCode == 101) {
                ShowShareUtil shareUtil = new ShowShareUtil(this);
                shareUtil.setShareContent(entity.getTitle(), user_code, " ", entity.getChild().get(0));
                shareUtil.show();
            }
        }
    }

    @Override
    public void getSpecList(List<SpecEntity> list) {
        if(dialog != null){
            dialog.setSpecList(list);
        }
    }

    @Override
    public void houseGoods(boolean flag) {
        if(flag){
            tvHouse.setImageResource(R.drawable.house_goods);
        }else {
            tvHouse.setImageResource(R.drawable.img_house);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void goods() {
        house = !house;
        Toast.makeText(this,house?"收藏成功":"取消收藏",Toast.LENGTH_SHORT).show();
        houseGoods(house);
    }

    @Override
    public void addCart(String number) {
        String count = AppTools.getPotNumber(number);
        if (TextUtils.isEmpty(count)) {
            tvCart.setVisibility(View.GONE);
        } else {
            tvCart.setText(count);
            tvCart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);//完成
    }
}