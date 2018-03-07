package com.android.wool.presenter;
import android.content.Context;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.model.interactor.PersonalInteractor;
import com.android.wool.model.interactor.PersonalInteractorImpl;
import com.android.wool.model.interactor.QiNiuTokenInteractor;
import com.android.wool.model.interactor.QiNiuTokenInteractorImpl;
import com.android.wool.model.interactor.UpLoadFileInteractor;
import com.android.wool.model.interactor.UpLoadFileInteractorImpl;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.view.activity.PersonalView;
import org.xutils.x;
import java.io.File;
/**
 * Created by AiMr on 2017/10/11
 */
public class PersonalPresenterIml extends DetachController<PersonalView> implements PersonalPresenter{
    private PersonalInteractor interactor;
    private UpLoadFileInteractor upLoadFileInteractor;
    private QiNiuTokenInteractor tokenInteractor;
    public PersonalPresenterIml(PersonalView mView) {
        super(mView);
        interactor = new PersonalInteractorImpl();
        upLoadFileInteractor = new UpLoadFileInteractorImpl();
        tokenInteractor = new QiNiuTokenInteractorImpl();
    }

    @Override
    public void qiNiuToken(final File file) {
        showLoading();
        tokenInteractor.qiNiuToken(new QiNiuTokenInteractor.QiNiuTokenListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                disMissProgress();
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.qiNiuToken(response.getString("list"),file);
                }
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                disMissProgress();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }

    @Override
    public void updateUserInfo(final Context context, String name, String sex,String imgPath) {
        showLoading();
        interactor.updateUserInfo(context, name, sex,imgPath,
                new PersonalInteractor.PersonalListener() {
                    @Override
                    public void onSuccess(ResponseMol response) {
                        if(response != null && response.isRequestSuccess() && isAttach()){
                            mView.finishUser();
                        }
                        disMissProgress();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }

                    @Override
                    public void onFailed() {
                        disMissProgress();
                    }
                });
    }

    @Override
    public void uploadFile(final Context context, final File file) {
        showLoading();
        upLoadFileInteractor.uploadFile(file, new UpLoadFileInteractor.UpLoadListener() {
            @Override
            public void onSuccess(ResponseMol response) {
                if(response != null && response.isRequestSuccess() && isAttach()){
                    mView.upload(response.getString("pic_url"),file);
                }
                disMissProgress();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                disMissProgress();
            }

            @Override
            public void onFailed() {
                disMissProgress();
            }
        });
    }

    @Override
    public void upLoadQiNiuFile(final Context context, File file,String token,int type) {
        showLoading();
        upLoadFileInteractor.upLoadQiNiuFile(file,token,
                new UpLoadFileInteractor.UpLoadQiNiuListener() {
                    @Override
                    public void onSuccess(File file,String url) {
                        disMissProgress();
                        if (isAttach())
                            mView.upload(url,file);
                    }

                    @Override
                    public void onProgress() {

                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(x.app(), message,Toast.LENGTH_SHORT).show();
                        disMissProgress();
                    }
                },
                type,
                context);
    }
}
