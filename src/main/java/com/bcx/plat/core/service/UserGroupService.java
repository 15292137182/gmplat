package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.entity.UserGroup;
import com.bcx.plat.core.entity.UserRelateUserGroup;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Message.NEW_ADD_FAIL;
import static com.bcx.plat.core.constants.Message.NEW_ADD_SUCCESS;
import static com.bcx.plat.core.constants.Message.OPERATOR_FAIL;
import static com.bcx.plat.core.constants.Message.QUERY_FAIL;
import static com.bcx.plat.core.constants.Message.QUERY_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>History: 2017/10/11  Wen TieHu Create </pre>
 */
@Service
public class UserGroupService extends BaseService<UserGroup> {

  /**
   * 删除用户组下的用户信息
   *
   * @param groupRowId 用户组rowId
   * @param userRowIds 用户rowId 集合
   * @return 返回结果
   */
  public boolean deleteUserInGroup(String groupRowId, List<String> userRowIds) {
    if (isValid(groupRowId) && null != userRowIds && userRowIds.size() != 0) {
      Condition condition = new ConditionBuilder(UserRelateUserGroup.class)
          .and().equal("userGroupRowId", groupRowId).in("userRowId", userRowIds).endAnd()
          .buildDone();
      new UserRelateUserGroup().delete(condition);
      return true;
    }
    return false;
  }


  /**
   * 根据用户组查询用户信息
   *
   * @param userGroupRowId 用户组唯一标示
   * @param pageNum        页码
   * @param pageSize       一页显示条数
   * @param orders         排序 "{\"str\":\"rowId\", \"num\":1}"
   * @return 用户信息
   */
  public ServerResult queryUserGroupUser(String userGroupRowId, Integer pageNum, Integer pageSize, List<Order> orders) {
    ServerResult serverResult;
    Condition condition = new ConditionBuilder(UserRelateUserGroup.class)
        .and().equal("userGroupRowId", userGroupRowId).endAnd().buildDone();
    List<UserRelateUserGroup> userRelateUserGroups =
        new UserRelateUserGroup().selectSimple(condition);
    if (userRelateUserGroups != null && userRelateUserGroups.size() > 0) {
      List<String> row = userRelateUserGroups
          .stream()
          .map(UserRelateUserGroup::getUserRowId)
          .collect(Collectors.toList());
      Condition buildDone =
          new ConditionBuilder(User.class)
              .and().in("rowId", row).endAnd().buildDone();
      PageResult users =
          new User().selectPage(buildDone, pageNum, pageSize,orders);
      if (users != null && users.getResult().size() > 0) {
        serverResult = successData(QUERY_SUCCESS, users);
      } else {
        serverResult = fail(QUERY_FAIL);
      }
    } else {
      serverResult = fail(QUERY_FAIL);
    }
    return serverResult;
  }


  /**
   * 给用户组下添加用户或删除用户
   *
   * @param userGroupRowId 用户组rowId
   * @param userRowIds     用户rowId
   * @return 新增信息
   */
  public ServerResult addUserGroupUser(List<String> userRowIds, String userGroupRowId) {
    ServerResult serverResult = null;
    Map<String, Object> map = new HashMap<>();
    Condition condition = new ConditionBuilder(UserRelateUserGroup.class).and().equal("userGroupRowId",userGroupRowId).endAnd().buildDone();
    int delete = new UserRelateUserGroup().delete(condition);
    if (-1 ==delete) {
      serverResult =fail(OPERATOR_FAIL);
    }
    for (String user : userRowIds) {
      map.put("userGroupRowId", userGroupRowId);
      map.put("userRowId", user);
      UserRelateUserGroup userGroup = new UserRelateUserGroup().buildCreateInfo().fromMap(map);
      if (isValid(userGroup)) {
        int insert = userGroup.insert();
        if (insert != -1) {
          serverResult = successData(NEW_ADD_SUCCESS, userGroup);
        } else {
          serverResult = fail(NEW_ADD_FAIL);
        }
      } else {
        serverResult = fail(NEW_ADD_FAIL);
      }
    }
    return serverResult;
  }

}
