package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BaseOrg;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.BaseOrgService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.constants.Message.PRIMARY_KEY_CANNOT_BE_EMPTY;
import static com.bcx.plat.core.utils.UtilsTool.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 组织机构控制器
 * Create By HCL at 2017/10/11
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/baseOrg")
public class BaseOrgController extends BaseController {

  @Resource
  private BaseOrgService baseOrgService;

  /**
   * 组织机构新增数据
   *
   * @param entity 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/add", method = POST)
  public PlatResult insertOrg(@RequestParam Map<String, Object> entity) {
    return PlatResult.success(baseOrgService.insertOrgMap(entity));
  }

  /**
   * 修改菜单
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyOrg(@RequestParam Map<String, Object> param) {
    return PlatResult.success(baseOrgService.updateOrgMap(param));
  }

  /**
   * 根据rowId删除组织机构信息
   *
   * @param rowIds 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String[] rowIds) {
    return PlatResult.success(baseOrgService.deleteByRowIds(rowIds));
  }

  /**
   * 根据组织机构rowId查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (isValid(rowId)) {
      return result(new ServerResult<>(new BaseOrg().selectOneById(rowId)));
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  /**
   * @return 空白查询的字段
   */
  protected List<String> blankSelectFields() {
    return Arrays.asList("orgPid", "orgId", "orgName", "orgSort", "orgLevel", "fixedPhone", "address", "desp");
  }

  /**
   * 分页查询数据
   *
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping(value = "/queryPage")
  @SuppressWarnings("unchecked")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = dataSort(BaseOrg.class, order);
    Condition condition;
    if (isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(BaseOrg.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = baseOrgService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult<>(baseOrgService.selectMap(condition, orders));
    }
    if (isValid(result)) {
      return result(new ServerResult<>(result));
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * @param orgRowId 部门 rowId
   * @return 响应结果
   */
  @RequestMapping(value = "/queryOrgUser")
  public PlatResult queryUserInOrg(String orgRowId) {
    return PlatResult.success(new ServerResult<>(baseOrgService.queryUserInOrg(orgRowId)));
  }

  /**
   * @param orgRowId 部门 rowId
   * @return 响应结果
   */
  @RequestMapping(value = "/queryOrgRole")
  public PlatResult queryRoleInOrg(String orgRowId) {
    return PlatResult.success(new ServerResult<>(baseOrgService.queryRoleInOrg(orgRowId)));
  }

}