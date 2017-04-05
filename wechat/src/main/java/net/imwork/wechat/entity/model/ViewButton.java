package net.imwork.wechat.entity.model;

/**
 * 视图类型的按钮
 * Created by Jason.
 * Date 2017/4/5 14:16
 */
public class ViewButton extends Button {

    private String url;
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
