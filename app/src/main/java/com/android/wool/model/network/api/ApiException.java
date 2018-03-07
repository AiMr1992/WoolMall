package com.android.wool.model.network.api;
/**
 * Created by AiMr on 2017/9/21
 */
public class ApiException extends RuntimeException{
    public ApiException(String detailMessage) {
        super(detailMessage);
    }
}
