package net.imwork.wechat.service.impl;

import com.google.gson.JsonObject;
import net.imwork.wechat.entity.dao.TtokenMapper;
import net.imwork.wechat.entity.model.Ttoken;
import net.imwork.wechat.service.ITokenService;
import net.imwork.wechat.utils.CommonAPI;
import net.imwork.wechat.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Jason.
 * Date 2017/4/1 14:45
 */
@Service("TokenServiceImpl")
public class TokenServiceImpl implements ITokenService {


    private TtokenMapper ttokenMapper;

    @Autowired
    public void setTtokenMapper(TtokenMapper ttokenMapper) {
        this.ttokenMapper = ttokenMapper;
    }

    public Ttoken getTToken(Integer tokenType) {
        /*
            1、查询数据库获取token，查询结果为空，调用微信获取token的接口，获取token同时插入到数据库
            2、查询结果不为空，判断是否过时，已过时调用微信获取token的接口，获取token同时更新到数据库
            3、根据修改时间和当前时间之差与token的有效时间进行比较，来判断是否已经失效
            4、否则直接返回
         */
        Ttoken token = ttokenMapper.selectByTokenType(1);
        if( token == null ){
            try {
               JsonObject jsonObjectResult =  HttpUtil.httpGet(CommonAPI.TOKENURL.replaceAll("APPID",CommonAPI.APPID).replaceAll("APPSECRET",CommonAPI.APPSECRET));
               if(jsonObjectResult.get("errcode")==null){
                   String accessToken  = jsonObjectResult.get("access_token").getAsString();
                   String expiresin  = jsonObjectResult.get("expires_in").getAsString();
                   token = new Ttoken();
                   token.setAccesstoken(accessToken);
                   token.setExpiresin(Integer.parseInt(expiresin));
                   token.setUpdateTime(new Date());
                   token.setTokenType(tokenType);
                   token.setRemark("微信订阅号");
                   ttokenMapper.insert(token);
               }else{
                   System.out.println(jsonObjectResult.get("errcode")+":"+jsonObjectResult.get("errmsg").getAsString());
               }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if ((new Date().getTime() - token.getUpdateTime().getTime())/1000>token.getExpiresin()-100){//预留100秒
            updateToken(token);
        }
        return token;
    }


    public Ttoken updateToken(Ttoken token) {
        try {
            JsonObject jsonObjectResult =  HttpUtil.httpGet(CommonAPI.TOKENURL.replaceAll("APPID",CommonAPI.APPID).replaceAll("APPSECRET",CommonAPI.APPSECRET));
            if(jsonObjectResult.get("errcode")==null){
                String accessToken  = jsonObjectResult.get("access_token").getAsString();
                String expiresin  = jsonObjectResult.get("expires_in").getAsString();
                token.setAccesstoken(accessToken);
                token.setExpiresin(Integer.parseInt(expiresin));
                token.setUpdateTime(new Date());
                ttokenMapper.updateByPrimaryKey(token);
            }else{
                System.out.println(jsonObjectResult.get("errcode")+":"+jsonObjectResult.get("errmsg").getAsString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}
