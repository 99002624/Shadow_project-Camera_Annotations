package com.example.camerahardware_new;


import androidx.annotation.NonNull;
//import android.graphics.Camera;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.Screenshot;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
//import android.hardware.camera2.*;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.SparseIntArray;
import android.widget.ToggleButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.graphics.Bitmap.CompressFormat;
import android.os.HandlerThread;
import java.lang.Object;
import java.util.Timer;
import java.util.TimerTask;

import 	android.graphics.Canvas;
import  	android.graphics.Paint;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {


    boolean flag1=false;
    boolean handFlag=false;
    final Context context = this;
    private Bitmap bmp;
    private ByteArrayOutputStream bos;
    private File dir_image2, dir_image;
    private FileInputStream fis;
    private FileOutputStream fos;

    int annoColor = 000000;

    ImageView myImage;
    ScreenCapture screen;
    String word="verification";
   // SurfaceView preview;
    ByteArrayInputStream fis2;

    //Harshits part declation

    private static final int REQUEST_CODE = 1000;
    private static final int REQUEST_PERMISSION = 1001;
    private static final int   PERMISSION_REQUEST_CODE = 200;
    private static final SparseIntArray ORIENTATION = new SparseIntArray();


    private MediaProjectionManager mMediaProjectionManager;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private MediaProjectionCallback mMediaProjectionCallback;
    private String mVideoUrl = "";

    private MediaRecorder mMediaRecorder;

    private int mScreenDensity;
    private static final int DISPLAY_WIDTH = 1920;
    private static final int DISPLAY_HEIGHT = 1080;

    static {
        ORIENTATION.append(Surface.ROTATION_0, 90);
        ORIENTATION.append(Surface.ROTATION_90, 0);
        ORIENTATION.append(Surface.ROTATION_180, 270);
        ORIENTATION.append(Surface.ROTATION_270, 180);
    }
    //ConstraintLayout mConstraintlayout;
    private RelativeLayout mRootLayout;
    private ToggleButton mToggleButton,HandAnnotation;

    // Sai kiran part declaration

    Button TextAnnotation,clearBtn,captureButton;
    EditText editTextFeild;

    private final static int START_DRAGGING = 0;
    private final static int STOP_DRAGGING = 1;
    private int status;
    int flag=0;
    float xAxis = 0f;
    float yAxis = 0f;
    float lastXAxis = 0f;
    float lastYAxis = 0f;
    int numberOfLines=0;
    int i=0;
    File imagePath;


    private Camera mCamera;
    //private CameraPreview mCameraPreview;
    boolean cam;
    private String currentPhotoPath="default path";
    GestureOverlayView gesture;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;

    private int seconds = 0;
    private boolean running = false;
    //private TextView timerTextView;
    TextView timerTextView;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gesture = (GestureOverlayView) findViewById(R.id.gestures);
        gesture.setEnabled(false);
        HandAnnotation = findViewById(R.id.toggleButton2);
        captureButton = (Button) findViewById(R.id.button_capture);
        mRootLayout = findViewById(R.id.Relative_Layout);
        TextAnnotation = (Button) findViewById(R.id.imageButton2);
        clearBtn= (Button) findViewById(R.id.clearbutton);
        editTextFeild = (EditText) findViewById(R.id.editTextTextPersonName);
        editTextFeild.setVisibility(View.GONE);
        mToggleButton = findViewById(R.id.toggleButton);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_Preview);
      
        if (CheckPermissionCamera()) {

            cameraopen();
//

        } else {
            requestPermission();
        }









        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.densityDpi;

        mMediaRecorder = new MediaRecorder();
        mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Log.d("data"," Identifng the error"+"    "+mMediaProjectionManager);
        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("data"," Identifng the error"+"  "+"enterin into thia function");
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)+
                        ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED){
                    Log.d("data"," Identifng the error"+"  "+"permission not granted");
                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)||
                            ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECORD_AUDIO)){
                        Log.d("data"," Identifng the error"+"  "+"permission not granted");
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]
                                        {
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.RECORD_AUDIO
                                        },
                                REQUEST_PERMISSION );

                    }else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]
                                        {
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.RECORD_AUDIO
                                        },
                                REQUEST_PERMISSION );
                    }
                } else {
                    //Toast();
                    toggleScreenShare(v);
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gesture.invalidate();
                gesture.clear(true);
                gesture.cancelClearAnimation();
            }
        });


        HandAnnotation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(HandAnnotation.isChecked()){
                    gesture.setEnabled(true);
//                    handFlag=true;
                }
                else{
                    gesture.setEnabled(false);
                    handFlag=false;
                }

            }
        });
        TextAnnotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextFeild.getVisibility()==View.GONE){
                    editTextFeild.setVisibility(View.VISIBLE);
                }
                else{
                    editTextFeild.setVisibility(View.GONE);
                }

            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyCameraApp", "mcamera access"+"   "+mCamera);
                Log.d("MyCameraApp", "camera open"+"   "+cam);
                try {

                    takePhoto();


                }
                catch (Exception e){
                    e.printStackTrace();
                }





            }
        });
        editTextFeild.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent me) {
                if(me.getAction()==MotionEvent.ACTION_DOWN){
                    status = START_DRAGGING;
                    final float x = me.getX();
                    final float y = me.getY();
                    lastXAxis = x;
                    lastYAxis = y;
                    v.setVisibility(View.INVISIBLE);
                }else if(me.getAction()==MotionEvent.ACTION_UP){
                    status = STOP_DRAGGING;
                    flag=0;
                    v.setVisibility(View.VISIBLE);
                }else if(me.getAction()==MotionEvent.ACTION_MOVE){
                    if (status == START_DRAGGING){
                        flag=1;
                        v.setVisibility(View.VISIBLE);
                        final float x = me.getX();
                        final float y = me.getY();
                        final float dx = x - lastXAxis;
                        final float dy = y - lastYAxis;
                        xAxis += dx;
                        yAxis += dy;
                        v.setX((int)xAxis);
                        v.setY((int)yAxis);
                        v.invalidate();
                    }
                }
                return false;
            }
        });

    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            Log.e("line","second line");

            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setDisplayOrientation(0);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("MyCameraApp", "camera prervierw should open");

            // left blank for now
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        // mCamera.release();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format,
                               int width, int height) {
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setDisplayOrientation(0);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d("MyCameraApp", "surface chaged");
//

        }
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();
        //  mCamera.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(CheckPermissionCamera()){

            mCamera.release();
        }





    }


    private boolean CheckPermissionCamera(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }



    private void requestPermission(){

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
    }

    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Unable to aceess cameara.", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();
        }
        return camera;
    }

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = null;
            try {
                pictureFile = getOutputMediaFile();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Input file problem.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                // finish();
                e.printStackTrace();
            }
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
//                finish();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Input file problem.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "input exception0.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    };
    private File getOutputMediaFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

