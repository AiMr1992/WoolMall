package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.HouseListEntity;
import com.android.wool.model.interactor.HouseListInteractor;
import com.android.wool.model.interactor.HouseListInteractorImpl;
import com.android.wool.model.interactor.HouseListUpdateInteractor;
import com.android.wool.model.interactor.HouseListUpdateInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.HouseListView;
import org.xutils.x;
import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
public class HouseListPresenterImpl extends DetachController<HouseListView> implements HouseListPresenter{
    private HouseListInteractor interactor;
    private HouseListUpdateInteractor updateInteractor;
    public HouseListPresenterImpl(HouseListView mView) {
        super(mView);
        interactor = new HouseListInteractorImpl();
        updateInteractor = new HouseListUpdateInteractorImpl();
    }

    @Override
    public void getHouseList(final Context context,String page) {
        interactor.house(context,page,new HouseListInteractor.HouseListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    List<HouseListEntity> mList = response.parsFromList("favorite",new HouseListEntity());
                    mView.getHouseList(mList,response.isLoad());
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
                mView.getHouseList(null,false);
                disMissProgress();
            }
        });
    }

    @Override
    public void update(final Context context, final HouseListEntity entity, String type, final int position) {
        showLoading();
        updateInteractor.update(context, entity.getId(), type,
                new HouseListUpdateInteractor.HouseListUpdateListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.remove(position);
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
