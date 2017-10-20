package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.Permission;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.PermissionService;
import com.bcx.plat.core.service.RoleService;
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
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 权限控制器
 * <p>
 * Create By HCL at 2017/10/12
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/permission")
public class PermissionController extends BaseController {

  @Resource
  private PermissionService permissionService;

  /**
   * 新增数据
   *
   * @param entity 接受一个实体参数
   * @return 返回操作信息
   */
  @RequestMapping(value = "/add", method = POST)
  public PlatResult insertOrg(@RequestParam Map<String, Object> entity) {
    return PlatResult.success(permissionService.insertPermissionMap(entity));
  }

  /**
   * 根据rowId删除组织机构信息
   *
   * @param rowIds 按照rowId查询
   * @return PlatResult
   */
  @PostMapping("/delete")
  public PlatResult delete(String[] rowIds) {
    return PlatResult.success(permissionService.deleteByRowIds(rowIds));
  }

  /**
   * @return 空白查询的字段
   */
  protected List<String> blankSelectFields() {
    return Arrays.asList("permission_id", "permission_name", "permission_type", "desc", "remarks");
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
    LinkedList<Order> orders = dataSort(Permission.class, order);
    Condition condition;
    if (isValid(param)) { // 判断是否有param参数，如果有，根据指定字段查询
      Map<String, Object> map = jsonToObj(param, Map.class);
      condition = UtilsTool.convertMapToAndConditionSeparatedByLike(Permission.class, map);
    } else { // 如果没有param参数，则进行空格查询
      condition = !UtilsTool.isValid(search) ? null : UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search));
    }
    PageResult result;
    if (UtilsTool.isValid(pageNum)) { // 判断是否分页查询
      result = permissionService.selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      result = new PageResult<>(permissionService.selectMap(condition, orders));
    }
    if (isValid(result)) {
      return result(new ServerResult<>(result));
    } else {
      return fail(Message.QUERY_FAIL);
    }
  }

  /**
   * 根据权限类型查询
   *
   * @param permissionType 权限类型
   * @param search         空白查询
   * @param param          参数
   * @param pageNum        页面号
   * @param pageSize       页面大小
   * @param order          排序
   * @return 返回操作结果
   */
  @RequestMapping(value = "/queryTypePermission")
  public PlatResult queryPermissionByType(String permissionType, String search, String param,
                                          @RequestParam(defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                          @RequestParam(defaultValue = BaseConstants.PAGE_SIZE) int pageSize, String order) {
    LinkedList<Order> orders = dataSort(Permission.class, order);
    if (isValid(permissionType)) {
      return successData(Message.QUERY_SUCCESS,
              permissionService.queryPermissionByType(permissionType, search, param, orders, pageNum, pageSize));
    }
    return fail(Message.INVALID_REQUEST);
  }

  /**
   * 修改菜单
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modifyOrg(@RequestParam Map<String, Object> param) {
    return PlatResult.success(permissionService.updateOrgMap(param));
  }

  /**
   * 根据 rowId 查询当前数据
   *
   * @param rowId 功能块 rowId
   * @return PlatResult
   */
  @RequestMapping("/queryById")
  public PlatResult queryById(String rowId) {
    if (isValid(rowId)) {
      return result(new ServerResult<>(new Permission().selectOneById(rowId)));
    } else {
      return fail(PRIMARY_KEY_CANNOT_BE_EMPTY);
    }
  }

  @Resource
  private RoleService roleService;

  /**
   * 查询包含权限的角色
   *
   * @param rowId    权限 rowId
   * @param search   空白查询
   * @param param    参数
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param order    排序字段
   * @return 返回操作结果
   */
  @RequestMapping(value = "/queryRole", method = {GET, POST})
  public PlatResult queryRole(String rowId, String search, String param,
                              @RequestParam(defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                              @RequestParam(defaultValue = BaseConstants.PAGE_SIZE) int pageSize, String order) {
    LinkedList<Order> orders = dataSort(Permission.class, order);
    return result(roleService.queryRoleContainsPermission(rowId, search, param, orders, pageNum, pageSize));
  }

}
