package com.bcx.plat.core.manager;

import com.bcx.plat.core.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * Create By HCL at 2017/8/30
 */
public class SpApplicationManager {

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
  public User getLoginUser() {
    User _user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
    if (null != _user) {
      return _user;
    }
    return new User();
  }
}
