package com.android.wool.base;
/**
 * Created by AiMr on 2017/9/21
 */
public class DetachController<T extends IBaseView>{
    public DetachController(T mView){
        attach(mView);
    }
    protected T mView;
    public void attach(T mView) {
        this.mView = mView;
    }
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
    public boolean isAttach(){
        if(mView != null)
            return true;
        return false;
    }

    public void showLoading(){
        if(isAttach()){
            mView.showLoading();
        }
    }

    public void disMissProgress(){
        if(isAttach()){
            mView.disMissProgress();
        }
    }
}
