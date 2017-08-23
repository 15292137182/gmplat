package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseControllerTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.service.DBTableColumnService;
import com.bcx.plat.core.service.MaintDBTablesService;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据库表信息Controller层
 * Created by Wen Tiehu on 2017/8/11.
 */
@RestController
@RequestMapping("/core/maintTable")
public class MaintDBTablesController extends BaseControllerTemplate<MaintDBTablesService,MaintDBTables>{

    private final BusinessObjectService businessObjectService;
    private final DBTableColumnService dbTableColumnService;

    @Autowired
    public MaintDBTablesController(BusinessObjectService businessObjectService, DBTableColumnService dbTableColumnService) {
        this.businessObjectService = businessObjectService;
        this.dbTableColumnService = dbTableColumnService;
    }

    @Override
    protected List<String> blankSelectFields() {
        return Arrays.asList("tableSchema","tableEname","tableCname");
    }

    /**
     * 通用删除方法
     *
     * @param rowId   按照rowId查询
     * @param request request请求
     * @param locale  国际化参数
     * @return
     */
    @RequestMapping("/delete")
    @Override
    public Object delete(String rowId, HttpServletRequest request, Locale locale) {
        Map<String, Object> map = new HashMap<>();
        map.put("relateTableRowId", rowId);
        List<Map<String, Object>> tableRowId = businessObjectService.select(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
        if (tableRowId.size() == 0) {
            List<Map<String, Object>> list = dbTableColumnService
                    .select(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
            if (UtilsTool.isValid(list)) {
                List<String> rowIds = list.stream().map((row) -> {
                    return (String) row.get("rowId");
                }).collect(Collectors.toList());
                dbTableColumnService.delete(new FieldCondition("rowId", Operator.IN, rowIds));
            }
            return super.delete(rowId, request, locale);
        }
        return super.result(request, ServiceResult.Msg(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE), locale);
    }
}
