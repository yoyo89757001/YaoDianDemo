package com.xiaojun.yaodiandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.beans.HuanJingBean;


import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */


public class ChaXunHuanJingAdapter extends BaseAdapter {

    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<HuanJingBean> fuWuQiBeanList;
    private Context context;



    /*构造函数*/
    public ChaXunHuanJingAdapter(Context context, List<HuanJingBean> plansBeanList) {
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.fuWuQiBeanList=plansBeanList;

    }



    @Override
    public int getCount() {

        return fuWuQiBeanList.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    /*书中详细解释该方法*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.goumaiyao_item3,null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.weizhi = (TextView) convertView.findViewById(R.id.weizhi);
            holder.wendu = (TextView) convertView.findViewById(R.id.wendu);
            holder.shidu = (TextView) convertView.findViewById(R.id.shidu);
            holder.shijian = (TextView) convertView.findViewById(R.id.shijian);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        holder.weizhi.setText(fuWuQiBeanList.get(position).getWeizhi());
        holder.wendu.setText(fuWuQiBeanList.get(position).getWendu());
        holder.shidu.setText(fuWuQiBeanList.get(position).getShidu());
        holder.shijian.setText(fuWuQiBeanList.get(position).getShijian());

        return convertView;
    }
    /*存放控件*/
   private class ViewHolder{
        public TextView weizhi,wendu,shidu,shijian;


    }

}

