package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.service.BaseOrgService;
import com.bcx.plat.core.utils.PlatResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
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
  public PlatResult insertEntity(@RequestParam Map<String, Object> entity) {
    return PlatResult.success(baseOrgService.insertEntityMap(entity));
  }

}