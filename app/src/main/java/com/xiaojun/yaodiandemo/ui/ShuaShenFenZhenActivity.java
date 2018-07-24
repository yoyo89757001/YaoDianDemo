package com.xiaojun.yaodiandemo.ui;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.BeepManager;
import com.sdsmdg.tastytoast.TastyToast;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.idcard.IdCard;
import com.telpo.tps550.api.idcard.IdentityInfo;
import com.xiaojun.yaodiandemo.MyAppLaction;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.beans.BaoCunBean;
import com.xiaojun.yaodiandemo.beans.BaoCunBeanDao;
import com.xiaojun.yaodiandemo.beans.FaSong;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import com.xiaojun.yaodiandemo.dialog.JiaZaiDialog;
import com.xiaojun.yaodiandemo.utils.FileUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;




public class ShuaShenFenZhenActivity extends Activity {
    private EditText shenfengzheng, xingbie, mingzu, chusheng, dianhua, fazhengjiguan,
            youxiaoqixian, zhuzhi, fanghao, shibiejieguo, xiangsifdu;
    private TextView chepai;
    private ImageView zhengjianzhao, xianchengzhao;
    private Button button;
    private TextView name;
    private File mSavePhotoFile;
    //  private HorizontalProgressBarWithNumber progressBarWithNumber;
    // public static final String HOST="http://192.168.0.104:8080";
    private JiaZaiDialog jiaZaiDialog = null;

