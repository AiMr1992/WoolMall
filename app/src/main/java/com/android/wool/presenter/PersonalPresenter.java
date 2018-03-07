package com.android.wool.presenter;
import android.content.Context;
import java.io.File;
/**
 * Created by AiMr on 2017/10/11
 */
public interface PersonalPresenter {
    void updateUserInfo(Context context,String name,String sex,String imgPath);
    void uploadFile(Context context,File file);
    void upLoadQiNiuFile(Context context,File file,String token,int type);
    void qiNiuToken(File file);
}
