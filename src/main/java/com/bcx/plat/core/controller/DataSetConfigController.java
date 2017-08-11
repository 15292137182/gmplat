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

    @Autowired
    private DataSetConfigService dataSetConfigService;
    @Override
    public void setEntityService(DataSetConfigService dataSetConfigService) {
        super.setEntityService(dataSetConfigService);
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("datasetCode","datasetName","datasetType");
    }


    /**
     * 新增业务对象:对象代码，对象名称，关联表(单选)，版本(系统生成)
     *
     * @param entity
     * @param request
     * @param locale
     */
    @RequestMapping("/add")
    @Override
    public Object insert(DataSetConfig entity, HttpServletRequest request, Locale locale) {
        entity.setDatasetCode("A00"+UtilsTool.lengthUUID(3).toUpperCase());
        entity.setVersion("1.0");
        return super.insert(entity,request,locale);
    }
}
