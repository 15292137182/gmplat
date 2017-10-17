package com.bcx.plat.core.service;

import com.bcx.BaseTest;
import com.bcx.plat.core.utils.ServerResult;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Create By HCL at 2017/10/17
 */
public class UserServiceTest extends BaseTest {

  @Resource
  private UserService userService;

  @Test
  public void test() {
    // 多次测试
    ServerResult serverResult = userService.queryPage("", null, 1, 10, "");
    ServerResult serverResult1 = userService.queryPage("", null, 1, 10, "");
    ServerResult serverResult2 = userService.queryPage("", null, 1, 10, "");
    ServerResult serverResult3 = userService.queryPage("", null, 1, 10, "");
    ServerResult serverResult4 = userService.queryPage("", null, 1, 10, "");
  }

}