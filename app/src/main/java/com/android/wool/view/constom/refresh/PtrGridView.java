package com.android.wool.view.constom.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 王统根
 * time 2016/11/16
 * desc 自定义gridView
 */

public class PtrGridView extends GridView implements PullableView {
    private Context context;
    private boolean canPullUp = false;
    private boolean canPullDown = false;
    public PtrGridView(Context context) {
        super(context);
        this.context = context;
    }

    public PtrGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PtrGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public boolean canPullDown() {

        return canPullDown;
    }

    @Override
    public boolean canPullUp() {
        return (computeVerticalScrollExtent() + computeVerticalScrollOffset()) == computeVerticalScrollRange();
    }
}
