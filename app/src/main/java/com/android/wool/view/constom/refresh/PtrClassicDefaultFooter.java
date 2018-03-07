package com.android.wool.view.constom.refresh;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.wool.R;
public class PtrClassicDefaultFooter extends FrameLayout implements PtrUIHandler {
    protected TextView mTitleTextView;
    private View mProgressBar;
    private boolean flag;
    public PtrClassicDefaultFooter(Context context) {
        super(context);
        initViews();
    }

    public PtrClassicDefaultFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrClassicDefaultFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    protected void initViews() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.cube_ptr_classic_default_footer, this);
        mTitleTextView = (TextView) header.findViewById(R.id.ptr_classic_header_rotate_view_header_title);
        mProgressBar = header.findViewById(R.id.ptr_classic_header_rotate_view_progressbar);

        resetView();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName() + "footer");
    }

    private void resetView() {
        mProgressBar.setVisibility(GONE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mProgressBar.setVisibility(GONE);
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up_to_load));
        } else {
            if(flag){
                mTitleTextView.setText(getResources().getString(R.string.load_finish));
            }else {
                mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up));
            }
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mTitleTextView.setVisibility(VISIBLE);
        if(flag){
            mProgressBar.setVisibility(GONE);
            mTitleTextView.setText(getResources().getString(R.string.load_finish));
        }else {
            mProgressBar.setVisibility(VISIBLE);
            mTitleTextView.setText(R.string.cube_ptr_loading);
        }
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame, boolean isHeader) {
        if(isHeader){
            return;
        }
        mProgressBar.setVisibility(GONE);
        mTitleTextView.setVisibility(VISIBLE);
        if(flag){
            mTitleTextView.setText(getResources().getString(R.string.load_finish));
        }else {
            mTitleTextView.setText(R.string.cube_ptr_loading);
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView.setVisibility(VISIBLE);
            if(flag){
                mTitleTextView.setText(R.string.load_finish);
            }else {
                mTitleTextView.setText(R.string.cube_ptr_release_to_load);
            }
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up_to_load));
        } else {
            if(flag){
                mTitleTextView.setText(R.string.load_finish);
            }else {
                mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_up));
            }
        }
    }
}