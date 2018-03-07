package com.android.wool.view.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.android.wool.R;
import com.android.wool.base.BaseActivity;
import com.android.wool.presenter.ScanPresenterImpl;
import com.android.wool.util.LogUtil;
import com.android.wool.view.constom.MyAlertDialog;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.client.android.AmbientLightManager;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.client.android.CaptureActivityHandler;
import com.google.zxing.client.android.InactivityTimer;
import com.google.zxing.client.android.ViewfinderView;
import com.google.zxing.client.android.camera.CameraManager;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
/**
 * Created by AiMr on 2017/10/11
 */
public class ScanQcActivity extends BaseActivity<ScanPresenterImpl> implements SurfaceHolder.Callback,ScanView{
    private ViewfinderView viewfinderView;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;
    private Collection<BarcodeFormat> decodeFormats;
    private String characterSet;
    private Map<DecodeHintType,?> decodeHints;
    private SurfaceHolder surfaceHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScanPresenterImpl(this);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);
        initView();
    }

    private void initView() {
        setTitle(R.string.scan_title);
        setLeftBack(R.drawable.back,"",true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_left:
                finish();
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager = new CameraManager(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);
        handler = null;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();

        Intent intent = getIntent();

        decodeFormats = null;
        characterSet = null;

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            surfaceHolder = holder;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public void handleDecode(Result obj, Bitmap barcode, float scaleFactor) {
        String result = obj.getText().toString();
        LogUtil.d("扫描结果："+result);
        presenter.scanResult(this,result);
//        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
//        restartScan();
    }

    public void drawViewfinder() {

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public Handler getHandler() {
        return handler;
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
//      decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();
        cameraManager.closeDriver();
        //historyManager = null; // Keep for onActivityResult
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**  扫描遇到问题  */
    private void displayFrameworkBugMessageAndExit() {
        MyAlertDialog dialog = new MyAlertDialog(this);
        dialog.setMsg(R.string.permissions_camera);
        dialog.setCenterButton(R.string.center_y, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    /** 重新激活扫描 */
    public void restartScan(){
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
        initCamera(surfaceHolder);
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
    public void finishPager() {
        finish();
    }

    @Override
    public void resetScan() {
        restartScan();
    }
}
