package com.android.wool.view.activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.base.VBaseFragment;
import com.android.wool.view.constom.tablayout.SlidingTabLayout;
import com.android.wool.view.constom.tablayout.listener.OnTabSelectListener;
import com.android.wool.view.fragment.OrderAllFragment;
import com.android.wool.view.fragment.OrderFinishFragment;
import com.android.wool.view.fragment.OrderNonePlayFragment;
import com.android.wool.view.fragment.OrderTackFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/25
 */
public class OrderListActivity extends BaseActivity implements OnTabSelectListener{
    @BindView(R.id.tab_layout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private MyPagerAdapter adapter;
    private List<VBaseFragment> mList;
    private String[] titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.mine_order);

        mList = new ArrayList<>();
        titles = getResources().getStringArray(R.array.order_list);
        mList.add(new OrderAllFragment());
        mList.add(new OrderNonePlayFragment());
        mList.add(new OrderTackFragment());
        mList.add(new OrderFinishFragment());
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setOnTabSelectListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mList.get(position).data();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

    @Override
    public void onTabSelect(int position) {
    }

    @Override
    public void onTabReselect(int position) {

    }
}