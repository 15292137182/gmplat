package com.sp.plat.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringConfigTool implements ApplicationContextAware {
	private static ApplicationContext context = null;
	private static SpringConfigTool stools = null;

	public synchronized static SpringConfigTool init() {
		if (stools == null) {
			stools = new SpringConfigTool();
		}
		return stools;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	/**
	 * 获取 Bean 方法
	 * 
	 * @param beanName
	 * @return
	 */
	public synchronized static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	public static boolean isInit() {
		return context != null;
	}

}
