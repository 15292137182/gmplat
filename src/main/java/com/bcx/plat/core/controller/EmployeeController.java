package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
