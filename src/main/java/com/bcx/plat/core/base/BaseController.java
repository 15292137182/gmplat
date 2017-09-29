package com.bcx.plat.core.base;

import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
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
  protected PlatResult result(ServerResult serverResult) {
    return PlatResult.success(serverResult);
  }


  /**
   * 操作成功
   *
   * @param successMessage 服务处理结果
   * @return 平台包装后的结果
   */
  protected PlatResult success(String successMessage) {
    return PlatResult.success(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS,successMessage));
  }


  /**
   * 操作失败
   *
   * @param failMessage 服务处理结果
   * @return 平台包装后的结果
   */
  protected PlatResult error(String failMessage) {
    return PlatResult.success(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL,failMessage));
  }

  /**
   * 返回成功数据
   *
   * @param successMessage 成功消息
   * @param data 成功数据
   * @return 平台包装后的结果
   */
  protected PlatResult successData(String successMessage , Object data) {
    return PlatResult.success(new ServerResult<>(BaseConstants.STATUS_SUCCESS,successMessage, data));
  }



}
