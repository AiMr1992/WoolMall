package com.android.wool.util.qiniu;
import android.content.Context;
import com.android.wool.common.Constant;
import com.android.wool.util.AppTools;
import com.android.wool.util.LogUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.AsyncRun;
import org.json.JSONObject;
import java.io.File;
/**
 * Created by AiMr on 2017/11/16
 */
public class QiNiuPhotoUpLoad {
    private UploadManager uploadManager;
    private QiNiuPhotoUpLoad() {
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(5)          // 服务器响应超时。默认60秒
                .build();
        uploadManager = new UploadManager(config);
    }

    private static class SingletonHolder{
        private static final QiNiuPhotoUpLoad INSTANCE = new QiNiuPhotoUpLoad();
    }
    public static QiNiuPhotoUpLoad getInstance(){
        return QiNiuPhotoUpLoad.SingletonHolder.INSTANCE;
    }

    public void upLoad(final UpLoadListener listener,String token,final File file, int type, Context context){
        //生成token
        //String token = Auth.create(Constant.ACCESS_KEY, Constant.SECRET_KEY).uploadToken(Constant.QINIU_ROOM);
        String key = AppTools.parseFileName(type,context)+AppTools.getFileName(file);
        uploadManager.put(
                file,
                key,
                token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(final String key, final ResponseInfo info, JSONObject response) {
                        AsyncRun.runInMain(new Runnable() {
                            @Override
                            public void run() {
                                if(info.isOK()){
                                    LogUtil.d("文件上传成功:"+Constant.SECRET_HOST+key);
                                    if(listener != null){
                                        listener.onSuccess(file,Constant.SECRET_HOST+key);
                                    }
                                }else {
                                    LogUtil.e(info.toString());
                                    if(listener != null){
                                        listener.onError("上传失败！");
                                    }
                                }
                            }
                        });
                    }
                },null);
    }

    public interface UpLoadListener{
        void onSuccess(File file,String url);
        void onError(String message);
    }
}