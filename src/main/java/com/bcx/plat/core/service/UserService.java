package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.User;
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
 * 用户信息业务层
 * Created by YoungerOu on 2017/10/10.
 */
@Service
public class UserService extends BaseService<User> {
  private List<String> blankSelectFields() {
    return Arrays.asList("id", "name", "nickname", "belongOrg", "idCard", "job", "hiredate");
  }

  /**
   * 人员信息分页查询
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
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(User.class, UtilsTool.jsonToObj(param, Map.class));
    } else {
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> users;
    if (UtilsTool.isValid(pageNum)) {//判断是否分页查询
      users = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      users = new PageResult(selectMap(condition, orders));
    }
    if (UtilsTool.isValid(null == users ? null : users.getResult())) {
      return new ServerResult<>(users);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据组织机构查询用户信息
   *
   * @param list 组织机构代码列表
   * @return ServerResult
   */
  public ServerResult queryByOrg(List list) {
    Condition condition = new ConditionBuilder(User.class).and().in("belongOrg", list).endAnd().buildDone();
    List<Map> employees = selectMap(condition);
    if (!employees.isEmpty()) {
      return successData(Message.QUERY_SUCCESS, employees);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }
}
