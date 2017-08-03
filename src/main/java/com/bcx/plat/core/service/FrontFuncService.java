package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;

/**
 * 前端功能块service层 Created by Went on 2017/8/2.
 */
public interface FrontFuncService {

  /**
   * 按照名称、代码、类型 条件查询
   */
  ServiceResult<FrontFunc> select(Map<String, Object> map);

  /**
   * 新增数据
   */
  String insert(FrontFunc frontFunc);

  /**
   * 根据id修改数据
   */
  int update(FrontFunc frontFunc);

  /**
   * 根据ID删除数据
   */
  int delete(FrontFunc frontFunc);


}
