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
import com.xiaojun.yaodiandemo.beans.TianJiaYao;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import com.xiaojun.yaodiandemo.utils.DateUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shua_yao);
        ButterKnife.bind(this);
        userInfoBena = userInfoBenaDao.load(123456L);
        if (userInfoBena == null) {
            Toast tastyToast = TastyToast.makeText(ShuaYaoActivity.this, "请先读取身份证信息", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            tastyToast.setGravity(Gravity.CENTER, 0, 0);
            tastyToast.show();
        }

        adapter = new GouMaiYaoAdapter(ShuaYaoActivity.this, tianJiaYaoList);
        listview.setAdapter(adapter);


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

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                Log.d("HomePageActivity", result.getContents());
                //  Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                TianJiaYao tianJiaYao = new TianJiaYao();
                tianJiaYao.setId(System.currentTimeMillis());
                tianJiaYao.setName(userInfoBena.getPartyName() == null ? "未获取到" : userInfoBena.getPartyName());
                tianJiaYao.setRiqi(DateUtils.timesTwo(System.currentTimeMillis() + ""));
                tianJiaYao.setShuliang(1);
                tianJiaYao.setSfzHao(userInfoBena.getCertNumber() == null ? "未获取到" : userInfoBena.getCertNumber());
                tianJiaYao.setYaoming("测试");

                tianJiaYaoList.add(tianJiaYao);
                adapter.notifyDataSetChanged();


                ggg.setVisibility(View.GONE);

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
