package com.android.wool.model.interactor;
import com.android.wool.MyApplication;
import com.android.wool.model.db.DBCityManager;
import com.android.wool.model.entity.CityInfoEntity;
import com.android.wool.model.network.ConnHttpHelper;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.subscribers.EventSubscriber;
import com.android.wool.model.network.subscribers.SubscriberOnNextListener;
import java.util.List;
import java.util.TreeMap;
/**
 * Created by AiMr on 2017/9/26
 */
public class AddressListInteractorImpl implements AddressListInteractor{
    @Override
    public void getRegionList(final AddressListener listener) {
        TreeMap<String,String> map = new TreeMap<>();
        ConnHttpHelper.getInstance().getServerApiPost(
                new EventSubscriber<ResponseMol>(new SubscriberOnNextListener() {
                    @Override
                    public void onNext(ResponseMol responseMol) {
                        if(listener != null){
                            if(responseMol != null){
                                if(responseMol.isRequestSuccess()){
                                    listener.onSuccess(responseMol);
                                }else {
                                    listener.onError(responseMol.getReturnMessage());
                                }
                            }else {
                                listener.onFailed();
                            }
                        }
                    }
                }),
                HttpConstant.ADDRESS_LIST,
                map
        );
    }

    @Override
    public void saveCityToDB(List<CityInfoEntity> cityInfoEntities) {
        CityRunnable cityRunnable = new CityRunnable(cityInfoEntities);
        new Thread(cityRunnable).start();
    }

    private boolean stopDBThread;
    @Override
    public void stopDBThread(boolean stopDBThread) {
        this.stopDBThread = stopDBThread;
    }

    class CityRunnable implements Runnable{
        private List<CityInfoEntity> cityInfoEntities;

        public CityRunnable(List<CityInfoEntity> cityInfoEntities) {
            this.cityInfoEntities = cityInfoEntities;
        }

        @Override
        public void run() {
            if(cityInfoEntities != null) {
                DBCityManager dbCityManager = new DBCityManager();
                for (int i = 0; i < cityInfoEntities.size(); i++) {
                    if(stopDBThread) {
                        break;
                    }
                    cityInfoEntities.get(i).insertSelfAndChild(dbCityManager);
                }
                dbCityManager.closeDB();
                MyApplication.setCityList(null);
            }
        }
    }

    /** 是否存在数据 */
    @Override
    public boolean isLocalCityDataEmpty() {
        DBCityManager dbCityManager = new DBCityManager();
        boolean result = !dbCityManager.hasCityData();
        dbCityManager.closeDB();
        return result;
    }
}