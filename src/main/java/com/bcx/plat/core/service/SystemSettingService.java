package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SystemSetting;
import com.bcx.plat.core.manager.SystemSettingManager;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

  /**
   * 保存最新的设置信息
   *
   * @param settingMap 设置map
   * @return 返回
   */
  public ServerResult saveSettings(Map<String, Object> settingMap) {
    if (settingMap != null) {
      settingMap.forEach((key, value) -> {
        if (SystemSettingManager.getValidSettingKeys().contains(key) && null != value) {
          SystemSetting systemSetting = new SystemSetting();
          systemSetting.setKey(key);
          systemSetting.setValue(String.valueOf(value));
          // 先查询是否存在
          Condition condition = new ConditionBuilder(SystemSetting.class)
                  .and()
                  .equal("key", key)
                  .endAnd()
                  .buildDone();
          if (select(condition).size() == 0) {
            systemSetting.buildCreateInfo().insert();
          } else {
            systemSetting.buildModifyInfo().update(condition);
          }
        }
      });
      return success(Message.OPERATOR_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * @return 删除所有设置信息即可
   */
  public ServerResult resetResult() {
    new SystemSetting().deleteAll();
    return success(Message.OPERATOR_SUCCESS);
  }

}