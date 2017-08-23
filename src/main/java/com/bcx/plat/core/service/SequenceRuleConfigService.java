package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Wen Tiehu on 2017/8/14.
 */
@Service
public class SequenceRuleConfigService extends BaseServiceTemplate<SequenceRuleConfig>{
    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
