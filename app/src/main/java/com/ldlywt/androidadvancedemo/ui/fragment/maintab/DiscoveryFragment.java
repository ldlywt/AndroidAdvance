package com.ldlywt.androidadvancedemo.ui.fragment.maintab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.ldlywt.androidadvancedemo.R;
import com.ldlywt.base.view.BaseFragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * <pre>
 *     author : lex
 *     e-mail : ldlywt@163.com
 *     time   : 2018/08/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class DiscoveryFragment extends BaseFragment implements SurfaceHolder.Callback, View.OnClickListener {

    private ImageView mImageView;
    private SurfaceView mSurfaceView;
    private android.hardware.Camera mCamera;

    public static DiscoveryFragment newInstance(String from) {
        DiscoveryFragment fragment = new DiscoveryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_discovery;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        mCamera = android.hardware.Camera.open();
        mCamera.setDisplayOrientation(90);
    }

    private void loadImage2() {
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (holder == null) {
                    return;
                }
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);
                InputStream inputStream = null;
                try {
                    inputStream = getActivity().getAssets().open("11.png");
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Canvas canvas = holder.lockCanvas();
                    canvas.drawBitmap(bitmap, 0, 0, paint);
                    holder.unlockCanvasAndPost(canvas);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void loadImage1() {
        try {
            InputStream inputStream = getActivity().getAssets().open("11.png");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        mImageView = getView().findViewById(R.id.iv);
        mSurfaceView = getView().findViewById(R.id.surfaceview);
        getView().findViewById(R.id.bt1).setOnClickListener(this);
//        loadImage1();
//        loadImage2();
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.release();
    }

    @Override
    public void onClick(View v) {

    }
}
