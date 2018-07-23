package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.ageestimation.ASAE_FSDKAge;
import com.arcsoft.ageestimation.ASAE_FSDKEngine;
import com.arcsoft.ageestimation.ASAE_FSDKError;
import com.arcsoft.ageestimation.ASAE_FSDKFace;
import com.arcsoft.ageestimation.ASAE_FSDKVersion;
import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKMatching;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.arcsoft.facetracking.AFT_FSDKEngine;
import com.arcsoft.facetracking.AFT_FSDKError;
import com.arcsoft.facetracking.AFT_FSDKFace;
import com.arcsoft.facetracking.AFT_FSDKVersion;
import com.arcsoft.genderestimation.ASGE_FSDKEngine;
import com.arcsoft.genderestimation.ASGE_FSDKError;
import com.arcsoft.genderestimation.ASGE_FSDKFace;
import com.arcsoft.genderestimation.ASGE_FSDKGender;
import com.arcsoft.genderestimation.ASGE_FSDKVersion;
import com.guo.android_extend.image.ImageConverter;
import com.guo.android_extend.java.AbsLoop;
import com.guo.android_extend.java.ExtByteArrayOutputStream;
import com.guo.android_extend.tools.CameraHelper;
import com.guo.android_extend.widget.CameraFrameData;
import com.guo.android_extend.widget.CameraGLSurfaceView;
import com.guo.android_extend.widget.CameraSurfaceView;
import com.sdsmdg.tastytoast.TastyToast;
import com.xiaojun.yaodiandemo.MyAppLaction;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.beans.FaSong;
import com.xiaojun.yaodiandemo.beans.FaceDB;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import com.xiaojun.yaodiandemo.utils.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShuaLianActivity extends Activity implements CameraSurfaceView.OnCameraListener, View.OnTouchListener, Camera.AutoFocusCallback {
    private final String TAG = "fggggggggg";
    private int mWidth, mHeight, mFormat;
    private CameraSurfaceView mSurfaceView;
    private CameraGLSurfaceView mGLSurfaceView;
    private Camera mCamera;
    private TextView tishi2;
    private TextView mTextView;
    private TextView mTextView1;
    private ImageView mImageView;

    AFT_FSDKVersion version = new AFT_FSDKVersion();
    AFT_FSDKEngine engine = new AFT_FSDKEngine();
    ASAE_FSDKVersion mAgeVersion = new ASAE_FSDKVersion();
    ASAE_FSDKEngine mAgeEngine = new ASAE_FSDKEngine();
    ASGE_FSDKVersion mGenderVersion = new ASGE_FSDKVersion();
    ASGE_FSDKEngine mGenderEngine = new ASGE_FSDKEngine();
    List<AFT_FSDKFace> result = new ArrayList<>();
    List<ASAE_FSDKAge> ages = new ArrayList<>();
    List<ASGE_FSDKGender> genders = new ArrayList<>();

    int mCameraID;
    int mCameraRotate;
    boolean mCameraMirror;
    byte[] mImageNV21 = null;
    FRAbsLoop mFRAbsLoop = null;
    AFT_FSDKFace mAFT_FSDKFace = null;
    Handler mHandler;

    private static int counts=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shua_lian);
        UserInfoBenaDao userInfoBenaDao=MyAppLaction.myAppLaction.getDaoSession().getUserInfoBenaDao();
        UserInfoBena userInfoBena=userInfoBenaDao.load(123456L);
        AFT_FSDKError err = engine.AFT_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.ft_key, AFT_FSDKEngine.AFT_OPF_0_HIGHER_EXT, 16, 5);
        Log.d(TAG, "AFT_FSDK_InitialFaceEngine =" + err.getCode());
        err = engine.AFT_FSDK_GetVersion(version);
        Log.d(TAG, "AFT_FSDK_GetVersion:" + version.toString() + "," + err.getCode());
        ASAE_FSDKError error = mAgeEngine.ASAE_FSDK_InitAgeEngine(FaceDB.appid, FaceDB.age_key);
        Log.d(TAG, "ASAE_FSDK_InitAgeEngine =" + error.getCode());
        error = mAgeEngine.ASAE_FSDK_GetVersion(mAgeVersion);
        Log.d(TAG, "ASAE_FSDK_GetVersion:" + mAgeVersion.toString() + "," + error.getCode());
        ASGE_FSDKError error1 = mGenderEngine.ASGE_FSDK_InitgGenderEngine(FaceDB.appid, FaceDB.gender_key);
        Log.d(TAG, "ASGE_FSDK_InitgGenderEngine =" + error1.getCode());
        error1 = mGenderEngine.ASGE_FSDK_GetVersion(mGenderVersion);
        Log.d(TAG, "ASGE_FSDK_GetVersion:" + mGenderVersion.toString() + "," + error1.getCode());
        if (userInfoBena!=null){
            //注册人脸
            zhuceShenFenZheng(BitmapFactory.decodeFile(userInfoBena.getCardPhoto()),userInfoBena.getPartyName().trim());

        }else {
            Toast tastyToast = TastyToast.makeText(ShuaLianActivity.this, "获取身份证信息失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();

        }


        mCameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
        mCameraRotate = 90;
        mCameraMirror = false;
        mWidth = 1280;
        mHeight = 960;
        mFormat = ImageFormat.NV21;
        mHandler = new Handler();

        mGLSurfaceView = (CameraGLSurfaceView) findViewById(R.id.glsurfaceView);
        mGLSurfaceView.setOnTouchListener(this);
        mSurfaceView = (CameraSurfaceView) findViewById(R.id.surfaceView);
        mSurfaceView.setOnCameraListener(this);
        mSurfaceView.setupGLSurafceView(mGLSurfaceView, true, mCameraMirror, mCameraRotate);
        mSurfaceView.debug_print_fps(true, false);

        //snap
        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setText("");
        mTextView1 = (TextView) findViewById(R.id.textView1);
        mTextView1.setText("");
        mImageView = (ImageView) findViewById(R.id.imageView);
        tishi2= (TextView) findViewById(R.id.tishi2);



        mFRAbsLoop = new FRAbsLoop();



    }

    Runnable hide = new Runnable() {
        @Override
        public void run() {
          //  mTextView.setAlpha(0.5f);
            //mImageView.setImageAlpha(128);
        }
    };


    class FRAbsLoop extends AbsLoop {

        AFR_FSDKVersion version = new AFR_FSDKVersion();
        AFR_FSDKEngine engine = new AFR_FSDKEngine();
        AFR_FSDKFace result = new AFR_FSDKFace();
        List<FaceDB.FaceRegist> mResgist = ((MyAppLaction)ShuaLianActivity.this.getApplicationContext()).mFaceDB.mRegister;
        List<ASAE_FSDKFace> face1 = new ArrayList<>();
        List<ASGE_FSDKFace> face2 = new ArrayList<>();

        @Override
        public void setup() {
            AFR_FSDKError error = engine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            Log.d(TAG, "AFR_FSDK_InitialEngine = " + error.getCode());
            error = engine.AFR_FSDK_GetVersion(version);
            Log.d(TAG, "FR=" + version.toString() + "," + error.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
        }

        @Override
        public void loop() {
            if (mImageNV21 != null) {
              //  long time = System.currentTimeMillis();
                AFR_FSDKError error = engine.AFR_FSDK_ExtractFRFeature(mImageNV21, mWidth, mHeight, AFR_FSDKEngine.CP_PAF_NV21, mAFT_FSDKFace.getRect(), mAFT_FSDKFace.getDegree(), result);
             //   Log.d(TAG, "AFR_FSDK_ExtractFRFeature cost :" + (System.currentTimeMillis() - time) + "ms");
                Log.d(TAG, "Face=" + result.getFeatureData()[0] + "," + result.getFeatureData()[1] + "," + result.getFeatureData()[2] + "," + error.getCode());
                AFR_FSDKMatching score = new AFR_FSDKMatching();
                float max = 0.0f;
                String name = null;
                for (FaceDB.FaceRegist fr : mResgist) {
                    for (AFR_FSDKFace face : fr.mFaceList) {
                        error = engine.AFR_FSDK_FacePairMatching(result, face, score);
                    //    Log.d(TAG,  "Score:" + score.getScore() + ", AFR_FSDK_FacePairMatching=" + error.getCode());
                        if (max < score.getScore()) {
                            max = score.getScore();
                            name = fr.mName;
                        }
                    }
                }

                //age & gender
                face1.clear();
                face2.clear();
                Rect rect=mAFT_FSDKFace.getRect();
                Rect rect1=new Rect();
                rect1.set((rect.left-80)<0?0:(rect.left-80),(rect.top-80)<0?0:(rect.top-80),(rect.right+80)>mWidth?mWidth:(rect.right+80)
                        ,(rect.bottom+80)>mHeight?mHeight:(rect.bottom+80));


           //     face1.add(new ASAE_FSDKFace(rect1, mAFT_FSDKFace.getDegree()));
           //     face2.add(new ASGE_FSDKFace(rect1, mAFT_FSDKFace.getDegree()));
            //    ASAE_FSDKError error1 = mAgeEngine.ASAE_FSDK_AgeEstimation_Image(mImageNV21, mWidth, mHeight, AFT_FSDKEngine.CP_PAF_NV21, face1, ages);
            //    ASGE_FSDKError error2 = mGenderEngine.ASGE_FSDK_GenderEstimation_Image(mImageNV21, mWidth, mHeight, AFT_FSDKEngine.CP_PAF_NV21, face2, genders);
              //  Log.d(TAG, "ASAE_FSDK_AgeEstimation_Image:" + error1.getCode() + ",ASGE_FSDK_GenderEstimation_Image:" + error2.getCode());
              //  Log.d(TAG, "age:" + ages.get(0).getAge() + ",gender:" + genders.get(0).getGender());
              //  final String age = ages.get(0).getAge() == 0 ? "年龄未知" : ages.get(0).getAge() + "岁";
             //   final String gender = genders.get(0).getGender() == -1 ? "性别未知" : (genders.get(0).getGender() == 0 ? "男" : "女");

                //crop
                byte[] data = mImageNV21;


                YuvImage yuv = new YuvImage(data, ImageFormat.NV21, mWidth, mHeight, null);
                ExtByteArrayOutputStream ops = new ExtByteArrayOutputStream();
                yuv.compressToJpeg(rect1, 100, ops);
                final Bitmap bmp = BitmapFactory.decodeByteArray(ops.getByteArray(), 0, ops.getByteArray().length);
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (max > 0.6f) {
                    if (mFRAbsLoop!=null)
                        mFRAbsLoop.shutdown();
                    if (mCamera!=null)
                        mCamera.stopPreview();
                    ((MyAppLaction)ShuaLianActivity.this.getApplicationContext()).mFaceDB.delete(name);

                    final float max_score = max;
                    //   Log.d(TAG, "fit Score:" + max + ", NAME:" + name);
                    final String mNameShow = name;
                    mHandler.removeCallbacks(hide);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                           // mTextView.setAlpha(1.0f);
                            mTextView.setText(mNameShow);
                            mTextView.setTextColor(Color.RED);
                            mTextView1.setVisibility(View.VISIBLE);
                            mTextView1.setText("相似度：" + (((float)((int)(max_score * 1000)) / 1000.0+0.15)>1?1:((float)((int)(max_score * 1000)) / 1000.0+0.15)));
                            mTextView1.setTextColor(Color.RED);
                            mImageView.setRotation(mCameraRotate);
                            if (mCameraMirror) {
                                mImageView.setScaleY(-1);
                            }
                          //  mImageView.setImageAlpha(255);
                            mImageView.setImageBitmap(bmp);

                            String fn = "bbbb.jpg";
                            FileUtil.isExists(FileUtil.PATH, fn);
                            saveBitmap2File2(bmp.copy(Bitmap.Config.ARGB_8888,false),
                                    FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);
                            //发送成功广播
                            EventBus.getDefault().post(new FaSong("im2","比对成功",true));
                            finish();
                        }
                    });
                } else {
                    counts++;
                    if (counts>3){
                        //关闭摄像头
                        if (mFRAbsLoop!=null)
                            mFRAbsLoop.shutdown();
                        if (mCamera!=null)
                            mCamera.stopPreview();
                        ((MyAppLaction)ShuaLianActivity.this.getApplicationContext()).mFaceDB.delete(name);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String fn = "bbbb.jpg";
                                FileUtil.isExists(FileUtil.PATH, fn);
                                saveBitmap2File3(bmp.copy(Bitmap.Config.ARGB_8888,false), FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);

                                EventBus.getDefault().post(new FaSong("im2","比对失败",false));
                                finish();
                            }
                        });
                        return;
                    }
                    final String mNameShow = "";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          //  mTextView.setAlpha(1.0f);
                            mTextView1.setVisibility(View.GONE);
