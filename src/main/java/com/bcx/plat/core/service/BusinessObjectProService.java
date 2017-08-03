package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.utils.ServiceResult;

/**
 * 业务对象属性业务层 Created by Went on 2017/8/1.
 */
public interface BusinessObjectProService {

  /**
   * 查询业务对象属性 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
   */
  ServiceResult<BusinessObjectPro> select(String rowId);

  /**
   * 新增业务对象录入框，包括：对象代码，对象名称，关联表(单选)，版本(系统生成)
   */
  ServiceResult<BusinessObjectPro> insert(BusinessObjectPro businessObjectPro);


  /**
   * 删除业务对象
   */
  ServiceResult<BusinessObjectPro> delete(String rowId);
}
