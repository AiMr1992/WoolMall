package com.android.wool.presenter;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.MaterialEntity;
import com.android.wool.model.interactor.MaterialListInteractor;
import com.android.wool.model.interactor.MaterialListInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.fragment.NaFindView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/11/9
 */
public class NaFindPresenterImpl extends DetachController<NaFindView> implements NaFindPresenter {
    private MaterialListInteractor listInteractor;
    public NaFindPresenterImpl(NaFindView mView) {
        super(mView);
        listInteractor = new MaterialListInteractorImpl();
    }

    @Override
    public void getMaterialList(String page) {
        listInteractor.getMaterialList(page, new MaterialListInteractor.MaterialListListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.getMaterialList(response.parsFromData("list",new MaterialEntity()),response.isLoad());
                }
                disMissProgress();
            }

            @Override
            public void onError(String message) {
                disMissProgress();
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }
}
