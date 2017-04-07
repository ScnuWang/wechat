import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.imwork.wechat.entity.model.*;
import net.imwork.wechat.service.ITokenService;
import net.imwork.wechat.utils.CommonAPI;
import net.imwork.wechat.utils.DownloadUtil;
import net.imwork.wechat.utils.HttpUtil;
import net.imwork.wechat.utils.UploadUtil;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.List;

/**
 * Created by Jason.
 * Date 2017/4/1 16:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class WeChatTest {
    private static Logger logger = Logger.getLogger(WeChatTest.class);

    @Autowired
    private ITokenService tokenService;

    /*
        永久非图文文件上传
     */
    @Test
    public void testuploadPermanent() throws FileNotFoundException {
        String accessToken = tokenService.getTToken(1).getAccesstoken();
        String type = "image";
        FileInputStream fileInputStream = new FileInputStream(new File("C:\\weixin\\timg.jpg"));
        String media_id = UploadUtil.uploadMediaPermanent(accessToken,type,"image/jpeg",fileInputStream);
        System.out.println(media_id);
        //media_id:blxqqvBBDSUn7aMogFm5MIKvtj_JsoRwdF1k-7DbfTk
        //url:http://mmbiz.qpic.cn/mmbiz_png/7LxQRFX3cyyWLoYPF1ribhpiaf8md6GWzxlRsax2j8haiaOfm1r8UkT0rASDb0hxFM5fpDv2icFrD7pn2n3TlqpZsw/0?wx_fmt=png
    }
    /*
        获取永久素材列表
     */
    @Test
    public void getuploadPermanent(){
        String accessToken = tokenService.getTToken(1).getAccesstoken();
        String string = DownloadUtil.getMediaPermanentList(accessToken,"image",0,10);
        System.out.println(string);
    }
    /*
        临时文件上传
     */
    @Test
    public void testuploadTemp(){
        String accessToken = tokenService.getTToken(1).getAccesstoken();
        String mediaFileUrl = "http://1670a21b58.imwork.net/wechat/picture/timg.jpg";
        String type = "image";
        String media_id = UploadUtil.uploadMediaTemp(accessToken,type,mediaFileUrl);
        System.out.println(media_id);
    }


    /**
     * 获取token
     */
    @Test
    public void testToken(){
        logger.info("获取凭证");
        Ttoken token = tokenService.getTToken(1);
        logger.info(token);
        System.out.println(token);
        logger.error(token.getAccesstoken());
    }

    /**
     * 创建菜单
     */
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

        ClickButton button33 = new ClickButton();
        button33.setName("个人简历");
        button33.setType("click");
        button33.setKey("3-3");

        ComplexButton button3 = new ComplexButton();
        button3.setName("商务合作");
        button3.setSub_button(new Button[]{button31,button32,button33});

        Menu menu = new Menu();
        menu.setButton(new Button[]{button1,button2,button3});

        Gson gson = new Gson();
        String menustr = gson.toJson(menu).toString();
        System.out.println(menustr);
        Ttoken token = tokenService.getTToken(1);
        System.out.println(token);
        String url = CommonAPI.CREATE_MENU_URL.replaceAll(CommonAPI.ACCESS_TOKEN,token.getAccesstoken());

        JsonObject result = HttpUtil.httpsRequest(url,"POST",menustr);
        if (result != null && result.get("errcode") != null &&result.get("errcode").getAsInt() == 0){
            System.out.println("菜单创建成功："+result.get("errmsg").getAsString());
        }else{
            System.out.println("菜单创建失败："+result.get("errmsg").getAsString());
        }
    }


}
