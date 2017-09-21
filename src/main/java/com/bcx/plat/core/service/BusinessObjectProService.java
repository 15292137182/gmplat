package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.ServletUtils;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务对象
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectProService extends BaseService<BusinessObjectPro> {

  @Autowired
  private DBTableColumnService dbTableColumnService;

  /**
   * 根据业务对象属性rowId查询当前数据
   *
   * @param rowId 唯一标识
   * @return PlatResult
   */
  public ServerResult queryById(String rowId) {
    List<BusinessObjectPro> businessObjectPros = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
    if (businessObjectPros == null || businessObjectPros.size() != 1) {
      ServerResult serverResult = new ServerResult();
      return serverResult.setStateMessage(BaseConstants.STATUS_FAIL, ServletUtils.getMessage(Message.QUERY_FAIL));
    }
    BusinessObjectPro businessObjectPro = businessObjectPros.get(0);
    String relateTableColumn = businessObjectPro.getRelateTableColumn();
    Map<String,Object> businessObjectProMap = businessObjectPro.toMap();
    List<DBTableColumn> dbTableColumns = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableColumn));
    if (null != dbTableColumns && !dbTableColumns.isEmpty()) {
      businessObjectProMap.put("columnCname", dbTableColumns.get(0).getColumnCname());
    }
    return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, new Object[]{businessObjectProMap});
  }

  /**
   * 供前端功能块属性使用
   * 根据业务对象rowId查询当前业务对象下的所有属性
   *
   * @param objRowId   业务对象唯一标识
   * @param frontRowId 功能块唯一标识
   * @return ServerResult
   */
  public ServerResult queryBusinPro(String objRowId, String frontRowId) {
    //通过业务对象查找到对应的模板的rowId
    String templateRowId;
    List<Map<String, Object>> execu;
    List<Map<String, Object>> results = new ArrayList<>();
    // 根据业务对象rowId查询出和业务对象属性关联的属性
    // 这里的业务对象的属性是基本属性
    //过滤参数
    Field field = super.columnByAlias(BusinessObjectPro.class, "objRowId");
    //过滤参数
    Condition condition = new And(new FieldCondition(field, Operator.EQUAL, objRowId));
    //查询条件
    List<Map<String, Object>> result =
            leftAssociationQuery(BusinessObject.class, BusinessObjectPro.class, "rowId", "objRowId", condition);
    // 遍历添加数据到results
    for (Map<String, Object> res : result) {
      res.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_BASE);
      results.add(res);
    }
    // 根据业务对象rowId来查询关联表数据,获取到关联业务对象的模板对象的rowId
    Field byAlias = super.columnByAlias(BusinessRelateTemplate.class, "businessRowId");
    //过滤条件
    FieldCondition businessRowId = new FieldCondition(byAlias, Operator.EQUAL, objRowId);
    List<Map<String, Object>> execute = leftAssociationQuery(BusinessObject.class, BusinessRelateTemplate.class,
            "rowId", "businessRowId", businessRowId);
    // 将返回的结果下划线转为驼峰
    List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(execute);
    // 遍历关联表中的结果,获取关联的模板对象的rowId
    for (Map<String, Object> ex : list) {
      //通过业务对象查找到对应的模板对象的rowId
      templateRowId = ex.get("templateRowId").toString();
      // 通过获取到的模板对象的rowId来查询出模板对象关联的模板对象属性的数据
      Field templateObjRowId = super.columnByAlias(TemplateObjectPro.class, "templateObjRowId");
      FieldCondition fieldCondition = new FieldCondition(templateObjRowId, Operator.EQUAL, templateRowId);
      execu = super.leftAssociationQuery(TemplateObject.class, TemplateObjectPro.class, "rowId", "templateObjRowId", fieldCondition);
      List<Map<String, Object>> exe = UtilsTool.underlineKeyMapListToCamel(execu);
      // 将查询出来的模板对象属性的数据,遍历获取数据,拿到代码和名称,将结果塞到results结果里面
      for (Map<String, Object> es : exe) {
        Map<String, Object> map = new HashMap<>();
        map.put("propertyCode", es.get("code").toString());
        map.put("propertyName", es.get("cname").toString());
        map.put("rowId", es.get("proRowId").toString());
        map.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_MODULE);
        results.add(map);
      }
    }
//        for (Map<String, Object> res : results) {
//            String obj = res.get("objRowId").toString();
//            Condition buildDone = new ConditionBuilder(FrontFuncPro.class).and().equal("relateBusiPro", obj).endAnd().buildDone();
//            List<Map<String, Object>> relateBusiPro = singleSelect(FrontFuncPro.class, buildDone);
//            if (!UtilsTool.isValid(relateBusiPro)) {
//                continue;
//            }
//            for (Map<String, Object> relate : relateBusiPro) {
//                if (relate.get("funcRowId").equals(frontRowId)) {
//                    res.remove(res);
//                }
//            }
//        }
    FieldCondition funcRowId = new FieldCondition("funcRowId", Operator.EQUAL, frontRowId);
    List<Map<String, Object>> relateBusiPro = super.singleSelect(FrontFuncPro.class, funcRowId);
    for (Map<String, Object> relate : relateBusiPro) {
      if (relate.get("relateBusiPro").equals(objRowId)) {
        for (int i = 0; i < results.size(); i++) {
          String relateBusiPro1;
          try {
            relateBusiPro1 = results.get(i).get("objRowId").toString();
          } catch (NullPointerException e) {
            continue;
          }
          if ((!UtilsTool.isValid(relateBusiPro1))) {
            continue;
          }
          if (relateBusiPro1.equals(objRowId)) {
            results.remove(i);
          }
        }
      }
    }
    if (result.size() == 0) {
      return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
    }
    return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, UtilsTool.underlineKeyMapListToCamel(results));
  }


}