package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;

/**
 * 序列号规则的服务类
 *
 * Create By HCL at 2017/8/6
 */
public interface SequenceRuleConfigService {

  /**
   * 查询数据事件
   */
  ServiceResult<SequenceRuleConfig> select(Map map);

  /**
   * 新建数据
   */
  ServiceResult<SequenceRuleConfig> insert(SequenceRuleConfig bean);

  /**
   * 更新数据事件
   */
  ServiceResult<SequenceRuleConfig> update(SequenceRuleConfig bean);

  /**
   * 删除数据事件
   */
  ServiceResult<SequenceRuleConfig> delete(Map map);
}
