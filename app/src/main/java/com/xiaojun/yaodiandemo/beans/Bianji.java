package com.xiaojun.yaodiandemo.beans;

/**
 * Created by Administrator on 2018/7/24.
 */

public class Bianji {
    private int p;
    private String yaoming;
    private int shuliang;
    private int type;

    public Bianji(int p, String yaoming, int shuliang, int type) {
        this.p = p;
        this.yaoming = yaoming;
        this.shuliang = shuliang;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public String getYaoming() {
        return yaoming;
    }

    public void setYaoming(String yaoming) {
        this.yaoming = yaoming;
    }

    public int getShuliang() {
        return shuliang;
    }

    public void setShuliang(int shuliang) {
        this.shuliang = shuliang;
    }
}
