package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.ServletUtils;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.constants.Message.NEW_ADD_FAIL;
import static com.bcx.plat.core.constants.Message.NEW_ADD_SUCCESS;
import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 业务对象业务层
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class BusinessObjectService extends BaseService<BusinessObject> {


  @Autowired
  private MaintDBTablesService maintDBTablesService;
  @Autowired
  private BusinessObjectProService businessObjectProService;
  @Autowired
  private DBTableColumnService dbTableColumnService;
  @Autowired
  private FrontFuncService frontFuncService;
  @Autowired
  private FrontFuncProService frontFuncProService;
  @Autowired
  private BusinessRelateTemplateService businessRelateTemplateService;


  /**
   * 新增业务对象
   *
   * @param param 业务对象实体
   * @return ServerResult
   */
  public ServerResult addBusiness(Map<String, Object> param) {
    //新增业务对象数据
    BusinessObject businessObject = new BusinessObject().fromMap(param).buildCreateInfo();
    //实例化业务对象关联模板对象
    BusinessRelateTemplate brt = new BusinessRelateTemplate();
    Map<String, Object> map = businessObject.toMap();
    int insert = businessObject.insert();
    String rowId = businessObject.getRowId();
    String relateTemplateObject = businessObject.getRelateTemplateObject();
    List list = UtilsTool.jsonToObj(relateTemplateObject, List.class);
    if (null != list) {
      for (Object li : list) {
        brt.setBusinessRowId(rowId);
        brt.setTemplateRowId(li.toString());
        //将业务对象业务对新增数据添加到关联表中
        new BusinessRelateTemplate().buildCreateInfo().fromMap(brt.toMap()).insert();
      }
    }
    if (insert != 1) {
      return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, NEW_ADD_FAIL);
    } else {
      return new ServerResult<>(BaseConstants.STATUS_SUCCESS, NEW_ADD_SUCCESS, businessObject);
    }
  }


  /**
   * 根据rowId查询数据
   *
   * @param rowId 唯一标示
   * @return ServerResult
   */
  public ServerResult queryById(String rowId) {
    List<BusinessObject> result = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
//        String relateTableRowId =String.valueOf(result.get(0).get("relateTableRowId"));
    String relateTableRowId = result.get(0).getRelateTableRowId();
    List<Map> rowId1 =
        maintDBTablesService.selectMap(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
    if (isValid(rowId1)) {
//            for (BusinessObject row : result) {
//                row.put("tableCname", rowId1.get(0).get("tableCname"));
//            }
      return new ServerResult<>(BaseConstants.STATUS_SUCCESS, ServletUtils.getMessage(Message.QUERY_SUCCESS), result);
    } else {
      return null;//  ServerResult.Msg(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
    }
  }

  /**
   * 分页显示查询
   *
   * @param condition 搜索条件
   * @param pageNum   页码
   * @param pageSize  一页显示条数
   * @param order     排序
   * @return ServerResult
   */
  public ServerResult queryPage(Condition condition, Integer pageNum, Integer pageSize, List<Order> order) {
    PageResult<Map<String, Object>> result;

    if (isValid(pageNum)) { // 有分页参数进行分页查询
      result = selectPageMap(condition, order, pageNum, pageSize);
    } else { // 没有分页参数则查询全部
      result = new PageResult(selectMap(condition, order));
    }

    if (result.getResult().size() == 0) {
      return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
    } else {
      for (Map<String, Object> rest : result.getResult()) {
        rest.put("disableButton", false);//前端页面删除,编辑,禁用按钮
      }
      return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, queryResultProcess(result));
    }
  }

  /**
   * 数据封装
   *
   * @param result 接受参数
   * @return PageResult
   */
  private PageResult<Map<String, Object>> queryResultProcess(PageResult<Map<String, Object>> result) {
    result.setResult(queryResultProcessAction(result.getResult()));
    return result;
  }

  /**
   * @param result 返回值
   * @return 返回值
   */
  private List<Map<String, Object>> queryResultProcessAction(List<Map<String, Object>> result) {
    List<String> rowIds = result.stream().map((row) ->
        (String) row.get("relateTableRowId")).collect(Collectors.toList());
    Condition condition = new ConditionBuilder(MaintDBTables.class).and().in("rowId", rowIds).endAnd().buildDone();
    List<Map> results = maintDBTablesService.selectMap(condition);
    HashMap<String, Object> map = new HashMap<>();
    for (Map<String, Object> row : results) {
      map.put((String) row.get("rowId"), row.get("tableCname"));
    }
    for (Map<String, Object> row : result) {
      row.put("associatTable", map.get(row.get("relateTableRowId")));
    }
    return result;
  }

  /**
   * 根据业务对象rowId查找当前对象下的所有属性并分页显示
   *
   * @param search   搜索条件
   * @param rowId    唯一标识
   * @param pageNum  页码
   * @param pageSize 一页显示条数
   * @param order    排序
   * @return ServerResult
   */
  public ServerResult queryProPage(String search, String param, String rowId, Integer pageNum, Integer pageSize, List<Order> order) {
    //查询属性的搜索条件
    Or blankQuery = !isValid(search) ? null : UtilsTool.createBlankQuery(Arrays.asList("propertyCode", "propertyName", "valueType"), UtilsTool.collectToSet(search));
    Condition condition;
    if (isValid(param)) {
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = new And(new FieldCondition("objRowId", Operator.EQUAL, rowId),
          UtilsTool.convertMapToAndConditionSeparatedByLike(BusinessObjectPro.class, map));
    } else {
      if (isValid(search)) {
        condition = new ConditionBuilder(BusinessObjectPro.class)
            .and().equal("objRowId", rowId)
            .or().addCondition(blankQuery).endOr()
            .endAnd().buildDone();
      } else {
        condition = new ConditionBuilder(BusinessObjectPro.class).and().equal("objRowId", rowId).endAnd().buildDone();
      }
    }

    PageResult<Map<String, Object>> result;
    if (isValid(pageNum)) { // 有分页参数进行分页查询
      result = businessObjectProService.selectPageMap(condition, order, pageNum, pageSize);
    } else { // 没有分页参数则查询全部
      result = new PageResult(businessObjectProService.selectMap(condition, order));
    }
    if (result.getResult().size() == 0) {
      return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
    } else {
      PageResult<Map<String, Object>> pageResult = queryProPage(result);
      return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, pageResult);
    }
  }

  /**
   * 根据业务对象rowId查找当前对象下的所有属性
   *
   * @param result 接受参数
   * @return PageResult
   */
  private PageResult<Map<String, Object>> queryProPage(PageResult<Map<String, Object>> result) {
    Map<String, Object> map = new HashMap<>();
    for (Map<String, Object> rest : result.getResult()) {
      String relateTableColumn = (String) rest.get("relateTableColumn");
      List<DBTableColumn> dbTableColumns = dbTableColumnService.select(new ConditionBuilder(DBTableColumn.class)
          .and()
          .equal("rowId", relateTableColumn).endAnd().buildDone());
      for (DBTableColumn aMapList : dbTableColumns) {
        map.put(aMapList.getRowId(), aMapList.getColumnCname());
      }
    }
    for (Map<String, Object> rest : result.getResult()) {
      String fieldAlias = String.valueOf(rest.get("fieldAlias"));
      if (fieldAlias.equals("null")) {
        String relateTableColumn = String.valueOf(rest.get("relateTableColumn"));
        Condition condition = new ConditionBuilder(DBTableColumn.class).and().equal("rowId", relateTableColumn).endAnd().buildDone();
        List<Map<String, Object>> singleSelect = singleSelect(DBTableColumn.class, condition);
        if (null!=singleSelect) {
          fieldAlias = String.valueOf(singleSelect.get(0).get("columnEname"));
        }
      }
      rest.put("ename",fieldAlias);
      rest.put("disableButton", false);
      rest.put("columnCname", map.get(rest.get("relateTableColumn")));
    }
    return result;
  }

  /**
   * 根据rowId来执行变更操作
   *
   * @param rowId 唯一标示
   * @return ServerResult
   */
  public ServerResult changeOperat(String rowId) {
    Map<String, Object> oldRowId = new HashMap<>();
    List<BusinessObject> businessObjects = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
    List<String> row = businessObjects.stream().map((rowIds) ->
        rowIds.getChangeOperat()).collect(Collectors.toList());
    if (isValid(rowId) && row.get(0).equals(BaseConstants.CHANGE_OPERAT_FAIL)) {
      List<BusinessObject> businessObjectList = select(new FieldCondition("rowId", Operator.EQUAL, rowId));
      BusinessObject objectMap = businessObjectList.get(0);
      //将Map数据转换为json结构的数据
      String toJson = UtilsTool.objToJson(objectMap);
      //将json数据转换为实体类
      BusinessObject businessObject = UtilsTool.jsonToObj(toJson, BusinessObject.class);
      //复制一份数据出来
      assert businessObject != null;
      businessObject.setChangeOperat(BaseConstants.CHANGE_OPERAT_FAIL);
      businessObject.buildCreateInfo();
      //获取当前版本号
      String version = businessObjectList.get(0).getBaseTemplateBean().getVersion();
      //转换为double类型
      double dou = new Double(version);
      //++当前版本号
      String str = ++dou + "";
      // TODO 设置版本号
      businessObject.getBaseTemplateBean().setVersion(str);
      String objectCode = businessObjectList.get(0).getObjectCode();
      businessObject.setObjectCode(objectCode);
      businessObject.insert();
//            insert(businessObject.toMap());

      oldRowId.put("rowId", rowId);
      oldRowId.put("status", BaseConstants.INVALID);
      oldRowId.put("changeOperat", BaseConstants.CHANGE_OPERAT_SUCCESS);

//            update(oldRowId);
      return new ServerResult().setStateMessage(BaseConstants.STATUS_SUCCESS, Message.UPDATE_SUCCESS);
    } else {
      return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.UPDATE_FAIL);
    }

  }

  /**
   * 根据rowId删除业务对象数据
   *
   * @param rowId 唯一标示
   * @return ServerResult
   */
  public ServerResult delete(String rowId) {
    //返回删除当前数据
    Condition buildDone = new ConditionBuilder(BusinessObject.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<BusinessObject> select = select(buildDone);

    List<FrontFunc> businObj = frontFuncService.select(new FieldCondition("relateBusiObj", Operator.EQUAL, rowId));
    if (businObj.size() > 0) {
      for (FrontFunc busin : businObj) {
        String rowId1 = busin.getRowId();
        List<FrontFuncPro> funcRowId = frontFuncProService.select(new FieldCondition("funcRowId", Operator.EQUAL, rowId1));
        if (funcRowId.size() != 0) {
          return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);
        }
      }
    } else if (businObj.size() == 0) {
      List<BusinessObjectPro> list = businessObjectProService
          .select(new FieldCondition("objRowId", Operator.EQUAL, rowId));
      if (isValid(list)) {
        List<String> rowIds = list.stream().map((row) ->
            (String) row.getRowId()).collect(Collectors.toList());
        businessObjectProService.delete(new FieldCondition("rowId", Operator.IN, rowIds));

      } else if (rowId != null && rowId.length() > 0) {
        Condition condition = new ConditionBuilder(BusinessObject.class).and().equal("rowId", rowId).endAnd().buildDone();
        delete(condition);
        return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS, select);
      }
    }
    return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);

  }

  /**
   * 根据业务对象rowId查询出对应的模板记录
   *
   * @param rowId  唯一标识
   * @param orders 排序
   * @return platResult
   */
  public ServerResult<List<Map<String, Object>>> queryTemplatePro(String rowId, LinkedList<Order> orders) {
    List<Map<String, Object>> linkedList = new ArrayList<>();
    List<BusinessRelateTemplate> businessRowId = businessRelateTemplateService.select(new FieldCondition("businessRowId", Operator.EQUAL, rowId),orders);
    for (BusinessRelateTemplate bri : businessRowId) {
      String templateRowId = bri.getTemplateRowId();
      List<Map<String, Object>> result = singleSelect(TemplateObjectPro.class, new FieldCondition("templateObjRowId", Operator.EQUAL, templateRowId));

      List<Map<String, Object>> list = UtilsTool.underlineKeyMapListToCamel(result);
      for (Map<String, Object> li : list) {
        if (result.size() != 0) {
          linkedList.add(li);
        }
      }
    }
    return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, linkedList);
  }

  /**
   * 根据rowId 查询最新的有效的对象信息
   *
   * @param rowId 主键
   * @return 返回查询的结果
   * <p>
   * Create by hcl at 2017--09-19
   */
  public BusinessObject getLatestEffectiveObj(String rowId) {
    // TODO 后续有改变请完善改方法
    if (isValid(rowId)) {
      return new BusinessObject().selectOneById(rowId);
    }
    return null;
  }

  /**
   * 根据业务对象的rowId查询其属性信息
   *
   * @param objRowId 对象主键
   * @return 返回查询结果合集
   * Create by hcl at 2017--09-19
   */
  public List<BusinessObjectPro> getObjProperties(String objRowId) {
    List<BusinessObjectPro> result = new ArrayList<>();
    if (isValid(objRowId)) {
      Condition condition = new ConditionBuilder(BusinessObjectPro.class)
          .and()
          .equal("objRowId", objRowId)
          .endAnd().buildDone();
      return businessObjectProService.select(condition);
    }
    return result;
  }
}