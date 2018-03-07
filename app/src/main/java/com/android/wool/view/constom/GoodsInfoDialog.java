package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.ProductsEntity;
import com.android.wool.view.adapter.ProductAdapter;
import java.util.List;
/**
 * Created by AiMr on 2017/10/17
 */
public class GoodsInfoDialog extends Dialog{
    private ListView listView;
    private TextView tvFinish;
    private ProductAdapter adapter;
    private Window window;
    public GoodsInfoDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_goods_info);
        listView = (ListView) findViewById(R.id.list_view);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        adapter = new ProductAdapter(context);
        listView.setAdapter(adapter);
        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(true);
    }

    public void setData(List<ProductsEntity> list){
        adapter.resetData(list);
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
}
