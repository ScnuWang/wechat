package net.imwork.wechat.entity.model;

/**
 * 微信公众号菜单
 * Created by Jason.
 * Date 2017/4/5 14:08
 */
public class Menu {
    //注意这里的属性名称对应生成json字符串之后的名称
    private Button[] button;

    public Button[] getButton() {
        return button;
    }

    public void setButton(Button[] button) {
        this.button = button;
    }
}
