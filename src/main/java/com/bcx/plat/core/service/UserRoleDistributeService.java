package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.entity.Role;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.entity.UserGroup;
import com.bcx.plat.core.entity.UserGroupRelateRole;
import com.bcx.plat.core.entity.UserRelateRole;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Aliased;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/23  WenTieHu Create </pre>
 */
@Service
public class UserRoleDistributeService extends BaseService<User> {


  /**
   * 查询用户关联角色信息
   *
   * @param search    模糊搜索条件
   * @param param     精确查询
   * @param belongOrg 查询部门
   * @param pageNum   一页显示条数
   * @param pageSize  页码
   * @param orders    排序
   * @return ServerResult
   * @Author wenTieHu
   * @Date 2017/10/23
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryUserRole(String search, String param, String belongOrg, Integer pageNum, Integer pageSize, List<Order> orders) {
    ServerResult serverResult;
    Condition condition;
    Condition buildDone;
    PageResult pageResult;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("id", "name"), UtilsTool.collectToSet(search));
    }
    if (condition != null && isValid(belongOrg)) {
      buildDone = new ConditionBuilder(User.class).and().addCondition(condition).endAnd().and().equal("belongOrg", belongOrg).endAnd().buildDone();
      pageResult = leftAssociationQueryPage(User.class, UserRelateRole.class, "rowId", "userRowId", buildDone, pageNum, pageSize, orders);
    } else {
      Aliased aliased = new Aliased(moreBatis.getColumnByAlias(User.class, "rowId"), "rowIds");
      pageResult = leftAssociationQueryPageAlias(User.class, UserRelateRole.class, "rowId", "userRowId", condition, pageNum, pageSize, orders, aliased);
    }

    List<Map> list = getUserRoleNameAndId();
    //获取角色名称
    Map roleName = list.get(0);
    //获取角色主键
    Map roleRowIds = list.get(1);
    if (null != pageResult && pageResult.getResult().size() > 0) {
      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        //获取返回结果关联角色的主键
        String roleRowId = String.valueOf(res.get("roleRowId"));
        //获取返回结果所属部门的主键
        String orgId = String.valueOf(res.get("belongOrg"));
        if (!"null".equals(orgId) && !"null".equals(roleRowId)) {
          //查询角色信息
          if (roleName.keySet().contains(String.valueOf(res.get("rowIds")))) {
            //添加关联角色的名称
            res.put("roleName", roleName.get(String.valueOf(res.get("rowIds"))));
            //添加关联角色主键
            res.put("roleRowIds", roleRowIds.get(String.valueOf(res.get("rowIds"))));
          }
          Condition BaseOrg = new ConditionBuilder(BaseOrg.class).and().equal("rowIds", orgId).endAnd().buildDone();
          //查询部门信息
          List<com.bcx.plat.core.entity.BaseOrg> baseOrgs = new BaseOrg().selectSimple(BaseOrg);
          if (null != baseOrgs && baseOrgs.size() > 0) {
            for (BaseOrg baseOrg : baseOrgs) {
              //添加关联部门信息
              res.put("orgName", baseOrg.getOrgName());
            }
          }
        }
      }
      serverResult = successData(QUERY_SUCCESS, pageResult);
    } else {
      serverResult = fail(QUERY_FAIL);
    }
    return serverResult;
  }

  /**
   * 查询用户关联角色的角色名称和主键
   *
   * @rturn list
   * @Author wenTieHu
   * @Date 2017/10/24
   */
  @SuppressWarnings("unchecked")
  private List getUserRoleNameAndId() {
    List list = new ArrayList();
    //查询所有的用户关联角色的信息
    List<UserRelateRole> userRelateRoles = new UserRelateRole().selectAll();
    Map<String, Object> mapGetRoleRowId = new HashMap<>();
    Map<String, Object> mapGetRoleName = new HashMap<>();
    Map<String, Object> mapGetUserRowId = new HashMap<>();
    Map<String, Object> mapGetUserName = new HashMap<>();
    for (UserRelateRole userRelateRole : userRelateRoles) {
      String roleName = "";
      String roleRowId = "";
      //通过获取的角色RoleRowId来构建条件
      Condition roleId = new ConditionBuilder(Role.class).and().equal("rowId", userRelateRole.getRoleRowId()).endAnd().buildDone();
      //查询对应的角色信息
      List<Role> roles = new Role().selectSimple(roleId);
      if (roles != null && roles.size() > 0) {
        Role role = roles.get(0);
        roleName = role.getRoleName();
        roleRowId = role.getRowId();
      }
      String userRowId = userRelateRole.getUserRowId();
      if (!(mapGetRoleName.get(userRowId) == null && mapGetRoleName.keySet().contains(userRowId))) {
        String roleNames = String.valueOf(mapGetRoleName.get(userRowId));
        String roleRow = String.valueOf(mapGetRoleRowId.get(userRowId));
        if (!"null".equals(roleNames) && !"null".equals(roleRow)) {
          //将关联的角色的名称拼接
          StringBuilder name = new StringBuilder().append(roleNames).append(",").append(roleName);
          //将关联的角色的主键拼接
          StringBuilder roleIds = new StringBuilder().append(roleRow).append(",").append(roleRowId);
          mapGetRoleName.put(userRowId, name);
          mapGetRoleRowId.put(userRowId, roleIds);
        } else {
          mapGetRoleName.put(userRowId, roleName);
          mapGetRoleRowId.put(userRowId, roleRowId);
        }
      } else {
        mapGetRoleName.put(userRowId, roleName);
        mapGetRoleRowId.put(userRowId, roleRowId);
      }

      String name = "";
      String userRowIds = "";
      //通过获取的角色RoleRowId来构建条件
      Condition userRow = new ConditionBuilder(User.class).and().equal("rowId", userRelateRole.getUserRowId()).endAnd().buildDone();
      //查询对应的角色信息
      List<User> users = new User().selectSimple(userRow);
      if (users != null && users.size() > 0) {
        User user = users.get(0);
        name = user.getName();
        userRowIds = user.getRowId();
      }
      String userRelateRoleRowId = userRelateRole.getRoleRowId();
      if (!(mapGetUserName.get(userRelateRoleRowId) == null && mapGetUserName.keySet().contains(userRelateRoleRowId))) {
        String userRelateUserName = String.valueOf(mapGetUserName.get(userRelateRoleRowId));
        String userRelateUserId = String.valueOf(mapGetUserRowId.get(userRelateRoleRowId));
        if (!"null".equals(userRelateUserName) && !"null".equals(userRelateUserId)) {
          //将关联的角色的名称拼接
          StringBuilder userName = new StringBuilder().append(userRelateUserName).append(",").append(name);
          //将关联的角色的主键拼接
          StringBuilder roleIds = new StringBuilder().append(userRelateUserId).append(",").append(userRowIds);
          mapGetUserName.put(userRelateRoleRowId, userName);
          mapGetUserRowId.put(userRelateRoleRowId, roleIds);
        } else {
          mapGetUserName.put(userRelateRoleRowId, name);
          mapGetUserRowId.put(userRelateRoleRowId, userRowIds);
        }
      } else {
        mapGetUserName.put(userRelateRoleRowId, name);
        mapGetUserRowId.put(userRelateRoleRowId, userRowIds);
      }
    }
    //添加角色名称 索引位置 0
    list.add(0, mapGetRoleName);
    //添加角色的主键 索引位置 1
    list.add(1, mapGetRoleRowId);
    //添加用户名称 索引位置 2
    list.add(2, mapGetUserName);
    //添加用户的主键 索引位置 3
    list.add(3, mapGetUserRowId);
    return list;
  }


