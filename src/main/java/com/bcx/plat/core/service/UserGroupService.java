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
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Message.OPERATOR_FAIL;
import static com.bcx.plat.core.constants.Message.OPERATOR_SUCCESS;
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
          new User().selectPage(buildDone, pageNum, pageSize, orders);
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
    for (String user : userRowIds) {
      map.put("userGroupRowId", userGroupRowId);
      map.put("userRowId", user);
      UserRelateUserGroup userGroup = new UserRelateUserGroup().buildCreateInfo().fromMap(map);
      if (isValid(userGroup)) {
        int insert = userGroup.insert();
        if (insert != -1) {
          serverResult = successData(OPERATOR_SUCCESS, userGroup);
        } else {
          serverResult = fail(OPERATOR_FAIL);
        }
      } else {
        serverResult = fail(OPERATOR_FAIL);
      }
    }
    return serverResult;
  }

  /**
   * 查询用户组下的用户信息,添加到用户组的数据不返回
   *
   * @param userGroupRowId 用户组rowId
   * @param search         模糊搜索条件
   * @param param          精确搜索条件
   * @param pageNum        一页显示条数
   * @param pageSize       页码
   * @param orders         排序
   * @return platResult
   * @Author wenTieHu
   * @Date 2017/10/21
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryUserInfo(String userGroupRowId, String search, String param, Integer pageNum, Integer pageSize, List<Order> orders) {
    ServerResult serverResult;
    Condition condition;
    //查询用户表中所有记录
    List<User> users = new User().selectAll();
    //构建查询关联用户用户组条件
    Condition groupRowId = new ConditionBuilder(UserRelateUserGroup.class).and().equal("userGroupRowId", userGroupRowId).endAnd().buildDone();
    //查询用户组中对应的一条数据
    List<UserRelateUserGroup> userRelateUserGroups = new UserRelateUserGroup().selectSimple(groupRowId);
    if (isValid(userRelateUserGroups)) {
      //获取用户关联用户组表中的已经存在的数据
      List<String> strings = userRelateUserGroups.stream().map(UserRelateUserGroup::getUserRowId).collect(Collectors.toList());
      for (int i = 0; i <= 5; i++) {
        for (int j = 0; j < users.size(); j++) {
          //如果包含关联表中包含用户相同的数据就直接删除
          if (strings.contains(users.get(j).getRowId())) {
            users.remove(j);
          }
        }
      }
      //获取用户中所有的用户记录
      List<String> collect = users.stream().map(User::getRowId).collect(Collectors.toList());
      if (isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
        Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
        condition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, map);
      } else { // 如果没有param参数，则进行空格查询
        condition = !isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("id", "name", "nickname", "belongOrg"), UtilsTool.collectToSet(search));
      }
      Condition buildDone;
      if (condition != null) {
        buildDone = new ConditionBuilder(User.class).and().in("rowId", collect).endAnd().and().addCondition(condition).endAnd().buildDone();
      } else {
        //将用户信息作为条件添加
        buildDone = new ConditionBuilder(User.class).and().in("rowId", collect).endAnd().buildDone();
      }
      //查询用户信息并分页排序显示
      PageResult<User> user = new User().selectPage(buildDone, pageNum, pageSize, orders);
      if (isValid(user)) {
        serverResult = successData(QUERY_SUCCESS, user);
      } else {
        serverResult = fail(QUERY_FAIL);
      }
    } else {
      PageResult<User> userPageResult = new User().selectPage(null, pageNum, pageSize, orders);
      serverResult = successData(QUERY_SUCCESS, userPageResult);
    }
    return serverResult;
  }

}
