package net.imwork.wechat.entity.model;

/**
 * 点击类型的按钮
 * Created by Jason.
 * Date 2017/4/5 14:13
 */
public class ClickButton extends Button {
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
