//package com.example.camerahardware_new;
//
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.hardware.Camera;
//import android.os.Handler;
//import android.os.HandlerThread;
//import android.util.Log;
//import android.view.PixelCopy;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.widget.Toast;
//
//import androidx.test.runner.screenshot.Screenshot;
//
//import java.io.IOException;
//
//import static android.content.ContentValues.TAG;
//
//public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
//    SurfaceHolder mSurfaceHolder;
//    Camera mCamera;
//
//
//
//    // Constructor that obtains context and camera
//    @SuppressWarnings("deprecation")
//    public CameraPreview(Context context, Camera camera) {
//        super(context);
//        this.mCamera = camera;
//        //surfaceView = new SurfaceView(context);
//        // addView(surfaceView);
//
//        //preview = (SurfaceView) findViewById(R.id.surfaceView);
//        this.mSurfaceHolder = this.getHolder();
//        this.mSurfaceHolder.addCallback(this);
//        this.mSurfaceHolder.setKeepScreenOn(true);
//
//        this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        try {
//            //mCamera.open();
//            mCamera.setPreviewDisplay(surfaceHolder);
//            mCamera.setDisplayOrientation(0);
//            mCamera.startPreview();
//        } catch (IOException e) {
//            Log.d("MyCameraApp", "camera prervierw should open");
//
//            // left blank for now
//        }
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        mCamera.stopPreview();
//       // mCamera.release();
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
//                               int width, int height) {
//        // start preview with new settings
//        try {
//            mCamera.setPreviewDisplay(surfaceHolder);
//            mCamera.setDisplayOrientation(0);
//            mCamera.startPreview();
//        } catch (Exception e) {
//            Log.d("MyCameraApp", "surface chaged");
////
//            // intentionally left blank for a test
//        }
//    }
//
//
//
//
//
//}
//
