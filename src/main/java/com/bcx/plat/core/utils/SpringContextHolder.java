package com.bcx.plat.core.utils;

import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 工具类
 * Created by HCL on 2017/7/28.
 */
public class SpringContextHolder implements ApplicationContextAware {

    /**
     * 自动注入应用程序上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 根据名称获取 JavaBean
     */
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 取得存储在静态变量中的 ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 该类用于获取Spring上下文
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     *
     * @param applicationContext 需要注入的 ApplicationContext
     * @throws BeansException 抛出 bean 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 检查 ApplicationContext
     */
    private static void checkApplicationContext() {
        if (null == applicationContext) {
            LoggerFactory
                    .getLogger("com.sw.plat.core.utils.SpringContextHolder")
                    .error("applicationContext未注入,请确保该类被注册为 javaBean");
            throw new IllegalStateException("applicationContext未注入,请确保该类被注册为 javaBean");
        }
    }
}