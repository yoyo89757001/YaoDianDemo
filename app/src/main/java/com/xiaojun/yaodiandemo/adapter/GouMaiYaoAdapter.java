package com.xiaojun.yaodiandemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.xiaojun.yaodiandemo.R;
import com.xiaojun.yaodiandemo.beans.TianJiaYao;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */


public class GouMaiYaoAdapter extends BaseAdapter {

    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<TianJiaYao> fuWuQiBeanList;




    /*构造函数*/
    public GouMaiYaoAdapter(Context context, List<TianJiaYao> plansBeanList) {
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
            convertView = mInflater.inflate(R.layout.goumaiyao_item,null);
            holder = new ViewHolder();
                    /*得到各个控件的对象*/
            holder.title = (TextView) convertView.findViewById(R.id.dizhi);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

      //  holder.title.setText(fuWuQiBeanList.get(position).getArea());

        return convertView;
    }
    /*存放控件*/
   private class ViewHolder{
        public TextView title;


    }

}
