package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.service.SequenceRuleConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * Create By HCL at 2017/8/8
 */
@RestController
@RequestMapping("/core/sequenceRule")
public class SequenceRuleConfigController extends BaseControllerTemplate<SequenceRuleConfigService, SequenceRuleConfig> {

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("seqCode", "seqName", "seqContent", "desp");
    }
}
