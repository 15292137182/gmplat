package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Permission;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

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
   * 更新数据
   *
   * @param params 参数
   * @return 返回操作结果
   */
  public ServerResult updateOrgMap(Map<String, Object> params) {
    if (null != params) {
      Permission permission = new Permission().fromMap(params);
      if (isValid(permission.getRowId())) {
        permission.buildModifyInfo().updateById();
        return success(Message.UPDATE_SUCCESS);
      }
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

  /**
   * 根据 rowIds 删除数据
   *
   * @param rowIds rowId 集合
   * @return 返回
   */
  public ServerResult deleteByRowIds(String[] rowIds) {
    if (null != rowIds && rowIds.length > 0) {
      List<String> rowIdList = Arrays.asList(rowIds);
      Condition condition = new ConditionBuilder(Permission.class)
              .and().in("rowId", rowIdList).endAnd().buildDone();
      delete(condition);
      return success(String.format("成功删除了 %d 数据！", rowIdList.size()));
    }
    return fail(Message.INVALID_REQUEST);
  }

}