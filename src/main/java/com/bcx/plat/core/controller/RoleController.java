package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.service.RoleService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 角色controller层
 *
 * @author YoungerOu
 * Created on 2017/10/13.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/role")
public class RoleController extends BaseController {

  private RoleService roleService;

  @Autowired
  public RoleController(RoleService roleService) {
    this.roleService = roleService;
  }

  /**
   * 角色 - 新增
   *
   * @param param 接收一个实体参数
   * @return PlatResult
   */
  @PostMapping("/add")
  public PlatResult add(@RequestParam Map<String, Object> param) {
    ServerResult result = roleService.add(param);
    return result(result);
  }

  /**
   * 角色 - 编辑
   *
   * @param param 接收一个实体参数
   * @return PlatResult
   */
  @PostMapping("/modify")
  public PlatResult modify(@RequestParam Map<String, Object> param) {
    ServerResult result = roleService.modify(param);
    return result(result);
  }

  /**
   * 角色 - 分页模糊多字段查询
   *
   * @param search   按照空格查询
   * @param param    按照指定字段模糊查询
   * @param pageNum  页码
   * @param pageSize 页面大小
   * @param order    排序方式
   * @return PlatResult
   */
  @RequestMapping("/queryPage")
  public PlatResult queryPage(String search, String param, Integer pageNum, Integer pageSize, String order) {
    ServerResult result = roleService.queryPage(search, param, pageNum, pageSize, order);
    return result(result);
  }

  /**
   * 角色 - 根据指定字段精确查询
   *
   * @param param 接收指定参数(rowId, roleId, roleName...)
   * @return PlatResult
   */
  @RequestMapping("/queryBySpecify")
  public PlatResult queryBySpecify(@RequestParam Map<String, Object> param) {
    ServerResult result=roleService.queryBySpecify(param);
    return result(result);
  }
}
