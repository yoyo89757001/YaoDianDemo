package com.xiaojun.yaodiandemo.ui;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.sdsmdg.tastytoast.TastyToast;
import com.xiaojun.yaodiandemo.MyAppLaction;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.adapter.PopupWindowAdapter;
import com.xiaojun.yaodiandemo.beans.FaSong;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GouMaiActivity extends AppCompatActivity {

    @BindView(R.id.t_fanhui)
    TextView tFanhui;
    @BindView(R.id.dengji2)
    TextView dengji2;
    @BindView(R.id.im1)
    ImageView im1;
    @BindView(R.id.dengji3)
    TextView dengji3;
    @BindView(R.id.im2)
    ImageView im2;
    @BindView(R.id.tongguo)
    TextView tongguo;
    @BindView(R.id.dengji4)
    TextView dengji4;
    @BindView(R.id.im3)
    ImageView im3;
    @BindView(R.id.dengji5)
    TextView dengji5;
    @BindView(R.id.im4)
    ImageView im4;
    @BindView(R.id.shenpiren)
    TextView shenpiren;
    @BindView(R.id.im5)
    ImageView im5;
    @BindView(R.id.shangchuan)
    Button shangchuan;
    private PopupWindowAdapter adapterss;
    private List<String> stringList2=new ArrayList<>();
    private PopupWindow popupWindow=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gou_mai);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);//订阅
        stringList2.add("请选择审方人");
        stringList2.add("杨钰莹");
        stringList2.add("马晓玲");
        stringList2.add("张丽");
        stringList2.add("罗芳");
    }

    @OnClick({R.id.t_fanhui, R.id.dengji2, R.id.dengji3, R.id.dengji4, R.id.dengji5, R.id.shenpiren, R.id.shangchuan,R.id.tt1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.t_fanhui:
                finish();
                break;
            case R.id.dengji2:

                startActivity(new Intent(GouMaiActivity.this, ShuaShenFenZhenActivity.class));

                break;
            case R.id.dengji3:
                dengji3.setEnabled(false);

                if (MyAppLaction.ShenfenzhengId!=0) {
                    //刷脸
                    startActivity(new Intent(GouMaiActivity.this, ShuaLianActivity.class));

                } else {
                    Toast tastyToast = TastyToast.makeText(GouMaiActivity.this, "请先读取身份证信息", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }
                dengji3.setEnabled(true);

                break;
            case R.id.dengji4:

                if (MyAppLaction.ShenfenzhengId!=0){
                    startActivity(new Intent(GouMaiActivity.this, ShuaYaoActivity.class));
                }
                else {
                    Toast tastyToast = TastyToast.makeText(GouMaiActivity.this, "请先读取身份证信息", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }
                break;
            case R.id.dengji5:

                if (MyAppLaction.ShenfenzhengId!=0){
                    startActivity(new Intent(GouMaiActivity.this, ShuaChuFangActivity.class));
                }
                else {
                    Toast tastyToast = TastyToast.makeText(GouMaiActivity.this, "请先读取身份证信息", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                  }

                break;
            case R.id.shenpiren:

                break;
            case R.id.shangchuan:
                if (im1.getVisibility()==View.VISIBLE && im3.getVisibility()==View.VISIBLE && im4.getVisibility()==View.VISIBLE
                        &&im5.getVisibility()==View.VISIBLE){


                    Toast tastyToast = TastyToast.makeText(GouMaiActivity.this, "上传成功", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                    finish();
                }else {

                    Toast tastyToast = TastyToast.makeText(GouMaiActivity.this, "抱歉,数据不完整,不能上传", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }


                break;
            case R.id.tt1:
                if (MyAppLaction.ShenfenzhengId!=0) {

                    View contentView2 = LayoutInflater.from(GouMaiActivity.this).inflate(R.layout.xiangmu_po_item, null);
                    ListView listView2 = (ListView) contentView2.findViewById(R.id.dddddd);
                    adapterss = new PopupWindowAdapter(GouMaiActivity.this, stringList2);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            shenpiren.setText(stringList2.get(position));
                            if (position > 0) {
                                EventBus.getDefault().post(new FaSong("im5", "", true));
                            } else {
                                EventBus.getDefault().post(new FaSong("im5", "", false));
                            }
                            UserInfoBenaDao dao = MyAppLaction.myAppLaction.getDaoSession().getUserInfoBenaDao();
                           UserInfoBena bena= dao.load(MyAppLaction.ShenfenzhengId);
                           bena.setShenFangRen(stringList2.get(position));
                           dao.update(bena);

                            popupWindow.dismiss();

                        }
                    });
                    listView2.setAdapter(adapterss);
                    popupWindow = new PopupWindow(contentView2, 320, 280);
                    popupWindow.setFocusable(true);//获取焦点
                    popupWindow.setOutsideTouchable(true);//获取外部触摸事件
                    popupWindow.setTouchable(true);//能够响应触摸事件
                    popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//设置背景
                    popupWindow.showAsDropDown(shenpiren, 0, 4);
                }else {

                    Toast tastyToast = TastyToast.makeText(GouMaiActivity.this, "请先读取身份证信息", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(FaSong event) {
        switch (event.getType()) {
            case "im1":
                if (event.isTrue()) {
                    im1.setVisibility(View.VISIBLE);
                    im1.setBackgroundResource(R.drawable.dagou);
                }

                break;
            case "im2":
                if (event.isTrue()) {
                    im2.setVisibility(View.VISIBLE);
                    im2.setBackgroundResource(R.drawable.dagou);
                    tongguo.setText(event.getCounts());
                } else {
                    Log.d("GouMaiActivity", "失败");
                    im2.setVisibility(View.VISIBLE);
                    im2.setBackgroundResource(R.drawable.dacha);
                    tongguo.setText(event.getCounts());
                }

                break;
            case "im3":

                if (event.isTrue()) {
                    im3.setVisibility(View.VISIBLE);
                    im3.setBackgroundResource(R.drawable.dagou);
                }

                break;
            case "im4":
                if (event.isTrue()) {
                    im4.setVisibility(View.VISIBLE);
                    im4.setBackgroundResource(R.drawable.dagou);
                }

                break;
            case "im5":
                if (event.isTrue()) {
                    im5.setVisibility(View.VISIBLE);
                    im5.setBackgroundResource(R.drawable.dagou);
                } else {
                    im5.setVisibility(View.GONE);
                }

                break;
        }


    }

    @Override
    protected void onDestroy() {
        MyAppLaction.ShenfenzhengId=0L;
        EventBus.getDefault().unregister(this);//解除订阅
        super.onDestroy();

    }
}
