package com.android.wool.view.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.presenter.MsgPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.SlideView;
import com.qiyukf.unicorn.api.pop.Session;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/25
 */
public class MsgListAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Session> mList;
    private MsgPresenterImpl presenter;
    private boolean firstVisible;
    public MsgListAdapter(Context context,MsgPresenterImpl presenter) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
        this.presenter = presenter;
    }

    public void resetData(List<Session> list){
        firstVisible = true;
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void delete(String sessionId){
        for (Session session : mList){
            if(sessionId.equals(session.getContactId())){
                mList.remove(session);
                notifyDataSetChanged();
                break;
            }
        }
    }

    @Override
    public int getCount() {
        if(!firstVisible){
            return 0;
        }
        return mList.size() == 0 ? 1:mList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(!firstVisible)
            return 1;
        return mList.size()==0?0:1;
    }

    @Override
    public Session getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(getItemViewType(position) == 0){
            if(convertView != null){
                view = convertView;
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.view_list_msg,null);
                view.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(),
                        AbsListView.LayoutParams.WRAP_CONTENT));
                return view;
            }
        }else {
            ViewHolder viewHolder;
            if(convertView == null){
                View itemView = layoutInflater.inflate(R.layout.view_list_msg,null);
                viewHolder = new ViewHolder();
                viewHolder.tvContent = (TextView) itemView.findViewById(R.id.tv_content);
                viewHolder.tvTime = (TextView) itemView.findViewById(R.id.tv_time);
                SlideView slideView = new SlideView(context);
                slideView.setLayoutParams(new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.WRAP_CONTENT
                ));
                slideView.setContentView(itemView);
                slideView.setTag(viewHolder);
                view = slideView;
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final Session session = mList.get(position);
            viewHolder.tvContent.setText(session.getContent());
            viewHolder.tvTime.setText(AppTools.parseLongDate(session.getTime()));
            ((SlideView) view).showSlideButton(true, false, new SlideView.OnSlideButtonClick() {
                @Override
                public void onDeleteClick() {
                    MyAlertDialog dialog = new MyAlertDialog(context);
                    dialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.setRightButton(R.string.delete, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            presenter.delete(session.getContactId());
                        }
                    });
                    dialog.setMsg(R.string.or_delete);
                    dialog.show();
                }

                @Override
                public void onEditClick() {

                }
            });
        }
        return view;
    }

    class ViewHolder{
        TextView tvContent;
        TextView tvTime;
    }
}
