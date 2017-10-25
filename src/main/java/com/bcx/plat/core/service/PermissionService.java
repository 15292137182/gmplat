package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Permission;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 权限服务类
 * Create By HCL at 2017/10/12
 */
@Service
public class PermissionService extends BaseService<Permission> {

  public static final String PERMISSION_TYPE_INTERFACE = "interfaceResources";
  public static final String PERMISSION_TYPE_BUTTON = "pageButton";
  public static final String PERMISSION_TYPE_PAGE = "pageResources";
  public static final String PERMISSION_TYPE_MENU = "menuResources";

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
      return success(String.format("成功删除了 %d 条数据！", delete(condition)));
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 根据权限类型分页查询权限
   *
   * @param permissionType 权限类型
   * @param search         空格查询
   * @param param          参数
   * @param orders         排序
   * @param pageNum        页码
   * @param pageSize       页面大小
   * @return 返回相应结果
   */
  public PageResult queryPermissionByType(String permissionType, String search, String param, List<Order> orders, int pageNum, int pageSize) {
    if (isValid(permissionType)) {
      List<Condition> list = new ArrayList<>();
      list.add(new FieldCondition("permissionType", Operator.EQUAL, permissionType));

      if (isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
        Map<String, Object> map = jsonToObj(param, Map.class);
        if (null != map) {
          list.add(convertMapToAndConditionSeparatedByLike(Permission.class, map));
        }
      }

      if (isValid(search)) { // 如果没有param参数，则进行空格查询
        list.add(createBlankQuery(new Permission().toMap().keySet(), collectToSet(search)));
      }

      return selectPageMap(new And(list), orders, pageNum, pageSize);
    }
    return null;
  }

}