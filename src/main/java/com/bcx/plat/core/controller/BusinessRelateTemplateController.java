package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.service.DataSetConfigService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;


/**
 * controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/busiRelate")
public class BusinessRelateTemplateController extends BaseControllerTemplate<DataSetConfigService, DataSetConfig> {

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("datasetCode", "datasetName", "datasetType");
    }

}
