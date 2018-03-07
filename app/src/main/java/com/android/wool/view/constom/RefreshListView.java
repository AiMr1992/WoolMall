package com.android.wool.view.constom;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.util.AppTools;

/**
 * Created by zhenglong on 2015/12/10.
 *
 * 上拉或下拉刷新listview
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener{

    //是否开启下拉刷新功能
    private boolean refreshDownOn = true;
    //是否开启上拉加载更多功能
    protected boolean refreshUpOn = true;
    protected boolean canDownRefresh = false;//是否可以下拉
    protected boolean canUpRefresh = false;//是否可以上拉
    //手指按下的位置
    private float touchY;
    private float touchX;
    //下拉，可刷新，正在刷新，刷新结束状态定义
    protected static final int STATUS_DONE = 1;//无状态，可下拉或上拉
    protected static final int STATUS_PULL_TO_REFRESH = 2;//正在下拉刷新状态
    protected static final int STATUS_RELEASE_TO_REFRESH = 3;//松开可刷新状态
    protected static final int STATUS_REFRESHING = 4;//正在刷新状态
    protected static final int STATUS_SLIDE = 5;//侧滑状态
    protected static final int STATUS_SCROLL = 6;//滚动状态，可上拉下拉不可侧滑
    //当前是上拉还是下拉
    protected static final int MODE_DOWN = 1;//下拉模式
    protected static final int MODE_UP = 2;//上拉模式
    protected static final int MODE_SLIDE = 3;//侧滑模式

    //当前状态
    protected int current_status = STATUS_DONE;
    protected int current_mode = MODE_DOWN;
    //listview头部
    private View headView;
    private TextView headTextView;
    private ProgressBar headProgressBar;
    private View footView;
    private TextView footTextView;
    private ProgressBar footProgressBar;
    //头部和底部的高度
    protected int refreshHeight;
    //刷新监听
    protected OnRefreshListener onRefreshListener;
    //是否开启侧滑功能
    private boolean openSlide;
    private SlideView slideView;
    private TouchMoveListener touchMoveListener;

    //是否上拉加载完数据
    private boolean loadFinish;
    /**
     * 侧滑判断使用
     */