    //  public static final String HOST="http://192.168.2.101:8081";
    // public static final String HOST="http://174p2704z3.51mypc.cn:11100";
    //  public static final String HOST="http://192.168.2.43:8080";
    public static final int TIMEOUT = 1000 * 60;
    private static boolean isTrue = true;
    private static boolean isTrue2 = true;
    private boolean bidui = false;
    private Bitmap bitmapBig = null;
    private GetIDInfoTask async = null;
    private UserInfoBena userInfoBena = null;
    private UserInfoBenaDao userInfoBenaDao=null;
    private Thread thread;
    private BeepManager beepManager;
    private IdentityInfo info;
    private Bitmap zhengjianBitmap;
    private byte[] images;
    private BaoCunBeanDao baoCunBeanDao = null;
    private BaoCunBean baoCunBean = null;



    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 300) {

                Toast tastyToast = TastyToast.makeText(ShuaShenFenZhenActivity.this, "开启读卡失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhujiemian4);
        userInfoBenaDao=MyAppLaction.myAppLaction.getDaoSession().getUserInfoBenaDao();
        baoCunBeanDao = MyAppLaction.myAppLaction.getDaoSession().getBaoCunBeanDao();
        baoCunBean = baoCunBeanDao.load(123456L);
//        userInfoBena=userInfoBenaDao.load(123456L);
//        if (userInfoBena==null){
//            userInfoBena = new UserInfoBena();
//            userInfoBena.setId(123456L);
//            userInfoBenaDao.insert(userInfoBena);
//        }

//        String fn = "bbbb.jpg";
//        FileUtil.isExists(FileUtil.PATH, fn);
//        mSavePhotoFile = new File(FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn);

        beepManager = new BeepManager(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    IdCard.open(ShuaShenFenZhenActivity.this);

                    startReadCard();

                } catch (Exception e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast tastyToast = TastyToast.makeText(ShuaShenFenZhenActivity.this, "无法连接读卡器", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            tastyToast.setGravity(Gravity.CENTER, 0, 0);
                            tastyToast.show();
                        }
                    });
                }
            }
        }).start();



        ImageView imageView = (ImageView) findViewById(R.id.dd);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        initView();

        jiaZaiDialog = new JiaZaiDialog(ShuaShenFenZhenActivity.this);
        jiaZaiDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        jiaZaiDialog.show();

    }


    private void startReadCard() {

        isTrue = true;
        isTrue2 = true;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                while (isTrue) {
                    if (isTrue2) {
                        isTrue2 = false;
                        try {

                            async = new GetIDInfoTask();
                            async.execute();

                        } catch (Exception e) {
                            isTrue = false;
                            Log.d("SerialReadActivity", e.getMessage());
                            mHandler.obtainMessage(300, e.getMessage()).sendToTarget();

                        }

                    }


                }
            }

        });
        thread.start();


    }


    private void initView() {

        name = (TextView) findViewById(R.id.name);
        shenfengzheng = (EditText) findViewById(R.id.shenfenzheng);
        xingbie = (EditText) findViewById(R.id.xingbie);
        mingzu = (EditText) findViewById(R.id.mingzu);
        chusheng = (EditText) findViewById(R.id.chusheng);
        dianhua = (EditText) findViewById(R.id.dianhua);
        fazhengjiguan = (EditText) findViewById(R.id.jiguan);
        youxiaoqixian = (EditText) findViewById(R.id.qixian);
        zhuzhi = (EditText) findViewById(R.id.dizhi);
        fanghao = (EditText) findViewById(R.id.fanghao);

        xiangsifdu = (EditText) findViewById(R.id.xiangsidu);
        shibiejieguo = (EditText) findViewById(R.id.jieguo);
        zhengjianzhao = (ImageView) findViewById(R.id.zhengjian);
        xianchengzhao = (ImageView) findViewById(R.id.paizhao);
        button = (Button) findViewById(R.id.wancheng);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new FaSong("im1","",true));
                finish();

            }
        });


    }



      class GetIDInfoTask extends
            AsyncTask<Void, Integer, TelpoException> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //开始
            info = null;
            zhengjianBitmap = null;

        }

        @Override
        protected TelpoException doInBackground(Void... arg0) {
            TelpoException result = null;
            try {
                publishProgress(1);
//				info = IdCard.checkIdCard(4000);
                info = IdCard.checkIdCard(1600);//luyq modify
                if (info != null) {
                    images = IdCard.getIdCardImage();
                    zhengjianBitmap = IdCard.decodeIdCardImage(images);
                    // luyq add 增加指纹信息
                    //fringerprint = IdCard.getFringerPrint();
                    //fringerprintData = Utils.getFingerInfo(fringerprint, InFoActivity2.this);
                }
            } catch (TelpoException e) {
                Log.d("GetIDInfoTask", "异常" + e.getMessage());
                result = e;
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(TelpoException result) {
            super.onPostExecute(result);

            if (result == null && !info.getName().equals("timeout")) {
                isTrue2 = false;
                isTrue = false;

                if (async != null) {
                    async.cancel(true);
                    async = null;
                }

                //设置信息
                beepManager.playBeepSoundAndVibrate();
                name.setText(info.getName().trim());
                xingbie.setText(info.getSex());
                shenfengzheng.setText(info.getNo());
                mingzu.setText(info.getNation());
                String time = info.getBorn().substring(0, 4) + "-" + info.getBorn().substring(4, 6) + "-" + info.getBorn().substring(6, 8);
                chusheng.setText(time);
                fazhengjiguan.setText(info.getApartment());

                String time2 = info.getPeriod().substring(0, 4) + "-" + info.getPeriod().substring(4, 6) + "-" + info.getPeriod().substring(6, 8);
                String time3 = info.getPeriod().substring(9, 13) + "-" + info.getPeriod().substring(13, 15) + "-" + info.getPeriod().substring(15, 17);
                youxiaoqixian.setText(time2 + " " + time3);
                zhuzhi.setText(info.getAddress());


                zhengjianzhao.setImageBitmap(zhengjianBitmap);
                String fn = System.currentTimeMillis()+".jpg";
                FileUtil.isExists(FileUtil.PATH, fn);
                Long id=System.currentTimeMillis();
                MyAppLaction.ShenfenzhengId=id;
                userInfoBena = new UserInfoBena(id,info.getName(), info.getSex().equals("男") ? 1 + "" : 2 + "", info.getNation(), time, info.getAddress(), info.getNo(), info.getApartment(), time2, time3, null, null, null,null,null);

                saveBitmap2File(zhengjianBitmap.copy(Bitmap.Config.ARGB_8888, false), FileUtil.SDPATH + File.separator + FileUtil.PATH + File.separator + fn, 100);

                Toast tastyToast = TastyToast.makeText(ShuaShenFenZhenActivity.this, "身份证信息读取成功", TastyToast.LENGTH_LONG, TastyToast.INFO);
                tastyToast.setGravity(Gravity.CENTER, 0, 0);
                tastyToast.show();

            } else {
                isTrue2 = true;
//                Toast tastyToast = TastyToast.makeText(InFoActivity2.this, "读取身份证信息失败", TastyToast.LENGTH_LONG, TastyToast.ERROR);
//                tastyToast.setGravity(Gravity.CENTER, 0, 0);
//                tastyToast.show();

            }
        }

    }


    /***
     *保存bitmap对象到文件中
     * @param bm
     * @param path
     * @param quality
     * @return
     */
    public void saveBitmap2File(Bitmap bm, final String path, int quality) {
        if (null == bm || bm.isRecycled()) {
            Log.d("InFoActivity", "回收|空");
            return;
        }
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();

            userInfoBena.setCardPhoto(path);
            userInfoBenaDao.insert(userInfoBena);
            if (jiaZaiDialog!=null &&jiaZaiDialog.isShowing()){
                jiaZaiDialog.dismiss();
                jiaZaiDialog=null;
            }


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
    protected void onPause() {
        super.onPause();

        isTrue2 = false;
        isTrue = false;

        if (jiaZaiDialog != null && jiaZaiDialog.isShowing()) {
            jiaZaiDialog.dismiss();
            jiaZaiDialog = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
//        if (beepManager != null) {
//            beepManager.
//            beepManager = null;
//        }
        IdCard.close();

        if (async != null) {
            async.cancel(true);
            async = null;
        }

    }








}
