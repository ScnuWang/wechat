package net.imwork.wechat.utils;

/**
 * Created by Jason.
 * Date 2017/4/6 13:24
 */
public class CommonUtils {
    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        switch (contentType) {
            case "image/jpeg" :
                fileExt = ".jpg";
                break;
            case "audio/mpeg" :
                fileExt = ".mp3";
                break;
            case "audio/amr" :
                fileExt = ".amr";
                break;
            case "video/mp4" :
                fileExt = ".mp4";
                break;
            case "video/mpeg4" :
                fileExt = ".mp4";
                break;
        }
        return fileExt;
    }
}
