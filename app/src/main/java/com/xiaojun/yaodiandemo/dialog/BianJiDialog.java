package com.xiaojun.yaodiandemo.dialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.view.GlideRoundTransform;


/**
 * @Function: 自定义对话框
 * @Date: 2013-10-28
 * @Time: 下午12:37:43
 * @author Tom.Cai
 */
public class BianJiDialog extends Dialog {
    private EditText shualiang,yaoming;
    private ImageView imageView;
    private Button queren,quxiao;
    private Context context;

    public BianJiDialog(Context context) {
        super(context, R.style.dialog_style2);
        this.context=context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.queren_ll3, null);
        shualiang= (EditText) mView.findViewById(R.id.shuliang);
        yaoming= (EditText) mView.findViewById(R.id.yaoming);
        imageView= (ImageView) mView.findViewById(R.id.tupian);
        queren= (Button) mView.findViewById(R.id.queren);
        quxiao= (Button) mView.findViewById(R.id.quxiao);
        super.setContentView(mView);


    }

    public void setText(String yaomin,String shuliag){
        shualiang.setText(shuliag);
        yaoming.setText(yaomin);
    }
    public void setImageView(String path){

        Glide.with(context)
                //	.load(R.drawable.vvv)
                .load(R.drawable.yao_bg)
                .error(R.drawable.erroy_bg)
                //.apply(myOptions)
                .transform(new GlideRoundTransform(context, 4))
                //.transform(new GlideCircleTransform(MyApplication.getAppContext(),2,Color.parseColor("#ffffffff")))
                .into(imageView);
    }

    public String getYaoMing(){
        return yaoming.getText().toString().trim();
    }

    public String getShuLiang(){
        return shualiang.getText().toString().trim();
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
