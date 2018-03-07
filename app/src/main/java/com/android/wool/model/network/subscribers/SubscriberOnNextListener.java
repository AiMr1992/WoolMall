package com.android.wool.model.network.subscribers;
import com.android.wool.model.network.ResponseMol;
/**
 * Created by AiMr on 2017/9/21.
 */
public interface SubscriberOnNextListener<T extends ResponseMol>{
    void onNext(T t);
}
