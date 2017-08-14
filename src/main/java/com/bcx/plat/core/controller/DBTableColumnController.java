package com.bcx.plat.core.controller;

/**
 * Create By HCL at 2017/8/1
 */

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.service.DBTableColumnService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/core/dbTableColumn")
public class DBTableColumnController extends BaseControllerTemplate<DBTableColumnService,DBTableColumn> {
  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("columnEname","columnCname");
  }
}