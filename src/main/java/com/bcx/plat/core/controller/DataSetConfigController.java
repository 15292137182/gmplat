package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.service.DataSetConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.collectToSet;


/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping("/core/dataSetConfig")
public class DataSetConfigController extends BaseController {

    @Autowired
    private DataSetConfigService dataSetConfigService;

    /**
     * 查询数据集配置信息
     *
     * @param str   接受传入进来的map参数
     * @param rowId 接受传入进来的rowId
     * @return 返回查询数据以及状态
     */
    @RequestMapping("/query")
    public Object queryDataSetConfig(String str,String rowId, HttpServletRequest request, Locale locale) {
        Map<String, Object> map = new HashMap<>();
        map.put("strArr", collectToSet(str));
        map.put("rowId", rowId);
        ServiceResult<DataSetConfig> result = dataSetConfigService.queryDataSetConfig(map);
        return super.result(request, result, locale);


    }

    /**
     * 新增数据集配置信息维护
     *
     * @param dataSetConfig 前端功能块属性实体
     * @return 返回新增状态
     */
    @RequestMapping("/add")
    public Object addDataSetConfig(DataSetConfig dataSetConfig, HttpServletRequest request, Locale locale) {
        ServiceResult result = dataSetConfigService.addDataSetConfig(dataSetConfig);
        return super.result(request, result, locale);

    }

    /**
     * 修改数据集配置信息维护
     *
     * @param dataSetConfig 前端功能块属性实体
     * @return 返回修改状态
     */
    @RequestMapping("/modify")
    public Object modifyDataSetConfig(DataSetConfig dataSetConfig, HttpServletRequest request, Locale locale) {
        ServiceResult result = dataSetConfigService.modifyDataSetConfig(dataSetConfig);
        return super.result(request, result, locale);

    }

    /**
     * 删除数据集配置信息维护
     *
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    @RequestMapping("/delete")
    public Object delete(String delData, HttpServletRequest request, Locale locale) {
        DataSetConfig dataSetConfig = new DataSetConfig();
        dataSetConfig.setRowId(delData);
        ServiceResult<DataSetConfig> result = dataSetConfigService.deleteDataSetConfig(dataSetConfig);
        return super.result(request, result, locale);


    }


}
