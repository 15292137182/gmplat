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

  @PostMapping("/add")
  public PlatResult add(@RequestParam Map<String, Object> param) {
    ServerResult result = roleService.add(param);
    return result(result);
  }

}
