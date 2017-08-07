package com.bcx.plat.core.base;


import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface BaseService<T extends BaseEntity> {

  /**
   * 查询方法
   *
   * @param map 查询参数
   * @param page 是否分页
   * @return 返回
   */
  ServiceResult select(Map map, boolean isPage, int page, int limit);

  ServiceResult update(T bean);

  ServiceResult insert(T bean);

  ServiceResult delete(Map map);
}