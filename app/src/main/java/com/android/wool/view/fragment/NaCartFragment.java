package com.android.wool.view.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseFragment;
import com.android.wool.common.MyPreference;
import com.android.wool.model.entity.CartEntity;
import com.android.wool.presenter.NaCartPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.activity.BuildOrderActivity;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.adapter.CartListAdapter;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.RefreshListView;
import java.util.List;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class NaCartFragment extends BaseFragment<NaCartPresenterImpl> implements NaCartView {
    @BindView(R.id.tv_accounts)
    TextView tvAccounts;
    @BindView(R.id.list_view)
    RefreshListView listView;
    @BindView(R.id.layout_play)
    RelativeLayout layoutPlay;
    @BindView(R.id.layout_delete)
    RelativeLayout layoutDelete;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_del)
    TextView tvDel;
    @BindView(R.id.btn_edit)
    TextView tvEdit;
    @BindView(R.id.layout1)
    RelativeLayout layout;
    @BindView(R.id.tv_none)
    TextView tvNone;
    private CartListAdapter adapter;
    private int currentState = CART_BALANCE;
    private int page = 1;
    private boolean currentDel;
    private String totalPrice;
    private Drawable selectY;
    private Drawable selectN;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView() {
        selectY = getResources().getDrawable(R.drawable.default_location);
        selectN = getResources().getDrawable(R.drawable.un_default_location);
        selectY.setBounds(0,0,selectY.getIntrinsicWidth(),selectY.getIntrinsicHeight());
        selectN.setBounds(0,0,selectN.getIntrinsicWidth(),selectN.getIntrinsicHeight());

        tvAccounts.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        tvEdit.setOnClickListener(this);
        adapter = new CartListAdapter(getActivity(),presenter);

        listView.setOpenSlide(true);
        listView.setRefreshOn(true,false);
        listView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onDownRefresh() {
                page = 1;
                presenter.getCartList(getActivity(),page+"");
            }

            @Override
            public void onUpRefresh() {
                presenter.getCartList(getActivity(),page+"");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(adapter.getItemViewType(position) != 0) {
                    CartEntity entity = adapter.getItem(position - 1);
                    Intent intent = new Intent(getActivity(), GoodsInfoActivity.class);
                    intent.putExtra("id", entity.getGoods_id());
                    startActivity(intent);
                }
            }
        });
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_edit:
                if(currentState == CART_BALANCE){
                    currentState = CART_DELETE;
                }else {
                    currentState = CART_BALANCE;
                }
                switchEdit(currentState);
                break;
            case R.id.tv_accounts:
                if(adapter.getItemSize() > 0){
                    Intent intent = new Intent(getActivity(),BuildOrderActivity.class);
                    intent.putExtra("total_price",totalPrice);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(),"请选择需要结算的商品",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_all:
                currentDel = !currentDel;
                selectAll(currentDel);
                adapter.changeDelAll(currentDel);
                break;
            case R.id.tv_del:
                final String delIds = adapter.getAllDel();
                if(TextUtils.isEmpty(delIds)){
                    Toast.makeText(getActivity(),"未勾选删除项",Toast.LENGTH_SHORT).show();
                }else {
                    MyAlertDialog dialog = new MyAlertDialog(getActivity());
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
                            presenter.delCart(getActivity(),delIds,-1);
                        }
                    });
                    dialog.setMsg(R.string.or_delete);
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            String uid = MyPreference.getInstance().getUid(getActivity());
            if(!TextUtils.isEmpty(uid)) {
                presenter.getCartList(getActivity(), page + "");
            }else {
                adapter.resetData(null);
                layoutPlay.setVisibility(View.GONE);
                layoutDelete.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void selectAll(boolean isAll){
        if(isAll)
            tvAll.setCompoundDrawables(selectY,null,null,null);
        else
            tvAll.setCompoundDrawables(selectN,null,null,null);
    }

    @Override
    public void init() {
        presenter = new NaCartPresenterImpl(this);
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
        listView.setRefreshFinish();
    }

    @Override
    public void switchEdit(int currentState) {
        adapter.setCurrentState(currentState);
        if(currentState == CART_DELETE){
            layoutDelete.setVisibility(View.VISIBLE);
            layoutPlay.setVisibility(View.GONE);
            tvEdit.setText(R.string.finish);
        }else {
            layoutDelete.setVisibility(View.GONE);
            layoutPlay.setVisibility(View.VISIBLE);
            tvEdit.setText(R.string.edit);
        }
    }

    @Override
    public void getCartList(List<CartEntity> list, String total, String discount) {
        if(currentState == CART_BALANCE) {
            if (list != null && list.size() > 0) {
                layoutPlay.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
            } else {
                layoutPlay.setVisibility(View.GONE);
                tvEdit.setVisibility(View.GONE);
            }
        }else {
            if (list != null && list.size() > 0) {
                layoutDelete.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
            } else {
                layoutDelete.setVisibility(View.GONE);
                tvEdit.setVisibility(View.GONE);
            }
        }
        adapter.resetData(list);
        updatePrice(total,discount);
    }

    @Override
    public void delCart(int position) {
        if(position != -1) {
            CartEntity entity = adapter.getItem(position);
            String count = AppTools.roundPrice(entity.getGoods_num(), entity.getPrice());
            String number = AppTools.subtract(totalPrice, count);
            tvPrice.setText(String.format(getString(R.string.rmb), number));
            totalPrice = number;
            adapter.delCart(position);
        }else {
            adapter.removeAll();
        }
        if(currentState == CART_BALANCE) {
            if (adapter.getItemCount() != 0) {
                layoutPlay.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
            } else {
                layoutPlay.setVisibility(View.GONE);
                tvEdit.setVisibility(View.GONE);
            }
        }else {
            if (adapter.getItemCount() != 0) {
                layoutDelete.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
            } else {
                layoutDelete.setVisibility(View.GONE);

            }
        }
        if(TextUtils.isEmpty(adapter.getSelectCountFavourable())){
            tvDiscount.setVisibility(View.GONE);
        }else {
            tvDiscount.setVisibility(View.VISIBLE);
            tvDiscount.setText(String.format(getString(R.string.preferential_rmb),adapter.getSelectCountFavourable()));
        }
    }

    @Override
    public void updateCheck(int position, boolean flag, String total, String discount) {
        adapter.update(position,flag?"0":"1");
        updatePrice(total,discount);
    }

    @Override
    public void updatePrice(String total, String discount) {
        totalPrice = total;
        tvPrice.setText(String.format(getString(R.string.rmb), AppTools.getScalePrice(total)));
        if(TextUtils.isEmpty(adapter.getSelectCountFavourable())){
            tvDiscount.setVisibility(View.GONE);
        }else {
            tvDiscount.setVisibility(View.VISIBLE);
            tvDiscount.setText(String.format(getString(R.string.preferential_rmb),adapter.getSelectCountFavourable()));
        }
    }

    @Override
    public void updateNumber(int position, String number, boolean isAdd) {
        if(!"0.00".equals(totalPrice) && "1".equals(adapter.getItem(position).getIs_choose())) {
            if (isAdd) {
                String count = AppTools.addition(totalPrice, adapter.getItem(position).getPrice());
                tvPrice.setText(String.format(getString(R.string.rmb), count));
                totalPrice = count;
            } else {
                String count = AppTools.subtract(totalPrice, adapter.getItem(position).getPrice());
                tvPrice.setText(String.format(getString(R.string.rmb), count));
                totalPrice = count;
            }
        }
        adapter.updateNumber(number,position);
        if(TextUtils.isEmpty(adapter.getSelectCountFavourable())){
            tvDiscount.setVisibility(View.GONE);
        }else {
            tvDiscount.setVisibility(View.VISIBLE);
            tvDiscount.setText(String.format(getString(R.string.preferential_rmb),adapter.getSelectCountFavourable()));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        String uid = MyPreference.getInstance().getUid(getActivity());
        if(!TextUtils.isEmpty(uid)){
            layout.setVisibility(View.VISIBLE);
            tvNone.setVisibility(View.GONE);
            tvEdit.setVisibility(View.VISIBLE);

        }else {
            tvEdit.setVisibility(View.GONE);
            tvNone.setVisibility(View.VISIBLE);
            layout.setVisibility(View.GONE);
        }
    }
}