import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.imwork.wechat.entity.model.*;
import net.imwork.wechat.service.ITokenService;
import net.imwork.wechat.utils.CommonAPI;
import net.imwork.wechat.utils.HttpUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Jason.
 * Date 2017/4/1 16:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TokenTest {

    @Autowired
    private ITokenService tokenService;

    @Test
    public void testToken(){
        Ttoken token = tokenService.getTToken(1);
        System.out.println(token);
    }

    @Test
    public void  testEvent(){

        String xml  = "<xml>" +
                "<ToUserName><![CDATA[toUser]]></ToUserName>" +
                "<FromUserName><![CDATA[FromUser]]></FromUserName>" +
                "<CreateTime>123456789</CreateTime>" +
                "<MsgType><![CDATA[event]]></MsgType>" +
                "<Event><![CDATA[CLICK]]></Event>" +
                "<EventKey><![CDATA[EVENTKEY]]></EventKey>" +
                "</xml>";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read((new ByteArrayInputStream(xml.getBytes("UTF-8"))));
            Element root = document.getRootElement();
            List<Element> elementList  = root.elements();
            for (Element e: elementList) {
                System.out.println(e.getName());
                System.out.println(e.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Test
    public void testCreatemenu(){
        createMebu();
    }

    public  void createMebu(){
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
        Ttoken token = tokenService.getTToken(1);
        System.out.println(token);
        String url = CommonAPI.CREATEMENUURL.replaceAll(CommonAPI.ACCESS_TOKEN,token.getAccesstoken());

        JsonObject result = HttpUtil.httpsRequest(url,"POST",menustr);
        if (result != null && result.get("errcode") != null &&result.get("errcode").getAsInt() == 0){
            System.out.println("菜单创建成功");
        }else{
            System.out.println("菜单创建失败");
        }
    }


}
