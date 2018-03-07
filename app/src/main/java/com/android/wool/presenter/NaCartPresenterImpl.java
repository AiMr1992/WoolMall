package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.CartEntity;
import com.android.wool.model.interactor.CartChangNumberInteractor;
import com.android.wool.model.interactor.CartChangNumberInteractorImpl;
import com.android.wool.model.interactor.CartDelInteractor;
import com.android.wool.model.interactor.CartDelInteractorImpl;
import com.android.wool.model.interactor.CartListInteractor;
import com.android.wool.model.interactor.CartListInteractorImpl;
import com.android.wool.model.interactor.CartSelectInteractor;
import com.android.wool.model.interactor.CartSelectInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.fragment.NaCartView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/10/14
 */
public class NaCartPresenterImpl extends DetachController<NaCartView> implements NaCartPresenter {
    private CartListInteractor interactor;
    private CartDelInteractor delInteractor;
    private CartSelectInteractor selectInteractor;
    private CartChangNumberInteractor numberInteractor;
    public NaCartPresenterImpl(NaCartView mView) {
        super(mView);
        interactor = new CartListInteractorImpl();
        delInteractor = new CartDelInteractorImpl();
        selectInteractor = new CartSelectInteractorImpl();
        numberInteractor = new CartChangNumberInteractorImpl();
    }

    @Override
    public void getCartList(final Context context, String page) {
        interactor.getCartList(context, page,
                new CartListInteractor.CartListListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<CartEntity> mList = response.parsFromData("list", new CartEntity());
                            mView.getCartList(mList,response.getString("total"),response.getString("discount"));
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        mView.getCartList(null,"0","");
                        disMissProgress();
                    }
                });
    }

    @Override
    public void delCart(final Context context, String cart_id, final int position) {
        showLoading();
        delInteractor.delCart(context, cart_id,
                new CartDelInteractor.CartDelListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.delCart(position);
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void selectCart(final Context context, String cart_id, final int position, final boolean select) {
        showLoading();
        selectInteractor.cartSelect(context, cart_id, new CartSelectInteractor.CartSelectListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    String total = response.getStringForData("total");
                    String discount = response.getStringForData("discount");
                    mView.updateCheck(position,select,total,discount);
                }
                disMissProgress();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                disMissProgress();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }

    @Override
    public void changeCart(final Context context, String cart_id, final String Goods_num, final int position, final boolean isAdd) {
        showLoading();
        numberInteractor.changeCart(context, cart_id, Goods_num,
                new CartChangNumberInteractor.CartChangNumberListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.updateNumber(position,Goods_num,isAdd);
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }
}