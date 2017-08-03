package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * 业务对象属性mapper Created by Went on 2017/8/1.
 */
@Mapper
public interface BusinessObjectProMapper extends BaseMapper<BusinessObjectPro> {

  /**
   * 根据rowId删除数据
   */
  int delete(String rowId);

  /**
   * 根据业务对象ID来查询业务对象属性
   */
  List<BusinessObject> selectById(String rowId);


}
