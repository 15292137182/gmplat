package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.service.BusinessObjectServiceA;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by WJF on 2017/8/8.
 */
@RestController
@RequestMapping("/core/businObj")
public class BusinessObjectControllerTest extends BaseControllerTemplate<BusinessObjectServiceA>{
  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("objectCode", "objectName");
  }
}
