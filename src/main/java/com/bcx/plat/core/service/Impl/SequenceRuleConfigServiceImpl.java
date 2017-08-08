package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.base.BaseConstants.LOGIC_DELETE;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;
import static com.bcx.plat.core.constants.Message.OPERATOR_SUCCESS;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.mapper.SequenceRuleConfigMapper;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 序列号配置服务类
 *
 * Create By HCL at 2017/8/6
 */
@Service
@Transactional
public class SequenceRuleConfigServiceImpl extends
    BaseServiceTemplate<SequenceRuleConfig> implements
    SequenceRuleConfigService {

  @Autowired
  private SequenceRuleConfigMapper sequenceRuleConfigMapper;

  /**
   * 查询数据事件
   *
   * @param map 查询条件
   * @return 返回
   */
  @Override
  public ServiceResult select(Map map) {

    return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS,
        sequenceRuleConfigMapper.select(map));
  }

  /**
   * 新建数据事件
   *
   * @param bean 数据
   * @return 返回
   */
  @Override
  public ServiceResult insert(SequenceRuleConfig bean) {
    bean.buildCreateInfo();
    return new ServiceResult<>(sequenceRuleConfigMapper.insert(bean), OPERATOR_SUCCESS,
        bean.getRowId());
  }

  @Override
  public ServiceResult update(SequenceRuleConfig bean) {
    bean.buildModifyInfo();
    return new ServiceResult<>(sequenceRuleConfigMapper.update(bean), OPERATOR_SUCCESS,
        bean.getRowId());
  }

  @Override
  public ServiceResult delete(Map map) {
    if (LOGIC_DELETE) {
      map.putAll(new BaseEntity<>().buildDeleteInfo().toMap());
      return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS,
          sequenceRuleConfigMapper.batchLogicDelete(map));
    } else {
      return new ServiceResult<>(STATUS_SUCCESS, OPERATOR_SUCCESS,
          sequenceRuleConfigMapper.batchDelete(map));
    }
  }
}
