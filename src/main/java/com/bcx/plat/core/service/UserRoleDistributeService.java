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
import java.util.LinkedList;
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

  public static final String NULL = "null";


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
    // 判断是否有param参数，如果有，根据指定字段查询
    if (UtilsTool.isValid(param)) {
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, map);
    } else {
      // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("id", "name"), UtilsTool.collectToSet(search));
    }
    //避免关联查询的时候,rowId被覆盖,将用户表主键的别名rowId改为rowIds
    Aliased aliased = new Aliased(moreBatis.getColumnByAlias(User.class, "rowId"), "rowIds");
    //关联查询字段去重,对用户关联角色表中的三个字段做移除操作
    List<Map<String, Class>> field = new LinkedList<>();
    Map<String, Class> fields = new HashMap<>(5);
    fields.put("userRowId", UserRelateRole.class);
    fields.put("roleRowId", UserRelateRole.class);
    fields.put("rowId", UserRelateRole.class);
    field.add(fields);

    if (condition != null && isValid(belongOrg)) {
      buildDone = new ConditionBuilder(User.class).and().addCondition(condition).endAnd().and().equal("belongOrg", belongOrg).endAnd().buildDone();
      pageResult =
          leftAssociationQueryPageAlias(User.class, UserRelateRole.class, "rowId", "userRowId",
              buildDone, pageNum, pageSize, orders, aliased, true, field);
    } else {
      pageResult =
          leftAssociationQueryPageAlias(User.class, UserRelateRole.class, "rowId", "userRowId",
              condition, pageNum, pageSize, orders, aliased, true, field);
    }

    List<Map> list = getNameAndIdOfTheUserRole();
    //获取角色名称
    Map roleName = list.get(0);
    //获取角色主键
    Map roleRowIds = list.get(1);
    if (null != pageResult && pageResult.getResult().size() > 0) {
      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        //获取返回结果所属部门的主键
        String orgId = String.valueOf(res.get("belongOrg"));
        if (!NULL.equals(orgId)) {
          //获取用户rowId
          String rowIds = String.valueOf(res.get("rowIds"));
          //查询角色信息
          if (roleName.keySet().contains(rowIds)) {
            //添加关联角色的名称
            res.put("roleName", roleName.get(rowIds));
            //添加关联角色主键
            res.put("roleRowIds", roleRowIds.get(rowIds));
          }
          Condition baseOrg = new ConditionBuilder(BaseOrg.class).and().equal("rowIds", orgId).endAnd().buildDone();
          //查询部门信息
          List<BaseOrg> baseOrgs = new BaseOrg().selectSimple(baseOrg);
          if (null != baseOrgs && baseOrgs.size() > 0) {
            for (BaseOrg org : baseOrgs) {
              //添加关联部门信息
              res.put("orgName", org.getOrgName());
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
    if (isValid(param)) {
      // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      if (null != map && !map.values().contains("")) {
        condition = UtilsTool.convertMapToAndConditionSeparatedByLike(UserGroup.class, map);
      }
    } else {
      // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("groupNumber", "groupName", "belongSector"),
          UtilsTool.collectToSet(search));
    }
    //避免关联查询的时候,rowId被覆盖,将用户表中主键别名rowId改为rowIds
    Aliased aliased = new Aliased(moreBatis.getColumnByAlias(UserGroup.class, "rowId"), "rowIds");
    //关联查询字段去重,对用户组关联角色表中的三个字段做移除操作
    List<Map<String, Class>> field = new LinkedList<>();
    Map<String, Class> fields = new HashMap<>(10);
    fields.put("userGroupRowId", UserGroupRelateRole.class);
    fields.put("roleRowId", UserGroupRelateRole.class);
    fields.put("rowId", UserGroupRelateRole.class);
    field.add(fields);

    if (isValid(belongOrg)) {
      Condition buildDone = new ConditionBuilder(UserGroup.class)
          .and().addCondition(condition).endAnd().and().equal("belongSector", belongOrg).endAnd().buildDone();

      pageResult =
          leftAssociationQueryPageAlias
              (UserGroup.class, UserGroupRelateRole.class, "rowId",
                  "userGroupRowId", buildDone, pageNum, pageSize, orders, aliased, true, field);
    } else {
      pageResult =
          leftAssociationQueryPageAlias
              (UserGroup.class, UserGroupRelateRole.class, "rowId",
                  "userGroupRowId", condition, pageNum, pageSize, orders, aliased, true, field);
    }
    if (pageResult != null && pageResult.getResult().size() > 0) {

      List<Map> list = getNameAndIdOfTheUserGroupRole();
      //获取角色名称
      Map roleName = list.get(0);
      //获取角色主键
      Map roleRowIds = list.get(1);
      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        String belongSector = String.valueOf(res.get("belongSector"));
        if (!NULL.equals(belongSector)) {
          Condition belongOrgId = new ConditionBuilder(BaseOrg.class).and().equal("rowId", belongSector).endAnd().buildDone();
          List<BaseOrg> baseOrgs = new BaseOrg().selectSimple(belongOrgId);
          if (baseOrgs != null && baseOrgs.size() > 0) {
            for (BaseOrg org : baseOrgs) {
              res.put("orgName", org.getOrgName());
            }
          }
        }
        String rowIds = String.valueOf(res.get("rowIds"));
        //查询角色信息
        if (roleName.keySet().contains(rowIds)) {
          //添加关联角色的名称
          res.put("roleName", roleName.get(rowIds));
          //添加关联角色主键
          res.put("roleRowIds", roleRowIds.get(rowIds));
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
    if (UtilsTool.isValid(param)) {
      // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Role.class, map);
    } else {
      // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("roleId", "roleName", "roleType"), UtilsTool.collectToSet(search));
    }
    //避免关联查询的时候,rowId被覆盖,将角色表主键的别名rowId改为rowIds
    Aliased aliased = new Aliased(moreBatis.getColumnByAlias(Role.class, "rowId"), "rowIds");
    //关联查询字段去重,对用户关联角色表中的三个字段做移除操作

    List<Map<String, Class>> list = new LinkedList<>();
    Map<String, Class> fields = new HashMap<>(10);
    fields.put("userRowId", UserRelateRole.class);
    fields.put("roleRowId", UserRelateRole.class);
    fields.put("rowId", UserRelateRole.class);
    list.add(fields);

    PageResult pageResult =
        leftAssociationQueryPageAlias
            (Role.class, UserRelateRole.class, "rowId",
                "roleRowId", condition, pageNum, pageSize, orders, aliased, true, list);

    List<Map> userRoleNameAndId = getNameAndIdOfTheUserRole();
    Map userName = userRoleNameAndId.get(2);
    Map userId = userRoleNameAndId.get(3);
    if (null != pageResult && pageResult.getResult().size() > 0) {

      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        String userRowId = String.valueOf(res.get("rowIds"));
        if (!NULL.equals(userRowId)) {
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
    if (UtilsTool.isValid(param)) {
      // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Role.class, map);
    } else {
      // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("roleId", "roleName", "roleType"), UtilsTool.collectToSet(search));
    }
    Aliased aliased = new Aliased(moreBatis.getColumnByAlias(Role.class, "rowId"), "rowIds");

    List<Map<String, Class>> list = new LinkedList<>();
    Map<String, Class> fields = new HashMap<>(10);
    fields.put("userGroupRowId", UserGroupRelateRole.class);
    fields.put("roleRowId", UserGroupRelateRole.class);
    list.add(fields);


    PageResult pageResult =
        leftAssociationQueryPageAlias
            (Role.class, UserGroupRelateRole.class, "rowId",
                "roleRowId", condition, pageNum, pageSize, orders, aliased, true, list);

    List<Map> userGroupRoleNameAndId = getNameAndIdOfTheUserGroupRole();
    Map userGroupName = userGroupRoleNameAndId.get(2);
    Map userGroupId = userGroupRoleNameAndId.get(3);
    if (null != pageResult && pageResult.getResult().size() > 0) {
      List<Map<String, Object>> result = pageResult.getResult();
      for (Map<String, Object> res : result) {
        String roleRowId = String.valueOf(res.get("rowIds"));
        if (!NULL.equals(roleRowId)) {
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
    Map<String, Object> map = new HashMap<>(10);
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
    Map<String, Object> map = new HashMap<>(10);
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
    Map<String, Object> map = new HashMap<>(10);
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
    Map<String, Object> map = new HashMap<>(10);
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


  /**
   * 查询用户关联角色的角色名称和主键
   *
   * @rturn list
   * @Author wenTieHu
   * @Date 2017/10/24
   */
  @SuppressWarnings("unchecked")
  private List getNameAndIdOfTheUserRole() {

    List<Map<String, Object>> list = new LinkedList<>();

    //为角色的主键创建集合并赋初值
    Map<String, Object> getRoleRowId = new HashMap<>(10);
    //为角色的名称创建集合并赋初值
    Map<String, Object> getRoleName = new HashMap<>(10);
    //为用户的主键创建集合并赋初值
    Map<String, Object> getUserRowId = new HashMap<>(10);
    //为用户的名称创建集合并赋初值
    Map<String, Object> getUserName = new HashMap<>(10);

    //查询所有的用户关联角色的信息
    List<UserRelateRole> userRelateRoles = new UserRelateRole().selectAll();

    for (UserRelateRole userRelateRole : userRelateRoles) {

      String roleName = "";
      String roleRowId = "";

      //通过获取的角色RoleRowId来构建条件
      Condition roleId = new ConditionBuilder(Role.class).and().equal("rowId", userRelateRole.getRoleRowId()).endAnd().buildDone();
      //查询对应的角色信息
      List<Role> roles = new Role().selectSimple(roleId);
      if (roles != null && roles.size() > 0) {
        Role role = roles.get(0);

        //获取角色的名称
        roleName = role.getRoleName();
        //获取角色的主键
        roleRowId = role.getRowId();
      }
      //获取用户关联角色表的用户主键
      String userRowId = userRelateRole.getUserRowId();
      //获取到用户关联的角色信息
      userRoleErgodic(getRoleRowId, getRoleName, roleName, roleRowId, userRowId);


      String name = "";
      String userRowIds = "";
      //通过获取的角色RoleRowId来构建条件
      Condition userRow = new ConditionBuilder(User.class).and().equal("rowId", userRelateRole.getUserRowId()).endAnd().buildDone();
      //查询对应的角色信息
      List<User> users = new User().selectSimple(userRow);
      if (users != null && users.size() > 0) {
        User user = users.get(0);
        //获取用户的名称
        name = user.getName();
        //获取用户的主键
        userRowIds = user.getRowId();
      }
      //获取用户关联角色表的角色的主键
      String userRelateRoleRowId = userRelateRole.getRoleRowId();
      //获取到角色关联的用户信息
      userRoleErgodic(getUserRowId, getUserName, name, userRowIds, userRelateRoleRowId);
    }
    //添加角色名称 索引位置 0
    list.add(0, getRoleName);
    //添加角色的主键 索引位置 1
    list.add(1, getRoleRowId);
    //添加用户名称 索引位置 2
    list.add(2, getUserName);
    //添加用户的主键 索引位置 3
    list.add(3, getUserRowId);
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
  private List getNameAndIdOfTheUserGroupRole() {
    List list = new ArrayList();
    //查询所有的用户关联角色的信息
    List<UserGroupRelateRole> userGroupRelateRoles = new UserGroupRelateRole().selectAll();
    Map<String, Object> getRoleName = new HashMap<>(10);
    Map<String, Object> getRoleRowId = new HashMap<>(10);
    Map<String, Object> getUserGroupName = new HashMap<>(10);
    Map<String, Object> getUserGroupRowId = new HashMap<>(10);
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
        //获取角色的名称
        roleName = role.getRoleName();
        //获取角色的主键
        rowId = role.getRowId();
      }
      //获取用户组的主键
      String userGroupRowId = userGroupRelateRole.getUserGroupRowId();
      //获取用户组关联的角色信息
      userRoleErgodic(getRoleRowId, getRoleName, roleName, rowId, userGroupRowId);

      String userGroupName = "";
      String userGroupId = "";
      //通过获取的角色RoleRowId来构建条件
      Condition condition = new ConditionBuilder(UserGroup.class).and().equal("rowId", userGroupRelateRole.getUserGroupRowId()).endAnd().buildDone();
      //查询对应的角色信息
      List<UserGroup> userGroups = new UserGroup().selectSimple(condition);
      if (userGroups != null && userGroups.size() > 0) {
        UserGroup userGroup = userGroups.get(0);
        //获取用户组的名称
        userGroupName = userGroup.getGroupName();
        //获取用户组的主键
        userGroupId = userGroup.getRowId();
      }
      //获取用户组关联的角色表中的角色主键
      String userRelateUserGroupRoleRowId = userGroupRelateRole.getRoleRowId();
      //获取角色关联的用户组的信息
      userRoleErgodic(getUserGroupRowId, getUserGroupName, userGroupName, userGroupId, userRelateUserGroupRoleRowId);

    }
    //添加角色名称 索引位置 0
    list.add(0, getRoleName);
    //添加角色主键 索引位置 1
    list.add(1, getRoleRowId);
    //添加用户组名称 索引位置 2
    list.add(2, getUserGroupName);
    //添加用户组主键 索引位置 3
    list.add(3, getUserGroupRowId);
    return list;
  }


  /**
   * @param getRowId  获取主键信息
   * @param getName   获取名称
   * @param userName  需要拼接的名称
   * @param userRowId 需要拼接的主键
   * @param rowId     用户key
   * @Author wenTieu
   * @Date 2017/10/28
   */
  private void userRoleErgodic(Map<String, Object> getRowId, Map<String, Object> getName, String userName, String userRowId, String rowId) {
    if (!(getName.get(rowId) == null && getName.keySet().contains(rowId))) {
      String names = String.valueOf(getName.get(rowId));
      String rowIds = String.valueOf(getRowId.get(rowId));
      if (!(NULL.equals(names) || NULL.equals(rowIds))) {
        //将关联的角色的名称拼接
        StringBuilder name = new StringBuilder().append(names).append(",").append(userName);
        //将关联的角色的主键拼接
        StringBuilder roleIds = new StringBuilder().append(rowIds).append(",").append(userRowId);
        getName.put(rowId, name);
        getRowId.put(rowId, roleIds);
      } else {
        getName.put(rowId, userName);
        getRowId.put(rowId, userRowId);
      }
    } else {
      getName.put(rowId, userName);
      getRowId.put(rowId, userRowId);
    }
  }

}
