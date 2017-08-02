package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.MaintTableInfo;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Went on 2017/7/31.
 */
@Mapper
public interface MaintTableMapper extends BaseMapper<MaintTableInfo> {

  /**
   * 输入框中输入空格分隔的查询关键字（表Schema、表中英文名称）
   */
  List<MaintTableInfo> selectMaint(Map<String, Object> map);

  /**
   * 根据id查询数据库表字段
   */
  List<MaintTableInfo> selectById(String rowId);
}
