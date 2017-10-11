package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.manager.SystemSettingManager;
import com.bcx.plat.core.service.SystemSettingService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 系统设置控制器
 * <p>
 * Create By HCL at 2017/10/11
 */
@RestController
@RequestMapping(value = PLAT_SYS_PREFIX + "/core/sysSetting")
public class SystemSettingController extends BaseController {

  @Resource
  private SystemSettingService systemSettingService;

  /**
   * @return 获取系统配置
   */
  @RequestMapping(value = "/getSettings", method = POST)
  public PlatResult getAllValidSettings() {
    ServerResult<List> serverResult = new ServerResult<>();
    serverResult.setData(SystemSettingManager.getSystemSettingList());
    serverResult.setStateMessage(STATUS_SUCCESS, "获取系统设置成功！");
    return super.result(serverResult);
  }

  /**
   * 保存设置事件
   *
   * @param settings 传入的settings 信息
   * @return 返回操作结果
   */
  @RequestMapping(value = "/saveSettings", method = POST)
  public PlatResult saveSettings(Map<String, Object> settings) {
    return super.result(systemSettingService.saveSettings(settings));
  }

  /**
   * 重置设置接口
   *
   * @return 返回有效信息
   */
  @RequestMapping(value = "/resetSettings", method = POST)
  public PlatResult resetSetting() {
    return super.result(systemSettingService.resetResult());
  }

}