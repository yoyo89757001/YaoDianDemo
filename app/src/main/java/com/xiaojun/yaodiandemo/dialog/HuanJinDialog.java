package com.xiaojun.yaodiandemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import com.xiaojun.yaodiandemo.R;



/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class HuanJinDialog extends Dialog {
    private TextView weizhi,wendu,shidu,shijian,jianyi,guanbi;
    private Context context;

    public HuanJinDialog(Context context) {
        super(context, R.style.dialog_style2);
        this.context=context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll6, null);
        weizhi= (TextView) mView.findViewById(R.id.weizhi);
        wendu= (TextView) mView.findViewById(R.id.wendu);
        shidu= (TextView) mView.findViewById(R.id.shidu);
        shijian= (TextView) mView.findViewById(R.id.shijian);
        jianyi= (TextView) mView.findViewById(R.id.jianyi);
        guanbi=mView.findViewById(R.id.guanbi);
        guanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.setContentView(mView);


    }

    public void setText(String wz,String wd,String sd,String sj,String jy){
        weizhi.setText(wz);
        wendu.setText(wd);
        shidu.setText(sd);
        shijian.setText(sj);
        jianyi.setText(jy);
    }


    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

//    /**
//     * 确定键监听器
//     * @param listener
//     */
//    public void setOnPositiveListener(View.OnClickListener listener){
//        queren.setOnClickListener(listener);
//    }
//    /**
//     * 取消键监听器
//     * @param listener
//     */
//    public void setOnQuXiaoListener(View.OnClickListener listener){
//        quxiao.setOnClickListener(listener);
//    }
}
