package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.HomeEntity;
import com.android.wool.model.entity.RecommendEntity;
import com.android.wool.model.interactor.HomeListInteractor;
import com.android.wool.model.interactor.HomeListInteractorImpl;
import com.android.wool.model.interactor.IndexRecommendInteractor;
import com.android.wool.model.interactor.IndexRecommendInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.fragment.HomeView;
import org.xutils.x;

import java.util.List;

/**
 * Created by AiMr on 2017/9/27
 */
public class NaHomePresenterImpl extends DetachController<HomeView> implements NaHomePresenter{
    private HomeListInteractor interactor;
    private IndexRecommendInteractor recommendInteractor;
    public NaHomePresenterImpl(HomeView mView) {
        super(mView);
        interactor = new HomeListInteractorImpl();
        recommendInteractor = new IndexRecommendInteractorImpl();
    }

    @Override
    public void getHomeList(final Context context) {
        interactor.getList(context,new HomeListInteractor.HomeListListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()) {
                    HomeEntity entity = response.getEntity("list", new HomeEntity());
                    mView.getHome(entity);
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

    @Override
    public void getLoadHomeList(String page) {
        recommendInteractor.getIndexRecommendList(page,
                new IndexRecommendInteractor.IndexRecommendListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()) {
                            List<RecommendEntity> list = response.parsFromData("list",new RecommendEntity());
                            mView.getHomeList(list,response.isLoad());
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