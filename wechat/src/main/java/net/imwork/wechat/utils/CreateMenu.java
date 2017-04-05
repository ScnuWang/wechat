package net.imwork.wechat.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.imwork.wechat.entity.model.*;

/**
 * 创建菜单
 * Created by Jason.
 * Date 2017/4/5 14:26
 */
public class CreateMenu {

    public static String createMebu(){
        /*
            1、创建合成菜单的按钮组件
            2、将菜单转换成json格式
            3、获取token
            4、调用微信自定义菜单的接口
         */
        ViewButton button1 = new ViewButton();
        button1.setName("今日头条");
        button1.setType("view");
        button1.setUrl("http://www.365yg.com/item/6405128177852613122/");

        ViewButton button2 = new ViewButton();
        button2.setName("CSDN博客");
        button2.setType("view");
        button2.setUrl("http://blog.csdn.net/qq_26718271");

        ClickButton button31 = new ClickButton();
        button31.setName("商务合作");
        button31.setType("click");
        button31.setKey("3-1");

        ViewButton button32 = new ViewButton();
        button32.setName("百度一下");
        button32.setType("view");
        button32.setUrl("http://www.365yg.com/item/6405128177852613122/");

        ComplexButton button3 = new ComplexButton();
        button3.setName("商务合作");
        button3.setSub_button(new Button[]{button31,button32});

        Menu menu = new Menu();
        menu.setButton(new Button[]{button1,button2,button3});

        Gson gson = new Gson();
        String menustr = gson.toJson(menu).toString();
        System.out.println(menustr);
        return  menustr;
    }

}
