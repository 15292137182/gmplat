package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 功能块业务层
 * Created by Wen Tiehu on 2017/8/9.
 */
@Service
public class FrontFuncService extends BaseServiceTemplate<FrontFunc> {

    private final MoreBatis moreBatis;
    private final TemplateObjectProService templateObjectProService;
    private final BusinessObjectProService businessObjectProService;
    private final DBTableColumnService dbTableColumnService;

    @Autowired
    public FrontFuncService(MoreBatis moreBatis, BusinessObjectProService businessObjectProService, TemplateObjectProService templateObjectProService, DBTableColumnService dbTableColumnService) {
        this.moreBatis = moreBatis;
        this.businessObjectProService = businessObjectProService;
        this.templateObjectProService = templateObjectProService;
        this.dbTableColumnService = dbTableColumnService;
    }

    @Override
    public boolean isRemoveBlank() {
        return false;
    }


    /**
     * 根据前端功能块的代码查询功能块属性的数据
     *
     * @param funcCode 代码
     * @return PlatResult
     */
    public PlatResult<LinkedList<Map<String, Object>>> queryFuncCode(List funcCode) {
        LinkedList<Map<String, Object>> linkedList = new LinkedList<>();
        List<Map<String, Object>> result = null;
        for (Object key : funcCode) {
            List<Map<String, Object>> keysetCode = moreBatis.select(FrontFunc.class)
                    .where(new FieldCondition("funcCode", Operator.EQUAL, key)).execute();
            List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(keysetCode);
            LinkedList<Order> orders = UtilsTool.dataSort("{\"str\":\"displayTitle\", \"num\":1}");//默认按照显示标题排序
            for (Map<String, Object> keySet : list) {
                result = moreBatis.select(FrontFuncPro.class)
                        .where(new FieldCondition("funcRowId", Operator.EQUAL, keySet.get("rowId").toString())).orderBy(orders)
                        .execute();
                for (Map<String, Object> map : result) {
                    map.put("funcType", keySet.get("funcType").toString());
                    String relateBusiPro = map.get("relateBusiPro").toString();
                    String attrSource = map.get("attrSource").toString();
                    switch (attrSource) {
                        case BaseConstants.ATTRIBUTE_SOURCE_MODULE:
                            List<Map<String, Object>> proRwoId = templateObjectProService.select(new FieldCondition("proRowId", Operator.EQUAL, relateBusiPro));
                            if (!UtilsTool.isValid(proRwoId)) {
                                continue;
                            }
                            //拿到模板对象属性给功能块属性赋值
                            for (Map<String, Object> pro : proRwoId) {
                                map.put("propertyName", pro.get("cname").toString());
                                map.put("ename", pro.get("ename").toString());
                            }
                            break;
                        case BaseConstants.ATTRIBUTE_SOURCE_BASE:
                            List<Map<String, Object>> relateTableColumn = businessObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
                            if (!UtilsTool.isValid(relateTableColumn)) {
                                continue;
                            }
                            for (Map<String, Object> relate : relateTableColumn) {
                                String relateTableRowId = relate.get("relateTableColumn").toString();
                                List<Map<String, Object>> rowId = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
                                if (!UtilsTool.isValid(rowId)) {
                                    continue;
                                }
                                for (Map<String, Object> row : rowId) {
                                    map.put("ename", row.get("columnEname").toString());
                                }
                            }
                            break;
                        default:
                            return new PlatResult<>(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL, linkedList);
                    }
                }

            }
            linkedList.addAll(UtilsTool.underlineKeyMapListToCamel(result));
        }
        logger.info("查询功能块属性的数据");
        return new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, linkedList);
    }
}
