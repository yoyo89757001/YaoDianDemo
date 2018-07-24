package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.beans.ZuiFan;
import com.xiaojun.yaodiandemo.utils.Utils;
import com.xiaojun.yaodiandemo.view.VerticalScrolledListview;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ShouYeActivity extends Activity {
    private LinearLayout linearLayout;
    private List<ZuiFan> zuiFanList=new ArrayList<>();
    private final Timer timer = new Timer();
    private TimerTask task;
    private static int count=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shou_ye);
        linearLayout= (LinearLayout) findViewById(R.id.tishi_ll);
        TextView imageView= (TextView) findViewById(R.id.dengji2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ShouYeActivity.this,GouMaiActivity.class),2);
            }
        });

        TextView imageView22= (TextView) findViewById(R.id.dengji3);
        imageView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShouYeActivity.this,ChaXunYaoJiLuActivity.class));
            }
        });

        TextView imageView44= (TextView) findViewById(R.id.dengji4);
        imageView44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShouYeActivity.this,ChaXunHuanJingActivity.class));
            }
        });

        TextView imageView2= (TextView) findViewById(R.id.shezhi);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    startActivity(new Intent(ShouYeActivity.this, SheZhiActivity.class));

            }
        });



        String s="关于举办2018年广东省食品药品科普宣传基地“一日游”活动的通告\n为进一步向社会公众普及食品药品知识，树立食品药品安全人人有责的观念，根据2018年省食品药品监督管理局宣传工作计划安排,结合科普宣传工作要求，广东省执业药师注册中心将开展2018年度“广东省食品药品科普宣传基地一日游”活动。详情及报名请扫描二维码关注安安有约微信公众号";

       List<String> ss= Utils.getStrList(s,250);


        ZuiFan zuiFan=new ZuiFan();
        zuiFan.setTishi(ss);
        zuiFan.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.erweima));

        zuiFanList.add(zuiFan);


    }



    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==999){
                final View view1 = View.inflate(ShouYeActivity.this, R.layout.tishi_item, null);
                ImageView imageView= (ImageView) view1.findViewById(R.id.touxiang);
                VerticalScrolledListview textView= (VerticalScrolledListview) view1.findViewById(R.id.tishi);
                imageView.setImageBitmap(zuiFanList.get(count).getBitmap());
                textView.setData(zuiFanList.get(count).getTishi());
                try {
                    linearLayout.removeViewAt(0);
                }catch (Exception e){
                    Log.d("ShouYeActivity", e.getMessage()+"");

                }

                linearLayout.addView(view1);
                count++;
                if (count>0){
                    count=0;
                }
              //  timer.schedule(task, 3000);


            }

            return false;
        }
    });

    @Override
    protected void onResume() {
        super.onResume();
        if (task!=null)
            task.cancel();

        task = new TimerTask() {
            @Override
            public void run() {

                Message message = new Message();
                message.what = 999;
                handler.sendMessage(message);
                //	Log.d(TAG, "gggggggggggg");

            }
        };
        timer.schedule(task, 1000,12000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task!=null)
            task.cancel();
        if (timer!=null)
            timer.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            System.out.println("按下了back键   onKeyDown()");
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 2) {
            //  Log.d("ShouYeActivity", "回来了");
            // 选择预约时间的页面被关闭




        }

    }
}
