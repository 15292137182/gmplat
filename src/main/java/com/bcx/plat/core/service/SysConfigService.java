package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.SysConfig;
import org.springframework.stereotype.Service;

/**
 * 系统资源配置信息维护 service层
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class SysConfigService extends BaseServiceTemplate<SysConfig> {
    @Override
    public boolean isRemoveBlank() {
        return false;
    }

}
