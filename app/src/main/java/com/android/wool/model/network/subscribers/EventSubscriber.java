package com.android.wool.model.network.subscribers;
import android.widget.Toast;
import com.android.wool.MyApplication;
import com.android.wool.R;
import com.android.wool.model.network.ResponseMol;
import com.android.wool.model.network.api.ApiException;
import com.android.wool.util.LogUtil;
import rx.Subscriber;
/**
 * Created by AiMr on 2017/9/21.
 */
public class EventSubscriber<T extends ResponseMol> extends Subscriber<T> {
    private SubscriberOnNextListener listener;
    public EventSubscriber(SubscriberOnNextListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        LogUtil.d(e.toString());
        if(e instanceof ApiException){
            //交互异常处理
        }else{
            if(listener != null)
                listener.onNext(null);
            Toast.makeText(MyApplication.getInstance(), R.string.net_work_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNext(T t) {
        if(listener != null)
            listener.onNext(t);
    }
}