package com.android.wool.view.adapter;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.wool.R;
import com.android.wool.model.entity.CartEntity;
import com.android.wool.presenter.NaCartPresenterImpl;
import com.android.wool.util.ImageUtils;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.SlideView;
import com.android.wool.view.fragment.NaCartView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by AiMr on 2017/9/25
 */
public class CartListAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private Context context;
    private List<CartEntity> mList;
    private NaCartPresenterImpl presenter;
    private boolean firstVisible;
    private int currentState = NaCartView.CART_BALANCE;
    public CartListAdapter(Context context, NaCartPresenterImpl presenter) {
        this.context = context;
        this.presenter = presenter;
        layoutInflater = LayoutInflater.from(context);
        mList = new ArrayList<>();
    }

    public void setCurrentState(int state){
        this.currentState = state;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(!firstVisible){
            return 0;
        }
        return mList.size() == 0 ? 1:mList.size();
    }

    public int getItemCount(){
        return mList.size();
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

    public void resetData(List<CartEntity> list){
        firstVisible = true;
        mList.clear();
        if(list != null && list.size() > 0){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void updateNumber(String number,int position){
        mList.get(position).setGoods_num(number);
        notifyDataSetChanged();
    }

    public void changeDelAll(boolean flag){
        for (CartEntity entity:mList){
            if(flag)
                entity.setIs_del("1");
            else
                entity.setIs_del("0");
        }
        notifyDataSetChanged();
    }

    public String getSelectMineNO(int position){
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0 ; i < mList.size() ; i ++){
            CartEntity entity = mList.get(i);
            if(position != i && "1".equals(entity.getIs_choose())){
                stringBuilder.append(entity.getCart_id());
                stringBuilder.append(",");
            }
        }
        int index = stringBuilder.lastIndexOf(",");
        if(index != -1)
            stringBuilder.deleteCharAt(index);
        return stringBuilder.toString();
    }

    public String getAllDel(){
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0 ; i < mList.size() ; i ++){
            CartEntity entity = mList.get(i);
            if("1".equals(entity.getIs_del())){
                stringBuilder.append(entity.getCart_id());
                stringBuilder.append(",");
            }
        }
        int index = stringBuilder.lastIndexOf(",");
        if(index != -1)
            stringBuilder.deleteCharAt(index);
        return stringBuilder.toString();
    }

    public String getSelectMine(int position){
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0 ; i < mList.size() ; i ++){
            CartEntity entity = mList.get(i);
            if("1".equals(entity.getIs_choose())) {
                stringBuilder.append(entity.getCart_id());
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(mList.get(position).getCart_id());
        return stringBuilder.toString();
    }

    public String getSelectCountFavourable(){
        BigDecimal totalAmount = new BigDecimal(0);
        for (int i = 0 ; i < mList.size() ; i ++){
            CartEntity entity = mList.get(i);
            if("1".equals(entity.getIs_choose())) {
                totalAmount = totalAmount.add(entity.getFavourable());
            }
        }
        if(totalAmount.compareTo(new BigDecimal("0")) == 0)
            return "";
        return totalAmount.toString();
    }

    public void removeAll(){
        for(int i = 0 ; i < mList.size() ; i ++){
            CartEntity entity = mList.get(i);
            if("1".equals(entity.getIs_del())){
                mList.remove(i);
                i--;
            }
        }
        notifyDataSetChanged();
    }

    public void delCart(int position){
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void update(int position,String isChoose){
        mList.get(position).setIs_choose(isChoose);
        notifyDataSetChanged();
    }

    public int getItemSize(){
        int size = 0;
        for(CartEntity entity : mList){
            if("1".equals(entity.getIs_choose())){
                size++;
            }
        }
        return size;
    }

    @Override
    public CartEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if(getItemViewType(position) == 0){
            if(convertView != null){
                view = convertView;
            }else {
                view = LayoutInflater.from(context).inflate(R.layout.view_null,null);
                view.setLayoutParams(new AbsListView.LayoutParams(parent.getWidth(),
                        parent.getHeight()));
                ((TextView)view.findViewById(R.id.tv_none)).setText(R.string.cart_none);
                return view;
            }
        }else {
            ViewHolder viewHolder;
            if (convertView == null) {
                View itemView = layoutInflater.inflate(R.layout.view_list_cart, null);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView) itemView.findViewById(R.id.img);
                viewHolder.tvName = (TextView) itemView.findViewById(R.id.tv_name);
                viewHolder.tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
                viewHolder.tvOldPrice = (TextView) itemView.findViewById(R.id.tv_old_price);
                viewHolder.tv_minus = (ImageView) itemView.findViewById(R.id.tv_minus);
                viewHolder.tv_number = (TextView) itemView.findViewById(R.id.tv_number);
                viewHolder.tv_add = (ImageView) itemView.findViewById(R.id.tv_add);
                viewHolder.textActivity = (TextView) itemView.findViewById(R.id.text_activity);
                viewHolder.imgBalance = (ImageView) itemView.findViewById(R.id.img_balance);
                viewHolder.imgDel = (ImageView) itemView.findViewById(R.id.img_del);

                viewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                SlideView slideView = new SlideView(context);
                slideView.setLayoutParams(new AbsListView.LayoutParams(
                        AbsListView.LayoutParams.MATCH_PARENT,
                        AbsListView.LayoutParams.WRAP_CONTENT
                ));
                slideView.setContentView(itemView);
                slideView.setTag(viewHolder);
                view = slideView;
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            final CartEntity entity = mList.get(position);
            viewHolder.tvName.setText(entity.getTitle());
            viewHolder.tvPrice.setText(String.format(context.getString(R.string.rmb_none), entity.getPrice()));
            if (!TextUtils.isEmpty(entity.getMarket_price()) && !TextUtils.equals("0.00", entity.getMarket_price()))
                viewHolder.tvOldPrice.setText(
                        String.format(context.getString(R.string.rmb_none), entity.getMarket_price()));
            if (TextUtils.isEmpty(entity.getActivity_name())) {
                viewHolder.textActivity.setVisibility(View.GONE);
            } else {
                viewHolder.textActivity.setVisibility(View.VISIBLE);
                viewHolder.textActivity.setText(entity.getActivity_name());
            }
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
                            presenter.delCart(context, entity.getCart_id(), position);
                        }
                    });
                    dialog.setMsg(R.string.or_delete);
                    dialog.show();
                }

                @Override
                public void onEditClick() {

                }
            });
            ImageUtils.downloadWidthDefault(
                    viewHolder.img.getWidth(),
                    viewHolder.img.getHeight(),
                    viewHolder.img,
                    ImageView.ScaleType.FIT_XY,
                    entity.getLogo(),
                    R.drawable.home_default10,
                    R.drawable.home_default10);
            if (currentState == NaCartView.CART_BALANCE) {
                viewHolder.imgBalance.setVisibility(View.VISIBLE);
                viewHolder.imgDel.setVisibility(View.GONE);
                if ("1".equals(entity.getIs_choose())) {
                    viewHolder.imgBalance.setImageResource(R.drawable.default_location);
                } else {
                    viewHolder.imgBalance.setImageResource(R.drawable.un_default_location);
                }
                viewHolder.imgBalance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("1".equals(entity.getIs_choose())) {
                            //【取消选中】取其他的，并且成功后该条数据更改为0
                            presenter.selectCart(context, getSelectMineNO(position), position, true);
                        } else {
                            //【点击选中】选择该条，并且成功后该条更改为1
                            presenter.selectCart(context, getSelectMine(position), position, false);
                        }
                    }
                });
            } else {
                viewHolder.imgBalance.setVisibility(View.GONE);
                viewHolder.imgDel.setVisibility(View.VISIBLE);
                if ("1".equals(entity.getIs_del())) {
                    viewHolder.imgDel.setImageResource(R.drawable.default_location);
                } else {
                    viewHolder.imgDel.setImageResource(R.drawable.un_default_location);
                }
                viewHolder.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entity.setIs_del("1".equals(entity.getIs_del()) ? "0" : "1");
                        notifyDataSetChanged();
                    }
                });
            }
            viewHolder.tv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(entity.getGoods_num());
                    if (number == 1)
                        return;
                    number--;
                    presenter.changeCart(context, entity.getCart_id(), number + "", position, false);
                }
            });
            viewHolder.tv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = Integer.parseInt(entity.getGoods_num());
                    number++;
                    presenter.changeCart(context, entity.getCart_id(), number + "", position, true);
                }
            });
            viewHolder.tv_number.setText(entity.getGoods_num());
        }
        return view;
    }

    class ViewHolder{
        ImageView imgBalance;
        ImageView imgDel;
        ImageView img;
        TextView tvName;
        TextView tvPrice;
        TextView tvOldPrice;
        TextView textActivity;
        ImageView tv_minus;
        TextView tv_number;
        ImageView tv_add;
    }
}