  /**
   * 查询用户关联角色的角色名称和主键
   *
   * @rturn list
   * @Author wenTieHu
   * @Date 2017/10/24
   */
  @SuppressWarnings("unchecked")
  private List getUserGroupRoleNameAndId() {
    List list = new ArrayList();
    //查询所有的用户关联角色的信息
    List<UserGroupRelateRole> userGroupRelateRoles = new UserGroupRelateRole().selectAll();
    Map<String, Object> mapGetRoleName = new HashMap<>();
    Map<String, Object> mapGetRoleRowId = new HashMap<>();
    Map<String, Object> mapGetUserGroupName = new HashMap<>();
    Map<String, Object> mapGetUserGroupRowId = new HashMap<>();
    for (UserGroupRelateRole userGroupRelateRole : userGroupRelateRoles) {
      String roleName = "";
      String rowId = "";
      String roleRowId = userGroupRelateRole.getRoleRowId();
      //通过获取的角色RoleRowId来构建条件
      Condition roleId = new ConditionBuilder(Role.class).and().equal("rowId", roleRowId).endAnd().buildDone();
      //查询对应的角色信息
      List<Role> roles = new Role().selectSimple(roleId);
      if (roles != null && roles.size() > 0) {
        Role role = roles.get(0);
        roleName = role.getRoleName();
        rowId = role.getRowId();
      }
      String userGroupRowId = userGroupRelateRole.getUserGroupRowId();
      if (!(mapGetRoleName.get(userGroupRowId) == null && mapGetRoleName.keySet().contains(userGroupRowId))) {
        String roleNames = String.valueOf(mapGetRoleName.get(userGroupRowId));
        String roleRow = String.valueOf(mapGetRoleRowId.get(userGroupRowId));
        if (!"null".equals(roleNames) && !"null".equals(roleRow)) {
          //将关联的角色的名称拼接
          StringBuilder name = new StringBuilder().append(roleNames).append(",").append(roleName);
          //将关联的角色的主键拼接
          StringBuilder roleIds = new StringBuilder().append(roleRow).append(",").append(rowId);
          mapGetRoleName.put(userGroupRowId, name);
          mapGetRoleRowId.put(userGroupRowId, roleIds);
        } else {
          mapGetRoleName.put(userGroupRowId, roleName);
          mapGetRoleRowId.put(userGroupRowId, rowId);
        }
      } else {
        mapGetRoleName.put(userGroupRowId, roleName);
        mapGetRoleRowId.put(userGroupRowId, rowId);
      }

      String userGroupName = "";
      String userGroupId = "";
      //通过获取的角色RoleRowId来构建条件
      Condition condition = new ConditionBuilder(UserGroup.class).and().equal("rowId", userGroupRelateRole.getUserGroupRowId()).endAnd().buildDone();
      //查询对应的角色信息
      List<UserGroup> userGroups = new UserGroup().selectSimple(condition);
      if (userGroups != null && userGroups.size() > 0) {
        UserGroup userGroup = userGroups.get(0);
        userGroupName = userGroup.getGroupName();
        userGroupId = userGroup.getRowId();
      }
      String userRelateUserGroupRoleRowId = userGroupRelateRole.getRoleRowId();
      if (!(mapGetUserGroupName.get(userRelateUserGroupRoleRowId) == null && mapGetUserGroupName.keySet().contains(userRelateUserGroupRoleRowId))) {
        String userGroupNames = String.valueOf(mapGetUserGroupName.get(userRelateUserGroupRoleRowId));
        String userGrouupRow = String.valueOf(mapGetUserGroupRowId.get(userRelateUserGroupRoleRowId));
        if (!"null".equals(userGroupNames) && !"null".equals(userGrouupRow)) {
          //将关联的角色的名称拼接
          StringBuilder groupName = new StringBuilder().append(userGroupNames).append(",").append(userGroupName);
          //将关联的角色的主键拼接
          StringBuilder userGroupIds = new StringBuilder().append(userGrouupRow).append(",").append(userGroupId);
          mapGetUserGroupName.put(userRelateUserGroupRoleRowId, groupName);
          mapGetUserGroupRowId.put(userRelateUserGroupRoleRowId, userGroupIds);
        } else {
          mapGetUserGroupName.put(userRelateUserGroupRoleRowId, userGroupName);
          mapGetUserGroupRowId.put(userRelateUserGroupRoleRowId, userGroupId);
        }
      } else {
        mapGetUserGroupName.put(userRelateUserGroupRoleRowId, userGroupName);
        mapGetUserGroupRowId.put(userRelateUserGroupRoleRowId, userGroupId);
      }

    }
    //添加角色名称 索引位置 0
    list.add(0, mapGetRoleName);
    //添加角色主键 索引位置 1
    list.add(1, mapGetRoleRowId);
    //添加用户组名称 索引位置 2
    list.add(2, mapGetUserGroupName);
    //添加用户组主键 索引位置 3
    list.add(3, mapGetUserGroupRowId);
    return list;
  }


