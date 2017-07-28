package com.bcx.plat.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 基础控制器
 */
public class BaseController {

    /**
     * logger 日志操作
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * messageSource 资源文件管理器
     */
    @Autowired
    protected ResourceBundleMessageSource messageSource;
}
