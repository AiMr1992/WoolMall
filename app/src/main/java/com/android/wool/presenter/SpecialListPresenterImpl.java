package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.SpecialListEntity;
import com.android.wool.model.interactor.SpecialListInteractor;
import com.android.wool.model.interactor.SpecialListInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.SpecialListView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/10/28
 */
public class SpecialListPresenterImpl extends DetachController<SpecialListView> implements SpecialListPresenter{
    private SpecialListInteractor interactor;
    public SpecialListPresenterImpl(SpecialListView mView) {
        super(mView);
        interactor = new SpecialListInteractorImpl();
    }

    @Override
    public void getSpecialList(final Context context, String id, String page) {
        interactor.getSpecialList(id, page,
                new SpecialListInteractor.SpecialListListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            SpecialListEntity entity =response.getEntity("list", new SpecialListEntity());
                            mView.getEntity(entity);
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
