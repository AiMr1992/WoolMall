package com.android.wool.view.constom.shop;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.ValueEntity;
import com.android.wool.util.AppTools;
import java.util.ArrayList;
import java.util.List;
public class FlowLayout extends ViewGroup implements View.OnClickListener{
    /**
     * 属性的标题
     */
    private String title;
    private List<ValueEntity> mList;

    public FlowLayout(Context context) {
        super(context);
        initView();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        mList = new ArrayList<>();
    }

    public void setData(List<ValueEntity> list){
        if(list != null && list.size() > 0){
            mList.addAll(list);
            for (int i = 0; i < mList.size(); i++) {
                String smallAttr = mList.get(i).getValue();
                //属性按钮
                TextView button = new TextView(getContext());
                //默认选中第一个
                //设置按钮的参数
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        AppTools.turnDipToPx(25,getContext()));
                //设置文字的边距
                int padding = AppTools.turnDipToPx(10,getContext());
                button.setPadding(padding, 0, padding, 0);
                //设置margin属性，需传入LayoutParams否则会丢失原有的布局参数
                MarginLayoutParams marginParams = new MarginLayoutParams(buttonParams);
                marginParams.leftMargin = AppTools.turnDipToPx(15,getContext());
                marginParams.topMargin = AppTools.turnDipToPx(10,getContext());
                button.setBackgroundResource(R.drawable.selector_goods_enable);
                button.setLayoutParams(marginParams);
                button.setGravity(Gravity.CENTER);
                button.setText(smallAttr);
                button.setTextColor(Color.BLACK);
                addView(button);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        // 如果是warp_content情况下，记录宽和高
        int width = 0;
        int height = 0;

        // 记录每一行的宽度与高度
        int lineWidth = 0;
        int lineHeight = 0;

        // 得到内部元素的个数
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            // 通过索引拿到每一个子view
            View child = getChildAt(i);
            // 测量子View的宽和高,系统提供的measureChild
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            // 子View占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            // 子View占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 换行 判断 当前的宽度大于 开辟新行
            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                // 对比得到最大的宽度
                width = Math.max(width, lineWidth);
                // 重置lineWidth
                lineWidth = childWidth;
                // 记录行高
                height += lineHeight;
                lineHeight = childHeight;
            } else { // 未换行
                // 叠加行宽
                lineWidth += childWidth;
                // 得到当前行最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }
            // 特殊情况,最后一个控件
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()//
        );

    }

    /** 存储所有的View */
    private List<List<View>> mAllViews = new ArrayList<>();
    /** 每一行的高度 */
    private List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        // 当前ViewGroup的宽度
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        // 存放每一行的子view
        List<View> lineViews = new ArrayList<>();

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child
                    .getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // 如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingLeft() - getPaddingRight()) {
                // 记录LineHeight
                mLineHeight.add(lineHeight);
                // 记录当前行的Views
                mAllViews.add(lineViews);

                // 重置我们的行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                // 重置我们的View集合
                lineViews = new ArrayList<>();
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);

        }// for end
        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置

        int left = getPaddingLeft();
        int top = getPaddingTop();

        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            // 当前行的所有的View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                // 判断child的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();
                // 为子View进行布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    /** 与当前ViewGroup对应的LayoutParams  */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    public void addView(View child) {
        child.setOnClickListener(this);
        super.addView(child);
    }

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;
        for (int i = 0 ; i < getChildCount() ; i++){
            TextView tv = (TextView) getChildAt(i);
            if(textView.getText().toString().equals(tv.getText().toString())){
                if(mList.get(i).getId().equals(specId)){
                    specId = "";
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundResource(R.drawable.goods_n);
                }else {
                    tv.setTextColor(getContext().getResources().getColor(R.color.mine_liner_color));
                    tv.setBackgroundResource(R.drawable.goods_y);
                    specId = mList.get(i).getId();
                    specName = mList.get(i).getValue();
                }
            }else {
                tv.setTextColor(Color.BLACK);
                tv.setBackgroundResource(R.drawable.goods_n);
            }
        }
        if(listener != null){
            listener.onclick();
        }
    }

    private String specId ="";
    private String specName ="";

    public String getSpecId() {
        return specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setListener(IOnclickListener listener) {
        this.listener = listener;
    }
    public IOnclickListener listener;
    public interface IOnclickListener{
        void onclick();
    }
}