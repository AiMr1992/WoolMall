package com.android.wool.util.qiniu;
import android.util.Log;
import com.android.wool.common.Constant;
import com.android.wool.util.FileUtil;
import com.android.wool.util.LogUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.AsyncRun;
import com.qiniu.android.utils.UrlSafeBase64;
import org.json.JSONObject;
import org.xutils.x;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
/**
 * Created by AiMr on 2017/11/16
 */
public class QiNiuMusicUpLoad {
    public static final String FILE_NAME = "/QiNiuAndroid";
    private FileRecorder fileRecorder;
    private KeyGenerator keyGenerator;
    private UploadManager uploadManager;
    private boolean cancelUpload;
    private QiNiuMusicUpLoad(){
        try {
            fileRecorder = new FileRecorder(x.app().getFilesDir()+FILE_NAME);
            keyGenerator = new KeyGenerator() {
                @Override
                public String gen(String key, File file) {
                    String recorderName = System.currentTimeMillis() + ".progress";
                    try {
                        recorderName = UrlSafeBase64.encodeToString(Tools.sha1(key + ":" + file
                                .getAbsolutePath() + ":" + file.lastModified())) + ".progress";
                    } catch (NoSuchAlgorithmException e) {
                        Log.e("QiniuLab", e.getMessage());
                    } catch (UnsupportedEncodingException e) {
                        Log.e("QiniuLab", e.getMessage());
                    }
                    return recorderName;
                }
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(5)          // 服务器响应超时。默认60秒
                .recorder(fileRecorder)           // recorder分片上传时，已上传片记录器。默认null
                .recorder(fileRecorder, keyGenerator)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .build();
        uploadManager = new UploadManager(config);
    }

    private static class SingletonHolder{
        private static final QiNiuMusicUpLoad INSTANCE = new QiNiuMusicUpLoad();
    }
    public static QiNiuMusicUpLoad getInstance(){
        return QiNiuMusicUpLoad.SingletonHolder.INSTANCE;
    }

    public void upLoad(final UpLoadListener listener,String toKen,final String fileName){
//        String toKen = Auth.create(Constant.ACCESS_KEY, Constant.SECRET_KEY).uploadToken(Constant.QINIU_ROOM);
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, final double percent) {
                        AsyncRun.runInMain(new Runnable() {
                            @Override
                            public void run() {
                                if(listener != null){
                                    listener.progress(percent*100);
                                }

                            }
                        });
                    }
                }, new UpCancellationSignal() {

            @Override
            public boolean isCancelled() {
                return cancelUpload;
            }
        });
        uploadManager.put(
                FileUtil.getWavFileAbsolutePath(fileName),
                "music/"+fileName,
                toKen,
                new UpCompletionHandler() {
                    @Override
                    public void complete(final String key, final ResponseInfo info, JSONObject response) {
                        AsyncRun.runInMain(new Runnable() {
                            @Override
                            public void run() {
                                if(info.isOK()){
                                    LogUtil.d("文件上传成功:"+Constant.SECRET_HOST+key);
                                    if(listener != null){
                                        listener.onSuccess(fileName,Constant.SECRET_HOST+key);
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
                },uploadOptions);
    }

    public interface UpLoadListener{
        void onSuccess(String filePath, String url);
        void onError(String message);
        void progress(double progress);
    }
}