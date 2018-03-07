package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.model.interactor.LocationDelInteractor;
import com.android.wool.model.interactor.LocationDelInteractorImpl;
import com.android.wool.model.interactor.LocationListInteractor;
import com.android.wool.model.interactor.LocationListInteractorImpl;
import com.android.wool.model.interactor.LocationUpdateInteractor;
import com.android.wool.model.interactor.LocationUpdateInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.LocationListView;

import org.xutils.x;

import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
public class LocationListPresenterImpl extends DetachController<LocationListView> implements LocationListPresenter{
    private LocationListInteractor interactor;
    private LocationDelInteractor delInteractor;
    private LocationUpdateInteractor updateInteractor;
    public LocationListPresenterImpl(LocationListView mView) {
        super(mView);
        interactor = new LocationListInteractorImpl();
        delInteractor = new LocationDelInteractorImpl();
        updateInteractor = new LocationUpdateInteractorImpl();
    }

    @Override
    public void getLocationList(final Context context,String page) {
        interactor.getLocation(context, page,new LocationListInteractor.LocationListListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    List<LocationEntity> mList = response.parsFromData("list",new LocationEntity());
                    mView.getLocationList(mList,response.isLoad());
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
                mView.getLocationList(null,false);
                disMissProgress();
            }
        });
    }

    @Override
    public void delLocation(String id, final Context context,final int position) {
        showLoading();
        delInteractor.delLocation(id,context,
                new LocationDelInteractor.LocationDelListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.deleteLocation(position);
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(),message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void updateDefault(final Context context, LocationEntity entity, final int position) {
        showLoading();
        updateInteractor.update(context, entity.getContact_name(), entity.getContact_phone(),
                entity.getAddress(), "1", entity.getProvince_id(), entity.getCity_id(),
                entity.getCountry_id(), entity.getProvince_name(), entity.getCity_name(),
                entity.getCountry_name(), entity.getId(),
                new LocationUpdateInteractor.LocationUpdateListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.updateLocationDefault(position);
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(),message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void finish() {
        if(isAttach())
            mView.finishPager();
    }
}
