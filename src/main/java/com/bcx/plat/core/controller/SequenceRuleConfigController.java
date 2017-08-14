package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.SequenceRuleConfig;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import com.bcx.plat.core.service.SequenceRuleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By HCL at 2017/8/8
 */
@RestController
@RequestMapping("/core/sequenceRule")
public class SequenceRuleConfigController extends BaseControllerTemplate<SequenceRuleConfigService,SequenceRuleConfig> {

  @Override
  protected List<String> blankSelectFields() {
    return Arrays.asList("seqCode","seqName","seqContent","desp");
  }
}
