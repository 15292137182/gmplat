package com.bcx.plat.core.service;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.SystemSetting;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统设置信息查询类
 * Create By HCL at 2017/10/11
 */
public class SystemSettingServiceTest extends BaseTest {

  @Resource
  private SystemSettingService systemSettingService;

  @Test
  public void test() {
    // 查询有效的配置信息，无异常查询结果不为 null
    List<SystemSetting> settings = systemSettingService.selectAllValidSetting();
    assert (settings != null);
  }

}