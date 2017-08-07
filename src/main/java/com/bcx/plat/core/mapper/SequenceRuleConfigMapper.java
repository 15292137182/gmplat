package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * 序列号规则配置流水号类
 *
 * Create By HCL at 2017/8/6
 */
@Mapper
public interface SequenceRuleConfigMapper extends BaseMapper {

  /**
   * 批量逻辑删除
   *
   * @param map 参数
   * @return 返回状态
   */
  int batchLogicDelete(Map map);
}
