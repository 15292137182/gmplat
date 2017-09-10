package com.bcx.plat.core.base;

import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.SystemResult;
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

  /**
   * 处理返回结果
   *
   * @param serverResult 服务处理结果
   * @return 平台包装后的结果
   */
  protected SystemResult result(ServerResult serverResult) {
    return SystemResult.success(serverResult);
  }

}
