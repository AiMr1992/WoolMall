package com.android.wool.view.activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.eventbus.LocationMessage;
import com.android.wool.model.entity.LocationEntity;
import com.android.wool.presenter.LocationAddPresenter;
import com.android.wool.presenter.LocationAddPresenterImpl;
import com.android.wool.util.AppTools;
import com.android.wool.view.constom.CheckCityDialog;
import com.android.wool.view.constom.MultiEditTextView;
import com.android.wool.view.constom.MyAlertDialog;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/25
 */
public class LocationAddActivity extends BaseActivity<LocationAddPresenterImpl> implements LocationAddView{
    @BindView(R.id.edit_receiver)
    MultiEditTextView editName;
    @BindView(R.id.edit_phone)
    MultiEditTextView editPhone;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.check_expire)
    CheckBox checkBox;
    @BindView(R.id.editText)
    EditText editText;
    private boolean check;
    private LocationEntity entity;
    private CheckCityDialog.CityEntity cityEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LocationAddPresenterImpl(this);
        initView();
    }

    private void initView(){
        setLeftBack(R.drawable.back,"",true);
        findViewById(R.id.mine_location).setOnClickListener(this);
        tvSave.setOnClickListener(this);
        presenter.switchPager(getIntent().getIntExtra("type",-1));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                check = isChecked;
            }
        });

        entity = getIntent().getParcelableExtra("entity");
        if(entity != null) {
            editName.setText(entity.getContact_name());
            editPhone.setText(entity.getContact_phone());
            tvCity.setText(entity.getCity());
            editText.setText(entity.getAddress());
            checkBox.setChecked("1".equals(entity.getIs_default()));
        }
    }

    @Override
    public void addLocationTitle() {
        setTitle(R.string.add_locations);
    }

    @Override
    public void updateLocationTitle() {
        setTitle(R.string.update_locations);
        setRightNext(0,R.string.delete,true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_location_add;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
            case R.id.mine_location:
                CheckCityDialog dialog = new CheckCityDialog(this);
                dialog.setOnCheckCityListener(new CheckCityDialog.OnCheckCityListener() {
                    @Override
                    public void cityPicker(CheckCityDialog.CityEntity entity) {
                        cityEntity = entity;
                        tvCity.setText(entity.toString());
                    }
                });
                dialog.show();
                break;
            case R.id.layout_right:
                if(TextUtils.isEmpty(editName.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(editPhone.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver_phone,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(tvCity.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver_city,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver_content,Toast.LENGTH_SHORT).show();
                    return;
                }
                MyAlertDialog myAlertDialog = new MyAlertDialog(this);
                myAlertDialog.setLeftButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                myAlertDialog.setRightButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        presenter.delLocation(LocationAddActivity.this,
                                entity.getId(),getIntent().getIntExtra("position",-1));
                    }
                });
                myAlertDialog.setMsg(R.string.or_delete);
                myAlertDialog.show();
                break;
            case R.id.tv_save:
                if(TextUtils.isEmpty(editName.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!AppTools.isMobileNo(editPhone.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver_phone,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(tvCity.getText().toString()) || "请选择".equals(tvCity.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver_city,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editText.getText().toString())){
                    Toast.makeText(this,R.string.none_receiver_content,Toast.LENGTH_SHORT).show();
                    return;
                }
                //新增
                if(getIntent().getIntExtra("type",-1) == LocationAddPresenter.LOCATION_ADD){
                    addLocation();
                }else {
                    updateLocation();
                }
                break;
        }
    }

    @Override
    public void addLocation() {
        presenter.addLocation(this,
                editName.getText().toString(),editPhone.getText().toString(),
                editText.getText().toString(),check?"1":"0",cityEntity.province_id,
                cityEntity.city_id,cityEntity.couny_id,cityEntity.province,
                cityEntity.city,cityEntity.couny);
    }

    @Override
    public void updateLocation() {
        if(cityEntity != null){
            presenter.updateLocation(this,
                    editName.getText().toString(), editPhone.getText().toString(),
                    editText.getText().toString(), check ? "1" : "0", cityEntity.province_id,
                    cityEntity.city_id, cityEntity.couny_id, cityEntity.province,
                    cityEntity.city, cityEntity.couny, entity.getId(), getIntent().getIntExtra("position", -1));
        }else {
            presenter.updateLocation(this,
                    editName.getText().toString(), editPhone.getText().toString(),
                    editText.getText().toString(), check ? "1" : "0", entity.getProvince_id(),
                    entity.getCity_id(), entity.getCountry_id(), entity.getProvince_name(),
                    entity.getCity_name(), entity.getCountry_name(), entity.getId(), getIntent().getIntExtra("position", -1));
        }
    }

    @Override
    public void addLocationSuccess(LocationEntity entity) {
        EventBus.getDefault().post(new LocationMessage(LocationMessage.INSER,-1,entity));
        finish();
    }

    @Override
    public void updateLocationSuccess(LocationEntity entity,int position) {
        EventBus.getDefault().post(new LocationMessage<>(LocationMessage.UPDATE,position,entity));
        finish();
    }

    @Override
    public void delLocationSuccess(int position) {
        EventBus.getDefault().post(new LocationMessage<>(LocationMessage.DELETE,position,entity));
        finish();
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }
}