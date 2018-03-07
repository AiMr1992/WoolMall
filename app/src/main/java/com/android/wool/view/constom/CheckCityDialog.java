package com.android.wool.view.constom;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.common.MyPreference;
/**
 * Created by AiMr on 2017/9/27
 */
public class CheckCityDialog extends Dialog{
    private Window window;
    private Context context;
    private CityEntity cityEntity;
    public CheckCityDialog(Context context) {
        super(context, R.style.dialog_transparent);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        window = getWindow();
        window.setWindowAnimations(R.style.pop_anim_style);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }

    @Override
    public void show() {
        if(TextUtils.isEmpty(MyPreference.getInstance().getPreferenceData(context,MyPreference.FIRSER_INSERT_CITY))){
            Toast.makeText(context,"正在加载...",Toast.LENGTH_SHORT).show();
        }else {
            setContentView(R.layout.dialog_check_city);
            setCanceledOnTouchOutside(true);
            findViewById(R.id.close_pop).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            findViewById(R.id.selected_address).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null && cityEntity != null){
                        listener.cityPicker(cityEntity);
                    }
                    dismiss();
                }
            });
            CityPicker cityPicker = (CityPicker) findViewById(R.id.check_view);
            cityPicker.setOnSelectingListener(new CityPicker.OnSelectingListener() {
                @Override
                public void selected(boolean selected, String province_name, String province_id, String city_name, String city_id, String couny_name, String city_code) {
                    if(selected) {
                        cityEntity = new CityEntity();
                        cityEntity.province = province_name;
                        cityEntity.province_id = province_id;
                        cityEntity.city = city_name;
                        cityEntity.city_id = city_id;
                        cityEntity.couny = couny_name;
                        cityEntity.couny_id = city_code;
                    }
                }
            });
            super.show();
        }
    }

    public OnCheckCityListener listener;
    public void setOnCheckCityListener(OnCheckCityListener listener) {
        this.listener = listener;
    }
    public interface OnCheckCityListener{
        void cityPicker(CityEntity entity);
    }

    public static class CityEntity{
        public String province;
        public String province_id;
        public String city;
        public String city_id;
        public String couny;
        public String couny_id;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(province);
            stringBuilder.append("\r");
            stringBuilder.append(city);
            stringBuilder.append("\r");
            stringBuilder.append(couny);
            return stringBuilder.toString();
        }
    }
}