package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.Role;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.entity.UserGroup;
import com.bcx.plat.core.entity.UserGroupRelateRole;
import com.bcx.plat.core.entity.UserRelateRole;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.RoleService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.constants.Message.DATA_CANNOT_BE_EMPTY;
import static com.bcx.plat.core.constants.Message.QUERY_FAIL;
import static com.bcx.plat.core.constants.Message.QUERY_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: UserRoleDistributeController</p>
 * <p>Description: 用户和角色分配页面</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/19  Wen TieHu Create </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/RoleDistribute")
public class UserRoleDistributeController extends BaseController {


  @Resource
  private RoleService roleService;

  /**
   * 用户分配角色关联信息
   *
   * @param userRwoId 用户rowId
   * @param roleRowId 角色rowId
   * @return PlatResult
   */
  @PostMapping("/addUserRole")
  public PlatResult addUserRole(String userRwoId, String[] roleRowId) {
    PlatResult platResult;
    if (isValid(userRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.addUserRole(userRwoId, roleRowId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 删除用户关联角色关联信息
   *
   * @param userRwoId 用户rowId
   * @param roleRowId 角色rowId
   * @return PlatResult
   */
  @PostMapping("/deleteUserRole")
  public PlatResult deleteUserRole(String userRwoId, String[] roleRowId) {
    PlatResult platResult;
    if (isValid(userRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.deleteUserRole(userRwoId, roleRowId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 查询所有用户信息获取关联角色的信息
   *
   * @return 查询角色信息
   */
  @GetMapping("/queryUserRole")
  public PlatResult queryUserRole() {
    PlatResult platResult;
    List<User> users = new User().selectAll();
    if (isValid(users)) {
      List<String> userRowId = users.stream().map(User::getRowId).collect(Collectors.toList());
      if (isValid(userRowId)) {
        Condition userRelateRole = new ConditionBuilder(UserRelateRole.class).and().in("userRowId", userRowId).endAnd().buildDone();
        List<UserRelateRole> userRelateRoles = new UserRelateRole().selectSimple(userRelateRole);
        if (isValid(userRelateRoles)) {
          List<String> roleRowId = userRelateRoles.stream().map(UserRelateRole::getRoleRowId).collect(Collectors.toList());//获取角色rowId
          Condition condition = new ConditionBuilder(Role.class).and().in("rowId", roleRowId).endAnd().buildDone();
          List<Role> select = roleService.select(condition);
          if (isValid(select)) {
            platResult = successData(QUERY_SUCCESS, select);
          } else {
            platResult = fail(QUERY_FAIL);
          }
        } else {
          platResult = fail(QUERY_FAIL);
        }
      } else {
        platResult = fail(QUERY_FAIL);
      }
    } else {
      platResult = fail(QUERY_FAIL);
    }
    return platResult;
  }


  /**
   * 查询角色及其用户
   *
   * @return PlatResult
   */
  @GetMapping("/queryRoleUserInfo")
  public PlatResult queryRoleUserInfo() {
    PlatResult platResult ;
    //查询全部角色信息
    List<Role> roles = new Role().selectAll();
    if (isValid(roles)) {
      //获取角色信息的rowId
      List<String> collect = roles.stream().map(Role::getRowId).collect(Collectors.toList());
      //通过获取角色的rowId来查询关联表中信息
      Condition roleRowId = new ConditionBuilder(UserRelateRole.class).and().in("roleRowId", collect).endAnd().buildDone();
      List<UserRelateRole> userRelateRoles = new UserRelateRole().selectSimple(roleRowId);
      if (isValid(userRelateRoles)) {
        List<String> userRowId = userRelateRoles.stream().map(UserRelateRole::getUserRowId).collect(Collectors.toList());
        Condition condition = new ConditionBuilder(User.class).and().in("rowId", userRowId).endAnd().buildDone();
        List list = new User().selectList(condition, null, true);
        platResult =successData(QUERY_SUCCESS,list);
      } else {
        platResult = fail(QUERY_FAIL);
      }
    } else {
      platResult = fail(QUERY_FAIL);
    }
    return platResult;
  }


  /**
   * 查询所有用户组信息获取关联角色的信息
   *
   * @return 查询角色信息
   */
  @GetMapping("/queryUserGroupRole")
  public PlatResult queryUserGroupRole() {
    PlatResult platResult;
    List<UserGroup> usersGroup = new UserGroup().selectAll();
    if (isValid(usersGroup)) {
      List<String> usersGroupRowId = usersGroup.stream().map(UserGroup::getRowId).collect(Collectors.toList());
      if (isValid(usersGroupRowId)) {
        Condition userRelateRole = new ConditionBuilder(UserGroupRelateRole.class).and().in("userGroupRowId", usersGroupRowId).endAnd().buildDone();
        List<UserGroupRelateRole> userGroupRelateRoles = new UserGroupRelateRole().selectSimple(userRelateRole);
        if (isValid(userGroupRelateRoles)) {
          List<String> roleRowId = userGroupRelateRoles.stream().map(UserGroupRelateRole::getRoleRowId).collect(Collectors.toList());//获取角色rowId
          Condition condition = new ConditionBuilder(Role.class).and().in("rowId", roleRowId).endAnd().buildDone();
          List<Role> select = roleService.select(condition);
          if (isValid(select)) {
            platResult = successData(QUERY_SUCCESS, select);
          } else {
            platResult = fail(QUERY_FAIL);
          }
        } else {
          platResult = fail(QUERY_FAIL);
        }
      } else {
        platResult = fail(QUERY_FAIL);
      }
    } else {
      platResult = fail(QUERY_FAIL);
    }
    return platResult;
  }


  /**
   * 用户组分配角色关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowIds     角色rowId
   * @return PlatResult
   */
  @PostMapping("/addUserGroupRole")
  public PlatResult addUserGroupRole(String userGroupRwoId, String[] roleRowIds) {
    PlatResult platResult;
    if (isValid(userGroupRwoId) && isValid(roleRowIds)) {
      ServerResult serverResult = roleService.addUserGroupRole(userGroupRwoId, roleRowIds);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 用户分配角色关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return PlatResult
   */
  @PostMapping("/deleteUserGroupRole")
  public PlatResult deleteUserGroupRole(String userGroupRwoId, String[] roleRowId) {
    PlatResult platResult;
    if (isValid(userGroupRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.deleteUserGroupRole(userGroupRwoId, roleRowId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 用户分配角色关联信息
   *
   * @param userRwoId 用户rowId
   * @param roleRowId 角色rowId
   * @return PlatResult
   */
  @PostMapping("/addRoleUser")
  public PlatResult addRoleUser(String roleRowId, String[] userRwoId) {
    PlatResult platResult;
    if (isValid(userRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.addRoleUser(roleRowId, userRwoId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 用户分配角色关联信息
   *
   * @param userRwoId 用户rowId
   * @param roleRowId 角色rowId
   * @return PlatResult
   */
  @PostMapping("/deleteRoleUser")
  public PlatResult deleteRoleUser(String roleRowId, String[] userRwoId) {
    PlatResult platResult;
    if (isValid(userRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.deleteRoleUser(roleRowId, userRwoId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 用户组分配角色关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return PlatResult
   */
  @PostMapping("/addRoleUserGroup")
  public PlatResult addRoleUserGroup(String roleRowId, String[] userGroupRwoId) {
    PlatResult platResult;
    if (isValid(userGroupRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.addRoleUserGroup(roleRowId, userGroupRwoId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 用户分配角色关联信息
   *
   * @param userGroupRwoId 用户rowId
   * @param roleRowId      角色rowId
   * @return PlatResult
   */
  @PostMapping("/deleteRoleUserGroup")
  public PlatResult deleteRoleUserGroup(String roleRowId, String[] userGroupRwoId) {
    PlatResult platResult;
    if (isValid(userGroupRwoId) && isValid(roleRowId)) {
      ServerResult serverResult = roleService.deleteRoleUserGroup(roleRowId, userGroupRwoId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


}
