package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.SequenceGenerate;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Wen Tiehu on 2017/8/15.
 */
@Service
public class SequenceGenerateService extends BaseServiceTemplate<SequenceGenerate>{
    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
