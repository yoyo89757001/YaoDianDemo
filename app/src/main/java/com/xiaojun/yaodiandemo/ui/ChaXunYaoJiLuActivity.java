package com.xiaojun.yaodiandemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.xiaojun.yaodiandemo.MyAppLaction;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.adapter.ChaXunYaoAdapter;
import com.xiaojun.yaodiandemo.beans.TianJiaYao;
import com.xiaojun.yaodiandemo.beans.TianJiaYaoDao;
import com.xiaojun.yaodiandemo.beans.UserInfoBena;
import com.xiaojun.yaodiandemo.beans.UserInfoBenaDao;
import com.xiaojun.yaodiandemo.dialog.YaoXiangqingDialog;
import com.xiaojun.yaodiandemo.utils.DateUtils;

import org.greenrobot.greendao.query.WhereCondition;

import java.util.ArrayList;
import java.util.List;

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
    private List<TianJiaYao> tianJiaYaoList=new ArrayList<>();
    private TianJiaYaoDao dao= MyAppLaction.myAppLaction.getDaoSession().getTianJiaYaoDao();
    private UserInfoBenaDao sfzDao=MyAppLaction.myAppLaction.getDaoSession().getUserInfoBenaDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cha_xun_yao_ji_lu);
        ButterKnife.bind(this);
        adapter=new ChaXunYaoAdapter(ChaXunYaoJiLuActivity.this,tianJiaYaoList);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Log.d("ChaXunYaoJiLuActivity", "position:" + position);
                List<UserInfoBena> userInfoBena1= sfzDao.queryBuilder().where(UserInfoBenaDao.Properties.CertNumber.eq(tianJiaYaoList.get(position).getSfzHao())).list();
                UserInfoBena userInfoBena=userInfoBena1.get(userInfoBena1.size()-1);
                YaoXiangqingDialog dialog=new YaoXiangqingDialog(ChaXunYaoJiLuActivity.this);
                dialog.setText(tianJiaYaoList.get(position).getName(),userInfoBena.getGender(),
                        (Integer.valueOf(DateUtils.timeNian(System.currentTimeMillis()+""))-Integer.valueOf(userInfoBena.getBornDay().split("-")[0]))+"",
                        userInfoBena.getDianhua(),tianJiaYaoList.get(position).getYaoming(),tianJiaYaoList.get(position).getRiqi(),
                        tianJiaYaoList.get(position).getShuliang()+"",userInfoBena.getShenFangRen());
                dialog.setImageView(null,userInfoBena.getYaoDanPath());
                dialog.show();
                Log.d("ChaXunYaoJiLuActivity", userInfoBena.getDianhua()+"电话");
            }
        });

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
              //  List mtaskPath = mTaskPathDao.queryBuilder().where(TaskPathDao.Properties.Task_name.like("小%")).list();
              String name2=name.getText().toString().trim();
              String yaoming2=yaoming.getText().toString().trim();
              String riqi2=qiri.getText().toString().trim();
                if (name2.equals("") && yaoming2.equals("")&& riqi2.equals("")){
                    Toast tastyToast = TastyToast.makeText(ChaXunYaoJiLuActivity.this, "请输入要查询的条件", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                    tastyToast.setGravity(Gravity.CENTER, 0, 0);
                    tastyToast.show();
                }else {
                 List<TianJiaYao> tianJiaYaoList2=  dao.queryBuilder().where(TianJiaYaoDao.Properties.Name.like("%"+name2+"%"),
                            TianJiaYaoDao.Properties.Riqi.like("%"+riqi2+"%"),
                            TianJiaYaoDao.Properties.Yaoming.like("%"+yaoming2+"%")).list();
                    if (tianJiaYaoList2!=null && tianJiaYaoList2.size()>0){
                        tianJiaYaoList.clear();
                        tianJiaYaoList.addAll(tianJiaYaoList2);
                        adapter.notifyDataSetChanged();

                    }else {
                        tianJiaYaoList.clear();
                        adapter.notifyDataSetChanged();
                        Toast tastyToast = TastyToast.makeText(ChaXunYaoJiLuActivity.this, "没有查询到相关信息", TastyToast.LENGTH_SHORT, TastyToast.INFO);
                        tastyToast.setGravity(Gravity.CENTER, 0, 0);
                        tastyToast.show();
                    }

                }




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
