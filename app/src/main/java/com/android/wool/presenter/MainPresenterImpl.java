package com.android.wool.presenter;
import android.content.Context;

import com.android.wool.MyApplication;
import com.android.wool.base.DetachController;
import com.android.wool.common.MyPreference;
import com.android.wool.model.entity.CityInfoEntity;
import com.android.wool.model.interactor.AddressListInteractor;
import com.android.wool.model.interactor.AddressListInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.MainView;
import java.util.List;
/**
 * Created by AiMr on 2017/9/23
 */
public class MainPresenterImpl extends DetachController<MainView> implements MainPresenter{
    private AddressListInteractor interactor;
    public MainPresenterImpl(MainView mView) {
        super(mView);
        interactor = new AddressListInteractorImpl();
    }

    @Override
    public void initCityData(Context context) {
        if(interactor.isLocalCityDataEmpty()){
            requestCity(context);
        }
    }

    @Override
    public void requestCity(final Context context) {
        interactor.getRegionList(new AddressListInteractor.AddressListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                List<CityInfoEntity> mList = response.parsFromData("list",new CityInfoEntity());
                if(mList != null && mList.size() > 0){
                    MyApplication.setCityList(mList);
                    MyPreference.getInstance().savePreferenceData(context,MyPreference.FIRSER_INSERT_CITY,"true");
                    interactor.saveCityToDB(mList);
                }
            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onFailed() {
            }
        });
    }

    @Override
    public void stopDBThread() {
        interactor.stopDBThread(true);
    }
}