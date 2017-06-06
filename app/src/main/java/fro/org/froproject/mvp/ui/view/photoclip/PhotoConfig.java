package fro.org.froproject.mvp.ui.view.photoclip;

import android.content.Context;
import android.os.Environment;


import java.io.File;

import fro.org.froproject.app.Constants;

/**
 * 照片配置类
 * Created by shixm on 2016/11/4.
 */
public class PhotoConfig {
    public final static String PHOTO_CROP_PATH = Environment.getExternalStorageDirectory() + Constants.STORAGE_PATH_CROP;

    public static File getCroppedFile(Context context) {
        return new File(getDiskCacheDir(context), "cropImage.jpeg");
    }

    public static boolean deleteCroppedFile(Context context) {
        File file = new File(getDiskCacheDir(context), "cropImage.jpeg");
        return file.delete();
    }

    public static File getPhotoTempFile(Context context) {
        return new File(getDiskCacheDir(context), "photo.jpeg");
    }

    public static boolean deletePhotoTempFile(Context context) {
        File file = new File(getDiskCacheDir(context), "photo.jpeg");
        return file.delete();
    }

    private static String getDiskCacheDir(Context context) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }
}
