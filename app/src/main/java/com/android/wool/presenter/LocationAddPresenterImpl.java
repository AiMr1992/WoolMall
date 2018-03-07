package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.model.interactor.LocationDelInteractorImpl;
import com.android.wool.model.interactor.LocationUpdateInteractor;
import com.android.wool.model.interactor.LocationUpdateInteractorImpl;
import com.android.wool.model.interactor.LocationDelInteractor;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.LocationAddView;
import org.xutils.x;
/**
 * Created by AiMr on 2017/10/9
 */
public class LocationAddPresenterImpl extends DetachController<LocationAddView> implements LocationAddPresenter {
    private LocationUpdateInteractor updateInteractor;
    private LocationDelInteractor delInteractor;
    public LocationAddPresenterImpl(LocationAddView mView) {
        super(mView);
        updateInteractor = new LocationUpdateInteractorImpl();
        delInteractor = new LocationDelInteractorImpl();
    }

    @Override
    public void switchPager(int position) {
        if (isAttach())
            if(position == LOCATION_ADD){
                mView.addLocationTitle();
            }else if(position == LOCATION_UPDATE){
                mView.updateLocationTitle();
            }
    }

    @Override
    public void addLocation(final Context context, final String contact_name, String contact_phone, String address,
                            String is_default, String province_id, String city_id, String country_id,
                            String province_name, String city_name, final String country_name) {
        showLoading();
        updateInteractor.update(context,contact_name,contact_phone,address,is_default,province_id,city_id,
                country_id,province_name,city_name,country_name,"",
                new LocationUpdateInteractor.LocationUpdateListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
                            LocationEntity entity = response.getEntity("list",new LocationEntity());
                            mView.addLocationSuccess(entity);
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
    public void updateLocation(final Context context, String contact_name, String contact_phone, String address,
                               String is_default, String province_id, String city_id, String country_id,
                               String province_name, String city_name, String country_name, String id, final int position) {
        showLoading();
        updateInteractor.update(context,contact_name,contact_phone,address,is_default,province_id,city_id,
                country_id,province_name,city_name,country_name,id,
                new LocationUpdateInteractor.LocationUpdateListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
                            LocationEntity entity = response.getEntity("list",new LocationEntity());
                            mView.updateLocationSuccess(entity,position);
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
    public void delLocation(final Context context, String id,final int position) {
        showLoading();
        delInteractor.delLocation(id, context,
                new LocationDelInteractor.LocationDelListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                            mView.delLocationSuccess(position);
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
}