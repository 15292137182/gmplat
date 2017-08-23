package com.bcx.plat.core.service;

import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.entity.FrontFuncPro;
import org.springframework.stereotype.Service;

/**
 * 前端功能块属性项信息维护 service层
 * Created by Wen Tiehu on 2017/8/4.
 */
@Service
public class FrontFuncProService extends BaseServiceTemplate<FrontFuncPro> {

    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}