//    private float moveX;
//    private float moveY;

    private OnListScrollListener onListScrollListener;

    public RefreshListView(Context context) {
        super(context);
        setOnScrollListener(this);
        setSelector(R.color.transparent);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollListener(this);
        setSelector(R.color.transparent);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        initView();
    }

    /**
     * 设置刷新监听
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnListScrollListener(OnListScrollListener onListScrollListener) {
        this.onListScrollListener = onListScrollListener;
    }

    /**
     * 设置是否可侧滑
     * @return
     */
    public boolean isOpenSlide() {
        return openSlide;
    }

    public void setOpenSlide(boolean openSlide) {
        this.openSlide = openSlide;
    }

    /** 是否加载完数据 */
    public void setLoadFinish(boolean loadFinish) {
        this.loadFinish = loadFinish;
    }

    /**
     * 是否开启上拉下拉功能
     * @param refreshDownOn
     * @param refreshUpOn
     */
    public void setRefreshOn(boolean refreshDownOn,boolean refreshUpOn){
        this.refreshDownOn = refreshDownOn;
        this.refreshUpOn = refreshUpOn;
        if(!refreshDownOn){
            if(getHeaderViewsCount() > 0){
                removeHeaderView(headView);
            }
        }
        if(!refreshUpOn){
            if(getFooterViewsCount() > 0){
                removeHeaderView(footView);
            }
        }
    }

    /**
     * 初始化view，加入header和footer
     */
    protected void initView(){
        refreshHeight = AppTools.turnDipToPx(50,getContext());
        headView = LayoutInflater.from(getContext()).inflate(R.layout.view_refresh_head,null);
        headTextView = (TextView)headView.findViewById(R.id.text_status);
        headProgressBar = (ProgressBar)headView.findViewById(R.id.progress);
        headView.setPadding(0, -1 * refreshHeight, 0, 0);
        addHeaderView(headView);

        initFooterView();
    }

    public void setHeadViewBg(int drawable){
        if(headView != null){
            headView.setBackgroundResource(drawable);
        }
    }

    public void initFooterView(){
        footView = LayoutInflater.from(getContext()).inflate(R.layout.view_refresh_head,null);
        footTextView = (TextView)footView.findViewById(R.id.text_status);
        footProgressBar = (ProgressBar)footView.findViewById(R.id.progress);
        footView.setPadding(0, 0, 0, -1 * refreshHeight);
        addFooterView(footView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        LogUtil.d("dispatchTouchEvent--->"+ev.getAction()+",current_status---->"+current_status);
//        if(current_status == STATUS_REFRESHING){
//            return super.dispatchTouchEvent(ev);
//        }
//        LogUtil.d("action-->"+ev.getAction()+",x-->"+ev.getX()+",y-->"+ev.getY());
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(current_status == STATUS_DONE) {
                    touchY = ev.getY();
                    touchX = ev.getX();
                }else if(openSlide && current_status == STATUS_SLIDE){
                    //点中的是当前view什么也不做，点中的不是则还原
                    int position = pointToPosition((int)ev.getX(),(int)ev.getY());
                    View view = getChildAt(position - getFirstVisiblePosition());
                    touchY = ev.getY();
                    touchX = ev.getX();
                    if (slideView != null && slideView != view) {
                        slideView.reset();
                        current_status = STATUS_DONE;
                        current_mode = MODE_DOWN;
                    }else if(slideView != null){
                        slideView.setmLastXY(touchX,touchY);
                        slideView.onTouchEvent(ev);
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float cy = ev.getY();
                float cx = ev.getX();
                if(current_status == STATUS_DONE || current_status == STATUS_SCROLL){
//                    if(Math.abs(cx - touchX) > 0 || Math.abs(cy - touchY) > 0){
//                        if(current_status != STATUS_SCROLL) {//可侧滑
//                            if(moveX == 0 && moveY == 0){
//                                moveX = ev.getX();
//                                moveY = ev.getY();
//                            }
                    if (Math.abs(cx - touchX) > 5 && Math.abs(cx - touchX) > Math.abs(cy - touchY)) {//侧滑
                        if (openSlide) {
                            current_status = STATUS_SLIDE;
                            current_mode = MODE_SLIDE;
                            int position = pointToPosition((int)touchX,(int)touchY);
                            View view = getChildAt(position - getFirstVisiblePosition());
                            if(view != null && view instanceof SlideView)
                                slideView = (SlideView)view;
                        }else{
                            current_status = STATUS_SCROLL;
                        }
                    } else {//下拉
                        if ((refreshUpOn && canUpRefresh) || (refreshDownOn && canDownRefresh)) {
                            if(touchX == 0 && touchY == 0){
                                touchX = ev.getX();
                                touchY = ev.getY();
                            }
                            if (cy > touchY + 5 && canDownRefresh && refreshDownOn &&
                                    getFirstVisiblePosition() == 0) {
                                current_mode = MODE_DOWN;
                                current_status = STATUS_PULL_TO_REFRESH;
                                setRefreshStatus();
                            } else if(getAdapter() != null && cy + 5 < touchY && canUpRefresh && refreshUpOn &&
                                    getLastVisiblePosition() >= getAdapter().getCount() - 1){
                                current_mode = MODE_UP;
                                current_status = STATUS_PULL_TO_REFRESH;
                                setRefreshStatus();
                            }
                        }else{
                            current_status = STATUS_SCROLL;
                        }
                    }
//                        }else{
//                            current_status = STATUS_SCROLL;
//                            if ((refreshUpOn && canUpRefresh) || (refreshDownOn && canDownRefresh)) {
//                                if(touchX == 0 && touchY == 0){
//                                    touchX = ev.getX();
//                                    touchY = ev.getY();
//                                }
//                                if (cy > touchY) {
//                                    current_mode = MODE_DOWN;
//                                    current_status = STATUS_PULL_TO_REFRESH;
//                                    setRefreshStatus();
//                                } else if(cy < touchY){
//                                    current_mode = MODE_UP;
//                                    current_status = STATUS_PULL_TO_REFRESH;
//                                    setRefreshStatus();
//                                }
//                            }else{
//                                current_status = STATUS_SCROLL;
//                            }
//                        }
//                    }
                }
                float des = Math.abs(cy - touchY);
                if((current_mode == MODE_DOWN && cy > touchY) ||
                        (current_mode == MODE_UP && cy < touchY)) {
                    if (current_status == STATUS_PULL_TO_REFRESH ||
                            current_status == STATUS_RELEASE_TO_REFRESH) {
                        if (des < refreshHeight) {
                            current_status = STATUS_PULL_TO_REFRESH;
                        } else {
                            current_status = STATUS_RELEASE_TO_REFRESH;
                        }
                        setRefreshPadding(des);
                    }
                }
                if(openSlide && current_status == STATUS_SLIDE && current_mode == MODE_SLIDE){
                    int position = pointToPosition((int)touchX,(int)touchY);
                    View view = getChildAt(position - getFirstVisiblePosition());
                    if(view != null)
                        view.onTouchEvent(ev);
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(current_mode == MODE_DOWN){
                    if(current_status == STATUS_RELEASE_TO_REFRESH){
                        current_status = STATUS_REFRESHING;
                        setRefreshStatus();
                    }else if(current_status != STATUS_REFRESHING){
                        setRefreshFinish();
                    }
                }else if(current_mode == MODE_UP){
                    if(current_status == STATUS_RELEASE_TO_REFRESH){
                        current_status = STATUS_REFRESHING;
                        setRefreshStatus();
                    }else if(current_status != STATUS_REFRESHING){
                        setRefreshFinish();
                    }
                }else if(openSlide && current_mode == MODE_SLIDE){
                    //如果是变成不可见则将状态还原，如果是可见状态还是slide
                    if(slideView != null){
                        if(!slideView.adjust()) {
                            current_status = STATUS_DONE;
                            current_mode = MODE_DOWN;
                        }
                        slideView.onTouchEvent(ev);
                        return false;
                    }else{
                        current_status = STATUS_DONE;
                        current_mode = MODE_DOWN;
                    }
                }
                if(touchMoveListener != null){
                    if(touchY > ev.getY()){
                        touchMoveListener.onMove(TYPE_UP);
                    }else if(touchY < ev.getY()){
                        touchMoveListener.onMove(TYPE_DOWN);
                    }
                }
                touchX = 0;
                touchY = 0;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        LogUtil.d("onInterceptTouchEvent--->"+ev.getAction()+",current_status--->"+current_status);
        if(openSlide && current_status == STATUS_SLIDE){
            return true;
        }
        if(current_status != STATUS_SCROLL && current_status != STATUS_DONE){
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        //判断能刷新的条件，在列表的最顶部，或者，在列表的最底部总条数大于当前可见条数
//        if(firstVisibleItem == 0){
        canDownRefresh = true;
//        }else{
//            canDownRefresh = false;
//        }
//        if(firstVisibleItem + visibleItemCount == totalItemCount &&
//                        totalItemCount> visibleItemCount){
        canUpRefresh = true;
//        }else{
//            canUpRefresh = false;
//        }
        if(onListScrollListener != null){
            onListScrollListener.onScroll(view,firstVisibleItem,visibleItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(onListScrollStateListener != null){
            onListScrollStateListener.onStateChange(scrollState);
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, 0, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        LogUtil.d("onTouchEvent--->"+ev.getAction()+",current_status--->"+current_status);
//        if(current_status != STATUS_SCROLL && current_status != STATUS_DONE){
//            return true;
//        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置当前状态
     */
    protected void setRefreshStatus(){
        switch (current_status){
            case STATUS_PULL_TO_REFRESH:
                if(current_mode == MODE_DOWN){
                    headTextView.setText(R.string.pull_down_to_refresh);
                    headProgressBar.setVisibility(GONE);
                }else{
                    if(loadFinish){
                        footTextView.setText(R.string.load_finish);
                    }else {
                        footTextView.setText(R.string.pull_up_load_more);
                    }
                    footProgressBar.setVisibility(GONE);
                }
                break;
            case STATUS_RELEASE_TO_REFRESH:
                if(current_mode == MODE_DOWN){
                    headTextView.setText(R.string.release_to_refresh);
                    headProgressBar.setVisibility(GONE);
//                    headGifView.setMovieResource(R.drawable.loading);
                }else{
                    if(loadFinish){
                        footTextView.setText(R.string.load_finish);
                    }else {
                        footTextView.setText(R.string.release_to_load_more);
                    }
                    footProgressBar.setVisibility(GONE);
//                    footGifView.setMovieResource(R.drawable.loading);
                }
                break;
            case STATUS_REFRESHING:
                if(current_mode == MODE_DOWN){
                    setHeadRefreshing();
                    if(onRefreshListener != null){
                        onRefreshListener.onDownRefresh();
                    }
                }else{
                    setFootRefreshing();
                    if(onRefreshListener != null){
                        onRefreshListener.onUpRefresh();
                    }
                }
                break;
        }
    }

    public interface OnRefreshHeightChangeListener{
        void onHeadHeightChange(int height);
    }
    private OnRefreshHeightChangeListener onRefreshHeightChangeListener;

    public void setOnRefreshHeightChangeListener(
            OnRefreshHeightChangeListener onRefreshHeightChangeListener) {
        this.onRefreshHeightChangeListener = onRefreshHeightChangeListener;
    }
    /**
     * 设置下拉距离
     * @param des
     */
    protected void setRefreshPadding(float des){
        setRefreshStatus();
        if(des > refreshHeight){
            des = refreshHeight + (des - refreshHeight)/2;
        }
        if(current_mode == MODE_DOWN && canDownRefresh){
            setSelection(0);
            int height = (int) (-1 * refreshHeight + des);
            headView.setPadding(0, height, 0, 0);
            if(onRefreshHeightChangeListener != null){
                onRefreshHeightChangeListener.onHeadHeightChange(height + refreshHeight);
            }
        }else if(current_mode == MODE_UP && canUpRefresh){
            if(getAdapter() != null) {
                setSelection(getAdapter().getCount());
            }
            footView.setPadding(0,0,0,(int)(-1*refreshHeight + des));
        }
    }

    /**
     * 设置刷新的状态为初始
     */
    public void setRefreshFinish(){
        headView.setPadding(0,-1*refreshHeight,0,0);
        footView.setPadding(0,0,0,-1*refreshHeight);
        if(onRefreshHeightChangeListener != null){
            onRefreshHeightChangeListener.onHeadHeightChange(0);
        }
        current_status = STATUS_DONE;
    }

    /**
     * 设置头部状态为正在刷新
     */
    public void setHeadRefreshing(){
        headView.setPadding(0, 0, 0, 0);
        headTextView.setText(R.string.refreshing);
        current_status = STATUS_REFRESHING;
        headProgressBar.setVisibility(VISIBLE);
//        headGifView.setMovieResource(R.drawable.loading);
    }

    /**
     * 设置底部状态为正在刷新
     */
    public void setFootRefreshing(){
        if(loadFinish){
            footView.setPadding(0,0,0,-1*refreshHeight);
            footTextView.setText(R.string.load_finish);
            current_status = STATUS_DONE;
            footProgressBar.setVisibility(GONE);
        }else {
            footView.setPadding(0, 0, 0, 0);
            footTextView.setText(R.string.loading_more);
            current_status = STATUS_REFRESHING;

            footProgressBar.setVisibility(VISIBLE);
//        footGifView.setMovieResource(R.drawable.loading);
        }
    }

    public interface OnRefreshListener{
        void onDownRefresh();//下拉刷新
        void onUpRefresh();//上拉刷新
    }
    public interface OnListScrollListener{
        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount);
    }

    /**
     * 手指滑动的想x,y坐标
     */
    public interface TouchMoveListener{
        void onMove(int type);
    }
    public static final int TYPE_UP = 1;
    public static final int TYPE_DOWN = 2;
    public void setTouchMoveListener(TouchMoveListener touchMoveListener) {
        this.touchMoveListener = touchMoveListener;
    }

    private OnListScrollStateListener onListScrollStateListener;

    public void setOnListScrollStateListener(OnListScrollStateListener onListScrollStateListener) {
        this.onListScrollStateListener = onListScrollStateListener;
    }

    public interface OnListScrollStateListener{
        void onStateChange(int state);
    }
}
