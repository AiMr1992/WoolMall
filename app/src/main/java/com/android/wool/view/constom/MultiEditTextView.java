package com.android.wool.view.constom;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import com.android.wool.R;
/**
 * Created by AiMr on 2017/12/9.
 */
public class MultiEditTextView extends EditText {

    private Drawable closeDrawable;
    private int closeDrawableWidth;
    private int closeDrawableHeight;

    public MultiEditTextView(Context context) {
        super(context);
    }

    public MultiEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
    }

    public MultiEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.MultiEditTextView,0,0);
        if(typedArray != null){
            for(int i = 0; i < typedArray.getIndexCount(); i++){
                int type = typedArray.getIndex(i);
                if(type == R.styleable.MultiEditTextView_close_drawable){
                    closeDrawable = typedArray.getDrawable(type);
                }
                if(type == R.styleable.MultiEditTextView_close_drawable_width){
                    closeDrawableWidth = (int)typedArray.getDimension(type,0);
                }
                if(type == R.styleable.MultiEditTextView_close_drawable_height){
                    closeDrawableHeight = (int)typedArray.getDimension(type,0);
                }
            }
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    public void setCloseDrawable(Drawable drawable, int width, int height){
        this.closeDrawable = drawable;
        this.closeDrawableWidth = width;
        this.closeDrawableHeight = height;
    }

    private void showCloseDrawable(){
        if(closeDrawable != null) {
            if(closeDrawableWidth == 0){
                closeDrawableWidth = getWidth();
            }
            if(closeDrawableHeight == 0){
                closeDrawableHeight = getHeight();
            }
            closeDrawable.setBounds(0, 0, closeDrawableWidth, closeDrawableHeight);
        }
        setCompoundDrawables(null, null, closeDrawable,null);
        setCompoundDrawablePadding(20);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if(text.length() > 0){
            showCloseDrawable();
        }else{
            setCompoundDrawables(null,null,null,null);
        }
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(closeDrawable != null && getText().length() > 0){//判断是否点击了关闭按钮
            Rect rect = closeDrawable.getBounds();
            int width = rect.width();
            int height = rect.height();

            int left = getWidth() - getPaddingRight() - width;
            int top = getHeight()/2 - height/2;

            if(event.getX() > left && event.getX() < left + width
                    && event.getY() > top && event.getY() < top + height){
                setText("");
                return false;
            }
        }
        return super.onTouchEvent(event);
    }
}