//                            mTextView1.setText( gender + "," + age);
//                            mTextView1.setTextColor(Color.RED);
                            mTextView.setText(mNameShow);
                            mTextView.setTextColor(Color.RED);
                           // mImageView.setImageAlpha(255);
                            mImageView.setRotation(mCameraRotate);
                            if (mCameraMirror) {
                                mImageView.setScaleY(-1);
                            }
                            mImageView.setImageBitmap(bmp);
                        }
                    });
                }
                mImageNV21 = null;
            }

        }

        @Override
        public void over() {
            AFR_FSDKError error = engine.AFR_FSDK_UninitialEngine();
            Log.d(TAG, "AFR_FSDK_UninitialEngine : " + error.getCode());
        }
    }

    public  void saveBitmap2File2(Bitmap bm, final String path, int quality) {
        try {

            if (null == bm) {
                Log.d("InFoActivity", "回收|空");
                return ;
            }

            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();


        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
        }
    }

    public  void saveBitmap2File3(Bitmap bm, final String path, int quality) {
        try {

            if (null == bm) {
                Log.d("InFoActivity", "回收|空");
                return ;
            }


            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();


        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            if (!bm.isRecycled()) {
                bm.recycle();
            }
            bm = null;
        }
    }



    @Override
    protected void onDestroy() {


        super.onDestroy();
        if (mFRAbsLoop!=null)
            mFRAbsLoop.shutdown();
        AFT_FSDKError err = engine.AFT_FSDK_UninitialFaceEngine();
        Log.d(TAG, "AFT_FSDK_UninitialFaceEngine =" + err.getCode());

        ASAE_FSDKError err1 = mAgeEngine.ASAE_FSDK_UninitAgeEngine();
        Log.d(TAG, "ASAE_FSDK_UninitAgeEngine =" + err1.getCode());

        ASGE_FSDKError err2 = mGenderEngine.ASGE_FSDK_UninitGenderEngine();
        Log.d(TAG, "ASGE_FSDK_UninitGenderEngine =" + err2.getCode());

    }


    public void zhuceShenFenZheng(Bitmap mBitmap,String name){

        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];
        ImageConverter convert = new ImageConverter();
        convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);
        if (convert.convert(mBitmap, data)) {
            Log.d(TAG, "convert ok!");
        }
        convert.destroy();

        AFD_FSDKEngine engine = new AFD_FSDKEngine();
        AFD_FSDKVersion version = new AFD_FSDKVersion();
        List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
        AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
        Log.d(TAG, "AFD_FSDK_InitialFaceEngine = " + err.getCode());
        if (err.getCode() != AFD_FSDKError.MOK) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tishi2.setVisibility(View.VISIBLE);
                    tishi2.setText("人脸比对器初始化失败");
                }
            });
        }
        err = engine.AFD_FSDK_GetVersion(version);
        Log.d(TAG, "AFD_FSDK_GetVersion =" + version.toString() + ", " + err.getCode());
        err  = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);
        Log.d(TAG, "AFD_FSDK_StillImageFaceDetection =" + err.getCode() + "<" + result.size());

        if (!result.isEmpty()) {
            //包含脸
            AFR_FSDKVersion version1 = new AFR_FSDKVersion();
            AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
            AFR_FSDKFace result1 = new AFR_FSDKFace();
            AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            Log.d("com.arcsoft", "AFR_FSDK_InitialEngine = " + error1.getCode());
            if (error1.getCode() != AFD_FSDKError.MOK) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tishi2.setVisibility(View.VISIBLE);
                        tishi2.setText("人脸比对器初始化失败");
                    }
                });
            }
            error1 = engine1.AFR_FSDK_GetVersion(version1);
            Log.d("com.arcsoft", "FR=" + version.toString() + "," + error1.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
            error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
            Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode());
            if(error1.getCode() == AFR_FSDKError.MOK) {
                //注册
                ((MyAppLaction)ShuaLianActivity.this.getApplicationContext()).mFaceDB.addFace(name, result1.clone());

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tishi2.setVisibility(View.VISIBLE);
                        tishi2.setText("证件照过于模糊,无法比对");
                    }
                });
            }
            error1 = engine1.AFR_FSDK_UninitialEngine();
            Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
        }


    }

    @Override
    protected void onResume() {
        counts=0;
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public Camera setupCamera() {

        mCamera = Camera.open(mCameraID);

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(mWidth, mHeight);
            parameters.setPreviewFormat(mFormat);

            for( Camera.Size size : parameters.getSupportedPreviewSizes()) {
                Log.d(TAG, "SIZE:" + size.width + "x" + size.height);
            }
            for( Integer format : parameters.getSupportedPreviewFormats()) {
                Log.d(TAG, "FORMAT:" + format);
            }

            List<int[]> fps = parameters.getSupportedPreviewFpsRange();
            for(int[] count : fps) {
                Log.d(TAG, "T:");
                for (int data : count) {
                    Log.d(TAG, "V=" + data);
                }
            }
            //parameters.setPreviewFpsRange(15000, 30000);
            //parameters.setExposureCompensation(parameters.getMaxExposureCompensation());
            //parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);
            //parameters.setAntibanding(Camera.Parameters.ANTIBANDING_AUTO);
            //parmeters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            //parameters.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
            //parameters.setColorEffect(Camera.Parameters.EFFECT_NONE);
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCamera != null) {
            mWidth = mCamera.getParameters().getPreviewSize().width;
            mHeight = mCamera.getParameters().getPreviewSize().height;
        }
        return mCamera;
    }

    @Override
    public void setupChanged(int format, int width, int height) {

        mFRAbsLoop.start();

    }

    @Override
    public boolean startPreviewLater() {

        return false;
    }

    @Override
    public Object onPreview(byte[] data, int width, int height, int format, long timestamp) {
        AFT_FSDKError err = engine.AFT_FSDK_FaceFeatureDetect(data, width, height, AFT_FSDKEngine.CP_PAF_NV21, result);
        Log.d(TAG, "AFT_FSDK_FaceFeatureDetect =" + err.getCode());
        Log.d(TAG, "Face=" + result.size());
        for (AFT_FSDKFace face : result) {
            Log.d(TAG, "Face:" + face.toString());

        }
        if (mImageNV21 == null) {
            if (!result.isEmpty()) {
                mAFT_FSDKFace = result.get(0).clone();
                mImageNV21 = data.clone();
            } else {
                mHandler.postDelayed(hide, 3000);
            }
        }
        //copy rects
        Rect[] rects = new Rect[result.size()];
        for (int i = 0; i < result.size(); i++) {
            rects[i] = new Rect(result.get(i).getRect());
        }
        //clear result.
        result.clear();
        //return the rects for render.
        return rects;
    }

    @Override
    public void onBeforeRender(CameraFrameData data) {

    }

    @Override
    public void onAfterRender(CameraFrameData data) {
        mGLSurfaceView.getGLES2Render().draw_rect((Rect[])data.getParams(), Color.GREEN, 2);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        CameraHelper.touchFocus(mCamera, event, v, this);
        return false;
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        if (success) {
            Log.d(TAG, "Camera Focus SUCCESS!");
        }
    }


}
