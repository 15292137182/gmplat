package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.service.PermissionResourceService;
import com.bcx.plat.core.service.PermissionService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 资源控制器
 * <p>
 * Create By HCL at 2017/10/24
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/permissionResource")
public class PermissionResourceController extends BaseController {

  @Resource
  private PermissionResourceService permissionResourceService;

  /**
   * @param permissionRowId 权限主键
   * @param sourceRowIds    资源主键
   * @return 删除资源
   */
  @RequestMapping(value = "/deleteResource")
  public PlatResult deleteResource(String permissionRowId, String[] sourceRowIds) {
    return PlatResult.success(permissionResourceService.deletePermissionResource(permissionRowId, sourceRowIds));
  }

  /**
   * 查询权限下对应的资源信息
   *
   * @param permissionRowId 权限主键
   * @param search          空白查询字段
   * @param param           参数
   * @param pageNum         页面号
   * @param pageSize        页面大小
   * @param order           排序字段
   * @return 返回平台响应结果
   */
  @RequestMapping(value = "/queryResource")
  public PlatResult queryResource(String permissionRowId
          , String search, String param, Integer pageNum, Integer pageSize, String order) {
    return PlatResult.success(permissionResourceService.queryResource(permissionRowId, search, param, pageNum, pageSize, order));
  }

  /**
   * 新建菜单关联
   *
   * @param permissionRowId 权限主键
   * @param menuRowIds      主键
   * @return 返回操作结果
   */
  @RequestMapping(value = "/insertPermissionMenu")
  public PlatResult insertPermissionMenu(String permissionRowId, String[] menuRowIds) {
    return insertPermissionRelate(PermissionService.PERMISSION_TYPE_MENU, permissionRowId, menuRowIds);
  }

  /**
   * 新建接口关联
   *
   * @param permissionRowId 权限主键
   * @param interfaceRowIds 主键
   * @return 返回操作结果
   */
  @RequestMapping(value = "/insertPermissionInterface")
  public PlatResult insertPermissionInterface(String permissionRowId, String[] interfaceRowIds) {
    return insertPermissionRelate(PermissionService.PERMISSION_TYPE_INTERFACE, permissionRowId, interfaceRowIds);
  }

  /**
   * 新建菜单关联
   *
   * @param permissionRowId 权限主键
   * @param pageRowIds      主键
   * @return 返回操作结果
   */
  @RequestMapping(value = "/insertPermissionPage")
  public PlatResult insertPermissionPage(String permissionRowId, String[] pageRowIds) {
    return insertPermissionRelate(PermissionService.PERMISSION_TYPE_PAGE, permissionRowId, pageRowIds);
  }

  /**
   * 新建菜单关联
   *
   * @param permissionRowId 权限主键
   * @param buttonRowIds    主键
   * @return 返回操作结果
   */
  @RequestMapping(value = "/insertPermissionButton")
  public PlatResult insertPermissionButton(String permissionRowId, String[] buttonRowIds) {
    return insertPermissionRelate(PermissionService.PERMISSION_TYPE_BUTTON, permissionRowId, buttonRowIds);
  }

  /**
   * 新增权限关联
   *
   * @return 返回操作结果信息
   */
  private PlatResult insertPermissionRelate(String type, String permissionRowId, String[] resourceRowIds) {
    ServerResult serverResult = permissionResourceService.insertPermissionRelate(type, permissionRowId, resourceRowIds);
    return PlatResult.success(serverResult);
  }
}