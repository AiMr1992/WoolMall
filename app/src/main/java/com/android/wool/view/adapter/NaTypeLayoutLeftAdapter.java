package com.android.wool.view.adapter;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.GoodsCartsEntity;
import com.android.wool.presenter.NaTypePresenterImpl;
import com.android.wool.util.AppTools;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/28
 */
public class NaTypeLayoutLeftAdapter extends BaseAdapter{
    private List<GoodsCartsEntity> mList;
    private Context context;
    private int pos;
    private NaTypePresenterImpl presenter;
    private String typeName;
    private boolean firstVisible;
    public NaTypeLayoutLeftAdapter(Context context,NaTypePresenterImpl presenter) {
        mList = new ArrayList<>();
        this.context = context;
        this.presenter = presenter;
    }

    public void resetData(List<GoodsCartsEntity> list){
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
            if(!firstVisible) {
                mList.get(0).setShow(true);
                pos = 0;
                firstVisible = true;
            }else {
                mList.get(getHistoryPos()).setShow(true);
                pos = getHistoryPos();
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public GoodsCartsEntity getItem(int position) {
        return mList.get(position);
    }

    public GoodsCartsEntity getSelectItem(){
        return getItem(pos);
    }

    private int getHistoryPos(){
        for (int i = 0 ; i < mList.size(); i ++){
            GoodsCartsEntity entity = mList.get(i);
            if(entity.getTitle().equals(typeName)){
                return i;
            }
        }
        return 0;
    }

    public void selectPos(int position){
        if(position != pos) {
            mList.get(position).setShow(true);
            mList.get(pos).setShow(false);
            pos = position;
            notifyDataSetChanged();
            presenter.refreshRight(getSelectItem());
        }
    }

    public int getPos() {
        return pos;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.height = AppTools.turnDipToPx(60,context);
        textView.setLayoutParams(layoutParams);
        GoodsCartsEntity entity = mList.get(position);
        textView.setText(entity.getTitle());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        if(entity.isShow()){
            typeName = entity.getTitle();
            textView.setTextColor(context.getResources().getColor(R.color.black));
            textView.setBackgroundColor(context.getResources().getColor(R.color.home_bg_color));
        }else {
            textView.setTextColor(context.getResources().getColor(R.color.na_type_y));
            textView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPos(position);
            }
        });
        return textView;
    }
}
