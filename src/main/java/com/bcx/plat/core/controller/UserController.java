package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.User;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.UserService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 用户信息controller层
 * Created by YoungerOu on 2017/10/10.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/user")
public class UserController extends BaseController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * 人员信息 - 查询方法
   *
   * @param search   按照空格查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param param    按照指定字段查询(json)
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult queryPage(String search, Integer pageNum, Integer pageSize, String param, String order) {
    ServerResult result = userService.queryPage(search, pageNum, pageSize, param, order);
    return result(result);
  }

  /**
   * 人员信息 - 根据指定的一个或多个组织机构查询
   *
   * @param param 组织机构参数，以数组的形式传入["1","2"]
   * @return PlatResult
   */
  @RequestMapping("/queryByOrg")
  public PlatResult queryByOrg(String param) {
    if (UtilsTool.isValid(param)) {
      List list = UtilsTool.jsonToObj(param, List.class);
      ServerResult serverResult = userService.queryByOrg(list);
      return result(serverResult);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 人员信息 - 根据指定字段精确查询
   *
   * @param param 接收指定参数(rowId, id, name...)
   * @return PlatResult
   */
  @RequestMapping("/queryBySpecify")
  public PlatResult queryBySpecify(@RequestParam Map<String, Object> param) {
    if (!param.isEmpty()) {
      Condition condition = UtilsTool.convertMapToAndCondition(User.class, param);
      List<Map> select = userService.selectMap(condition);
      if (!select.isEmpty()) {
        return successData(Message.QUERY_SUCCESS, select);
      } else {
        return fail(Message.QUERY_FAIL);
      }
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 人员信息 - 新增
   *
   * @param param
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult add(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();

    return null;
  }

  /**
   * 人员信息 - 编辑
   *
   * @param param 接收一个实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modify(@RequestParam Map<String, Object> param) {
    if (UtilsTool.isValid(param.get("rowId"))) {
      Object id = param.get("id");
      Object name = param.get("name");
      if (null != id && !"".equals(id.toString().trim())
          && null != name && !"".equals(name.toString().trim())) {//工号和姓名不能为空
        //工号不能重复
        Condition condition = new ConditionBuilder(User.class).and().equal("id", id).endAnd().buildDone();
        List<User> users = userService.select(condition);
        if (users.isEmpty()) {
          param.put("id", id.toString().trim());
          param.put("name", name.toString().trim());
          User user = new User();
          User modify = user.fromMap(param).buildModifyInfo();
          if (modify.updateById() != -1) {
            return success(Message.UPDATE_SUCCESS);
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
   * 人员信息 - 删除
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      User user = new User().buildDeleteInfo();
      if (user.logicalDeleteById(rowId) != -1) {
        return successData(Message.DELETE_SUCCESS, user);
      } else {
        return fail(Message.DELETE_FAIL);
      }
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 人员信息 - 锁定账号
   *
   * @param rowId 唯一标识
   *              状态（1：解锁，2：锁定，3：失效，4：启用）
   * @return PlatResult
   */
  @PostMapping
  public PlatResult lock(String rowId) {

    return null;
  }
}
