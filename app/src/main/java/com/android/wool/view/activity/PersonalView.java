package com.android.wool.view.activity;
import com.android.wool.base.IBaseView;
import java.io.File;
/**
 * Created by AiMr on 2017/10/11
 */
public interface PersonalView extends IBaseView{
    void upload(String path, File file);
    void finishUser();
    void showSelectDialog(boolean flag,int selectType);
    void qiNiuToken(String token,File file);
}
