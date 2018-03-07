package com.android.wool.model.network.api;
import java.util.Map;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;
import rx.Observable;
/**
 * Created by AiMr on 2017/9/21
 */
public interface IRetrofitServer {
    @FormUrlEncoded
    @POST
    Observable<String> getServerApiPost(@Url String api, @FieldMap Map<String,String> map);

    @Multipart
    @POST
    Observable<String> uploadFile(@Url String api,@Part MultipartBody.Part MultipartFile);
}
