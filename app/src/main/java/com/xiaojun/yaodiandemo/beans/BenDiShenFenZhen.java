package com.xiaojun.yaodiandemo.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/7/23.
 */

@Entity
public class BenDiShenFenZhen {
    @Id
    @NotNull
    private Long id;
    private String partyName;
    private String gender;
    private String nation;
    private String bornDay;
    private String certAddress;
    private String certNumber=null;
    private String certOrg;
    private String effDate;
    private String expDate;
    private String cardPhoto=null;
    private String scanPhoto=null;
    private String type;
    @Generated(hash = 646931991)
    public BenDiShenFenZhen(@NotNull Long id, String partyName, String gender,
            String nation, String bornDay, String certAddress, String certNumber,
            String certOrg, String effDate, String expDate, String cardPhoto,
            String scanPhoto, String type) {
        this.id = id;
        this.partyName = partyName;
        this.gender = gender;
        this.nation = nation;
        this.bornDay = bornDay;
        this.certAddress = certAddress;
        this.certNumber = certNumber;
        this.certOrg = certOrg;
        this.effDate = effDate;
        this.expDate = expDate;
        this.cardPhoto = cardPhoto;
        this.scanPhoto = scanPhoto;
        this.type = type;
    }
    @Generated(hash = 1463439076)
    public BenDiShenFenZhen() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPartyName() {
        return this.partyName;
    }
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNation() {
        return this.nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getBornDay() {
        return this.bornDay;
    }
    public void setBornDay(String bornDay) {
        this.bornDay = bornDay;
    }
    public String getCertAddress() {
        return this.certAddress;
    }
    public void setCertAddress(String certAddress) {
        this.certAddress = certAddress;
    }
    public String getCertNumber() {
        return this.certNumber;
    }
    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }
    public String getCertOrg() {
        return this.certOrg;
    }
    public void setCertOrg(String certOrg) {
        this.certOrg = certOrg;
    }
    public String getEffDate() {
        return this.effDate;
    }
    public void setEffDate(String effDate) {
        this.effDate = effDate;
    }
    public String getExpDate() {
        return this.expDate;
    }
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }
    public String getCardPhoto() {
        return this.cardPhoto;
    }
    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }
    public String getScanPhoto() {
        return this.scanPhoto;
    }
    public void setScanPhoto(String scanPhoto) {
        this.scanPhoto = scanPhoto;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }




}
