package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * Created by Wen Tiehu on 2017/8/14.
 */
@Service
public class SequenceRuleConfigService extends BaseService<SequenceRuleConfig> {

  /**
   * @param rowId rowId 主键
   * @return 返回查询到的序列号规则配置信息，查询不到时会返回 null
   */
  public SequenceRuleConfig selectOneById(Serializable rowId) {
    return new SequenceRuleConfig().selectOneById(rowId);
  }

}
