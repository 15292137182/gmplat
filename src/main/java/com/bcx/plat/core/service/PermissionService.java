package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Permission;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
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
  public ServerResult insertPermissionMap(Map<String, Object> entity) {
    if (null != entity) {
      Permission permission = new Permission().fromMap(entity);
      // 权限编号不能重复
      if (isExistPermissionId(permission.getPermissionId())) {
        return fail(String.format("权限编号: %s 已经存在！", permission.getPermissionId()));
      }
      permission.buildCreateInfo();
      insert(permission);
      return success(Message.NEW_ADD_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * @param permissionId 权限编号
   * @return 当前权限编号是否已经存子啊
   */
  private boolean isExistPermissionId(String permissionId) {
    Condition condition = new ConditionBuilder(Permission.class)
            .and().equal("permissionId", permissionId).endAnd()
            .buildDone();
    return !select(condition).isEmpty();
  }

}