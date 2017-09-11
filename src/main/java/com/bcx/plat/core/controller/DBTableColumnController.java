package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectProService;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;

/**
 * Create By HCL at 2017/8/1
 */
@RestController
@RequestMapping(PLAT_SYS_PREFIX + "/core/dbTableColumn")
public class DBTableColumnController extends BaseController {
    @Autowired
    private DBTableColumnService dbTableColumnService;
    @Autowired
    private BusinessObjectProService businessObjectProService;


    protected List<String> blankSelectFields() {
        return Arrays.asList("columnEname", "columnCname");
    }


    /**
     * 通过表信息字段rowId查询表信息并分页显示
     *
     * @param search   按照空格查询
     * @param rowId    接受rowId
     * @param pageNum  当前第几页
     * @param pageSize 一页显示多少条
     * @return PlatResult
     */
    @RequestMapping("/queryPageById")
    public PlatResult queryPageById(String search, String rowId,
                                    @RequestParam(value = "pageNum", defaultValue = BaseConstants.PAGE_NUM) int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = BaseConstants.PAGE_SIZE) int pageSize,
                                    String order) {
        LinkedList<Order> orders = UtilsTool.dataSort(order);
        if (UtilsTool.isValid(rowId)) {
            ServerResult serverResult = dbTableColumnService.queryPageById(search, rowId, orders, pageNum, pageSize);
            return result(new ServerResult<>(serverResult));
        }
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }

    /**
     * 根据表信息的rowId来查询表字段中的信息
     *
     * @param search 按照空格查询
     * @return PlatResult
     */
    @RequestMapping("/queryTabById")
    public Object singleInputSelect(String search, String rowId) {
        if (UtilsTool.isValid(rowId)) {
            ServerResult serverResult = dbTableColumnService.queryTableById(rowId, search);
            return super.result(new ServerResult<>(serverResult));
        }
        return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL));
    }


    /**
     * 通用删除方法
     *
     * @param rowId 按照rowId查询
     * @return serviceResult
     */
    @RequestMapping("/delete")
    public Object delete(String rowId) {
        if (UtilsTool.isValid(rowId)) {
            List<BusinessObjectPro> relateTableColumn = businessObjectProService.select(new FieldCondition("relateTableColumn", Operator.EQUAL, rowId));
            if (relateTableColumn.size() == 0) {
                DBTableColumn dbTableColumn = new DBTableColumn().buildDeleteInfo();
                int del = dbTableColumn.deleteById(rowId);
                if (del != -1) {
                    return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS));
                } else {
                    return super.result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL));
                }
            } else {
                return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE));
            }
        } else {
            return result(new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.PRIMARY_KEY_CANNOT_BE_EMPTY));
        }
    }

}