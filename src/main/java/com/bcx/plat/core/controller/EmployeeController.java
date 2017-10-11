package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Employee;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.EmployeeService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(PLAT_SYS_PREFIX + "/core/employee")
public class EmployeeController extends BaseController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  /**
   * 人员信息查询方法
   *
   * @param search   按照模糊查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param param    按照指定字段查询
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult queryPage(String search, Integer pageNum, Integer pageSize, String param, String order) {
    ServerResult result = employeeService.queryPage(search, pageNum, pageSize, param, order);
    return result(result);
  }

  /**
   * 根据指定的一个或多个组织机构查询人员信息
   *
   * @param param 组织机构参数，以数组的形式传入["1","2"]
   * @return PlatResult
   */
  @RequestMapping("/queryByOrganization")
  public PlatResult queryByOrganization(String param) {
    if (UtilsTool.isValid(param)) {
      List list = UtilsTool.jsonToObj(param, List.class);
      ServerResult serverResult = employeeService.queryByOrganization(list);
      return result(serverResult);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据工号精确查询
   *
   * @param employeeNo 用户工号
   * @return PlatResult
   */
  @RequestMapping("/queryByEmployeeNo")
  public PlatResult queryByEmpNo(String employeeNo) {
    if (UtilsTool.isValid(employeeNo)) {
      Condition condition = new ConditionBuilder(Employee.class).and().equal("employeeNo", employeeNo).endAnd().buildDone();
      return selectMapByCondition(condition);
    } else {
      return fail(Message.PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 根据rowId精确查询
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (UtilsTool.isValid(rowId)) {
      Condition condition = new ConditionBuilder(Employee.class).and().equal("rowId", rowId).endAnd().buildDone();
      return selectMapByCondition(condition);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 提取冗余代码单独封装
   *
   * @param condition 查询条件
   * @return PlatResult
   */
  private PlatResult selectMapByCondition(Condition condition) {
    List<Map> select = employeeService.selectMap(condition);
    if (!select.isEmpty()) {
      return successData(Message.QUERY_SUCCESS, select);
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  public PlatResult add(@RequestParam Map<String, Object> param) {
    ServerResult result = new ServerResult();

    return null;
  }
}
