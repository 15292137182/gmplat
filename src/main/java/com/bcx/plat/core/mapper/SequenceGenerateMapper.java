package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.SequenceGenerate;
import org.apache.ibatis.annotations.Mapper;

/**
 * Create By HCL at 2017/8/9
 */
@Mapper
public interface SequenceGenerateMapper extends BaseMapper<SequenceGenerate> {

  int updateCurrentValue(SequenceGenerate sequenceGenerate);

  int resetSequenceValue(SequenceGenerate sequenceGenerate);
}
