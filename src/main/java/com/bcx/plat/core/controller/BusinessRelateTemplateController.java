package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;


/**
 * controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/busiRelate")
public class BusinessRelateTemplateController extends BaseController {

  protected List<String> blankSelectFields() {
    return Arrays.asList("datasetCode", "datasetName", "datasetType");
  }

}
