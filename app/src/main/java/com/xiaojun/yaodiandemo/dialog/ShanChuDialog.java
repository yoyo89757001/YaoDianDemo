package com.xiaojun.yaodiandemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import com.xiaojun.yaodiandemo.R;



/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class ShanChuDialog extends Dialog {
    private Button queren,quxiao;
    private Context context;

    public ShanChuDialog(Context context) {
        super(context, R.style.dialog_style2);
        this.context=context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll4, null);
        queren= (Button) mView.findViewById(R.id.queren);
        quxiao= (Button) mView.findViewById(R.id.quxiao);
        super.setContentView(mView);


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

    /**
     * 确定键监听器
     * @param listener
     */
    public void setOnPositiveListener(View.OnClickListener listener){
        queren.setOnClickListener(listener);
    }
    /**
     * 取消键监听器
     * @param listener
     */
    public void setOnQuXiaoListener(View.OnClickListener listener){
        quxiao.setOnClickListener(listener);
    }
}
