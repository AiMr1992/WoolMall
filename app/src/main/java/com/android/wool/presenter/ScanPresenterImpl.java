package com.android.wool.presenter;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import com.android.wool.base.DetachController;
import com.android.wool.view.activity.GoodsInfoActivity;
import com.android.wool.view.activity.ScanView;
/**
 * Created by AiMr on 2017/10/27
 */
public class ScanPresenterImpl extends DetachController<ScanView> implements ScanPresenter{
    public ScanPresenterImpl(ScanView mView) {
        super(mView);
    }

    public void scanResult(Context context,String result){
        String type = "";
        String data = "";
        if(!TextUtils.isEmpty(result)){
            try {
                String[] strings = result.split("&");
                if (strings != null && strings.length > 0) {
                    for (int i = 0; i < strings.length; i++) {
                        String text = strings[i];
                        String[] s = text.split(":");
                        if (s != null && s.length > 0) {
                            String key = s[0];
                            String value = s[1];
                            if ("type".equals(key)) {
                                type = value;
                            } else if ("data".equals(key)) {
                                data = value;
                            }else {
                                Toast.makeText(context,"二维码不存在",Toast.LENGTH_SHORT).show();
                                mView.resetScan();
                            }
                        }
                    }
                    if ("goods".equals(type)) {
                        Intent intent = new Intent(context, GoodsInfoActivity.class);
                        intent.putExtra("id", data);
                        context.startActivity(intent);
                        mView.finishPager();
                    }
                }
            }catch (Exception e){
                Toast.makeText(context,"二维码不存在",Toast.LENGTH_SHORT).show();
                mView.resetScan();
            }
        }
    }
}