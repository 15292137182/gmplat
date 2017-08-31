package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.common.BaseServiceTemplate;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.PlatResult;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectProService extends BaseServiceTemplate<BusinessObjectPro> {

    @Override
    public boolean isRemoveBlank() {
        return false;
    }
    @Autowired
    DBTableColumnService dbTableColumnService;
    @Autowired
    private MoreBatis moreBatis;

    /**
     * 根据业务对象属性rowId查询当前数据
     *
     * @param rowId     唯一标识
     * @return ServiceResult
     */
    public PlatResult queryById(String rowId) {

        List<Map<String, Object>> result = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        String relateTableColumn = (String)result.get(0).get("relateTableColumn");

        List<Map<String, Object>> rowId1 =
                dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableColumn));
        if (rowId1.size()==0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL);
        }
        for (Map<String ,Object> row :result){
            row.put("columnCname",rowId1.get(0).get("columnCname"));
        }
        return new  PlatResult(BaseConstants.STATUS_SUCCESS,Message.QUERY_SUCCESS,result);
    }

    /**
     *
     * @param objRowId
     * @return
     */
    public PlatResult queryBusinPro( String objRowId) {
        String templateRowId;

        List<Map<String, Object>> result = moreBatis.selectStatement()
                .select(QueryAction.ALL_FIELD)
                .from(new JoinTable(moreBatis.getTable(BusinessObject.class), JoinType.LEFT_JOIN,moreBatis.getTable(BusinessObjectPro.class))
                        .on(new FieldCondition(
                                moreBatis.getColumnByAlies(BusinessObject.class,"rowId"),
                                Operator.EQUAL,
                                moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"))))
                .where(new FieldCondition(moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"),Operator.EQUAL,objRowId)).execute();

        List<Map<String, Object>> execute = moreBatis.selectStatement()
                .select(QueryAction.ALL_FIELD)
                .from(new JoinTable(moreBatis.getTable(BusinessObject.class), JoinType.LEFT_JOIN, moreBatis.getTable(BusinessRelateTemplate.class))
                        .on(new FieldCondition(
                                moreBatis.getColumnByAlies(BusinessObject.class, "rowId"),
                                Operator.EQUAL,
                                moreBatis.getColumnByAlies(BusinessRelateTemplate.class, "businessRowId"))))
                .where(new FieldCondition(moreBatis.getColumnByAlies(BusinessRelateTemplate.class, "businessRowId"), Operator.EQUAL, objRowId)).execute();
        List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(execute);
        for (Map<String,Object> ex :list){
            templateRowId = ex.get("templateRowId").toString();

            List<Map<String, Object>> execu = moreBatis.selectStatement()
                    .select(QueryAction.ALL_FIELD)
                    .from(new JoinTable(moreBatis.getTable(TemplateObject.class), JoinType.LEFT_JOIN, moreBatis.getTable(TemplateObjectPro.class))
                            .on(new FieldCondition(
                                    moreBatis.getColumnByAlies(TemplateObject.class, "rowId"),
                                    Operator.EQUAL,
                                    moreBatis.getColumnByAlies(TemplateObjectPro.class, "templateObjRowId"))))
                    .where(new FieldCondition(moreBatis.getColumnByAlies(TemplateObjectPro.class, "templateObjRowId"), Operator.EQUAL, templateRowId)).execute();
        }
        if (result.size() == 0) {
            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        return new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS,UtilsTool.underlineKeyMapListToCamel(result));
    }



}