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
import java.util.*;

/**
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
     * @param rowId 唯一标识
     * @return ServiceResult
     */
    public PlatResult queryById(String rowId) {

        List<Map<String, Object>> result = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
        String relateTableColumn = (String) result.get(0).get("relateTableColumn");

        List<Map<String, Object>> rowId1 =
                dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableColumn));
//        if (rowId1.size() == 0) {
//            logger.warn("根据业务对象属性rowId查询当前数据失败");
//            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
//        }
        try {
            rowId1.get(0).get("columnCname");
        } catch (Exception e) {
            return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result);
        }
        for (Map<String, Object> row : result) {
            row.put("columnCname", rowId1.get(0).get("columnCname"));
        }
        return new PlatResult(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, result);
    }

    /**
     * 供前端功能块属性使用
     * 根据业务对象rowId查询当前业务对象下的所有属性
     *
     * @param objRowId
     * @return
     */
    public PlatResult queryBusinPro(String objRowId) {
        //通过业务对象查找到对应的模板的rowId
        String templateRowId;
        List<Map<String, Object>> execu;
        List<Map<String, Object>> results = new ArrayList<>();
        /*
            根据业务对象rowId查询出和业务对象属性关联的属性
            这里的业务对象的属性是基本属性
         */
        List<Map<String, Object>> result = moreBatis.select(entityClass)
                .from(new JoinTable(moreBatis.getTable(BusinessObject.class), JoinType.LEFT_JOIN, moreBatis.getTable(BusinessObjectPro.class))
                        .on(new FieldCondition(
                                moreBatis.getColumnByAlies(BusinessObject.class, "rowId"),
                                Operator.EQUAL,
                                moreBatis.getColumnByAlies(BusinessObjectPro.class, "objRowId"))))
                .where(new FieldCondition(moreBatis.getColumnByAlies(BusinessObjectPro.class, "objRowId"), Operator.EQUAL, objRowId)).execute();
        /*
            遍历添加数据到results
         */
        for (Map<String, Object> res : result) {
            res.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_BASE);
            results.add(res);
        }
        /*
            根据业务对象rowId来查询关联表数据,获取到关联业务对象的模板对象的rowId
         */
        List<Map<String, Object>> execute = moreBatis.select(entityClass)
                .from(new JoinTable(moreBatis.getTable(BusinessObject.class), JoinType.LEFT_JOIN, moreBatis.getTable(BusinessRelateTemplate.class))
                        .on(new FieldCondition(
                                moreBatis.getColumnByAlies(BusinessObject.class, "rowId"),
                                Operator.EQUAL,
                                moreBatis.getColumnByAlies(BusinessRelateTemplate.class, "businessRowId"))))
                .where(new FieldCondition(moreBatis.getColumnByAlies(BusinessRelateTemplate.class, "businessRowId"), Operator.EQUAL, objRowId)).execute();
        /*
            将返回的结果下划线转为驼峰
         */
        List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(execute);
        /*
        遍历关联表中的结果,获取关联的模板对象的rowId
         */
        for (Map<String, Object> ex : list) {
            //通过业务对象查找到对应的模板对象的rowId
            templateRowId = ex.get("templateRowId").toString();
            /*
            通过获取到的模板对象的rowId来查询出模板对象关联的模板对象属性的数据
             */
            execu = moreBatis.select(entityClass)
                    .from(new JoinTable(moreBatis.getTable(TemplateObject.class), JoinType.LEFT_JOIN, moreBatis.getTable(TemplateObjectPro.class))
                            .on(new FieldCondition(
                                    moreBatis.getColumnByAlies(TemplateObject.class, "rowId"),
                                    Operator.EQUAL,
                                    moreBatis.getColumnByAlies(TemplateObjectPro.class, "templateObjRowId"))))
                    .where(new FieldCondition(moreBatis.getColumnByAlies(TemplateObjectPro.class, "templateObjRowId"), Operator.EQUAL, templateRowId)).execute();
            List<Map<String, Object>> exe = UtilsTool.underlineKeyMapListToCamel(execu);
            /*
            将查询出来的模板对象属性的数据,遍历获取数据,拿到代码和名称,将结果塞到results结果里面
             */
            for (Map<String, Object> es : exe) {
                Map<String, Object> map = new HashMap<>();
                map.put("propertyCode", es.get("code").toString());
                map.put("propertyName", es.get("cname").toString());
                map.put("rowId", es.get("proRowId").toString());
                map.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_MODULE);
                results.add(map);
            }
        }
        if (result.size() == 0) {
            logger.warn("根据业务对象rowId查询当前业务对象下的所有属性_失败");
            return PlatResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
        }
        return new PlatResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, UtilsTool.underlineKeyMapListToCamel(results));
    }


}