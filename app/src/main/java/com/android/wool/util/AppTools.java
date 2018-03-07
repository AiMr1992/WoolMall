package com.android.wool.util;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.android.wool.common.MyPreference;
import com.android.wool.util.qiniu.QiNiuUpLoadManager;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;
/**
 * Created by AiMr on 2017/9/21
 */
public class AppTools {
    //手机格式
    public static final String REGEX_MOBILE = "^1\\d{10}$";

    /**版本号*/
    public static String getAppVersion(Context context){
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo =  packageManager.getPackageInfo(context.getPackageName(), 0);
            if(packageInfo != null){
                return packageInfo.versionName;
            }
        }catch(PackageManager.NameNotFoundException e){
            LogUtil.e(e.getMessage());
        }
        return "";
    }

    /**手机号检测 */
    public static boolean isMobileNo(String mobile){
        if(!TextUtils.isEmpty(mobile) && Pattern.matches(REGEX_MOBILE,mobile)){
            return true;
        }
        return false;
    }

    /** dp -> px*/
    public static int turnDipToPx(float dip, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 打开软键盘
     */
    public static void openKeyBord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void closeKeyBord(EditText mEditText, Context mContext){
        InputMethodManager inputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static int getRow(int size){
        int row = 0;
        int n = size % 3;
        if(n == 0)
            row = size / 3;
        else {
            row = size / 3 + 1;
        }
        return row;
    }

    /**
     * 将毫秒时间转化为yyyy-MM-DD HH:mm
     */
    public static String parseLongDate(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(Long.parseLong(time)*1000));
        }catch (Exception e){

        }
        return "";
    }

    /**
     * 将毫秒时间转化为yyyy-MM-DD HH:mm
     */
    public static String parseLongDate(long time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(time));
        }catch (Exception e){

        }
        return "";
    }

    public static boolean isFileExists(String fileName){
        if(!TextUtils.isEmpty(fileName)) {
            String filePath = FileUtil.getWavFileAbsolutePath(fileName);
            File file = new File(filePath);
            if (file.exists()){
                return true;
            }
        }
        return false;
    }

    /**
     * 将毫秒时间转化为yyyy-MM-DD HH:mm
     */
    public static String parseMDate(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            return sdf.format(new Date(Long.parseLong(time)*1000));
        }catch (Exception e){

        }
        return "";
    }

    /**
     * 将毫秒时间转化为yyyy-MM-DD HH:mm
     */
    public static String parseSDate(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("s");
            return sdf.format(new Date(Long.parseLong(time)*1000));
        }catch (Exception e){

        }
        return "";
    }

    /**
     * HH
     */
    public static String parseHDate(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            return sdf.format(new Date(Long.parseLong(time)*1000));
        }catch (Exception e){

        }
        return "";
    }

    /**
     * 将毫秒时间转化为HH:mm
     */
    public static String parseTapeTime(long time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            return sdf.format(new Date(time));
        }catch (Exception e){

        }
        return "";
    }

    public static String parseTapeTime(String time){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            return sdf.format(new Date(Long.parseLong(time)*1000));
        }catch (Exception e){

        }
        return "";
    }

    /**
     * 文件名称
     */
    public static String parseFileName(int type,Context context){
        Random random = new Random();
        String uid = MyPreference.getInstance().getUid(context);
        StringBuilder sb = new StringBuilder();
        if(type == QiNiuUpLoadManager.FILE_MUSIC) {
            sb.append(uid);
            sb.append("_");
            sb.append(Math.abs(random.nextInt(100000)));
            sb.append(System.currentTimeMillis());
            sb.append(".wav");
        }else if(type == QiNiuUpLoadManager.FILE_MINE_PHOTO){
            sb.append("mine_photo/");
            sb.append(uid);
            sb.append("_");
            sb.append(Math.abs(random.nextInt(100000)));
            sb.append(System.currentTimeMillis());
        }else if(type == QiNiuUpLoadManager.FILE_COURSE_K) {
            sb.append("course_k/");
            sb.append(uid);
            sb.append("_");
            sb.append(Math.abs(random.nextInt(100000)));
            sb.append(System.currentTimeMillis());
        }else{
            sb.append("course_z/");
            sb.append(uid);
            sb.append("_");
            sb.append(Math.abs(random.nextInt(100000)));
            sb.append(System.currentTimeMillis());
        }
        return sb.toString();
    }

    public static String addition(String num1,String num2){
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        return bigDecimal1.add(bigDecimal2).toString();
    }

    public static String subtract(String num1,String num2){
        BigDecimal bigDecimal1 = new BigDecimal(num1);
        BigDecimal bigDecimal2 = new BigDecimal(num2);
        return bigDecimal1.subtract(bigDecimal2).toString();
    }

    /** 乘法四舍五入*/
    public static String roundPrice(String price,String proCount){
        if(!TextUtils.isEmpty(price) && !TextUtils.isEmpty(proCount)){
            try {
                BigDecimal bigDecimal = new BigDecimal(price);
                BigDecimal big = bigDecimal.multiply(new BigDecimal(proCount));
                BigDecimal big1 = big.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
                return big1.toPlainString();
            }catch (Exception e){

            }
        }
        return "";
    }

    /**
     * 保留两位小数
     * @param price
     * @return
     */
    public static String getScalePrice(String price){
        if(!TextUtils.isEmpty(price)){
            if(price.contains(".")){
                String[] prices = price.split("\\.");
                if(prices.length == 2){
                    String temp = prices[1];
                    if(temp.length() == 0){
                        price = price + "00";
                    }else if(temp.length() == 1){
                        price = price + "0";
                    }else if(temp.length() > 2){
                        price = prices[0] + "." +temp.substring(0,2);
                    }
                }
            }else{
                price = price + ".00";
            }
        }
        return price;
    }

    public static double getScaleNumber(double progress){
        BigDecimal b = new BigDecimal(progress);
        return b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isUpLoad(String current_page,String all_page){
        boolean flag = false;
        try {
            int currentPage = Integer.parseInt(current_page);
            int countPage = Integer.parseInt(all_page);
            if(currentPage < countPage){
                flag = false;
            }else {
                flag = true;
            }
        }catch (Exception e){
            LogUtil.e(e.getMessage());
            return false;
        }
        return  flag;
    }

    public static String getFileName(File file){
        return file.getName().substring(file.getName().lastIndexOf("."));
    }

    public static String getPotNumber(String number){
        try {
            int n = Integer.parseInt(number);
            if(n > 99){
                return "99+";
            }else if(n == 0){
                return "";
            }else {
                return  number;
            }
        }catch (Exception e){
            return "";
        }
    }
}