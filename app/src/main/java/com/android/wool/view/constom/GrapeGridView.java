package com.android.wool.view.constom;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;
/**
 * Created by AiMr on 2017/11/1
 */
public class GrapeGridView extends GridView{
    public GrapeGridView(Context context) {
        super(context);
    }

    public GrapeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.dispatchTouchEvent(ev);
    }
}
