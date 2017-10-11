package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.manager.SystemSettingManager;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

  /**
   * @return 获取系统配置
   */
  @RequestMapping(value = "/getSetting", method = POST)
  public PlatResult getAllValidSetting() {
    ServerResult<List> serverResult = new ServerResult<>();
    serverResult.setData(SystemSettingManager.getSystemSettingList());
    serverResult.setStateMessage(STATUS_SUCCESS, "获取系统设置成功！");
    return super.result(serverResult);
  }

}