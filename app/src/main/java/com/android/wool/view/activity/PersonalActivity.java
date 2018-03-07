package com.android.wool.view.activity;
import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.eventbus.IoMessage;
import com.android.wool.model.entity.MineEntity;
import com.android.wool.presenter.PersonalPresenterIml;
import com.android.wool.util.FileUtil;
import com.android.wool.util.ImageUtils;
import com.android.wool.util.qiniu.QiNiuUpLoadManager;
import com.android.wool.view.constom.MyAlertDialog;
import com.android.wool.view.constom.SelectPhotoDialog;
import com.android.wool.view.constom.SelectSexDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import butterknife.BindView;
/**
 * Created by AiMr on 2017/9/23
 */
public class PersonalActivity extends BaseActivity<PersonalPresenterIml> implements PersonalView{
    @BindView(R.id.layout_name)
    LinearLayout layoutName;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.layout_sex)
    LinearLayout layoutSex;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.layout_photo)
    RelativeLayout layoutPhoto;
    @BindView(R.id.round_view)
    ImageView roundImageView;
    private String sex = "";
    private MineEntity entity;
    private String imgPath;

    private final int GET_PICTURE_CODE = 101;
    private final int TAKE_PHOTO = 103;
    //拍照
    private String filePath1;
    //相册
    private String takePhotoFilePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PersonalPresenterIml(this);
        initView();
    }

    private void initView() {
        roundImageView.setImageResource(R.drawable.default_mine);
        entity = getIntent().getParcelableExtra("entity");
        if(entity != null) {
            ImageUtils.downloadQiNiuCropCircle(roundImageView.getWidth(),
                    roundImageView.getHeight(),
                    roundImageView, ImageView.ScaleType.FIT_XY,
                    entity.getAvatar(),
                    0, 0);
            tvName.setText(entity.getName());
            sex = entity.getSex();
            tvSex.setText(entity.getSex().equals("0") ? "男" : "女");
        }

        setLeftBack(R.drawable.back,"",true);
        setTitle(R.string.personal);
        setRightNext(0,R.string.save,true);
        layoutName.setOnClickListener(this);
        layoutSex.setOnClickListener(this);
        layoutPhoto.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void qiNiuToken(String token,File file) {
        presenter.upLoadQiNiuFile(this,file,token,QiNiuUpLoadManager.FILE_MINE_PHOTO);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEven(IoMessage event){
        tvName.setText(event.getT().toString());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_name:
                Intent intent = new Intent(this,ResetNameActivity.class);
                intent.putExtra("name",tvName.getText().toString());
                startActivity(intent);
                break;
            case R.id.layout_left:
                finish();
                break;
            case R.id.layout_sex:
                SelectSexDialog dialog = new SelectSexDialog(this);
                dialog.setOnCloseClickListener(new SelectSexDialog.OnCloseClickListener() {
                    @Override
                    public void onCloseClick(boolean flag, String text, Dialog dialog) {
                        if(flag) {
                            sex = text;
                            tvSex.setText(text.equals("0") ? "男" : "女");
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.layout_photo:
                SelectPhotoDialog dialog1 = new SelectPhotoDialog(this);
                dialog1.setOnCloseClickListener(new SelectPhotoDialog.OnCloseClickListener() {
                    @Override
                    public void onCloseClick(boolean flag, int selectType, Dialog dialog) {
                        dialog.dismiss();
                        showSelectDialog(flag, selectType);
                    }
                });
                dialog1.show();
                break;
            case R.id.layout_right:
                if(TextUtils.isEmpty(tvName.getText().toString())){
                    Toast.makeText(this,"请填写姓名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(tvSex.getText().toString())){
                    Toast.makeText(this,"请选择性别",Toast.LENGTH_SHORT).show();
                    return;
                }
                presenter.updateUserInfo(this,tvName.getText().toString(),sex,imgPath);
                break;
        }
    }

    @Override
    public void showSelectDialog(boolean flag,int selectType) {
        if(flag){
            if(selectType == SelectPhotoDialog.PHOTO_PX){
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        //7.0拍照适配
                        if(Build.VERSION.SDK_INT > 23){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePhotoFilePath = FileUtil.getUploadDir(PersonalActivity.this)
                                    + FileUtil.getRandomPicName();
                            ContentValues contentValues = new ContentValues(1);
                            contentValues.put(MediaStore.Images.Media.DATA,takePhotoFilePath);
                            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, TAKE_PHOTO);
                        }else {
                            //6.0
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePhotoFilePath = FileUtil.getUploadDir(PersonalActivity.this)
                                    + FileUtil.getRandomPicName();
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(takePhotoFilePath)));
                            startActivityForResult(intent, TAKE_PHOTO);
                        }
                    }else  {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA
                                        ,Manifest.permission.READ_EXTERNAL_STORAGE},
                                101);
                    }
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePhotoFilePath = FileUtil.getUploadDir(PersonalActivity.this)
                            + FileUtil.getRandomPicName();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(takePhotoFilePath)));
                    startActivityForResult(intent, TAKE_PHOTO);
                }
            }else {
                if (Build.VERSION.SDK_INT >= 23){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, GET_PICTURE_CODE);
                    }else {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                102);
                    }
                }else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, GET_PICTURE_CODE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasAllGranted = true;
        for (int i = 0; i < grantResults.length; ++i) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                hasAllGranted = false;
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    String msg = TextUtils.equals(Manifest.permission.CAMERA,permissions[i])
                            ?getString(R.string.permissions_camera): getString(R.string.permissions_wr);
                    MyAlertDialog dialog = new MyAlertDialog(this);
                    dialog.setMsg(msg);
                    dialog.setCenterButton(R.string.center_y, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    dialog.show();
                }
                break;
            }
        }
        if (hasAllGranted) {
            //权限请求成功
            if(requestCode == 101){
                if(Build.VERSION.SDK_INT > 23){
                    if(checkSelfPermission(Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED &&
                            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePhotoFilePath = FileUtil.getUploadDir(PersonalActivity.this)
                                + FileUtil.getRandomPicName();
                        ContentValues contentValues = new ContentValues(1);
                        contentValues.put(MediaStore.Images.Media.DATA, takePhotoFilePath);
                        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, TAKE_PHOTO);
                    }
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePhotoFilePath = FileUtil.getUploadDir(PersonalActivity.this)
                            + FileUtil.getRandomPicName();
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(takePhotoFilePath)));
                    startActivityForResult(intent, TAKE_PHOTO);
                }
            }else if(requestCode == 102){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, GET_PICTURE_CODE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GET_PICTURE_CODE:
                    Uri uri = data.getData();
                    String filePath = FileUtil.getImageAbsolutePath(this, uri);
                    filePath1 = FileUtil.getUploadDir(this)+FileUtil.getRandomPicName();
                    FileUtil.compressPic(filePath, filePath1,0);
                    setImg();
                    break;
                case TAKE_PHOTO:
                    File file = new File(takePhotoFilePath);
                    if(file.exists()){
                        String tempPath = FileUtil.getUploadDir(this) + FileUtil.getRandomPicName();
                        FileUtil.compressPic(takePhotoFilePath, tempPath,0);
                        filePath1 = tempPath;
                        setImg();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImg(){
        File file = new File(filePath1);
        if(file.exists()){
            presenter.qiNiuToken(file);
        }
    }

    @Override
    public void upload(String path,File file) {
        imgPath = path;
        ImageUtils.downloadWidthLocationCropCircle(
                roundImageView.getWidth(),
                roundImageView.getHeight(),
                roundImageView,
                ImageView.ScaleType.FIT_XY,
                file,
                0,0);
    }

    @Override
    public void showLoading() {
        showLoadingProgressDialog(null);
    }

    @Override
    public void disMissProgress() {
        dismissProgressDialog();
    }

    @Override
    public void finishUser() {
        finish();
    }
}
