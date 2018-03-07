package com.android.wool.model.network.conver;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;
/**
 * Created by AiMr on 2017/9/21.
 */
public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}