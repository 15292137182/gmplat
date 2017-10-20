package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.*;

/**
 * 角色业务层
 *
 * @author YoungerOu
 * <p>
 * Created on 2017/10/13.
 */
@Service
public class RoleService extends BaseService<Role> {


  private UserService userService;
  private BaseOrgService baseOrgService;

  @Autowired
  public RoleService(UserService userService, BaseOrgService baseOrgService) {
    this.userService = userService;
    this.baseOrgService = baseOrgService;
  }

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
    if (isValid(rowId)) {
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
    LinkedList<Order> orders = UtilsTool.dataSort(Role.class, order);
    Condition condition;
    if (isValid(param)) {//判断是否根据指定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Role.class, UtilsTool.jsonToObj(param, Map.class));
    } else {
      condition = !isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> roles;
    if (isValid(pageNum)) {//判断是否分页查询
      roles = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      roles = new PageResult(selectMap(condition, orders));
    }
    if (isValid(null == roles ? null : roles.getResult())) {
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
      }
    }
    return fail(Message.QUERY_FAIL);
  }

  /**
   * 根据 rowId 查询角色下的用户
   *
   * @param rowId    角色的 rowId
   * @param orders   排序
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @return 返回分页查询结果
   */
  public PageResult<Map<String, Object>> queryRoleUserByRowId(String rowId, String search, String param, List<Order> orders, Integer pageNum, Integer pageSize) {
    Condition userCondition = null;
    List<UserRelateRole> relateRoles;
    if (isValid(rowId)) {
      Condition condition = new ConditionBuilder(UserRelateRole.class)
              .and().equal("roleRowId", rowId).endAnd().buildDone();
      relateRoles = new UserRelateRole().selectSimple(condition);

    } else {//如果没有角色rowId，就查询所有有角色的用户
      relateRoles = new UserRelateRole().selectAll();
    }
    if (!relateRoles.isEmpty()) {
      List<String> userRowIds = relateRoles.stream()
              .map(UserRelateRole::getUserRowId)
              .collect(Collectors.toList());
      userCondition = new ConditionBuilder(User.class)
              .and().in("rowId", userRowIds).endAnd().buildDone();
    }
    if (isValid(search)) {
      if (null != userCondition) {
        userCondition = new And(userCondition, UtilsTool.createBlankQuery(userService.blankSelectFields(), UtilsTool.collectToSet(search)));
      } else {
        userCondition = UtilsTool.createBlankQuery(userService.blankSelectFields(), UtilsTool.collectToSet(search));
      }
    }
    if (isValid(param)) {
      if (null != userCondition) {
        userCondition = new And(userCondition, UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, UtilsTool.jsonToObj(param, Map.class)));
      } else {
        userCondition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, UtilsTool.jsonToObj(param, Map.class));
      }
    }
    userCondition = UtilsTool.addNotDeleteCondition(userCondition, User.class);
    Collection<Field> fields = new LinkedList<>(moreBatis.getColumns(User.class));
    fields.add(moreBatis.getColumnByAlias(BaseOrg.class, "orgName"));
    if (isValid(pageNum)) {
      return leftAssociationQueryPage(User.class, BaseOrg.class, "belongOrg", "rowId", fields, userCondition, pageNum, pageSize, orders);
    }
    return new PageResult<>(leftAssociationQuery(User.class, BaseOrg.class, "belongOrg", "rowId", fields, userCondition, orders));
  }

  /**
   * 根据角色编号进行查询
   *
   * @param roleId   角色编号
   * @param orders   排序
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @return 返回查询结果
   */
  public PageResult<Map<String, Object>> queryRoleUserByRoleId(String roleId, String search, String param, List<Order> orders, int pageNum, int pageSize) {
    if (isValid(roleId)) {
      Condition condition = new ConditionBuilder(Role.class)
              .and().equal("roleId", roleId).endAnd().buildDone();
      List<Role> roles = select(condition);
      if (!roles.isEmpty()) {
        return queryRoleUserByRowId(roles.get(0).getRowId(), search, param, orders, pageNum, pageSize);
      }
    }
    return null;
  }

  /**
   * 删除角色下的用户信息
   *
   * @param roleRowId  角色rowId
   * @param userRowIds 用户rowId 集合
   * @return 返回结果
   */
  public ServerResult deleteUserInRole(String roleRowId, String[] userRowIds) {
    if (isValid(roleRowId) && null != userRowIds && userRowIds.length != 0) {
      Condition condition = new ConditionBuilder(UserRelateRole.class)
              .and().equal("roleRowId", roleRowId).in("userRowId", Arrays.asList(userRowIds)).endAnd()
              .buildDone();
      new UserRelateRole().delete(condition);
      return success(Message.DELETE_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 添加权限到角色
   *
   * @param roleRowId        角色主键
   * @param permissionRowIds 权限主键集合
   * @return 返回操作结果信息
   */
  public ServerResult addRolePermission(String roleRowId, String[] permissionRowIds) {
    if (null != roleRowId && null != permissionRowIds && permissionRowIds.length != 0) {
      // 选择当前已被添加的权限
      Condition condition = new ConditionBuilder(RoleRelatePermission.class)
              .and().equal("roleRowId", roleRowId).in("permissionRowId", Arrays.asList(permissionRowIds)).endAnd()
              .buildDone();
      List<RoleRelatePermission> roleRelatePermissions =
              new RoleRelatePermission().selectSimple(condition);
      Set<String> strings = new HashSet<>();
      strings.addAll(Arrays.asList(permissionRowIds));
      if (!roleRelatePermissions.isEmpty()) {
        roleRelatePermissions.forEach(roleRelatePermission -> strings.remove(roleRelatePermission.getPermissionRowId()));
      }
      if (!strings.isEmpty()) {
        strings.forEach(s -> {
          RoleRelatePermission roleRelatePermission = new RoleRelatePermission();
          roleRelatePermission.setRoleRowId(roleRowId);
          roleRelatePermission.setPermissionRowId(s);
          roleRelatePermission.buildCreateInfo().insert();
        });
      }
      return success(Message.NEW_ADD_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 添加组织机构到角色
   *
   * @param roleRowId 角色主键
   * @param orgRowIds
   * @return
   */
  public ServerResult addRoleOrg(String roleRowId, String[] orgRowIds) {
    if (null != roleRowId && null != orgRowIds && orgRowIds.length != 0) {
      // 选择当前已被添加的权限
      Condition condition = new ConditionBuilder(BaseOrgRelateRole.class)
              .and().equal("roleRowId", roleRowId).in("baseOrgRowId", Arrays.asList(orgRowIds)).endAnd()
              .buildDone();
      List<BaseOrgRelateRole> baseOrgRelateRoles =
              new BaseOrgRelateRole().selectSimple(condition);
      Set<String> strings = new HashSet<>();
      strings.addAll(Arrays.asList(orgRowIds));
      if (!baseOrgRelateRoles.isEmpty()) {
        baseOrgRelateRoles.forEach(baseOrgRelateRole -> strings.remove(baseOrgRelateRole.getBaseOrgRowId()));
      }
      if (!strings.isEmpty()) {
        strings.forEach(s -> {
          BaseOrgRelateRole baseOrgRelateRole = new BaseOrgRelateRole();
          baseOrgRelateRole.setRoleRowId(roleRowId);
          baseOrgRelateRole.setBaseOrgRowId(s);
          baseOrgRelateRole.buildCreateInfo().insert();
        });
      }
      return success(Message.NEW_ADD_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 删除角色下的权限信息
   *
   * @param roleRowId        角色主键
   * @param permissionRowIds 权限主键合集
   * @return 返回
   */
  public ServerResult deleteRolePermission(String roleRowId, String[] permissionRowIds) {
    if (null != roleRowId) {
      Condition condition;
      if (null != permissionRowIds && permissionRowIds.length != 0) {
        condition = new ConditionBuilder(RoleRelatePermission.class)
                .and().equal("roleRowId", roleRowId).in("permissionRowId", Arrays.asList(permissionRowIds)).endAnd()
                .buildDone();
      } else {
        condition = new ConditionBuilder(RoleRelatePermission.class)
                .and().equal("roleRowId", roleRowId).endAnd()
                .buildDone();
      }
      new RoleRelatePermission().delete(condition);
      return success(Message.DELETE_SUCCESS);
    }
    return fail(Message.INVALID_REQUEST);
  }


  /**
   * 用户分配角色关联信息
   *
   * @param userRwoId 用户rowId
   * @param roleRowId 角色rowId
   * @return ServerResult
   */
  public ServerResult addUserRole(String userRwoId, String[] roleRowId) {
    ServerResult serverResult;
    Map<String, Object> map = new HashMap<>();
    map.put("userRowId", userRwoId);
    int insert = -1;
    for (String role : roleRowId) {
      map.put("roleRowId", role);
      UserRelateRole userRelateRole = new UserRelateRole().fromMap(map);
      insert = userRelateRole.insert();
    }
    if (insert == -1) {
      serverResult = fail(NEW_ADD_FAIL);
    } else {
      serverResult = success(NEW_ADD_SUCCESS);
    }
    return serverResult;
  }

  /**
   * 用户删除角色关联信息
   *
   * @param userRowId 用户rowId
   * @param roleRowId 角色rowId
   * @return ServerResult
   */
  public ServerResult deleteUserRole(String userRowId, String[] roleRowId) {
    ServerResult serverResult;
    Condition condition = new ConditionBuilder(UserRelateRole.class)
            .and().equal(userRowId, userRowId).in("roleRowId", Arrays.asList(roleRowId))
            .endAnd().buildDone();
    int delete = new UserRelateRole().delete(condition);
    if (delete == -1) {
      serverResult = success(DELETE_SUCCESS);
    } else {
      serverResult = success(DELETE_FAIL);
    }
    return serverResult;
  }


  /**
   * 用户组分配角色关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return ServerResult
   */
  public ServerResult addUserGroupRole(String userGroupRwoId, String[] roleRowId) {
    Map<String, Object> map = new HashMap<>();
    map.put("userGroupRwoId", userGroupRwoId);
    ServerResult serverResult;
    int insert = -1;
    for (String role : roleRowId) {
      map.put("roleRowId", role);
      UserGroupRelateRole userGroupRelateRole = new UserGroupRelateRole().fromMap(map);
      insert = userGroupRelateRole.insert();
    }
    if (insert == -1) {
      serverResult = fail(NEW_ADD_FAIL);
    } else {
      serverResult = success(NEW_ADD_SUCCESS);
    }
    return serverResult;
  }


  /**
   * 用户删除角色关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return ServerResult
   */
  public ServerResult deleteUserGroupRole(String userGroupRwoId, String[] roleRowId) {
    Condition condition = new ConditionBuilder(UserGroupRelateRole.class)
            .and().equal("userGroupRwoId", userGroupRwoId).in("roleRowId", Arrays.asList(roleRowId))
            .endAnd()
            .buildDone();
    ServerResult serverResult;
    int delete = new UserGroupRelateRole().delete(condition);
    if (delete == -1) {
      serverResult = success(DELETE_SUCCESS);
    } else {
      serverResult = success(DELETE_FAIL);
    }
    return serverResult;
  }


  /**
   * 角色分配用户关联信息
   *
   * @param userRwoId 用户rowId
   * @param roleRowId 角色rowId
   * @return ServerResult
   */
  public ServerResult addRoleUser(String roleRowId, String[] userRwoId) {
    ServerResult serverResult;
    Map<String, Object> map = new HashMap<>();
    map.put("roleRowId", roleRowId);
    int insert = -1;
    for (String user : userRwoId) {
      map.put("userRwoId", user);
      UserRelateRole userRelateRole = new UserRelateRole().fromMap(map);
      insert = userRelateRole.insert();
    }
    if (insert == -1) {
      serverResult = fail(NEW_ADD_FAIL);
    } else {
      serverResult = success(NEW_ADD_SUCCESS);
    }
    return serverResult;
  }

  /**
   * 角色删除用户关联信息
   *
   * @param userRowId 用户rowId
   * @param roleRowId 角色rowId
   * @return ServerResult
   */
  public ServerResult deleteRoleUser(String roleRowId, String[] userRowId) {
    if (isValid(roleRowId)) {
      Condition condition;
      if (null != userRowId && userRowId.length != 0) {
        condition = new ConditionBuilder(UserRelateRole.class)
                .and().equal("roleRowId", roleRowId).in("userRowId", Arrays.asList(userRowId))
                .endAnd().buildDone();
      } else {
        condition = new ConditionBuilder(UserRelateRole.class)
                .and().equal("roleRowId", roleRowId).endAnd().buildDone();
      }
      int delete = new UserRelateRole().delete(condition);
      if (delete != -1) {
        return success(DELETE_SUCCESS);
      }
    }
    return fail(DELETE_FAIL);
  }


  /**
   * 角色分配用户组关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return ServerResult
   */
  public ServerResult addRoleUserGroup(String roleRowId, String[] userGroupRwoId) {
    ServerResult serverResult;
    Map<String, Object> map = new HashMap<>();
    map.put("roleRowId", roleRowId);
    int insert = -1;
    for (String userGroup : userGroupRwoId) {
      map.put("userGroupRwoId", userGroup);
      UserGroupRelateRole userGroupRelateRole = new UserGroupRelateRole().fromMap(map);
      insert = userGroupRelateRole.insert();
    }
    if (insert == -1) {
      serverResult = fail(NEW_ADD_FAIL);
    } else {
      serverResult = success(NEW_ADD_SUCCESS);
    }
    return serverResult;
  }


  /**
   * 角色删除用户组关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return ServerResult
   */
  public ServerResult deleteRoleUserGroup(String roleRowId, String[] userGroupRwoId) {
    if (isValid(roleRowId)) {
      Condition condition;
      if (null != userGroupRwoId && userGroupRwoId.length != 0) {
        condition = new ConditionBuilder(UserGroupRelateRole.class)
                .and().equal("roleRowId", roleRowId).in("userGroupRowId", Arrays.asList(userGroupRwoId))
                .endAnd().buildDone();
      } else {
        condition = new ConditionBuilder(UserGroupRelateRole.class)
                .and().equal("roleRowId", roleRowId).endAnd().buildDone();
      }
      int delete = new UserGroupRelateRole().delete(condition);
      if (delete != -1) {
        return success(DELETE_SUCCESS);
      }
    }
    return fail(DELETE_FAIL);
  }

  @Transactional
  public ServerResult deleteRole(String rowId) {
    //删除：逻辑删除；  与用户、用户组、组织机构、权限的关联关系同步逻辑删除
    if (isValid(rowId)) {
      deleteRoleUser(rowId, null);
      deleteRoleUserGroup(rowId, null);
      deleteRolePermission(rowId, null);
      int delete = new Role().deleteById(rowId);
      if (delete != -1) {
        return success(DELETE_SUCCESS);
      }
    }
    return fail(DELETE_FAIL);
  }

  /**
   * 查询包含权限的角色
   *
   * @param permissionRowId 权限主键
   * @return 返回操作结果
   */
  public ServerResult queryRoleContainsPermission(String permissionRowId, String search, String param, List<Order> orders, int pageNum, int pageSize) {
    if (isValid(permissionRowId)) {
      Condition relateCond = new ConditionBuilder(RoleRelatePermission.class)
              .and().equal("permissionRowId", permissionRowId).endAnd().buildDone();
      List<RoleRelatePermission> roleRelatePermissions = new RoleRelatePermission().selectSimple(relateCond);

      if (!roleRelatePermissions.isEmpty()) {
        List<String> roleRowIds = new ArrayList<>();
        roleRelatePermissions.forEach(roleRelatePermission -> roleRowIds.add(roleRelatePermission.getRoleRowId()));
        Condition roleCond = new ConditionBuilder(Role.class)
                .and().in("rowId", roleRowIds).endAnd().buildDone();
        List<Condition> conditions = new ArrayList<>();
        conditions.add(roleCond);

        if (isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
          Map<String, Object> map = jsonToObj(param, Map.class);
          if (null != map) {
            conditions.add(convertMapToAndConditionSeparatedByLike(Role.class, map));
          }
        }

        if (isValid(search)) { // 如果没有param参数，则进行空格查询
          conditions.add(createBlankQuery(new Role().toMap().keySet(), collectToSet(search)));
        }
        return successData(Message.QUERY_SUCCESS, selectPageMap(new And(conditions), orders, pageNum, pageSize));
      }
      return successData(Message.QUERY_SUCCESS, new ArrayList<>());
    }
    return fail(Message.INVALID_REQUEST);
  }

  public PageResult queryRoleOrgByRowId(String rowId, LinkedList<Order> orders, Integer pageNum, Integer pageSize) {
    Condition orgCondition = null;
    List<BaseOrgRelateRole> baseOrgRelateRoles;
    if (isValid(rowId)) {
      Condition condition = new ConditionBuilder(BaseOrgRelateRole.class)
              .and().equal("roleRowId", rowId).endAnd().buildDone();
      baseOrgRelateRoles = new BaseOrgRelateRole().selectSimple(condition);

    } else {//如果没有角色rowId，就查询所有有角色的部门
      baseOrgRelateRoles = new BaseOrgRelateRole().selectAll();
    }
    if (!baseOrgRelateRoles.isEmpty()) {
      List<String> baseOrgRowIds = baseOrgRelateRoles.stream()
              .map(BaseOrgRelateRole::getBaseOrgRowId)
              .collect(Collectors.toList());
      orgCondition = new ConditionBuilder(BaseOrg.class)
              .and().in("rowId", baseOrgRowIds).endAnd().buildDone();
    }
    if (isValid(pageNum)) {
      return baseOrgService.selectPageMap(orgCondition, orders, pageNum, pageSize);
    }
    return new PageResult<>(baseOrgService.selectMap(orgCondition, orders));
  }

  /**
   * 根据角色编号查询部门
   *
   * @param roleId   角色编号
   * @param orders   排序
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @return 返回查询结果
   */
  public PageResult queryRoleOrgByRoleId(String roleId, LinkedList<Order> orders, Integer pageNum, Integer pageSize) {
    if (isValid(roleId)) {
      Condition condition = new ConditionBuilder(Role.class)
              .and().equal("roleId", roleId).endAnd().buildDone();
      List<Role> roles = select(condition);
      if (!roles.isEmpty()) {
        return queryRoleOrgByRowId(roles.get(0).getRowId(), orders, pageNum, pageSize);
      }
    }
    return null;
  }
}
