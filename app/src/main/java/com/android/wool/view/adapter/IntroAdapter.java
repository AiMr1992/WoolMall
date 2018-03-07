package com.android.wool.view.adapter;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.IntroEntity;
import com.android.wool.util.AppTools;
import com.android.wool.util.ImageUtils;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/14
 */
public class IntroAdapter extends BaseAdapter{
    private Context context;
    private List<IntroEntity> mList;
    private int h;
    private int imgH;
    public IntroAdapter(Context context) {
        this.context = context;
        mList = new ArrayList<>();
        h = (context.getResources().getDisplayMetrics().widthPixels - AppTools.turnDipToPx(20,context))/4;
        imgH = AppTools.turnDipToPx(35,context);
    }

    public void resetData(List<IntroEntity> list){
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public IntroEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(context);
        textView.setTextColor(context.getResources().getColor(R.color.edit_phone));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams1);
        layoutParams1.topMargin = AppTools.turnDipToPx(6,context);

        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(imgH,imgH);
        layoutParams2.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams2);
        linearLayout.setClickable(false);

        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setLayoutParams(new AbsListView.LayoutParams(h,h));
        IntroEntity entity = mList.get(position);
        ImageUtils.downloadWidthDefault(h,h,imageView, ImageView.ScaleType.FIT_XY,
                entity.getLogo(),0,0);
        textView.setText(entity.getTitle());
        return linearLayout;
    }
}
