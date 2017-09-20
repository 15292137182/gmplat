package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * 业务对象属性Controller层
 * Created by Wen Tiehu on 2017/8/8.
 */
@RequestMapping(PLAT_SYS_PREFIX + "/core/businObjPro")
@RestController
public class BusinessObjectProController extends BaseController {

    private final FrontFuncProService frontFuncProService;
    private final BusinessObjectProService businessObjectProService;

    @Autowired
    public BusinessObjectProController(FrontFuncProService frontFuncProService, BusinessObjectProService businessObjectProService) {
        this.frontFuncProService = frontFuncProService;
        this.businessObjectProService = businessObjectProService;
    }


    protected List<String> blankSelectFields() {
        return Arrays.asList("propertyCode", "propertyName");
    }


    /**
     * 根据业务对象属性rowId查询当前数据
     *
     * @param rowId 唯一标识
     * @return PlatResult
     */
    @RequestMapping("/queryById")
    public PlatResult queryById(String rowId) {
        ServerResult result = new ServerResult();
        if (UtilsTool.isValid(rowId)) {
            ServerResult serverResult = businessObjectProService.queryById(rowId);
            return result(serverResult);
        } else {
            return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
    }

    /**
     * 供前端功能块属性使用
     * 根据业务对象rowId查询当前业务对象下的所有属性
     *
     * @param objRowId   根据业务对象rowId查找业务对象下所有属性
     * @param frontRowId 功能块rowId
     * @return PlatResult
     */
    @RequestMapping("/queryBusinPro")
    public Object queryBusinPro(String objRowId, String frontRowId) {
        ServerResult result = new ServerResult();
        if (UtilsTool.isValid(objRowId)) {
            ServerResult serverResult = businessObjectProService.queryBusinPro(objRowId, frontRowId);
            return result(serverResult);
        } else {
            return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
        }
    }

    /**
     * 新增业务对象属性
     *
     * @param paramEntity 接受实体参数
     * @return Map
     */
    @RequestMapping("/add")
    public PlatResult addBusinessObjPro(@RequestParam Map<String, Object> paramEntity) {
        ServerResult result = new ServerResult();
        BusinessObjectPro businessObjectPro = new BusinessObjectPro().buildCreateInfo().fromMap(paramEntity);
        int insert = businessObjectPro.insert();
        if (insert != -1) {
            return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS,businessObjectPro));
        } else {

            return result(result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_FAIL));
        }
    }

    /**
     * 编辑业务对象属性
     *
     * @param paramEntity 实体参数
     * @return Map
     */
    @RequestMapping("/modify")
    public PlatResult modifyBusinessObjPro(@RequestParam Map<String, Object> paramEntity) {
        BusinessObjectPro businessObjectPro=null;
        if (UtilsTool.isValid(paramEntity.get("rowId"))) {
            businessObjectPro = new BusinessObjectPro().buildModifyInfo().fromMap(paramEntity);
            businessObjectPro.updateById();
        }
        return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS,businessObjectPro));
    }

    /**
     * 业务对象属性删除方法
     *
     * @param rowId 按照rowId查询
     * @return serviceResult
     */
    @RequestMapping("/delete")
    public Object delete(String rowId) {
        //通过rowId查询数据
        Condition condition = new ConditionBuilder(BusinessObjectPro.class).and().equal("rowId", rowId).endAnd().buildDone();
        List<BusinessObjectPro> businessObjectPros = businessObjectProService.select(condition);

        ServerResult result = new ServerResult();
        List<FrontFuncPro> frontFuncPros = frontFuncProService.select(new FieldCondition("relateBusiPro", Operator.EQUAL, rowId));
        int del;
        if (frontFuncPros.size() == 0) {
            BusinessObjectPro businessObjectPro = new BusinessObjectPro();
            del = businessObjectPro.deleteById(rowId);
            if (del != -1) {
                //接受rowId返回业务对象数据
                return result(new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS,businessObjectPros));
            } else {
                return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
            }
        } else {
            return result(result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE));
        }
    }
}
