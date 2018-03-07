package com.android.wool.util;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AiMr on 2017/10/11
 */
public class FileUtil {
    public static final String DIR_UPDATE = "upload";
    public static final String FOLDER_NAME = "cyb_user";

    //录音文件根目录
    public  static String rootPath ="wool_music";
    //原始文件(不能播放)
    public final static String AUDIO_PCM_BASE_PATH = "/"+rootPath+"/pcm/";
    //可播放的高质量音频文件
    public final static String AUDIO_WAV_BASE_PATH = "/"+rootPath+"/wav/";
    //封面拍照图片
    public final static String COURSE_IMGAGE_PATH = "/"+rootPath+"/img/";

    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 获取上传目录
     */
    public static String getUploadDir(Context context) {
        String path = getExternalStoragePath(context) + DIR_UPDATE + File.separator;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    /**
     * 获取sd卡目录
     * @return
     */
    public static String getExternalStoragePath(Context context){
        String storageState = Environment.getExternalStorageState();
        String cacheDir = "";
        if(storageState.equals(Environment.MEDIA_MOUNTED)){
            cacheDir = Environment.getExternalStorageDirectory().getPath();
        }else{
            LogUtil.d("no sdcard");
            cacheDir = context.getCacheDir().getPath();
        }
        return cacheDir+File.separator+FOLDER_NAME+File.separator;
    }

    public static String getRandomPicName(){
        return "IMG"+System.currentTimeMillis()+".jpg";
    }

    public static void compressPic(String orgPath,String terPath,int rotate){
        if(!TextUtils.isEmpty(orgPath)){
            File file = new File(orgPath);
            if(file.exists() && file.isFile()){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(orgPath,options);
//                int scale = options.outWidth / 480;
//                if(scale < 1){
//                    scale = 1;
//                }
                int scale = calculateInSampleSize(options, 480, 800);
                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;
                Bitmap tempBitmap = BitmapFactory.decodeFile(orgPath, options);
                int degree = imageDegree(orgPath) + rotate;
                Matrix matrix = new Matrix();
                matrix.setRotate(degree);
                tempBitmap = Bitmap.createBitmap(tempBitmap,0,0,tempBitmap.getWidth(),
                        tempBitmap.getHeight(),matrix,true);

                FileOutputStream fos = null;
                try {
                    File terFile = new File(terPath);
                    if (!terFile.exists()) {
                        terFile.createNewFile();
                    }
                    fos = new FileOutputStream(terFile);
                    tempBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                    fos.flush();
                }catch (Exception e){
                    LogUtil.e(e.getMessage());
                }finally {
                    if(fos != null){
                        try {
                            fos.close();
                            tempBitmap.recycle();
                        }catch (IOException e){
                            LogUtil.e(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height/ (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static int imageDegree(String fileName){
        ExifInterface exifInterface = null;
        int degree = 0;
        try {
            exifInterface = new ExifInterface(fileName);
        }catch (IOException e){
            LogUtil.e(e.getMessage());
        }

        if(exifInterface != null){
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,-1);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        }
        return degree;
    }

    public static Bitmap getSmallBitmap(String filePath,int width){
        Bitmap bitmap = null;
        if(!TextUtils.isEmpty(filePath)){
            File file = new File(filePath);
            if(file.exists() && file.isFile()){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath,options);
                int scale = 1;
                if(options.outWidth > options.outHeight){
                    scale = options.outWidth / width;
                }else{
                    scale = options.outHeight / width;
                }
                if(scale < 1){
                    scale = 1;
                }

                options.inJustDecodeBounds = false;
                options.inSampleSize = scale;
                bitmap = BitmapFactory.decodeFile(filePath, options);
            }
        }
        return bitmap;
    }

    public static String getPcmFileAbsolutePath(String fileName){
        if(TextUtils.isEmpty(fileName)){
            throw new NullPointerException("fileName isEmpty");
        }
        if(!isSdcardExit()){
            throw new IllegalStateException("sd card no found");
        }
        String mAudioRawPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".pcm")) {
                fileName = fileName + ".pcm";
            }
            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASE_PATH;
            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioRawPath = fileBasePath + fileName;
        }

        return mAudioRawPath;
    }

    public static File compressImage(Bitmap bitmap,String fileName) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (bao.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            bao.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, bao);//这里压缩options%，把压缩后的数据存放到baos中
        }
        File terFile = null;
        try {
            terFile = new File(fileName);
            if (!terFile.exists()) {
                terFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(terFile);
            try {
                fos.write(bao.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return terFile;
    }

    public static String getWavFileAbsolutePath(String fileName) {
        if(fileName==null){
            throw new NullPointerException("fileName can't be null");
        }
        if(!isSdcardExit()){
            throw new IllegalStateException("sd card no found");
        }

        String mAudioWavPath = "";
        if (isSdcardExit()) {
            if (!fileName.endsWith(".wav")) {
                fileName = fileName + ".wav";
            }
            String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_WAV_BASE_PATH;
            File file = new File(fileBasePath);
            //创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            mAudioWavPath = fileBasePath + fileName;
        }
        return mAudioWavPath;
    }

    /** 判断是否有外部存储设备sdcard */
    public static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /** 获取全部pcm文件列表 */
    public static List<File> getPcmFiles() {
        List<File> list = new ArrayList<>();
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_PCM_BASE_PATH;

        File rootFile = new File(fileBasePath);
        if (!rootFile.exists()) {
        } else {

            File[] files = rootFile.listFiles();
            for (File file : files) {
                list.add(file);
            }
        }
        return list;

    }

    /** 获取全部wav文件列表  */
    public static List<File> getWavFiles() {
        List<File> list = new ArrayList<>();
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + AUDIO_WAV_BASE_PATH;
        File rootFile = new File(fileBasePath);
        if (!rootFile.exists()) {
        } else {
            File[] files = rootFile.listFiles();
            for (File file : files) {
                list.add(file);
            }
        }
        return list;
    }
}
