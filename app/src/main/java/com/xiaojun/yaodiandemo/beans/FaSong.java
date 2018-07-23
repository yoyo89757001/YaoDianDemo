package com.xiaojun.yaodiandemo.beans;

/**
 * Created by Administrator on 2018/7/23.
 */

public class FaSong {
    String type;
    String counts;
    boolean isTrue;


    public FaSong(String type, String counts, boolean isTrue) {
        this.type = type;
        this.counts = counts;
        this.isTrue = isTrue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }
}
