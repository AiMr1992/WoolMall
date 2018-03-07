package com.android.wool.model.network;
import android.text.TextUtils;
import com.android.wool.model.network.api.HttpConstant;
import com.android.wool.model.network.api.IRetrofitServer;
import com.android.wool.model.network.conver.StringConverterFactory;
import com.android.wool.util.LogUtil;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/**
 * Created by AiMr on 2017/9/21
 */
public class ConnHttpHelper {
    private IRetrofitServer movieService;
    private Retrofit retrofit;
    private static final int DEFAULT_TIMEOUT = 5;

    private ConnHttpHelper(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(HttpConstant.BASE_URL)
                .build();
        movieService = retrofit.create(IRetrofitServer.class);
    }

    private static class SingletonHolder{
        private static final ConnHttpHelper INSTANCE = new ConnHttpHelper();
    }

    public static ConnHttpHelper getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void getServerApiPost(Subscriber<ResponseMol> subscriber, String api, TreeMap<String,String> map){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String value = "";
            if (!TextUtils.isEmpty(entry.getValue())) {
                value = entry.getValue();
            }
            sb.append("&" + entry.getKey() + "=" + value);
        }
        LogUtil.d(api+" ——》"+sb.toString());
        Observable observable = movieService.getServerApiPost(api,map)
                .map(new HttpResultFunc<String>());
        toSubscribe(observable, subscriber);
    }

    public void uploadFile(Subscriber<ResponseMol> subscriber,File file){
        RequestBody requestBody = RequestBody.create(MediaType.parse("/image/jpg"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("picture",file.getName(),requestBody);
        Observable observable = movieService.uploadFile(HttpConstant.HEAD_IMG,body)
                .map(new HttpResultFunc<String>());
        toSubscribe(observable, subscriber);
    }

    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        Subscription i = o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /** 类型转换和预处理 */
    private class HttpResultFunc<T> implements Func1<T, ResponseMol> {
        @Override
        public ResponseMol call(T httpResult) {
            LogUtil.d(httpResult.toString());
            ResponseMol responseMol = new ResponseMol(httpResult.toString());
            return responseMol;
        }
    }
}