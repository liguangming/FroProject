package org.fro.common.widgets.photoclop.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Oleksii Shliama (https://github.com/shliama).
 */
public class BitmapLoadUtils {

    private static final String TAG = "BitmapLoadUtils";
    private static final int SUCCESS = 200;
    private static final int FAILED = 201;

    public interface BitmapLoadCallback {

        void onBitmapLoaded(@NonNull Bitmap bitmap);

        void onFailure(@NonNull Exception bitmapWorkerException);

    }

    public static void decodeBitmapInBackground(@NonNull Context context, @Nullable Uri uri,
                                                int requiredWidth, int requiredHeight,
                                                BitmapLoadCallback loadCallback) {
        new BitmapWorkerTask(context, uri, requiredWidth, requiredHeight, loadCallback).start();
    }

    private static class BitmapWorkerResult {

        Bitmap mBitmapResult;
        Exception mBitmapWorkerException;

        public BitmapWorkerResult(@Nullable Bitmap bitmapResult, @Nullable Exception bitmapWorkerException) {
            mBitmapResult = bitmapResult;
            mBitmapWorkerException = bitmapWorkerException;
        }

    }

    /**
     * Creates and returns a Bitmap for a given Uri.
     * inSampleSize is calculated based on requiredWidth property. However can be adjusted if OOM occurs.
     * If any EXIF config is found - bitmap is transformed properly.
     */
    private static class BitmapWorkerTask extends Thread {

        private final Context mContext;
        private final Uri mUri;
        private final int mRequiredWidth;
        private final int mRequiredHeight;

        private BitmapWorkerResult bitmapWorkerResult;
        private final BitmapLoadCallback mBitmapLoadCallback;

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SUCCESS:
                        if (bitmapWorkerResult == null) {
                            mBitmapLoadCallback.onFailure(new Exception("BitmapLoadCallback is null"));
                        } else {
                            mBitmapLoadCallback.onBitmapLoaded(bitmapWorkerResult.mBitmapResult);
                        }
                        break;
                    case FAILED:
                        if (bitmapWorkerResult == null) {
                            mBitmapLoadCallback.onFailure(new Exception("BitmapLoadCallback is null"));
                        } else {
                            mBitmapLoadCallback.onFailure(bitmapWorkerResult.mBitmapWorkerException);
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        BitmapWorkerTask(@NonNull Context context, @Nullable Uri uri,
                         int requiredWidth, int requiredHeight,
                         BitmapLoadCallback loadCallback) {
            mContext = context;
            mUri = uri;
            mRequiredWidth = requiredWidth;
            mRequiredHeight = requiredHeight;
            mBitmapLoadCallback = loadCallback;
        }

        @Override
        public void run() {
            super.run();
            if (mUri == null) {
                bitmapWorkerResult = new BitmapWorkerResult(null, new NullPointerException("Uri cannot be null"));
                handler.sendEmptyMessage(FAILED);
                return;
            }
            try {
                final ParcelFileDescriptor parcelFileDescriptor = mContext.getContentResolver().openFileDescriptor(mUri, "r");
                final FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                if (options.outWidth == -1 || options.outHeight == -1) {
                    bitmapWorkerResult = new BitmapWorkerResult(null, new IllegalArgumentException("Bounds for bitmap could not be retrieved from Uri"));
                    handler.sendEmptyMessage(FAILED);
                    return;
                }

                options.inSampleSize = calculateInSampleSize(options, mRequiredWidth, mRequiredHeight);
                options.inJustDecodeBounds = false;

                Bitmap decodeSampledBitmap = null;

                boolean decodeAttemptSuccess = false;
                while (!decodeAttemptSuccess) {
                    try {
                        decodeSampledBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                        decodeAttemptSuccess = true;
                    } catch (OutOfMemoryError error) {
                        options.inSampleSize++;
                    }
                }

                if (decodeSampledBitmap == null) {
                    bitmapWorkerResult = new BitmapWorkerResult(null, new IllegalArgumentException("Bitmap could not be decoded from Uri"));
                    handler.sendEmptyMessage(FAILED);
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    close(parcelFileDescriptor);
                }

                int exifOrientation = getExifOrientation(mContext, mUri);
                int exifDegrees = exifToDegrees(exifOrientation);
                int exifTranslation = exifToTranslation(exifOrientation);

                Matrix matrix = new Matrix();
                if (exifDegrees != 0) {
                    matrix.preRotate(exifDegrees);
                }
                if (exifTranslation != 1) {
                    matrix.postScale(exifTranslation, 1);
                }
                if (!matrix.isIdentity()) {
                    bitmapWorkerResult = new BitmapWorkerResult(transformBitmap(decodeSampledBitmap, matrix), null);
                    handler.sendEmptyMessage(SUCCESS);
                    return;
                }
                bitmapWorkerResult = new BitmapWorkerResult(decodeSampledBitmap, null);
                handler.sendEmptyMessage(SUCCESS);
            } catch (Exception e) {
                bitmapWorkerResult = new BitmapWorkerResult(null, e);
                handler.sendEmptyMessage(FAILED);
            }
        }
    }

    private static Bitmap transformBitmap(@NonNull Bitmap bitmap, @NonNull Matrix transformMatrix) {
        try {
            Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), transformMatrix, true);
            if (bitmap != converted) {
                bitmap.recycle();
                bitmap = converted;
            }
        } catch (OutOfMemoryError error) {
        }
        return bitmap;
    }

    private static int calculateInSampleSize(@NonNull BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width lower or equal to the requested height and width.
            while ((height / inSampleSize) > reqHeight || (width / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static int getExifOrientation(@NonNull Context context, @NonNull Uri imageUri) {
        int orientation = ExifInterface.ORIENTATION_UNDEFINED;
        try {
            InputStream stream = context.getContentResolver().openInputStream(imageUri);
            if (stream == null) {
                return orientation;
            }
            orientation = new ImageHeaderParser(stream).getOrientation();
            close(stream);
        } catch (IOException e) {
        }
        return orientation;
    }

    private static int exifToDegrees(int exifOrientation) {
        int rotation;
        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
            case ExifInterface.ORIENTATION_TRANSPOSE:
                rotation = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                rotation = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
            case ExifInterface.ORIENTATION_TRANSVERSE:
                rotation = 270;
                break;
            default:
                rotation = 0;
        }
        return rotation;
    }

    private static int exifToTranslation(int exifOrientation) {
        int translation;
        switch (exifOrientation) {
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
            case ExifInterface.ORIENTATION_TRANSPOSE:
            case ExifInterface.ORIENTATION_TRANSVERSE:
                translation = -1;
                break;
            default:
                translation = 1;
        }
        return translation;
    }

    @SuppressWarnings("ConstantConditions")
    public static void close(@Nullable Closeable c) {
        if (c != null && c instanceof Closeable) { // java.lang.IncompatibleClassChangeError: interface not implemented
            try {
                c.close();
            } catch (IOException e) {
                // silence
            }
        }
    }

}
