package com.android.wool.view.fragment;
import com.android.wool.base.IBaseView;
import com.android.wool.model.entity.CartEntity;
import java.util.List;
/**
 * Created by AiMr on 2017/10/24
 */
public interface NaCartView extends IBaseView {
    int CART_BALANCE = 0;
    int CART_DELETE = 1;
    void switchEdit(int currentState);
    void getCartList(List<CartEntity> list, String total, String discount);
    void delCart(int position);
    void updateCheck(int position, boolean flag, String total, String discount);
    void updatePrice(String total, String discount);
    void updateNumber(int position, String number, boolean isAdd);
    void selectAll(boolean isAll);
}
