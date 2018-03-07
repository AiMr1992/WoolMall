package com.android.wool.view.constom;
/**
 * Created by zhenglong on 2015/12/28.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import com.android.wool.R;
public class SlideView extends LinearLayout {
    private static final int TAN = 2;
    private int mHolderWidth;
    private float mLastX = 0;
    private float mLastY = 0;
    private LinearLayout mViewContent;
    private Scroller mScroller;
    private TextView textDelete;//删除按钮
    private TextView textEdit;//编辑按钮
    private OnSlideButtonClick onSlideButtonClick;

    public SlideView(Context context) {
        super(context);
        initView();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        mScroller = new Scroller(getContext());
        LayoutInflater.from(getContext()).inflate(
                getResources().getLayout(R.layout.slide_view_merge), this);
        mViewContent = (LinearLayout) findViewById(R.id.view_content);
        mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
                        .getDisplayMetrics()));
        textDelete = (TextView)findViewById(R.id.slide_delete);
        textEdit = (TextView)findViewById(R.id.slide_edt);
    }

    public void setContentView(View view) {
        if (mViewContent != null) {
            mViewContent.addView(view);
        }
    }

    public void setmLastXY(float mLastX,float mLastY) {
        this.mLastX = mLastX;
        this.mLastY = mLastY;
    }

    /**
     * 是否显示删除按钮
     */
    public void showSlideButton(boolean showDelete,boolean showEdit,OnSlideButtonClick
            onSlideButtonClick){
        this.onSlideButtonClick = onSlideButtonClick;
        int slideWidth = 0;
        if(showDelete){
            textDelete.setVisibility(View.VISIBLE);
            slideWidth += Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                            .getDisplayMetrics()));
//            textDelete.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(onSlideButtonClick != null){
//                        onSlideButtonClick.onDeleteClick();
//                    }
//                }
//            });
        }
        if(showEdit){
            textEdit.setVisibility(View.VISIBLE);
            slideWidth += Math.round(TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 80, getResources()
                            .getDisplayMetrics()));
//            textEdit.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(onSlideButtonClick != null){
//                        onSlideButtonClick.onEditClick();
//                    }
//                }
//            });
        }
        mHolderWidth = slideWidth;
        findViewById(R.id.holder).setLayoutParams(new LayoutParams(
                mHolderWidth,
                LayoutParams.MATCH_PARENT
        ));
    }

    public void reset() {
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        smoothScrollTo(0, 0);
    }

    /**
     * 设置打开或关闭动作，打开返回true关闭返回false
     * @return
     */
    public boolean adjust() {
        int offset = getScrollX();
        if (offset == 0) {
            return false;
        }
        if (offset < mHolderWidth/2) {
            this.smoothScrollTo(0, 0);
            return false;
        } else{
            this.smoothScrollTo(mHolderWidth, 0);
            return  true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }
    float downX;
    float downY;
    private boolean deleteClick = false;
    private boolean editClick = false;
    private long currentTime;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentTime = System.currentTimeMillis();
                downX = event.getX();
                downY = event.getY();
                int[] location = new  int[2] ;
                textDelete.getLocationInWindow(location);
                if(location[0] < downX &&
                        downX < location[0] + textDelete.getWidth()){
                    deleteClick = true;
                }else{
                    deleteClick = false;
                }
                textEdit.getLocationInWindow(location);
                if(location[0] < downX &&
                        downX < location[0] + textEdit.getWidth()){
                    editClick = true;
                }else{
                    editClick = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                float deltaX = x - mLastX;
                float delatY = y - mLastY;
                mLastX = x;
                mLastY = y;
                if (Math.abs(deltaX) < Math.abs(delatY) * TAN) {
                    break;
                }
                if (deltaX != 0) {
                    float newScrollX = getScrollX() - deltaX;
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > mHolderWidth) {
                        newScrollX = mHolderWidth;
                    }
                    this.scrollTo((int) newScrollX, 0);
                }
                setPressed(false);
                dispatchSetPressed(false);
                if(Math.abs(x - downX) > 5){
                    deleteClick = false;
                    editClick = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                dispatchSetPressed(false);
                setPressed(false);
                location = new  int[2] ;
                textDelete.getLocationInWindow(location);
                if(event.getX() > location[0] && deleteClick &&
                        event.getX() < location[0] + textDelete.getWidth()){
                    if(onSlideButtonClick != null){
                        onSlideButtonClick.onDeleteClick();
                        smoothScrollTo(0, 0);
                    }
                }
                textEdit.getLocationInWindow(location);
                if(event.getX() > location[0] && editClick &&
                        event.getX() < location[0] + textEdit.getWidth()){
                    if(onSlideButtonClick != null){
                        onSlideButtonClick.onEditClick();
                        smoothScrollTo(0, 0);
                    }
                }
                break;
        }
        return false;
    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public interface OnSlideButtonClick{
        void onDeleteClick();
        void onEditClick();
    }
}

