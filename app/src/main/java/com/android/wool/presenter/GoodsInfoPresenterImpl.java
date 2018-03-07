package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.GoodsInfoEntity;
import com.android.wool.model.entity.SpecEntity;
import com.android.wool.model.interactor.GoodsInfoInteractor;
import com.android.wool.model.interactor.GoodsInfoInteractorImpl;
import com.android.wool.model.interactor.HouseUpdateInteractor;
import com.android.wool.model.interactor.HouseUpdateInteractorImpl;
import com.android.wool.model.interactor.JoinCartInteractor;
import com.android.wool.model.interactor.JoinCartInteractorImpl;
import com.android.wool.model.interactor.SpecGoodsInteractor;
import com.android.wool.model.interactor.SpecGoodsInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.activity.GoodsInfoView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/10/13
 */
public class GoodsInfoPresenterImpl extends DetachController<GoodsInfoView> implements GoodsInfoPresenter{
    private GoodsInfoInteractor infoInteractor;
    private HouseUpdateInteractor updateInteractor;
    private JoinCartInteractor joinCartInteractor;
    private SpecGoodsInteractor specGoodsInteractor;
    public GoodsInfoPresenterImpl(GoodsInfoView mView) {
        super(mView);
        infoInteractor = new GoodsInfoInteractorImpl();
        updateInteractor = new HouseUpdateInteractorImpl();
        joinCartInteractor = new JoinCartInteractorImpl();
        specGoodsInteractor = new SpecGoodsInteractorImpl();
    }

    @Override
    public void goodsSpec(String id, String specId) {
        showLoading();
        specGoodsInteractor.goodsSpec(id, specId,
                new SpecGoodsInteractor.SpecGoodsListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        disMissProgress();
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            List<SpecEntity> mList = response.parsFromData("list",new SpecEntity());
                            mView.getSpecList(mList);
                        }
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
    public void getGoodsInfo(final Context context, String id) {
        showLoading();
        infoInteractor.getGoodsInfo(context, id,
                new GoodsInfoInteractor.GoodsInfoListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            GoodsInfoEntity entity = response.getEntityFromList("goods",new GoodsInfoEntity());
                            String cart_num = response.getStringForData("cart_num");
                            String user_code = response.getStringForData("url");
                            mView.getGoodsInfo(entity,cart_num,user_code);
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                        ((GoodsInfoActivity)context).finish();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void update(final Context context, String goods_id, String type) {
        showLoading();
        updateInteractor.update(context, goods_id, type,
                new HouseUpdateInteractor.HouseUpdateListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.goods();
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
    public void joinCart(final Context context, String goodsId, String goodsNum, String specArray) {
        joinCartInteractor.joinCart(context, goodsId, goodsNum, specArray,
                new JoinCartInteractor.JoinCartListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.addCart(response.getStringForData("goods_num"));
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
