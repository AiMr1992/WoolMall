package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.MineEntity;
import com.android.wool.model.interactor.MineInteractor;
import com.android.wool.model.interactor.MineInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.fragment.MineView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/9/26
 */
public class NaMinePresenterImpl extends DetachController<MineView> implements NaMinePresenter {
    private MineInteractor interactor;
    public NaMinePresenterImpl(MineView mView) {
        super(mView);
        interactor = new MineInteractorImpl();
    }

    @Override
    public void getMine(final Context context) {
        interactor.getMine(context, new MineInteractor.MineListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    MineEntity entity = response.getEntityFromList("data",new MineEntity());
                    if(entity != null)
                        mView.getMine(entity);
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
