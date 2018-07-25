package com.xiaojun.yaodiandemo.beans;

/**
 * Created by Administrator on 2018/7/25.
 */

public class HuanJingBean {
    private String weizhi;
    private String wendu;
    private String shidu;
    private String shijian;
    private String jianyi;

    public HuanJingBean(String weizhi, String wendu, String shidu, String shijian, String jianyi) {
        this.weizhi = weizhi;
        this.wendu = wendu;
        this.shidu = shidu;
        this.shijian = shijian;
        this.jianyi = jianyi;
    }

    public String getWeizhi() {
        return weizhi;
    }

    public void setWeizhi(String weizhi) {
        this.weizhi = weizhi;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getJianyi() {
        return jianyi;
    }

    public void setJianyi(String jianyi) {
        this.jianyi = jianyi;
    }

}
