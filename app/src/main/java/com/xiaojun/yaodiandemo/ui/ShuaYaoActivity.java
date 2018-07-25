package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sdsmdg.tastytoast.TastyToast;
import com.xiaojun.yaodiandemo.MyAppLaction;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.adapter.GouMaiYaoAdapter;
import com.xiaojun.yaodiandemo.beans.Bianji;
import com.xiaojun.yaodiandemo.beans.FaSong;
import com.xiaojun.yaodiandemo.beans.TianJiaYao;
import com.xiaojun.yaodiandemo.beans.TianJiaYaoDao;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import com.xiaojun.yaodiandemo.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShuaYaoActivity extends Activity {

    @BindView(R.id.t_fanhui)
    TextView tFanhui;
    @BindView(R.id.tianjia)
    TextView tianjia;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.baocun)
    Button baocun;
    @BindView(R.id.ggg)
    TextView ggg;
    private UserInfoBenaDao userInfoBenaDao = MyAppLaction.myAppLaction.getDaoSession().getUserInfoBenaDao();
    private UserInfoBena userInfoBena = null;
    private GouMaiYaoAdapter adapter;
    private List<TianJiaYao> tianJiaYaoList = new ArrayList<>();
    private TianJiaYaoDao tianJiaYaoDao=MyAppLaction.myAppLaction.getDaoSession().getTianJiaYaoDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shua_yao);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//订阅

        userInfoBena = userInfoBenaDao.load(MyAppLaction.ShenfenzhengId);
        if (userInfoBena == null) {
            Toast tastyToast = TastyToast.makeText(ShuaYaoActivity.this, "请先读取身份证信息", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }

        adapter = new GouMaiYaoAdapter(ShuaYaoActivity.this, tianJiaYaoList);
        listview.setAdapter(adapter);


    }


    //编辑adapter传来的改变值
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(Bianji event) {
        Log.d("ShuaYaoActivity", "event.getP():" + event.getP()+"  "+event.getShuliang()+"  "+event.getYaoming());
        if (event.getType()==1){
            tianJiaYaoList.get(event.getP()).setYaoming(event.getYaoming());
            tianJiaYaoList.get(event.getP()).setShuliang(event.getShuliang());
            adapter.notifyDataSetChanged();
        }else {
            //删除
            tianJiaYaoList.remove(event.getP());
            adapter.notifyDataSetChanged();
        }

    }


    @OnClick({R.id.t_fanhui, R.id.tianjia, R.id.baocun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.t_fanhui:
                finish();
                break;
            case R.id.tianjia:
                IntentIntegrator integrator = new IntentIntegrator(this);
                // integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                // integrator.setPrompt("对准二维码自动扫描");
                // integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setCaptureActivity(ErWeiMaActivity.class);
                //  integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();

                break;
            case R.id.baocun:
                if (tianJiaYaoList.size()>0) {
                    for (TianJiaYao tianJiaYao : tianJiaYaoList) {
                        try {
                            tianJiaYaoDao.insert(tianJiaYao);
                        } catch (Exception e) {
                            Log.d("ShuaYaoActivity", e.getMessage() + "添加到本地异常");
                        }
                    }

                    Toast tastyToast = TastyToast.makeText(ShuaYaoActivity.this, "保存成功", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                    EventBus.getDefault().post(new FaSong("im3", "", true));
                    finish();
                }else {
                    Toast tastyToast = TastyToast.makeText(ShuaYaoActivity.this, "没有药品数据,不能保存", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                Log.d("HomePageActivity", result.getContents());
               int size= tianJiaYaoList.size();
                boolean fs=false;
                for (int i=0;i<size;i++){
                    if (tianJiaYaoList.get(i).getBianma().equals(result.getContents())){
                        //是同一个编码 数量加1
                        fs=true;
                        tianJiaYaoList.get(i).setShuliang(tianJiaYaoList.get(i).getShuliang()+1);
                        adapter.notifyDataSetChanged();
                        break;
                    }

                }
                //不是同一个编码 重新生成一个药品
                if (!fs){
                    //  Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    TianJiaYao tianJiaYao = new TianJiaYao();
                    tianJiaYao.setId(System.currentTimeMillis());
                    tianJiaYao.setName(userInfoBena.getPartyName() == null ? "未获取到" : userInfoBena.getPartyName().trim());
                    tianJiaYao.setRiqi(DateUtils.timesTwo(System.currentTimeMillis() + ""));
                    tianJiaYao.setShuliang(1);
                    tianJiaYao.setBianma(result.getContents());
                    tianJiaYao.setSfzHao(userInfoBena.getCertNumber() == null ? "未获取到" : userInfoBena.getCertNumber().trim());
                    tianJiaYao.setYaoming("氢溴酸右美沙芬口服溶液");
                    tianJiaYaoList.add(tianJiaYao);
                    adapter.notifyDataSetChanged();
                }
                ggg.setVisibility(View.GONE);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }
}
