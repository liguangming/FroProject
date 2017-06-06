package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.photoclop.UCrop;
import org.fro.common.widgets.photoclop.util.BitmapLoadUtils;
import org.fro.common.widgets.photoclop.view.CropImageView;
import org.fro.common.widgets.photoclop.view.GestureCropImageView;
import org.fro.common.widgets.photoclop.view.OverlayView;
import org.fro.common.widgets.photoclop.view.TransformImageView;
import org.fro.common.widgets.photoclop.view.UCropView;

import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.di.component.DaggerPhotoCropComponent;
import fro.org.froproject.di.module.PhotoCropModule;
import fro.org.froproject.mvp.contract.PhotoCropContract;
import fro.org.froproject.mvp.presenter.PhotoCropPresenter;
import fro.org.froproject.mvp.ui.view.photoclip.PhotoConfig;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

public class PhotoCropActivity extends BaseActivity<PhotoCropPresenter> implements PhotoCropContract.View {
    private GestureCropImageView gestureCropImageView;
    @BindView(R.id.crop_view)
    UCropView cropView;
    private OverlayView overlayView;
    private Uri mOutputUri;

    @OnClick(R.id.tv_selected)
    public void selected() {
        cropAndSaveImage();
    }

    @OnClick(R.id.tv_cancel)
    public void cancle() {
        killMyself();
    }

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPhotoCropComponent.builder()
                .appComponent(appComponent)
                .photoCropModule(new PhotoCropModule(this)) //请将PhotoCropModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        return R.layout.activity_photo_crop;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initCropView();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    private void cropAndSaveImage() {
        PhotoConfig.deleteCroppedFile(this);
        OutputStream outputStream = null;
        try {
            final Bitmap croppedBitmap = gestureCropImageView.cropImage();
            if (croppedBitmap != null) {
                outputStream = getContentResolver().openOutputStream(mOutputUri);
                croppedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                croppedBitmap.recycle();
                setResultUri(mOutputUri, gestureCropImageView.getTargetAspectRatio());

                finish();
            } else {
                setResultException(new NullPointerException("CropImageView.cropImage() returned null."));
            }
        } catch (Exception e) {
            setResultException(e);
            finish();
        } finally {
            BitmapLoadUtils.close(outputStream);
        }
    }

    /**
     * 初始化裁剪View
     */
    private void initCropView() {
        cropView = (UCropView) findViewById(R.id.crop_view);
        gestureCropImageView = cropView.getCropImageView();
        gestureCropImageView.setScaleEnabled(true);
        gestureCropImageView.setRotateEnabled(false);
        gestureCropImageView.setRectHeight(getResources().getDimensionPixelOffset(R.dimen.sa_banner_height)); // 剪裁图片
        gestureCropImageView.setTopRectMargin(UiUtils.dip2px(this, 125));
        overlayView = cropView.getOverlayView();
        overlayView.setShowCropFrame(true);
        overlayView.setShowCropGrid(false);
        overlayView.setOvalDimmedLayer(false);
        overlayView.setDimmedColor(Color.parseColor(getString(R.string.sa_home_edit_pic_crop_bg)));
        overlayView.setmReactMarginTop(UiUtils.dip2px(this, 125));
        overlayView.setmReactHeight(getResources().getDimensionPixelOffset(R.dimen.sa_banner_height)); // 剪裁框
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        overlayView.setLayoutParams(params);
        gestureCropImageView.setTransformImageListener(mImageListener);
        Intent intent = getIntent();
        setImageData(intent);
    }

    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
        @Override
        public void onRotate(float currentAngle) {
//            setAngleText(currentAngle);
        }

        @Override
        public void onScale(float currentScale) {
//            setScaleText(currentScale);
        }

        @Override
        public void onLoadComplete() {
            Animation fadeInAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_center);
            fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    cropView.setVisibility(View.VISIBLE);
                    gestureCropImageView.setImageToWrapCropBounds();
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            cropView.startAnimation(fadeInAnimation);
        }

        @Override
        public void onLoadFailure(Exception e) {
            setResultException(e);
            finish();
        }

    };

    private void setResultUri(Uri uri, float resultAspectRatio) {
        setResult(RESULT_OK, new Intent()
                .putExtra(UCrop.EXTRA_OUTPUT_URI, uri)
                .putExtra(UCrop.EXTRA_OUTPUT_CROP_ASPECT_RATIO, resultAspectRatio));
    }

    private void setResultException(Throwable throwable) {
        setResult(UCrop.RESULT_ERROR, new Intent().putExtra(UCrop.EXTRA_ERROR, throwable));
    }

    private void setImageData(Intent intent) {
        Uri inputUri = intent.getParcelableExtra(UCrop.EXTRA_INPUT_URI);
        mOutputUri = intent.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);

        if (inputUri != null && mOutputUri != null) {
            try {
                gestureCropImageView.setImageUri(inputUri);
            } catch (Exception e) {
                setResultException(e);
                finish();
            }
        } else {
            setResultException(new NullPointerException("Both input and output Uri must be specified"));
            finish();
        }

        // 设置裁剪宽高比
        if (intent.getBooleanExtra(UCrop.EXTRA_ASPECT_RATIO_SET, false)) {
            float aspectRatioX = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_X, 0);
            float aspectRatioY = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_Y, 0);

            if (aspectRatioX > 0 && aspectRatioY > 0) {
                gestureCropImageView.setTargetAspectRatio(aspectRatioX / aspectRatioY);
            } else {
                gestureCropImageView.setTargetAspectRatio(CropImageView.SOURCE_IMAGE_ASPECT_RATIO);
            }
        }

        // 设置裁剪的最大宽高
        if (intent.getBooleanExtra(UCrop.EXTRA_MAX_SIZE_SET, false)) {
            int maxSizeX = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_X, 0);
            int maxSizeY = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_Y, 0);

            if (maxSizeX > 0 && maxSizeY > 0) {
                gestureCropImageView.setMaxResultImageSizeX(maxSizeX);
                gestureCropImageView.setMaxResultImageSizeY(maxSizeY);
            } else {
                Log.w(TAG, "EXTRA_MAX_SIZE_X and EXTRA_MAX_SIZE_Y must be greater than 0");
            }
        }
    }
}