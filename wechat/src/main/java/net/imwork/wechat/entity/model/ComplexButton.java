package net.imwork.wechat.entity.model;

/**
 * 组合按钮
 * Created by Jason.
 * Date 2017/4/5 14:20
 */
public class ComplexButton extends Button{
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
