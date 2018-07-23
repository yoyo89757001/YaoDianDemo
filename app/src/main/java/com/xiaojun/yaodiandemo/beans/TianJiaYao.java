package com.xiaojun.yaodiandemo.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/7/23.
 */
@Entity
public class TianJiaYao {
    @Id
    @NotNull
    private Long id;
    private String name;
    private String yaoming;
    private int shuliang;
    private String riqi;
    private String sfzHao;
    private String tuPianPath;
    @Generated(hash = 1643088478)
    public TianJiaYao(@NotNull Long id, String name, String yaoming, int shuliang,
            String riqi, String sfzHao, String tuPianPath) {
        this.id = id;
        this.name = name;
        this.yaoming = yaoming;
        this.shuliang = shuliang;
        this.riqi = riqi;
        this.sfzHao = sfzHao;
        this.tuPianPath = tuPianPath;
    }
    @Generated(hash = 1309602906)
    public TianJiaYao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getYaoming() {
        return this.yaoming;
    }
    public void setYaoming(String yaoming) {
        this.yaoming = yaoming;
    }
    public int getShuliang() {
        return this.shuliang;
    }
    public void setShuliang(int shuliang) {
        this.shuliang = shuliang;
    }
    public String getRiqi() {
        return this.riqi;
    }
    public void setRiqi(String riqi) {
        this.riqi = riqi;
    }
    public String getSfzHao() {
        return this.sfzHao;
    }
    public void setSfzHao(String sfzHao) {
        this.sfzHao = sfzHao;
    }
    public String getTuPianPath() {
        return this.tuPianPath;
    }
    public void setTuPianPath(String tuPianPath) {
        this.tuPianPath = tuPianPath;
    }

    

}
