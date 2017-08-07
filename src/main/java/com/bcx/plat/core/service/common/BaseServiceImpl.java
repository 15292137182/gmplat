package com.bcx.plat.core.service.common;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By HCL at 2017/8/7
 */
public class BaseServiceImpl<T extends BaseEntity<T>> extends BaseServiceTemplate<T> implements
    BaseService<T> {

  /**
   * 日志输出
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());
}
