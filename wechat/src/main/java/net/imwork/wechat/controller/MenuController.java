package net.imwork.wechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.imwork.wechat.entity.model.*;
import net.imwork.wechat.service.ITokenService;
import net.imwork.wechat.utils.CommonAPI;
import net.imwork.wechat.utils.CreateMenu;
import net.imwork.wechat.utils.HttpUtil;
import net.imwork.wechat.utils.XmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by Jason.
 * Date 2017/4/5 16:04
 */
@Controller
public class MenuController {

    @Autowired
    private ITokenService tokenService;

    @RequestMapping("/createmenu")
    public void createMebu(){
        /*
            1、创建合成菜单的按钮组件
            2、将菜单转换成json格式
            3、获取token
            4、调用微信自定义菜单的接口
         */
        String menuStr = CreateMenu.createMebu();
        Ttoken token = tokenService.getTToken(1);
        String url = CommonAPI.CREATEMENUURL.replaceAll(CommonAPI.ACCESS_TOKEN,token.getAccesstoken());
        JsonObject result = HttpUtil.httpsRequest(url,"POST",menuStr);
        if (result != null && result.get("errcode") != null &&result.get("errcode").getAsInt() == 0){
            System.out.println("菜单创建成功");
        }else{
            System.out.println("菜单创建失败");
        }
    }
    //点击菜单拉取消息时的事件推送：服务器地址和配置的地址一致
    @RequestMapping("/wechat")
    public void menuEvent(HttpServletRequest request, HttpServletResponse response){
        /*
            1、微信客户端点击按钮
            2、接收微信服务器请求
            3、解析请求信息
            4、处理响应:使用PrintWriter处理返回内容;回复的内容也是xml格式的
         */
        PrintWriter out = null;
        try {
            InputStream is = request.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is,CommonAPI.UTF8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            is.close();
            String reqPram = sb.toString().replace("<![CDATA[", "").replace("]]>", "");
            System.out.println(reqPram);
            Map reqMap = XmlUtil.xmlToMap(reqPram);

            //获取发送者以及接收者
            String toUser = "";
            String FromUser = "";
            if (reqMap!=null){
                toUser = (String) reqMap.get("reqMap");
                FromUser = (String) reqMap.get("FromUser");
            }
            //组装返回的信息
            String respxml = "<xml>" +
                    "<ToUserName><![CDATA["+FromUser+"]]></ToUserName>" +
                    "<FromUserName><![CDATA["+toUser+"]]></FromUserName>" +
                    "<CreateTime>12345678</CreateTime>" +
                    "<MsgType><![CDATA[text]]></MsgType>" +
                    "<Content><![CDATA[你好]]></Content>" +
                    "</xml>";
            response.setContentType("application/xml;charset=utf-8");
            out = response.getWriter();
            out.print(respxml);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
                out = null;
            }
        }
    }
}
