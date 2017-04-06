package net.imwork.wechat.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.imwork.wechat.entity.model.*;
import net.imwork.wechat.service.ITokenService;
import net.imwork.wechat.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.Map;

/**
 * Created by Jason.
 * Date 2017/4/5 16:04
 */
@Controller
public class MenuController {

    @Autowired
    private ITokenService tokenService;

//    @RequestMapping("/wechat")
    @ResponseBody
    public String sign ( String signature,String timestamp,String nonce,String echostr ){
        System.out.println("========接收到微信服务器开发者服务器配置请求===========");
        String[] strArr = new String[]{CommonAPI.TOKEN,timestamp,nonce};
        //将token、timestamp、nonce三个参数进行字典序排序
        Arrays.sort(strArr);
        //将三个参数字符串拼接成一个字符串
        StringBuffer buffer = new StringBuffer();
        for (String string : strArr) {
            buffer.append(string);
        }
        //进行sha1加密并与signature对比
        if (signature.equals(EncodeUtil.SHA1(buffer.toString()))) {
            return echostr;
        }else{
            return "Hi!brother,What do you want to do?";
        }
    }


    @RequestMapping("/addTempMaterial")
    public void addTempMaterial(){
        //这个地址为可访问的网上的图片地址，可以是通过外网映射的本服务器的地址
        String accessToken = tokenService.getTToken(1).getAccesstoken();
        String mediaFileUrl = "http://1670a21b58.imwork.net/wechat/picture/timg.jpg";
        String type = "image";
        String media_id = UploadUtil.uploadMediaTemp(accessToken,type,mediaFileUrl);
        System.out.println(media_id);
    }

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
        String url = CommonAPI.CREATE_MENU_URL.replaceAll(CommonAPI.ACCESS_TOKEN,token.getAccesstoken());
        JsonObject result = HttpUtil.httpsRequest(url,"POST",menuStr);
        if (result != null && result.get("errcode") != null &&result.get("errcode").getAsInt() == 0){
            System.out.println("菜单创建成功");
        }else{
            System.out.println("菜单创建失败");
        }
    }

    //服务器地址和配置的地址一致
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
            Map reqMap = XmlUtil.xmlToMap(reqPram);

            //获取发送者以及接收者
            String toUser = "";//发送人
            String FromUser = "";//接收人
            String EventKey = "";//按钮的id
            String respxml = "";//响应的内容
            String MsgType = "";//消息类型
            String Content = "";//消息内容（关键字）
            String MediaId = "";//消息内容为图片时的id（关键字）
            if (reqMap!=null){
                toUser = (String) reqMap.get("ToUserName");
                FromUser = (String) reqMap.get("FromUserName");
                EventKey = (String) reqMap.get("EventKey");
                Content = (String) reqMap.get("Content");
                MsgType = (String) reqMap.get("MsgType");
                MediaId = (String) reqMap.get("MediaId");
            }
            //组装返回的信息
            /*  优化：
                1、可以将返回信息存入数据库
                2、写一个方法用于构建返回的XML消息，记得交换ToUserName和FromUserName
                3、通过上传文件接口获取MediaId
                4、用对象封装接收到的消息
             */
            //点击菜单拉取消息时的事件推送：
            if ("3-1".equals(EventKey)){//返回文本消息
                respxml = "<xml>" +
                        "<ToUserName><![CDATA["+FromUser+"]]></ToUserName>" +
                        "<FromUserName><![CDATA["+toUser+"]]></FromUserName>" +
                        "<CreateTime>12345678</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[你好,ImWork]]></Content>" +
                        "</xml>";
            }else if ("3-3".equals(EventKey)){//返回图片消息：MediaId是在微信的测试接口上传的临时图片
                respxml = "<xml>" +
                        "<ToUserName><![CDATA["+FromUser+"]]></ToUserName>" +
                        "<FromUserName><![CDATA["+toUser+"]]></FromUserName>" +
                        "<CreateTime>12345678</CreateTime>" +
                        "<MsgType><![CDATA[image]]></MsgType>" +
                        "<Image>" +
                        "<MediaId><![CDATA[dPEKw5MyWl2ra8dIdB9gdd3PjFoSwRHdP3nlKZokAXSBwwJpf42e3MCMZwOyaaDP]]></MediaId>" +
                        "</Image>" +
                        "</xml>";
            }
            //关键字回复文本
            if("关键字".equals(Content)){//订阅者发送关键字回复
                respxml = "<xml>" +
                        "<ToUserName><![CDATA["+FromUser+"]]></ToUserName>" +
                        "<FromUserName><![CDATA["+toUser+"]]></FromUserName>" +
                        "<CreateTime>12345678</CreateTime>" +
                        "<MsgType><![CDATA[text]]></MsgType>" +
                        "<Content><![CDATA[你好,ImWork，我正在做微信公众号开发]]></Content>" +
                        "</xml>";
            }else if("image".equals(MsgType)){//订阅者发送什么图片回复什么图片
                respxml = "<xml>" +
                        "<ToUserName><![CDATA["+FromUser+"]]></ToUserName>" +
                        "<FromUserName><![CDATA["+toUser+"]]></FromUserName>" +
                        "<CreateTime>12345678</CreateTime>" +
                        "<MsgType><![CDATA[image]]></MsgType>" +
                        "<Image>" +
                        "<MediaId><![CDATA["+MediaId+"]]></MediaId>" +
                        "</Image>" +
                        "</xml>";
            }
            //要先设置属性，再给out赋值，不然微信接收中文会乱码
            response.setContentType("application/xml;charset=utf-8");
            out = response.getWriter();
            out.print(respxml);
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
