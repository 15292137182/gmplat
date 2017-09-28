package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.BusinessRelateTemplate;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.SequenceRuleConfig;
import com.bcx.plat.core.entity.TemplateObject;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
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
  @Autowired
  private SequenceRuleConfigService sequenceRuleConfigService;
  @Autowired
  private FrontFuncProService frontFuncProService;



  /**
   * 新增业务对象属性
   *
   * @param paramEntity 接受实体参数
   * @return Map
   */
  public ServerResult insertBusinessPro(Map<String, Object> paramEntity) {
    String fieldAlias = String.valueOf(paramEntity.get("fieldAlias")).trim();
    paramEntity.remove("fieldAlias");
    paramEntity.put("fieldAlias", fieldAlias);
    ServerResult result = new ServerResult();
    if (!"".equals(fieldAlias)) {
      Condition condition = new ConditionBuilder(BusinessObjectPro.class).and().equal("fieldAlias", fieldAlias).endAnd().buildDone();
      List<BusinessObjectPro> select = select(condition);
      if (select.size() == 0) {
        BusinessObjectPro businessObjectPro = new BusinessObjectPro().buildCreateInfo().fromMap(paramEntity);
        int insert = businessObjectPro.insert();
        if (insert != -1) {
          return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS, businessObjectPro);
        } else {
          return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL);
        }
      } else {
        return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 编辑业务对象属性
   *
   * @param paramEntity 实体参数
   * @return Map
   */
  public ServerResult updateBusinessPro(Map<String, Object> paramEntity) {
    ServerResult result = new ServerResult();
    BusinessObjectPro businessObjectPro;
    String fieldAlias = String.valueOf(paramEntity.get("fieldAlias")).trim();
    paramEntity.put("fieldAlias", fieldAlias);
    if (!"".equals(fieldAlias)) {
      Condition condition = new ConditionBuilder(BusinessObjectPro.class).and().equal("fieldAlias", fieldAlias).endAnd().buildDone();
      List<BusinessObjectPro> select = select(condition);
      if (select.size() == 0 || select.get(0).getRowId().equals(paramEntity.get("rowId"))) {
        businessObjectPro = new BusinessObjectPro().buildModifyInfo().fromMap(paramEntity);
        businessObjectPro.updateById();
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS, businessObjectPro);
      } else {
        return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_EMPTY);
    }
  }

  /**
   * 业务对象属性删除方法
   *
   * @param rowId 按照rowId查询
   * @return ServerResult
   */
  public ServerResult deleteBusinessPro(String rowId){
    //通过rowId查询数据
    Condition condition = new ConditionBuilder(BusinessObjectPro.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<Map> businessObjectPros = selectMap(condition);
    ServerResult result = new ServerResult();
    List<Map> frontFuncPros = frontFuncProService.selectMap(new FieldCondition("relateBusiPro", Operator.EQUAL, rowId));
    int del;
    if (frontFuncPros.size() == 0) {
      BusinessObjectPro businessObjectPro = new BusinessObjectPro();
      del = businessObjectPro.deleteById(rowId);
      if (del != -1) {
        //接受rowId返回业务对象数据
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS, businessObjectPros);
      } else {
        return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL);
      }
    } else {
      return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);
    }
  }



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
    Map<String, Object> businessObjectProMap = businessObjectPro.toMap();
    if (relateTableColumn == null) {
      return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, businessObjectProMap);
    }
    List<Map> mapList = dbTableColumnService.selectMap(new FieldCondition("rowId", Operator.EQUAL, relateTableColumn));
    if (null != mapList) {
      businessObjectProMap.put("columnCname", mapList.get(0).get("columnCname"));
    }
    return new ServerResult<>( businessObjectProMap);
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


  /**
   * 获取来源值类型来源为序列
   *
   * @param objRowId 接受业务对象rowId
   * @return
   */
  public Map obtainSequenceCode(String objRowId) {
    Condition condition = new ConditionBuilder(BusinessObjectPro.class).and().equal("objRowId", objRowId).endAnd().buildDone();
    List<BusinessObjectPro> maps = select(condition);
    Map<String, Object> map = new HashMap<>();
    for (BusinessObjectPro m : maps) {
      String valueResourceType = String.valueOf(m.getValueResourceType());
      if ("sequenceRule".equals(valueResourceType)) {
        String valueResourceContent = String.valueOf(m.getValueResourceContent());
        Condition done = new ConditionBuilder(SequenceRuleConfig.class).and().equal("rowId", valueResourceContent).endAnd().buildDone();
        List<SequenceRuleConfig> select = sequenceRuleConfigService.select(done);
        if (select.size() > 0) {
          SequenceRuleConfig sequenceRuleConfig = select.get(0);
          String seqCode = sequenceRuleConfig.getSeqCode();
          String seqContent = sequenceRuleConfig.getSeqContent();
          map.put("seqCode", seqCode);
          map.put("seqContent", seqContent);
        }
        String relateTable = String.valueOf(m.getRelateTableColumn());
        Condition buildDone = new ConditionBuilder(DBTableColumn.class).and().equal("rowId", relateTable).endAnd().buildDone();
        List<DBTableColumn> dbTableColumns = dbTableColumnService.select(buildDone);
        if (dbTableColumns.size() > 0) {
          DBTableColumn dbTableColumn = dbTableColumns.get(0);
          String columnEname = dbTableColumn.getColumnEname();
          String toCamel = UtilsTool.underlineToCamel(columnEname, false);
          map.put("columnEname", toCamel);
        }
      }
    }
    return map;
  }

  public Map queryByIds(String rowId) {
    List<BusinessObjectPro> businessObjectPros = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
    BusinessObjectPro businessObjectPro = businessObjectPros.get(0);
    Map<String, Object> businessObjectProMap = businessObjectPro.toMap();
    return businessObjectProMap;
  }


}