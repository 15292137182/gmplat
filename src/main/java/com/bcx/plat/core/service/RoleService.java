package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Role;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 角色业务层
 *
 * @author YoungerOu
 * <p>
 * Created on 2017/10/13.
 */
@Service
public class RoleService extends BaseService<Role> {
  private List<String> blankSelectFields() {
    return Arrays.asList("roleId", "roleName", "roleType");
  }

  public ServerResult add(Map<String, Object> param) {
    Object roleId = param.get("roleId");
    Object roleName = param.get("roleName");
    Object roleType = param.get("roleType");
    if (UtilsTool.isValidAll(roleId, roleName, roleType)) {//验证非空
      //角色编号唯一
      Condition condition = new ConditionBuilder(Role.class).and().equal("roleId", roleId).endAnd().buildDone();
      List<Role> list = select(condition);
      if (list.isEmpty()) {
        param.put("roleId", roleId.toString().trim());
        param.put("roleName", roleName.toString().trim());
        param.put("roleType", roleType.toString().trim());
        Role role = new Role().buildCreateInfo().fromMap(param);
        if (role.insert() != -1) {
          return successData(Message.NEW_ADD_SUCCESS, role);
        } else {
          return fail(Message.NEW_ADD_FAIL);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      return fail(Message.DATA_CANNOT_BE_EMPTY);
    }
  }

  public ServerResult modify(Map<String, Object> param) {
    Object rowId = param.get("rowId");
    Object roleId = param.get("roleId");
    Object roleName = param.get("roleName");
    Object roleType = param.get("roleType");
    if (UtilsTool.isValid(rowId)) {
      if (UtilsTool.isValidAll(roleId, roleName, roleType)) {//验证非空
        //角色编号唯一
        Condition condition = new ConditionBuilder(Role.class).and().equal("roleId", roleId).endAnd().buildDone();
        List<Role> list = select(condition);
        if (list.isEmpty() || (list.size() == 1 && list.get(0).getRowId().equals(rowId))) {
          param.put("roleId", roleId.toString().trim());
          param.put("roleName", roleName.toString().trim());
          param.put("roleType", roleType.toString().trim());
          Role role = new Role().buildModifyInfo().fromMap(param);
          if (role.updateById() != -1) {
            return successData(Message.UPDATE_SUCCESS, role);
          } else {
            return fail(Message.UPDATE_FAIL);
          }
        } else {
          return fail(Message.DATA_CANNOT_BE_DUPLICATED);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_EMPTY);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 角色 - 分页查询
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param order    排序方式
   * @return ServerResult
   */
  public ServerResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    Condition condition;
    if (UtilsTool.isValid(param)) {//判断是否根据指定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Role.class, UtilsTool.jsonToObj(param, Map.class));
    } else {
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> roles;
    if (UtilsTool.isValid(pageNum)) {//判断是否分页查询
      roles = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      roles = new PageResult(selectMap(condition, orders));
    }
    if (UtilsTool.isValid(null == roles ? null : roles.getResult())) {
      return new ServerResult<>(roles);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 角色 - 根据指定字段精确查询
   *
   * @param param 指定的字段
   * @return ServerResult
   */
  public ServerResult queryBySpecify(Map<String, Object> param) {
    if (!param.isEmpty()) {
      Condition condition = UtilsTool.convertMapToAndCondition(Role.class, param);
      List<Map> select = selectMap(condition);
      if (!select.isEmpty()) {
        return successData(Message.QUERY_SUCCESS, select);
      } else {
        return fail(Message.QUERY_FAIL);
      }
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }
}
