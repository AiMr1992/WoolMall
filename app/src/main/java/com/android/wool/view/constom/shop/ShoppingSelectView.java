package com.android.wool.view.constom.shop;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.model.entity.SpecEntity;
import com.android.wool.presenter.GoodsInfoPresenterImpl;
import java.util.ArrayList;
import java.util.List;
public class ShoppingSelectView extends LinearLayout {
    private GoodsInfoPresenterImpl presenter;
    /**
     * 数据源
     */
    private List<SpecEntity> list;
    /**
     * 上下文
     */
    private Context context;

    /**
     * 规格标题栏的文本间距
     */
    private int titleMarginLeft = 15;
    private int titleMarginTop = 10;
    /**
     * 整个商品属性的左右间距
     */
    private int flowLayoutMargin = 0;
    private List<FlowLayout> flowLayouts;


    public ShoppingSelectView(Context context) {
        super(context);
        initView(context);
    }

    public ShoppingSelectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        flowLayouts = new ArrayList<>();
    }

    public void getView() {
        if (list.size() < 0) {
            return;
        }
        for (SpecEntity attr : list) {
            //设置规格分类的标题
            TextView textView = new TextView(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            int marginLeft = dip2px(context, titleMarginLeft);
            int marginTop = dip2px(context, titleMarginTop);
            textView.setText(attr.getTitle());
            params.setMargins(marginLeft, marginTop,0,0);
            textView.setLayoutParams(params);
            addView(textView);
            //设置一个大规格下的所有小规格
            FlowLayout layout = new FlowLayout(context);
            layout.setTitle(attr.getTitle());
            layout.setPadding(dip2px(context, flowLayoutMargin), 0, dip2px(context, flowLayoutMargin), 0);
            //设置选择监听
            layout.setData(attr.getValue());
            layout.setListener(new FlowLayout.IOnclickListener() {
                @Override
                public void onclick() {
                    presenter.goodsSpec(id,getSelectSpecId());
                }
            });
            flowLayouts.add(layout);
            addView(layout);
        }
    }

    public String getSelectSpecId(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0 ; i < flowLayouts.size(); i ++){
            FlowLayout flowLayout = flowLayouts.get(i);
            if(!TextUtils.isEmpty(flowLayout.getSpecId())) {
                builder.append(flowLayout.getSpecId());
                builder.append(",");
            }
        }
        int index = builder.lastIndexOf(",");
        if(index != -1)
            builder.deleteCharAt(index);
        return builder.toString();
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private String id;
    public void setData(List<SpecEntity> data,GoodsInfoPresenterImpl presenter,String id) {
        list = data;
        this.id = id;
        this.presenter = presenter;
        getView();
    }

    public String getSelect(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0 ; i < flowLayouts.size(); i ++){
            FlowLayout flowLayout = flowLayouts.get(i);
            builder.append(flowLayout.getTitle());
            builder.append(":");
            builder.append(flowLayout.getSpecName());
            builder.append(",");
        }
        int index = builder.lastIndexOf(",");
        if(index != -1)
            builder.deleteCharAt(index);
        return builder.toString();
    }
}
