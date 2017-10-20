package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.entity.UserGroup;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.UserGroupService;
import com.bcx.plat.core.service.UserService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.TRUE_FLAG;
import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.dataSort;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * <p>Title: UserGroupController</p>
 * <p>Description: 用户组控制层</p>
 * <p>Copyright: Shanghai Batchsight GMP Information of management platform, Inc. Copyright(c) 2017</p>
 *
 * @author Wen TieHu
 * @version 1.0
 *          <pre>Histroy: 2017/10/11  Wen TieHu Create </pre>
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/userGroup")
public class UserGroupController extends BaseController {

  @Resource
  private UserGroupService userGroupService;

  protected List<String> blankSelectFields() {
    return Arrays.asList("groupNumber", "groupName", "belongSector", "groupCategory", "desc", "remarks");
  }


  /**
   * 新增用户组
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult addUserGroup(@RequestParam Map<String, Object> param) {
    PlatResult platResult;
    String groupName = String.valueOf(param.get("groupName"));
    if (isValid(groupName)) {
      UserGroup userGroup = new UserGroup().buildCreateInfo().fromMap(param);
      int insert = userGroup.insert();
      if (insert != -1) {
        platResult = successData(NEW_ADD_SUCCESS, userGroup);
      } else {
        platResult = fail(NEW_ADD_FAIL);
      }
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 修改用户组
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyUserGroup(@RequestParam Map<String, Object> param) {
    PlatResult platResult;
    if (UtilsTool.isValid(param.get("rowId"))) {
      String groupName = String.valueOf(param.get("groupName"));
      if (isValid(groupName)) {
        UserGroup userGroup = new UserGroup().buildModifyInfo().fromMap(param);
        int update = userGroup.updateById();
        if (update != -1) {
          platResult = successData(NEW_ADD_SUCCESS, userGroup);
        } else {
          platResult = fail(UPDATE_FAIL);
        }
      } else {
        platResult = fail(DATA_CANNOT_BE_EMPTY);
      }
    } else {
      platResult = fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 根据rowId删除用户组信息
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/logicDelete")
  public PlatResult deleteLogic(@RequestParam List<String> rowId) {
    PlatResult platResult =null;
    Condition condition = new ConditionBuilder(UserGroup.class).and().in("rowId", rowId).endAnd().buildDone();
    List<UserGroup> userGroups = userGroupService.select(condition);
    if (UtilsTool.isValid(rowId)) {
      for (UserGroup group : userGroups){
        group.getBaseTemplateBean().setDeleteFlag(TRUE_FLAG);
        int update = group.updateById();
        if (update != -1) {
          platResult = successData(Message.DELETE_SUCCESS, userGroups);
        } else {
          platResult = fail(Message.DELETE_FAIL);
        }
      }
    } else {
      platResult = fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }


  /**
   * 根据rowId删除用户组信息
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/physicsDelete")
  public PlatResult deletePhysics(String rowId) {
    PlatResult platResult;
    Condition condition = new ConditionBuilder(UserGroup.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<UserGroup> userGroups = userGroupService.select(condition);
    if (UtilsTool.isValid(rowId)) {
      UserGroup userGroup = new UserGroup();
      int del = userGroup.deleteById(rowId);
      if (del != -1) {
        platResult = successData(Message.DELETE_SUCCESS, userGroups);
      } else {
        platResult = fail(Message.DELETE_FAIL);
      }
    } else {
      platResult = fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 根据用户组rowId查找数据
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @GetMapping("/queryPage")
  @SuppressWarnings("unchecked")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    PlatResult platResult;
    LinkedList<Order> orders = dataSort(UserGroup.class, order);
    Condition condition;
    if (UtilsTool.isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(UserGroup.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = userGroupService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult<>(userGroupService.selectMap(condition, orders));
    }
    if (isValid(result)) {
      platResult = result(new ServerResult<>(result));
    } else {
      platResult = fail(Message.QUERY_FAIL);
    }
    return platResult;
  }

  /**
   * 根据用户组rowId查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @GetMapping("/queryById")
  public PlatResult queryById(String rowId) {
    PlatResult platResult;
    if (isValid(rowId)) {
      platResult = result(new ServerResult<>(new UserGroup().selectOneById(rowId)));
    } else {
      platResult = fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 根据用户组查询用户信息
   * @param userGroupRowId 用户组唯一标示
   * @param pageNum 页码
   * @param pageSize 一页显示条数
   * @param order 排序 "{\"str\":\"rowId\", \"num\":1}"
   * @return 用户信息
   */
  @GetMapping("/queryUserGroupUser")
  @SuppressWarnings("unchecked")
  public PlatResult queryUserGroupUser(String userGroupRowId, Integer pageNum, Integer pageSize, String order) {
    PlatResult platResult;
    LinkedList<Order> orders = dataSort(User.class, order == null ? "" : order);
    if (isValid(userGroupRowId)) {
      ServerResult serverResult = userGroupService.queryUserGroupUser(userGroupRowId, pageNum, pageSize, orders);
      platResult= result(serverResult);
    }else{
      platResult = fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

  /**
   * 删除用户组下的用户信息
   *
   * @param userGroupRowId 用户组rowId
   * @param userRowIds     用户rowId 集合
   * @return 返回操作结果信息
   */
  @PostMapping("/deleteGroupUsers")
  public PlatResult deleteGroupUsers(String userGroupRowId, @RequestParam("userRowIds") List<String> userRowIds) {
    boolean success = userGroupService.deleteUserInGroup(userGroupRowId, userRowIds);
    if (success) {
      return success(Message.DELETE_SUCCESS);
    } else {
      return fail(Message.INVALID_REQUEST);
    }
  }

  /**
   * 给用户组下添加用户
   *
   * @param userGroupRowId 用户组rowId
   * @param userRowIds      用户rowId
   * @return 新增信息
   */
  @PostMapping("/addUserGroupUser")
  public PlatResult addUserGroupUser(@RequestParam("userRowIds") List<String> userRowIds, String userGroupRowId) {
    PlatResult platResult ;
    if (isValid(userRowIds) && isValid(userGroupRowId.trim())) {
      ServerResult serverResult = userGroupService.addUserGroupUser(userRowIds, userGroupRowId);
      platResult = result(serverResult);
    } else {
      platResult = fail(DATA_CANNOT_BE_EMPTY);
    }
    return platResult;
  }

}
