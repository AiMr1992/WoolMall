package com.android.wool.model.interactor;
import com.android.wool.model.entity.CityInfoEntity;
import com.android.wool.model.network.ResponseMol;
import java.util.List;
/**
 * Created by AiMr on 2017/9/26
 */
public interface AddressListInteractor {
    void getRegionList(AddressListener listener);
    interface AddressListener{
        void onSuccess(ResponseMol response);
        void onError(String message);
        void onFailed();
    }

    void saveCityToDB(List<CityInfoEntity> cityInfoEntities);
    boolean isLocalCityDataEmpty();
    void stopDBThread(boolean stopDBThread);
}
