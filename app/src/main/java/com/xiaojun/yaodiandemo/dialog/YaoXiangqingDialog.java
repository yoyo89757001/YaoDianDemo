package com.xiaojun.yaodiandemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.view.GlideRoundTransform;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class YaoXiangqingDialog extends Dialog {
    private TextView guanbi,xingming,xingbie,nianling,dianhua,yaoming,riqi,shuliang,shenfangren;
    private ImageView imageView,imageView2;
    private Context context;

    public YaoXiangqingDialog(Context context) {
        super(context, R.style.dialog_style2);
        this.context=context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll5, null);
        xingming= (TextView) mView.findViewById(R.id.xingming);
        xingbie= (TextView) mView.findViewById(R.id.xingbie);
        nianling= (TextView) mView.findViewById(R.id.nianling);
        dianhua= (TextView) mView.findViewById(R.id.dianhua);
        yaoming= (TextView) mView.findViewById(R.id.yaoming);
        riqi= (TextView) mView.findViewById(R.id.riqi);
        shuliang= (TextView) mView.findViewById(R.id.shuliang);
        shenfangren= (TextView) mView.findViewById(R.id.shenfangren);
        imageView= (ImageView) mView.findViewById(R.id.tupian);
        imageView2= (ImageView) mView.findViewById(R.id.tupian2);
        guanbi=mView.findViewById(R.id.guanbi);
        guanbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        super.setContentView(mView);


    }

    public void setText(String xm,String xb,String nl,String dh,String ym,String rq,String sl,String sfr){
        xingming.setText(xm);
        xingbie.setText(xb);
        nianling.setText(nl);
        dianhua.setText(dh);
        Log.d("YaoXiangqingDialog", dh+"电话");
        yaoming.setText(ym);
        riqi.setText(rq);
        shuliang.setText(sl);
        shenfangren.setText(sfr);

    }
    public void setImageView(String path,String path2){

        Glide.with(context)
                //	.load(R.drawable.vvv)
                .load(R.drawable.yao_bg)
                .error(R.drawable.erroy_bg)
                //.apply(myOptions)
                .transform(new GlideRoundTransform(context, 4))
                //.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
                .into(imageView);

        Glide.with(context)
                //	.load(R.drawable.vvv)
                .load(path2)
                .error(R.drawable.erroy_bg)
                //.apply(myOptions)
                .transform(new GlideRoundTransform(context, 4))
                //.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
                .into(imageView2);
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
