package net.imwork.wechat.listener;

import net.imwork.wechat.utils.CommonAPI;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ParameterInitListener implements ApplicationListener<ContextRefreshedEvent> {

	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null){
			initParameter();
		}
	}

	/**
	 * 初始化参数
	 */
	private void initParameter(){
		
		try {
			System.out.println("初始化参数...");
			String classPath  = this.getClass().getResource("/").getPath();
			//初始化classpath
			CommonAPI.CLASSPATH = classPath.substring(0,classPath.indexOf("classes"));
			System.out.println(CommonAPI.CLASSPATH);
			System.out.println("初始化参数成功！");
		} catch (Exception e) {
			System.out.println("初始化参数失败！");
			e.printStackTrace();
		}
	}

}
