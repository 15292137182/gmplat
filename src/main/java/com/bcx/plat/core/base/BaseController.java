package com.bcx.plat.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 基础控制器
 */
public abstract class BaseController {

  protected Logger logger = LoggerFactory.getLogger(getClass());
  @Autowired
  protected ResourceBundleMessageSource messageSource;



}
