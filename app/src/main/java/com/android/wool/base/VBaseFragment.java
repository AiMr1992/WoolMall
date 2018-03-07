package com.android.wool.base;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.wool.view.constom.ProgressDialog;
import butterknife.ButterKnife;

/**
 * Created by AiMr on 2017/9/23.
 */
public abstract class VBaseFragment<T extends DetachController> extends Fragment implements View.OnClickListener{
    protected View rootView;
    public T presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(getLayoutId(),null);
        ButterKnife.bind(this,rootView);
        initView();
        return rootView;
    }

    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void init();

    @Override
    public void onClick(View v) {
    }

    private ProgressDialog progressDialog;
    public void showLoadingProgressDialog(DialogInterface.OnCancelListener onCancelListener){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getActivity(),onCancelListener);
        }
        if(!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public void dismissProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter != null)
            presenter.detachView();
    }

    public abstract void data();
}
