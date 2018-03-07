package com.android.wool.presenter;
import android.content.Context;
/**
 * Created by AiMr on 2017/9/23
 */
public interface MainPresenter {
    void initCityData(Context context);
    void requestCity(Context context);
    void stopDBThread();
}