//    public void runTimer()
//    {
//        final Handler handler = new Handler();
//
//
////        timerTextView = (TextView) findViewById(R.id.timertextview);
//        //timerTextView.setVisibility(View.INVISIBLE);
//
//        handler.post(new Runnable() {
//            @Override
//
//            public void run()
//            {
//                int hours = seconds / 3600;
//                int minutes = (seconds % 3600) / 60;
//                int secs = seconds % 60;
//
//                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
//
//                timerTextView.setText(time);
//
//                if (running) {
//                    seconds++;
//                }
//
//                handler.postDelayed(this, 1000);
//            }
//        });
//    }

    private void cameraopen(){
        mCamera = getCameraInstance();
        cam = checkCameraHardware(this);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
    }


    private void toggleScreenShare(View v) {
        ToggleButton toggleButton = (ToggleButton) v;
        if (toggleButton.isChecked()){
            initRecorder();
            recordScreen();

//            timerTextView.setVisibility(View.VISIBLE);
//            running = true;

        } else {
            if(mMediaRecorder!=null) {
                if (flag1 == true) {
                    Log.d("Error","this stop is running");
                    mMediaRecorder.stop();
                    mMediaRecorder.reset();
                    stopRecordScreen();
                    flag1 = false;

//                    timerTextView.setVisibility(View.INVISIBLE);
//                    running = false;
                    seconds = 0;
                }
            }
        }

    }

    private void recordScreen() {
        if (mMediaProjection == null){
            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(),REQUEST_CODE);
            return;
        }

        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
        flag1 = true;
    }

    private VirtualDisplay createVirtualDisplay(){
        return mMediaProjection.createVirtualDisplay("MainActivity", DISPLAY_WIDTH,DISPLAY_HEIGHT,
                mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(),
                null,null);
    }

    private void initRecorder() {
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

//            runTimer();


            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "CameraAnnotations/ScreenRecordings");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("App", "failed to creMediaProjectionManagerServiceate directory");
                }
            }


            mVideoUrl = mediaStorageDir +
                    new StringBuilder("/CamAnotRecord-").append(new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss")
                            .format(new Date())).append(".mp4").toString();

            mMediaRecorder.setOutputFile(mVideoUrl);
            mMediaRecorder.setVideoSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512*10000);
            mMediaRecorder.setVideoFrameRate(60);

            int rotation = getWindowManager().getDefaultDisplay().getRotation();
            int orientation = ORIENTATION.get(rotation+90);
            mMediaRecorder.setOrientationHint(orientation);
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("resultCode"," Identifng the error"+"    "+resultCode);
        Log.d("data"," Identifng the error"+"    "+data);


        if(requestCode != REQUEST_CODE){
            Toast.makeText(this, "Unk error", Toast.LENGTH_SHORT).show();
            return;
        }
        if(resultCode != RESULT_OK){
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            mToggleButton.setChecked(false);
            return;
        }
        mMediaProjectionCallback = new MediaProjectionCallback();

        Log.d("data"," Identifng the error"+"    "+mMediaProjectionCallback);

        try {
            mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Log.d("data"," Identifng the error"+"    "+mMediaProjection);


        mMediaProjection.registerCallback(mMediaProjectionCallback,null);

        mVirtualDisplay = createVirtualDisplay();
        mMediaRecorder.start();
        flag1=true;
        Log.d("Error"," this is media"+"     "+ mMediaProjection);
    }


    private class MediaProjectionCallback extends MediaProjection.Callback {

        @Override
        public void onStop() {
            super.onStop();

            if (mToggleButton.isChecked()){
                mToggleButton.setChecked(false);
                mMediaRecorder.stop();
                mMediaRecorder.reset();
            }

            mMediaProjection = null;
            stopRecordScreen();
        }
    }

    private void stopRecordScreen() {
        if(mVirtualDisplay == null)
            return;

        mVirtualDisplay.release();
        destroyMediaProjection();
    }

    private void destroyMediaProjection() {
        if (mMediaProjection != null){
            mMediaProjection.unregisterCallback(mMediaProjectionCallback);
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_PERMISSION:{
                if (grantResults.length > 0 && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                    Log.e("checking","it was entering in to the Request permission in switch");
                    toggleScreenShare(mToggleButton);
                }else
                {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]
                                    {
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.RECORD_AUDIO
                                    },
                            REQUEST_PERMISSION );

                }
                return;
            }
            case PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("permisooin"," grated");
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    mSurfaceView.setVisibility(View.INVISIBLE);
                    cameraopen();
                    mSurfaceView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "You need to allow access permissions", Toast.LENGTH_SHORT).show();
                            new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                            {
                                                requestPermission();
                                            }
                                        }
                                    };
                        }
                    }
                }

            }
        }
    }

