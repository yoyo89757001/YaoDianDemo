package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaojun.yaodiandemo.MyAppLaction;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.beans.FaSong;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import com.xiaojun.yaodiandemo.utils.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShuaChuFangActivity extends Activity {

    @BindView(R.id.t_fanhui)
    TextView tFanhui;
    @BindView(R.id.tupian)
    ImageView tupian;
    @BindView(R.id.baocun)
    Button baocun;
    @BindView(R.id.chongpai)
    Button chongpai;
    private File mSavePhotoFile;
    private final int REQUEST_TAKE_PHOTO=33;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shua_chu_fang);
        ButterKnife.bind(this);

        String fn =System.currentTimeMillis()+".jpg";
        FileUtil.isExists(FileUtil.PATH, fn);
        mSavePhotoFile=new File( FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn);

            startCamera();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:  //拍照
                    //注意，如果拍照的时候设置了MediaStore.EXTRA_OUTPUT，data.getData=null
                    tupian.setImageURI(Uri.fromFile(mSavePhotoFile));

                    break;

            }
        }

    }




    /**
     * 启动拍照
     * @param
     */
    private void startCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (mSavePhotoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(mSavePhotoFile));//设置文件保存的URI
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @OnClick({R.id.t_fanhui, R.id.baocun, R.id.chongpai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.t_fanhui:
                finish();
                break;
            case R.id.baocun:
                EventBus.getDefault().post(new FaSong("im4","",true));
                UserInfoBenaDao dao= MyAppLaction.myAppLaction.getDaoSession().getUserInfoBenaDao();
               UserInfoBena bena= dao.load(MyAppLaction.ShenfenzhengId);
               bena.setYaoDanPath(mSavePhotoFile.getPath());
               dao.update(bena);
               finish();

                break;
            case R.id.chongpai:
                startCamera();
                break;
        }
    }
}
