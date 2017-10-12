package com.bcx.plat.core.manager;

import com.bcx.plat.core.entity.Users;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By HCL at 2017/8/30
 */
public class SpApplicationManager {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private SpApplicationManager() {
  }

  public static SpApplicationManager getInstance() {
    return new SpApplicationManager();
  }

  /**
   * @return 登录者 Id
   */
  public String getLoginUserId() {
    return getLoginUser().getId();
  }

  /**
   * @return 登录者名称
   */
  public String getLoginUserName() {
    return getLoginUser().getName();
  }

  /**
   * 获取登录者信息
   *
   * @return 返回登录者
   */
  public Users getLoginUser() {
    Users _user = null;
    try {
      _user = (Users) SecurityUtils.getSubject().getSession().getAttribute("user");
    } catch (RuntimeException ignore) {
      logger.error("获取用户失败，已使用空 user 信息");
    }
    if (null != _user) {
      return _user;
    }
    return new Users();
  }
}