  /**
   * 查询用户组关联角色信息
   *
   * @param search    模糊搜索条件
   * @param param     精确查询
   * @param belongOrg 查询部门
   * @param pageNum   一页显示条数
   * @param pageSize  页码
   * @param orders    排序
   * @return ServerResult
   * @Author wenTieHu
   * @Date 2017/10/23
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryUserGroupRole(String search, String param, String belongOrg, Integer pageNum, Integer pageSize, List<Order> orders) {
    ServerResult serverResult;
    Condition condition = null;
    PageResult pageResult;
    if (isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      if (null != map && !map.values().contains("")) {
        condition = UtilsTool.convertMapToAndConditionSeparatedByLike(UserGroup.class, map);
      }
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("groupNumber", "groupName", "belongSector"), UtilsTool.collectToSet(search));
    }
    if (isValid(belongOrg)) {
      Condition buildDone = new ConditionBuilder(UserGroup.class).and().addCondition(condition).endAnd().and().equal("belongSector", belongOrg).endAnd().buildDone();

      pageResult = leftAssociationQueryPage(UserGroup.class, UserGroupRelateRole.class, "rowId", "userGroupRowId", buildDone, pageNum, pageSize, orders);
    } else {
      Aliased aliased = new Aliased(moreBatis.getColumnByAlias(UserGroup.class, "rowId"), "rowIds");

      pageResult = leftAssociationQueryPageAlias(UserGroup.class, UserGroupRelateRole.class, "rowId", "userGroupRowId", condition, pageNum, pageSize, orders, aliased);
    }
    if (pageResult != null && pageResult.getResult().size() > 0) {

      List<Map> list = getUserGroupRoleNameAndId();
      //获取角色名称
      Map roleName = list.get(0);
      //获取角色主键
      Map roleRowIds = list.get(1);
      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        String belongSector = String.valueOf(res.get("belongSector"));
        if (!"null".equals(belongSector)) {
          Condition belongOrgId = new ConditionBuilder(BaseOrg.class).and().equal("rowId", belongSector).endAnd().buildDone();
          List<BaseOrg> baseOrgs = new BaseOrg().selectSimple(belongOrgId);
          if (baseOrgs != null && baseOrgs.size() > 0) {
            for (BaseOrg org : baseOrgs) {
              res.put("orgName", org.getOrgName());
            }
          }
        }
        //查询角色信息
        if (roleName.keySet().contains(String.valueOf(res.get("rowIds")))) {
          //添加关联角色的名称
          res.put("roleName", roleName.get(String.valueOf(res.get("rowIds"))));
          //添加关联角色主键
          res.put("roleRowIds", roleRowIds.get(String.valueOf(res.get("rowIds"))));
        }
      }
      serverResult = successData(QUERY_SUCCESS, pageResult);
    } else {
      serverResult = fail(QUERY_FAIL);
    }
    return serverResult;
  }


  /**
   * 查询角色关联用户信息
   *
   * @param search   模糊搜索条件
   * @param param    精确查询
   * @param pageNum  一页显示条数
   * @param pageSize 页码
   * @param orders   排序
   * @return ServerResult
   * @Author wenTieHu
   * @Date 2017/10/23
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryRoleUserInfo(String search, String param, Integer pageNum, Integer pageSize, List<Order> orders) {
    ServerResult serverResult;
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Role.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("roleId", "roleName", "roleType"), UtilsTool.collectToSet(search));
    }
    Aliased aliased = new Aliased(moreBatis.getColumnByAlias(Role.class, "rowId"), "rowIds");
    PageResult pageResult = leftAssociationQueryPageAlias(Role.class, UserRelateRole.class, "rowId", "roleRowId", condition, pageNum, pageSize, orders, aliased);
    List<Map> userRoleNameAndId = getUserRoleNameAndId();
    Map userName = userRoleNameAndId.get(2);
    Map userId = userRoleNameAndId.get(3);
    if (null != pageResult && pageResult.getResult().size() > 0) {

      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        String userRowId = String.valueOf(res.get("roleRowId"));
        if (!"null".equals(userRowId)) {
          //查询用户信息
          if (userName.keySet().contains(userRowId)) {
            //添加关联用户的名称
            res.put("userNames", userName.get(userRowId));
            //添加关联用户主键
            res.put("userRowIds", userId.get(userRowId));
          }
        }
      }
      serverResult = successData(QUERY_SUCCESS, pageResult);
    } else {
      serverResult = fail(QUERY_FAIL);
    }
    return serverResult;
  }


  /**
   * 查询角色关联用户组信息
   *
   * @param search   模糊搜索条件
   * @param param    精确查询
   * @param pageNum  一页显示条数
   * @param pageSize 页码
   * @param orders   排序
   * @return ServerResult
   * @Author wenTieHu
   * @Date 2017/10/23
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryRoleUserGroup(String search, String param, Integer pageNum, Integer pageSize, List<Order> orders) {
    ServerResult serverResult;
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Role.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("roleId", "roleName", "roleType"), UtilsTool.collectToSet(search));
    }
    Aliased aliased = new Aliased(moreBatis.getColumnByAlias(Role.class, "rowId"), "rowIds");
    PageResult pageResult = leftAssociationQueryPageAlias(Role.class, UserGroupRelateRole.class, "rowId", "roleRowId", condition, pageNum, pageSize, orders, aliased);
    List<Map> userGroupRoleNameAndId = getUserGroupRoleNameAndId();
    Map userGroupName = userGroupRoleNameAndId.get(2);
    Map userGroupId = userGroupRoleNameAndId.get(3);
    if (null != pageResult && pageResult.getResult().size() > 0) {
      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        String roleRowId = String.valueOf(res.get("roleRowId"));
        if (!"null".equals(roleRowId)) {
          //查询用户信息
          if (userGroupName.keySet().contains(roleRowId)) {
            //添加关联用户的名称
            res.put("userGroupNames", userGroupName.get(roleRowId));
            //添加关联用户主键
            res.put("userGroupRowIds", userGroupId.get(roleRowId));
          }
        }
      }
      serverResult = successData(QUERY_SUCCESS, pageResult);
    } else {
      serverResult = fail(QUERY_FAIL);
    }
    return serverResult;
  }


  /**
   * 用户分配角色关联信息
   *
   * @param userRowId 用户rowId
   * @param roleRowId 角色rowId
   * @return ServerResult
   */
  public ServerResult addUserRole(String userRowId, List<String> roleRowId) {
    ServerResult serverResult;

    Condition rowId = new ConditionBuilder(UserRelateRole.class).and().equal("userRowId", userRowId).endAnd().buildDone();
    new UserRelateRole().delete(rowId);
    Map<String, Object> map = new HashMap<>();
    map.put("userRowId", userRowId);
    int insert = -1;
    if (roleRowId.size() > 0) {
      for (String role : roleRowId) {
        map.put("roleRowId", role);
        UserRelateRole userRelateRole = new UserRelateRole().buildCreateInfo().fromMap(map);
        insert = userRelateRole.insert();
      }
      if (insert == -1) {
        serverResult = fail(OPERATOR_FAIL);
      } else {
        serverResult = success(OPERATOR_SUCCESS);
      }
    } else {
      serverResult = success(OPERATOR_SUCCESS);
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
   * @param userGroupRowId 用户rowId
   * @param roleRowId      角色rowId
   * @return ServerResult
   */
  public ServerResult addUserGroupRole(String userGroupRowId, List<String> roleRowId) {
    Map<String, Object> map = new HashMap<>();
    ServerResult serverResult;
    Condition rowId = new ConditionBuilder(UserGroupRelateRole.class).and().equal("userGroupRowId", userGroupRowId).endAnd().buildDone();
    int delete = new UserGroupRelateRole().delete(rowId);
    if (delete == -1) {
      serverResult = fail(OPERATOR_FAIL);
    } else {
      serverResult = success(OPERATOR_SUCCESS);
    }
    map.put("userGroupRowId", userGroupRowId);
    int insert = -1;
    if (roleRowId.size() > 0) {
      for (String role : roleRowId) {
        map.put("roleRowId", role);
        UserGroupRelateRole userGroupRelateRole = new UserGroupRelateRole().buildCreateInfo().fromMap(map);
        insert = userGroupRelateRole.insert();
      }
      if (insert == -1) {
        serverResult = fail(OPERATOR_FAIL);
      } else {
        serverResult = success(OPERATOR_SUCCESS);
      }
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
      serverResult = fail(DELETE_FAIL);
    } else {
      serverResult = success(DELETE_SUCCESS);
    }
    return serverResult;
  }


  /**
   * 角色分配用户关联信息
   *
   * @param userRowIds 用户rowId
   * @param roleRowId  角色rowId
   * @return ServerResult
   */
  public ServerResult addRoleUser(String roleRowId, List<String> userRowIds) {
    ServerResult serverResult;
    Map<String, Object> map = new HashMap<>();
    map.put("roleRowId", roleRowId);
    Condition condition = new ConditionBuilder(UserRelateRole.class).and().equal("roleRowId", roleRowId).endAnd().buildDone();
    int delete = new UserRelateRole().delete(condition);
    if (delete == -1) {
      serverResult = fail(OPERATOR_FAIL);
    } else {
      serverResult = success(OPERATOR_SUCCESS);
    }
    int insert = -1;
    if (userRowIds.size() > 0) {
      for (String user : userRowIds) {
        map.put("userRowId", user);
        UserRelateRole userRelateRole = new UserRelateRole().buildCreateInfo().fromMap(map);
        insert = userRelateRole.insert();
      }
      if (insert == -1 && delete == -1) {
        serverResult = fail(OPERATOR_FAIL);
      } else {
        serverResult = success(OPERATOR_SUCCESS);
      }
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
   * @param userGroupRowIds 用户rowId
   * @param roleRowId       角色rowId
   * @return ServerResult
   */
  public ServerResult addRoleUserGroup(String roleRowId, List<String> userGroupRowIds) {
    ServerResult serverResult;
    Condition condition = new ConditionBuilder(UserGroupRelateRole.class).and().equal("roleRowId", roleRowId).endAnd().buildDone();
    int delete = new UserGroupRelateRole().delete(condition);
    if (delete == -1) {
      serverResult = fail(OPERATOR_FAIL);
    } else {
      serverResult = success(OPERATOR_SUCCESS);
    }
    Map<String, Object> map = new HashMap<>();
    map.put("roleRowId", roleRowId);
    int insert = -1;
    if (userGroupRowIds.size() > 0) {
      for (String userGroup : userGroupRowIds) {
        map.put("userGroupRowId", userGroup);
        UserGroupRelateRole userGroupRelateRole = new UserGroupRelateRole().buildCreateInfo().fromMap(map);
        insert = userGroupRelateRole.insert();
      }
      if (insert == -1 && delete == -1) {
        serverResult = fail(OPERATOR_FAIL);
      } else {
        serverResult = success(OPERATOR_SUCCESS);
      }
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


}
