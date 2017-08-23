package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.common.BaseServiceTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectService extends BaseServiceTemplate<BusinessObject> {


    @Override
    public boolean isRemoveBlank() {
        return false;
    }
}