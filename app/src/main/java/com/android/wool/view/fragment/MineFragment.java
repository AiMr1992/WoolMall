package com.android.wool.view.fragment;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.base.BaseFragment;
import com.android.wool.common.MyPreference;
import com.android.wool.model.entity.MineEntity;
import com.android.wool.presenter.NaMinePresenterImpl;
import com.android.wool.util.ImageUtils;
import com.android.wool.util.SystemUtils;
import com.android.wool.view.activity.HouseListActivity;
import com.android.wool.view.activity.LocationListActivity;
import com.android.wool.view.activity.MsgListActivity;
import com.android.wool.view.activity.OrderListActivity;
import com.android.wool.view.activity.PersonalActivity;
import com.android.wool.view.activity.SettingActivity;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class MineFragment extends BaseFragment<NaMinePresenterImpl> implements MineView {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_msg)
    RelativeLayout tvMsg;
    @BindView(R.id.tv_house)
    TextView tvHouse;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_install)
    TextView tvInstall;
    @BindView(R.id.layout_personal)
    LinearLayout layoutPersonal;
    @BindView(R.id.round_view)
    ImageView roundImageView;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private MineEntity entity;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    private boolean first;
    @Override
    public void onResume() {
        super.onResume();
        if(first)
            presenter.getMine(getActivity());
    }

    public void setMineImg(){
        roundImageView.setImageResource(R.drawable.default_mine);
        tvCount.setVisibility(View.GONE);
    }

    @Override
    public void initView() {
        presenter = new NaMinePresenterImpl(this);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutPersonal.getLayoutParams();
        int headHeight;
        int statusHeight = SystemUtils.getStatusBarHeight(getActivity());
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            layoutPersonal.setPadding(0,statusHeight,0,0);
            headHeight = getResources().getDisplayMetrics().widthPixels * 300 / 750;
        }else{
            headHeight = getResources().getDisplayMetrics().widthPixels * 300 / 750 - statusHeight;
        }
        layoutParams.height = headHeight;
        layoutPersonal.setLayoutParams(layoutParams);
        roundImageView.setImageResource(R.drawable.default_mine);

        layoutPersonal.setOnClickListener(this);
        tvInstall.setOnClickListener(this);
        tvMsg.setOnClickListener(this);
        tvHouse.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void disMissProgress() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_personal:
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("entity",entity);
                startActivity(intent);
                break;
            case R.id.tv_install:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.tv_msg:
                startActivity(new Intent(getActivity(), MsgListActivity.class));
                break;
            case R.id.tv_house:
                startActivity(new Intent(getActivity(), HouseListActivity.class));
                break;
            case R.id.tv_address:
                startActivity(new Intent(getActivity(), LocationListActivity.class));
                break;
            case R.id.tv_order:
                startActivity(new Intent(getActivity(), OrderListActivity.class));
                break;
        }
    }

    @Override
    public void getMine(MineEntity entity) {
        this.entity = entity;
        ImageUtils.downloadQiNiuCropCircle(roundImageView.getWidth(),
                roundImageView.getHeight(),
                roundImageView, ImageView.ScaleType.FIT_XY,
                entity.getAvatar(),
                0, 0);
        tvName.setText(entity.getName());
    }

    public void setTvPos(int count){
        if(count == 0){
            tvCount.setVisibility(View.GONE);
        }else {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(count+"");
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden && !TextUtils.isEmpty(MyPreference.getInstance().getUid(getActivity()))){
            presenter.getMine(getActivity());
            first = true;
        }else {
            first = false;
        }
    }

    @Override
    public void init() {

    }
}