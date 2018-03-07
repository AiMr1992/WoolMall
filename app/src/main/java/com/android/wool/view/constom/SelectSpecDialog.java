package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.GoodsInfoEntity;
import com.android.wool.model.entity.SpecEntity;
import com.android.wool.presenter.GoodsInfoPresenterImpl;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.constom.shop.ShoppingSelectView;
import java.util.List;
/**
 * Created by AiMr on 2017/10/17
 */
public class SelectSpecDialog extends Dialog implements View.OnClickListener{
    private Window window;
    private ImageView img;
    private TextView tvPrice;
    private TextView tvGoods;
    private ImageView imgClose;
    private TextView tvAdd;
    private ShoppingSelectView selectView;
    private ImageView imgMinus;
    private ImageView imgAdd;
    private EditText editNumber;
    private GoodsInfoEntity entity;
    public SelectSpecDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_spec);

        selectView = (ShoppingSelectView) findViewById(R.id.select_view);
        img = (ImageView) findViewById(R.id.img);
        imgClose = (ImageView) findViewById(R.id.img_close);
        imgMinus = (ImageView) findViewById(R.id.img_minus);
        imgAdd = (ImageView) findViewById(R.id.img_add);
        editNumber = (EditText) findViewById(R.id.edit_number);
        tvAdd = (TextView) findViewById(R.id.tv_add);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvGoods = (TextView) findViewById(R.id.tv_goods);
        imgMinus.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(true);
    }

    public void setData(GoodsInfoEntity entity,GoodsInfoPresenterImpl presenter,String id){
        this.entity = entity;
        List<SpecEntity> list = entity.getSpce_array();
        if(list != null && list.size() > 0) {
            selectView.setData(list, presenter, id);
            tvAdd.setEnabled(false);
        }else {
            tvAdd.setEnabled(true);
        }
        ImageUtils.downloadWidthDefault(
                img.getWidth(),
                img.getHeight(),
                img,
                ImageView.ScaleType.FIT_XY,
                entity.getChild().get(0),
                0,0);
        tvPrice.setText(String.format(getContext().getString(R.string.rmb_none),entity.getPrice()));
        tvGoods.setText("商品编号："+entity.getId());
    }

    public void setSpecList(List<SpecEntity> list){
        if(list != null && list.size() > 0 && selectView.getSelectSpecId().split(",").length == entity.getSize()){
            tvAdd.setEnabled(true);
        }else {
            tvAdd.setEnabled(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        window.setWindowAnimations(R.style.pop_anim_style);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = getContext().getResources().getDisplayMetrics().heightPixels * 5 / 8;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_minus:
                changeCount(-1);
                break;
            case R.id.img_add:
                changeCount(1);
                break;
            case R.id.tv_add:
                try {
                    int num = Integer.parseInt(editNumber.getText().toString());
                    int stock = Integer.parseInt(entity.getStock());
                    if (num >= stock) {
                        editNumber.setText(entity.getStock());
                        return;
                    }
                }catch (Exception e){
                    editNumber.setText(entity.getStock());
                    return;
                }
                if(listener != null){
                    listener.selectGoods(entity.getId(),editNumber.getText().toString(),selectView.getSelect());
                }
                break;
        }
    }

    private void changeCount(int sub){
        String count = editNumber.getText().toString();
        if("".equals(count)){
            count = "1";
            editNumber.setText(count);
            return;
        }
        try {
            int num = Integer.parseInt(count);
            if(num >= Integer.parseInt(entity.getStock()) && sub > 0){
                editNumber.setText(entity.getStock());
            }else {
                num = num + sub;
                if (num <= 0) {
                    num = 1;
                }
                editNumber.setText(num + "");
            }
        }catch (Exception e){
            editNumber.setText(entity.getStock());
        }
    }

    public void setButtonCenterListener(OnButtonCenterListener listener) {
        this.listener = listener;
    }
    private OnButtonCenterListener listener;
    public interface OnButtonCenterListener{
        void selectGoods(String goodsId,String goodsNum,String specArray);
    }
}