package com.android.wool.util.qiniu;
/**
 * Created by AiMr on 2017/11/20
 */
public class QiNiuUpLoadManager {
    public static final int FILE_MUSIC = 1;
    public static final int FILE_MINE_PHOTO= 2;
    public static final int FILE_COURSE_K = 3;
    public static final int FILE_COURSE_Z = 4;
    public QiNiuPhotoUpLoad getQiNiuPhotoInstance(){
        return QiNiuPhotoUpLoad.getInstance();
    }
    public QiNiuMusicUpLoad getQiNiuMusicInstance(){
        return QiNiuMusicUpLoad.getInstance();
    }
}
