package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.UserGroup;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.UserGroupService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.TRUE_FLAG;
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
public class UserGroupController extends BaseController {

  @Autowired
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
    String groupName = String.valueOf(param.get("groupName"));
    if (isValid(groupName)) {
      UserGroup menu = new UserGroup().buildCreateInfo().fromMap(param);
      int insert = menu.insert();
      if (insert != -1) {
        return successData(NEW_ADD_SUCCESS, menu);
      } else {
        return fail(NEW_ADD_FAIL);
      }
    } else {
      return fail(DATA_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 修改用户组
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyUserGroup(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      String groupName = String.valueOf(param.get("groupName"));
      if (isValid(groupName)) {
        UserGroup userGroup = new UserGroup().buildModifyInfo().fromMap(param);
        int update = userGroup.updateById();
        if (update != -1) {
          return successData(NEW_ADD_SUCCESS, userGroup);
        } else {
          return fail(UPDATE_FAIL);
        }
      } else {
        return fail(DATA_CANNOT_BE_EMPTY);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 根据rowId删除用户组信息
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/logicDelete")
  public PlatResult deleteLogic(String rowId) {
    Condition condition = new ConditionBuilder(UserGroup.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<UserGroup> userGroups = userGroupService.select(condition);
    if (UtilsTool.isValid(rowId)) {
      Map<String,Object> map = new HashMap<>();
      map.put("deleteFlag",TRUE_FLAG);
      UserGroup userGroup = new UserGroup().buildModifyInfo().fromMap(map);
      int update = userGroup.updateById();
      if (update != -1) {
        return successData(Message.DELETE_SUCCESS, userGroups);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }


  /**
   * 根据rowId删除用户组信息
   *
   * @param rowId 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/physicsDelete")
  public PlatResult deletePhysics(String rowId) {
    Condition condition = new ConditionBuilder(UserGroup.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<UserGroup> userGroups = userGroupService.select(condition);
    if (UtilsTool.isValid(rowId)) {
      UserGroup userGroup = new UserGroup();
      int del = userGroup.deleteById(rowId);
      if (del != -1) {
        return successData(Message.DELETE_SUCCESS, userGroups);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
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
      return result(new ServerResult<>(result));
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据用户组rowId查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @GetMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (isValid(rowId)) {
      return result(new ServerResult<>(new UserGroup().selectOneById(rowId)));
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }
}
