package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.GoodsCartsEntity;
import com.android.wool.model.entity.NaTypeEntity;
import com.android.wool.model.interactor.NaTypeInteractor;
import com.android.wool.model.interactor.NaTypeInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.fragment.NaTypeView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/9/28
 */
public class NaTypePresenterImpl extends DetachController<NaTypeView> implements NaTypePresenter{
    private NaTypeInteractor interactor;
    public NaTypePresenterImpl(NaTypeView mView) {
        super(mView);
        interactor = new NaTypeInteractorImpl();
    }

    @Override
    public void getTypeGoodsList(final Context context) {
        interactor.getTypeGoodsList(new NaTypeInteractor.NaTypeListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()) {
                    NaTypeEntity entity = response.getEntity("list",new NaTypeEntity());
                    mView.getTypeGoodsList(entity);
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
    public void refreshRight(GoodsCartsEntity entity) {
        if(isAttach())
            mView.refreshRight(entity.getChild());
    }
}
