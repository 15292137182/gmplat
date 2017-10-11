package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Employee;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户信息业务层
 * Created by YoungerOu on 2017/10/10.
 */
@Service
public class EmployeeService extends BaseService<Employee> {
  private List<String> blankSelectFields() {
    return Arrays.asList("employeeNo", "employeeName", "employeeNickName", "belongOrganization", "idCard", "job", "hiredate");
  }

  /**
   * 用户信息分页查询
   *
   * @param search   按照空格查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param param    按照指定字段查询
   * @param order    排序方式
   * @return ServerResult
   */
  public ServerResult queryPage(String search, Integer pageNum, Integer pageSize, String param, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(order);
    Condition condition;
    if (UtilsTool.isValid(param)) {//判断是否根据指定字段查询
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Employee.class, UtilsTool.jsonToObj(param, Map.class));
    } else {
      condition = !UtilsTool.isValid(param) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult<Map<String, Object>> employees;
    if (UtilsTool.isValid(pageNum)) {//判断是否分页查询
      employees = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      employees = new PageResult(selectMap(condition, orders));
    }
    if (UtilsTool.isValid(employees.getResult())) {
      return new ServerResult<>(employees);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据组织机构查询用户信息
   *
   * @param list
   * @return
   */
  public ServerResult queryByOrganization(List list) {
    Map<Object, Object> maps = new HashMap<>();
    Condition condition = new ConditionBuilder(Employee.class).and().in("belongOrganization", list).endAnd().buildDone();
    List<Map> employees = selectMap(condition);
    if (!employees.isEmpty()) {
      return successData(Message.QUERY_SUCCESS, employees);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }
}
