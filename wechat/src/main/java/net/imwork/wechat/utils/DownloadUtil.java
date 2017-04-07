package net.imwork.wechat.utils;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载
 * Created by Jason.
 * Date 2017/4/7 14:25
 */
public class DownloadUtil {

    /**
     * 获取永久素材列表
     * @param accessToken 接口访问凭证
     * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
     * @param count 返回素材的数量，取值在1到20之间
     * @return
     */
    public static String getMediaPermanentList(String accessToken, String type, int offset, int count) {
        String retuJson = null;
        // 拼接请求地址
        String requestUrl = CommonAPI.URL_GET_MEDIA_PERMANENT_LIST+accessToken;
        try
        {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod(CommonAPI.POST);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            String jsonStr = "{\"type\":\""+type+"\",\"offset\":"+offset+",\"count\":"+count+"}";
            out.write(jsonStr.getBytes(CommonAPI.UTF8));
            out.flush();
            out.close();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            StringBuilder sBuilder = new StringBuilder();
            byte[] buf = new byte[8096];
            int size = 0;
            while ((size = bis.read(buf)) != -1)
                sBuilder.append(new String(buf));
            bis.close();
            conn.disconnect();
            retuJson  =sBuilder.toString();
            System.out.println(sBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retuJson;
    }
}
