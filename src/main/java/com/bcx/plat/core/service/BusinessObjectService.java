package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;

/**
 * 业务对象业务层 Created by Went on 2017/8/1.
 */
public interface BusinessObjectService {

  /**
   * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  ServiceResult<BusinessObject> select(Map map);

  /**
   * 新增业务对象录入框，包括：对象代码，对象名称，关联表(单选)，版本(系统生成)
   */
  ServiceResult<BusinessObject> insert(BusinessObject businessObject);

  /**
   * 编辑对象名称字段
   */
  ServiceResult<BusinessObject> update(BusinessObject businessObject);

  /**
   * 删除业务对象
   */
  ServiceResult<BusinessObject> delete(String rowId);

  /**
   * 获取ID对该条记录,失效改为生效
   */
  ServiceResult<BusinessObject> updateTakeEffect(String rowId);
}
