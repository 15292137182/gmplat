package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.service.PermissionService;
import com.bcx.plat.core.utils.PlatResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
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
}
