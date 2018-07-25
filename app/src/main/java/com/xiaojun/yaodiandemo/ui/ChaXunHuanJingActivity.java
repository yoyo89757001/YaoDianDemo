package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.adapter.ChaXunHuanJingAdapter;
import com.xiaojun.yaodiandemo.beans.HuanJingBean;
import com.xiaojun.yaodiandemo.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChaXunHuanJingActivity extends Activity {

    @BindView(R.id.t_fanhui)
    TextView tFanhui;
    @BindView(R.id.weizhi)
    EditText weizhi;
    @BindView(R.id.qiri)
    TextView qiri;
    @BindView(R.id.chaxun)
    Button chaxun;
    @BindView(R.id.listview)
    ListView listview;
    private ChaXunHuanJingAdapter adapter;
    private List<HuanJingBean> huanJingBeanList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_xun_huan_jing);
        ButterKnife.bind(this);
         HuanJingBean bean=new HuanJingBean("海星药房处方药柜","28°","73%", DateUtils.timesTwo(System.currentTimeMillis()+""),"正常");
        huanJingBeanList.add(bean);
        adapter=new ChaXunHuanJingAdapter(ChaXunHuanJingActivity.this,huanJingBeanList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

    }

    @OnClick({R.id.t_fanhui, R.id.chaxun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.t_fanhui:
                finish();
                break;
            case R.id.chaxun:


                break;
        }
    }
}
