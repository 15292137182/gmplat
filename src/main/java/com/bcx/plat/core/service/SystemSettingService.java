package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.SystemSetting;
import com.bcx.plat.core.manager.SystemSettingManager;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统设置服务类
 * <p>
 * Create By HCL at 2017/10/11
 */
@Service
public class SystemSettingService extends BaseService<SystemSetting> {

  /**
   * 查询所有有效的系统配置
   * 哪些是有效的系统配置：
   * 例如：sessionTimeout 这个键在系统中是有用到的，我们就认为他是有效的
   * impossible 这种没什么用的就是无效的
   *
   * @return 返回系统配置集合
   * @see com.bcx.plat.core.manager.SystemSettingManager
   */
  public List<SystemSetting> selectAllValidSetting() {
    Condition condition = new ConditionBuilder(SystemSetting.class)
            .and()
            .in("key", SystemSettingManager.getValidSettingKeys())
            .endAnd()
            .buildDone();
    return select(condition);
  }

}