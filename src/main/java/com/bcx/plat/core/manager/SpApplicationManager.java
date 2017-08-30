package com.bcx.plat.core.manager;

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
    // TODO 登录者 Id
    return "admin";
  }

  /**
   * @return 登录者名称
   */
  public String getLoginUserName() {
    // TODO 登录者名称
    return "系统管理员";
  }
}
