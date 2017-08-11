package com.bcx.plat.core.controller;

import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.service.DataSetConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping("/core/dataSetConfig")
public class DataSetConfigController extends BaseControllerTemplate<DataSetConfigService,DataSetConfig> {

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("datasetCode","datasetName","datasetType");
    }

}
