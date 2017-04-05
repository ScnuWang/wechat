package net.imwork.wechat.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jason.
 * Date 2017/4/1 15:02
 */
public class HttpUtil {

    public static JsonObject httpGet(String url) throws IOException {
        JsonObject jsonObject = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        if( responseEntity != null ){
            String result = EntityUtils.toString(responseEntity);
            jsonObject = new JsonParser().parse(result).getAsJsonObject();
        }
        return jsonObject;
    }

    public static JsonObject httpPost (String url, Map<String ,Object> mapParams) throws IOException {
        JsonObject jsonObject = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        //这里只能转换成String类型的键值对
        for (Map.Entry map : mapParams.entrySet()) {
            formparams.add(new BasicNameValuePair(map.getKey().toString(), map.getValue().toString()));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        System.out.println(entity);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if( responseEntity != null ){
            String result = EntityUtils.toString(responseEntity);
            System.out.println(result);
            jsonObject = new JsonParser().parse(result).getAsJsonObject();
        }
        return jsonObject;
    }

    /**
     *
     * @param urlStr 请求地址
     * @param resquestMethod 请求方式
     * @param outputStr 请求参数
     * @return 返回Json对象
     */
    public static JsonObject httpsRequest(String urlStr, String resquestMethod, String outputStr) {
        JsonObject jsonObject = null;
        HttpsURLConnection conn = null;
        try {
            //建立连接
            URL url = new URL(urlStr);
            conn = (HttpsURLConnection) url.openConnection();

            //设置自定义菜单信任管理器
            TrustManager[] tm = {new ImWorkTrustManager()};
            SSLContext ssl = SSLContext.getInstance("SSL", "SunJSSE");
            ssl.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = ssl.getSocketFactory();
            conn.setSSLSocketFactory(ssf);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //设置请求方式
            conn.setRequestMethod(resquestMethod);

            if (CommonAPI.POST.equals(resquestMethod)) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes(CommonAPI.UTF8));
                os.close();
            }
            //取得输入流
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, CommonAPI.UTF8);
            BufferedReader bfr = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }
            bfr.close();
            isr.close();
            is.close();
            jsonObject = new JsonParser().parse(sb.toString()).getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();

        }finally{
            if(conn != null){
                conn.disconnect();
            }
        }
        return jsonObject;
    }

    /**
     *
     * @param urlStr
     * @param resquestMethod
     * @param outputStr
     * @return 返回XML字符串
     */
    public static String httpsRequestXML(String urlStr, String resquestMethod, String outputStr) {
        String result = null;
        try {
            //建立连接
            URL url = new URL(urlStr);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            //设置自定义菜单信任管理器
            TrustManager[] tm = {new ImWorkTrustManager()};
            SSLContext ssl = SSLContext.getInstance("SSL", "SunJSSE");
            ssl.init(null, tm, new SecureRandom());
            SSLSocketFactory ssf = ssl.getSocketFactory();
            conn.setSSLSocketFactory(ssf);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //设置请求方式
            conn.setRequestMethod(resquestMethod);

            if (CommonAPI.POST.equals(resquestMethod))
            {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes(CommonAPI.UTF8));
                os.close();
            }

            //取得输入流
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, CommonAPI.UTF8);
            BufferedReader bfr = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = bfr.readLine()) != null) {
                sb.append(line);
            }
            bfr.close();
            isr.close();
            is.close();
            conn.disconnect();
            result=sb.toString().replace("<![CDATA[", "").replace("]]>", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        Map<String ,Object> map = new HashMap();
        map.put("name","冬冬");
        map.put("age:",26);
        map.put("level:",1);
        JsonObject jsonObject = httpPost("http://localhost/analysis_system/show/test",map);
        System.out.println(jsonObject.toString());
    }
}
