package com.bcx.plat.core.service;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create By HCL at 2017/8/8
 */
@Transactional
public class SequenceRuleConfigServiceTest extends BaseTest {

  @Autowired
  private SequenceRuleConfigService sequenceRuleConfigService;

  @Test
  @Rollback
  public void test() {
    SequenceRuleConfig config = new SequenceRuleConfig();
    config.buildModifyInfo().buildCreateInfo().buildDeleteInfo();
    sequenceRuleConfigService.insert(config);
  }
}