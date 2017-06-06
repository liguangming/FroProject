package fro.org.froproject.mvp.ui.view.photoclip;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import fro.org.froproject.app.utils.FileUtils;

/**
 * 相簿工具类
 */
public class AlbumUtils {
    public static final String TAG = "AlbumUtils";

    /**
     * 得到相册图片对象
     * @param context
     * @param limit
     * @return
     */
    static ArrayList<ImageItem> getLstAlbums(Context context, int limit) {
        ArrayList<ImageItem> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_TAKEN},
                    null, null, MediaStore.Images.Media.DATE_TAKEN + " desc limit " + limit);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ImageItem item = new ImageItem();
                    item.setImageId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                    item.setImagePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    item.setDate(cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
                    item.setImage(true);
                    lst.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "e=" + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return lst;
    }

    /**
     * 保存剪切的图片到系统相簿
     * 注：sendBroadcast+Intent.ACTION_MEDIA_SCANNER_SCAN_FILE不能准确更新指定文件，使用MediaScannerConnection.scanFile
     * @param context
     * @throws IOException
     */
    public static void updateToAlbum(Context context) {
        String fileName = System.currentTimeMillis() + ".jpeg";
        try {
            File file = FileUtils.getSDCardFile(PhotoConfig.PHOTO_CROP_PATH, fileName);
            FileUtils.copyFile(PhotoConfig.getCroppedFile(context), file);
            // 插入到系统图库
            String fileNameUri = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
            // 通知图库更新
            Cursor cursor = context.getContentResolver().query(Uri.parse(fileNameUri), new String[] {MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                int columnId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String img_path = cursor.getString(columnId);
                cursor.close();
                MediaScannerConnection.scanFile(context, new String[]{img_path}, null, null);
            }
        } catch (IOException e) {
            // do nothing
            Log.d(TAG, "updateToAlbum failed e="+e.getMessage());
        }
    }
}
