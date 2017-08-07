package com.bcx.plat.core.base.impl;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create By HCL at 2017/8/7
 */
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public ServiceResult select(Map map, boolean isPage, int pageNo, int pageSize) {
    return null;
  }

  @Override
  public ServiceResult update(T bean) {
    return null;
  }

  @Override
  public ServiceResult insert(T bean) {
    return null;
  }

  @Override
  public ServiceResult delete(Map map) {
    return null;
  }
}
