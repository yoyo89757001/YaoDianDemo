package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.adapter.ChaXunYaoAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChaXunYaoJiLuActivity extends Activity {

    @BindView(R.id.t_fanhui)
    TextView tFanhui;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.yaoming)
    EditText yaoming;
    @BindView(R.id.qiri)
    TextView qiri;
    @BindView(R.id.chaxun)
    Button chaxun;
    @BindView(R.id.listview)
    ListView listview;
    private ChaXunYaoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_xun_yao_ji_lu);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.t_fanhui, R.id.qiri, R.id.chaxun})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.t_fanhui:

                finish();

                break;
            case R.id.qiri:
                Intent intent = new Intent(ChaXunYaoJiLuActivity.this, DatePickActivity.class);
                startActivityForResult(intent,2);

                break;
            case R.id.chaxun:

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            // 选择预约时间的页面被关闭
            String date2 = data.getStringExtra("date");
            Log.d("ChaXunYaoJiLuActivity", "data:" + date2);
            qiri.setText(date2.split(" ")[0]);

        }

    }

}
