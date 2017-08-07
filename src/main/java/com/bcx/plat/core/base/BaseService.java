package com.bcx.plat.core.base;


import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface BaseService<T extends BaseEntity> {

  ServiceResult select(Map map);

  ServiceResult update(T bean);

  ServiceResult insert(T bean);

  ServiceResult delete(Map map);
}