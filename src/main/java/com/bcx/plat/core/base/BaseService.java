package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseService
 * <p>
 * Create By HCL at 2017/8/1
 */
public abstract class BaseService<T extends BeanInterface<T>> {

  /**
   * 日志
   */
  private Logger logger = LoggerFactory.getLogger(getClass());


}