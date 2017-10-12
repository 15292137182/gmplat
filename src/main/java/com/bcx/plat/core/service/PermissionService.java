package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Permission;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 权限服务类
 * Create By HCL at 2017/10/12
 */
@Service
public class PermissionService extends BaseService<Permission> {

  /**
   * 插入数据，数据来自 map
   *
   * @param entity 实体数据
   * @return 返回操作信息
   */
  public ServerResult insertEntityMap(Map<String, Object> entity) {
    if (null != entity) {

    }
    return fail(Message.INVALID_REQUEST);
  }

}