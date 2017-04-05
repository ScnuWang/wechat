package net.imwork.wechat.utils;

/**
 * 公共的参数
 * @author Jason
 *
 */
public class CommonAPI {
	
	public static String CLASSPATH;

	public static String POST = "POST";

	public static String UTF8 = "UTF-8";

	public static String APPID = "wx1b1dc3b5f5ced089";

	public static String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";

	public static String ACCESS_TOKEN = "ACCESS_TOKEN";

	//获取ACCESS_TOKEN的地址
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	//自定义菜单创建接口地址
	public static String CREATEMENUURL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

}