package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;


/**
 * 数据集controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/dataSetConfig")
public class DataSetConfigController extends BaseController {

    protected List<String> blankSelectFields() {
        return Arrays.asList("datasetCode", "datasetName", "datasetType");
    }

    /**
     * 新增数据集
     *
     * @param param 接受实体参数
     * @return PlatResult
     */
    @RequestMapping("/add")
    public PlatResult addDataSet(@RequestParam Map<String, Object> param) {
        DataSetConfig dataSetConfig = new DataSetConfig().buildCreateInfo().fromMap(param);
        int insert = dataSetConfig.insert();
        if (insert != -1) {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS));
        } else {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 修改数据集数据
     *
     * @param param 接受实体参数
     * @return platResult
     */
    @RequestMapping("/modify")
    public PlatResult modifyDataSet(@RequestParam Map<String, Object> param) {
        int update;
        if (UtilsTool.isValid(param.get("rowId"))) {
            DataSetConfig dataSetConfig = new DataSetConfig().buildModifyInfo().fromMap(param);
            update = dataSetConfig.updateById();
            if (update != -1) {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS));
            } else {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL));
            }
        } else {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }

    /**
     * 业务对象属性删除方法
     *
     * @param rowId 按照rowId查询
     * @return serviceResult
     */
    @RequestMapping("/delete")
    public Object delete(String rowId) {
        int del;
        if (!rowId.isEmpty()) {
            DataSetConfig dataSetConfig = new DataSetConfig();
            del = dataSetConfig.deleteById(rowId);
            if (del != -1) {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            } else {
                return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.DELETE_SUCCESS));
            }
        } else {
            return super.result(ServerResult.setMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }


}