//  ?
    private void takePhoto() throws IOException{

        // Create a bitmap the size of the scene view.
        final Bitmap bitmap = Bitmap.createBitmap(mSurfaceView.getWidth(),mSurfaceView.getHeight(),Bitmap.Config.ARGB_8888);



//        // Create a handler thread to offload the processing of the image.
       final HandlerThread handlerThread = new HandlerThread("PixelCopier");
       handlerThread.start();



        File picStorageDir = new File(Environment.getExternalStorageDirectory(), "CameraAnnotations/Snapshots");

        if (!picStorageDir.exists()) {
            if (!picStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }

        String mPhotoUrl = picStorageDir +
                new StringBuilder("/CamAnotPhoto-").append(new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss")
                        .format(new Date())).append(".jpg").toString();

        File imageShot = new File(mPhotoUrl);

        FileOutputStream fos;

        fos = new FileOutputStream(imageShot);

       PixelCopy.request(mSurfaceView, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                Log.e(word,bitmap.toString());
               String name = String.valueOf(System.currentTimeMillis() + ".jpg");
                //imageFile = Screenshot.Utils.store(bitmap,name);
                bitmap.compress(CompressFormat.JPEG,100,fos);
                try {
                    fos.flush();
                    fos.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Failed to copyPixels: " + copyResult, Toast.LENGTH_LONG);
                toast.show();
            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));



        if(imageShot.exists()){
            String path1 = imageShot.getAbsolutePath().toString();
            File myFile =  new File(path1);
            Log.d("error checking","entering into this function");
            if(myFile.exists()) {
                Bitmap myBitmap = null;
                while (myBitmap==null) {
                    myBitmap = BitmapFactory.decodeFile(path1);
                }
                Log.d("Null checking","  "+myBitmap+"   ");
                myImage = (ImageView) findViewById(R.id.imageView);
                //ImageView myImage = new ImageView(this);
                myImage.setImageBitmap(myBitmap);
            }




            myImage.setVisibility(View.VISIBLE);
            getScreenshot();
            myImage.setVisibility(View.GONE);

        }
    }


public void getScreenshot() throws IOException{




    File picStorageDir = new File(Environment.getExternalStorageDirectory(), "CameraAnnotations/SnapshotsAnnotations");

    if (!picStorageDir.exists()) {
        if (!picStorageDir.mkdirs()) {
            Log.d("App", "failed to create directory");
        }
    }

    String mPhotoUrl = picStorageDir +
            new StringBuilder("/CamAnotPhotoAnno-").append(new SimpleDateFormat("dd-MM-yyyy-hh_mm_ss")
                    .format(new Date())).append(".jpg").toString();

    Date now = new Date();

    File screenAnnotation = new File(mPhotoUrl);


    FileOutputStream fos;
    screen = Screenshot.capture(mRootLayout);
    fos = new FileOutputStream(screenAnnotation);

    Log.d("path","   "+screen+"   ");
    screen.getBitmap().compress(CompressFormat.JPEG,100,fos);
    fos.flush();
    fos.close();

}
}