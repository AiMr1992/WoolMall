package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.ActivityEntity;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/10/23
 */
public class SelectActivityTypeDialog extends Dialog{
    private TextView tvTitle;
    private ListView listView;
    private TextView tv_close;
    private TypeAdapter adapter;
    private Window window;
    public SelectActivityTypeDialog(Context context) {
        super(context, R.style.dialog_transparent);
        setContentView(R.layout.dialog_select);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv_close = (TextView) findViewById(R.id.tv_close);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new TypeAdapter(context);
        listView.setAdapter(adapter);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.select(adapter.getSelectItem(),adapter.getmPos());
                }
                dismiss();
            }
        });
        setCanceledOnTouchOutside(true);
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

    public void setTvTitle(String text){
        tvTitle.setText(text);
    }

    public void setTvTitle(int textId){
        this.setTvTitle(getContext().getString(textId));
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.listener = listener;
    }
    private OnSelectListener listener;
    public interface OnSelectListener{
        void select(ActivityEntity entity,int mPos);
    }

    public void setData(List<ActivityEntity> list,int pos){
        adapter.resetData(list,pos);
    }

    class TypeAdapter extends BaseAdapter{
        private List<ActivityEntity> mList;
        private Context context;
        private Drawable drawable;
        private int mPos;
        public int getmPos() {
            return mPos;
        }
        public TypeAdapter(Context context) {
            this.context = context;
            mList = new ArrayList<>();
            drawable = context.getResources().getDrawable(R.drawable.default_location);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public ActivityEntity getItem(int position) {
            return mList.get(position);
        }

        public void resetData(List<ActivityEntity> list,int mPos){
            mList.clear();
            this.mPos = mPos;
            if(list != null && list.size()>0){
                mList.addAll(list);
                for (int i = 0;i<mList.size();i++){
                    if(mPos == i){
                        mList.get(mPos).setIsChoose("1");
                    }else {
                        mList.get(i).setIsChoose("0");
                    }
                }
                notifyDataSetChanged();
            }
        }

        public ActivityEntity getSelectItem(){
            if(mList.size() > 0)
                return getItem(mPos);
            return null;
        }

        private void notifySelect(int position){
            mList.get(mPos).setIsChoose("0");
            mList.get(position).setIsChoose("1");
            mPos = position;
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(context).inflate(R.layout.item_dialog_order,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_select = (TextView) view.findViewById(R.id.tv_select);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final ActivityEntity activityEntity = mList.get(position);
            if("1".equals(activityEntity.getIsChoose())){
                viewHolder.tv_select.setCompoundDrawables(null,null,drawable,null);
            }else {
                viewHolder.tv_select.setCompoundDrawables(null,null,null,null);
            }
            if("0".equals(activityEntity.getIsChoose()))
                viewHolder.tv_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notifySelect(position);
                    }
                });
            viewHolder.tv_select.setText(activityEntity.getTitle());
            return view;
        }
    }

    class ViewHolder{
        TextView tv_select;
    }
}
