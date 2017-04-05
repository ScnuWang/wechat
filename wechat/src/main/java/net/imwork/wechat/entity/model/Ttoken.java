package net.imwork.wechat.entity.model;

import java.util.Date;

/**
 * 凭证
 */
public class Ttoken {
    private Integer pkId;

    private Integer tokenType;

    private String accesstoken;

    private Integer expiresin;

    private Date updateTime;

    private String remark;

    public Integer getPkId() {
        return pkId;
    }

    public void setPkId(Integer pkId) {
        this.pkId = pkId;
    }

    public Integer getTokenType() {
        return tokenType;
    }

    public void setTokenType(Integer tokenType) {
        this.tokenType = tokenType;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken == null ? null : accesstoken.trim();
    }

    public Integer getExpiresin() {
        return expiresin;
    }

    public void setExpiresin(Integer expiresin) {
        this.expiresin = expiresin;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        return "Ttoken{" +
                "pkId=" + pkId +
                ", tokenType=" + tokenType +
                ", accesstoken='" + accesstoken + '\'' +
                ", expiresin=" + expiresin +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}