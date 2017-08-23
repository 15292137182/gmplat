package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 键值集合信息
 * Created by Went on 2017/8/3.
 */
@Service
public class KeySetService extends BaseServiceTemplate<KeySet>{
    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
