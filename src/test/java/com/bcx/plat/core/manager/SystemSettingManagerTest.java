package com.bcx.plat.core.manager;

import com.bcx.BaseTest;
import org.junit.Test;

import java.util.List;

/**
 * 系统设置单元测试
 * <p>
 * Create By HCL at 2017/10/11
 */
public class SystemSettingManagerTest extends BaseTest {

  @Test
  public void test() {
    List settings = SystemSettingManager.getSystemSettingList();
    assert settings != null;

    String defaultPassword = SystemSettingManager.getDefaultPwd();
    assert null != defaultPassword;
  }

}