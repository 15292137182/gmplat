package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;

/**
 * 前端功能块属性项信息维护 service层
 * Created by Wen Tiehu on 2017/8/4.
 */
@Service
public class FrontFuncProService extends BaseService<FrontFuncPro> {

  private final BusinessObjectProService businessObjectProService;
  private final DBTableColumnService dbTableColumnService;
  private final TemplateObjectProService templateObjectProService;

  @Autowired
  public FrontFuncProService(BusinessObjectProService businessObjectProService, DBTableColumnService dbTableColumnService, TemplateObjectProService templateObjectProService) {
    this.businessObjectProService = businessObjectProService;
    this.dbTableColumnService = dbTableColumnService;
    this.templateObjectProService = templateObjectProService;
  }


  /**
   * 前端功能块属性新增数据
   *
   * @param paramEntity 接受一个实体参数
   * @return PlatResult
   */
  public ServerResult addFrontPro(Map<String, Object> paramEntity) {
    String relateBusiPro = String.valueOf(paramEntity.get("relateBusiPro"));//关联对象属性
    List<Map> rowId = selectMap(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
    if (!isValid(rowId)) { // 如果没有关联对象属性，进行新增
      //判断关联对象属性是属于基本属性还是模板属性
      //从模板对象属性表中查询是否存在此属性，如果存在，则attrSource="module"，否则attrSource="base"
      List<Map> templateObjectPros = templateObjectProService.selectMap(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
      if (isValid(templateObjectPros)) {
        paramEntity.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_MODULE);
      } else {
        paramEntity.put("attrSource", BaseConstants.ATTRIBUTE_SOURCE_BASE);
      }
      // 进行新增
      FrontFuncPro frontFuncPro = new FrontFuncPro().buildCreateInfo().fromMap(paramEntity);
      int insert = frontFuncPro.insert();
      if (insert == -1) {
        return fail(Message.NEW_ADD_FAIL);
      } else {
        return successData(Message.NEW_ADD_SUCCESS, frontFuncPro);
      }
    } else {//如果已存在关联对象属性的rowId，则直接返回提示信息
      return fail(Message.DATA_QUOTE);
    }
  }

  /**
   * 根据功能块 rowId 查找当前对象下的所有属性并分页显示
   *
   * @param result 接受ServiceResult
   * @return list
   */
  private List<Map<String, Object>> queryProPage(List<Map<String, Object>> result) {
    //遍历模板对象
    for (Map<String, Object> res : result) {
      String attrSource = res.get("attrSource").toString();
      if (attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_MODULE)) {
        String relateBusiPro = res.get("relateBusiPro").toString();
        List<TemplateObjectPro> proRowId = templateObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
        //拿到模板对象属性给功能块属性赋值
        for (TemplateObjectPro pro : proRowId) {
          res.put("propertyName", pro.getCname());
          res.put("ename", pro.getEname());
        }
      } else if (attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_BASE) || attrSource.equals(BaseConstants.ATTRIBUTE_SOURCE_EXTEND)) {
        String relateBusiPro = res.get("relateBusiPro").toString();
        List<BusinessObjectPro> relateTableColumn = businessObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
        for (BusinessObjectPro relate : relateTableColumn) {
          if (isValid(relate.getFieldAlias())) {
            res.put("ename", relate.getFieldAlias());
            res.put("propertyName", relate.getPropertyName());
          } else {
            String relateTableRowId = relate.getRelateTableColumn();
            List<DBTableColumn> rowId = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
            for (DBTableColumn row : rowId) {
              res.put("ename", row.getColumnEname());
              res.put("propertyName", row.getColumnCname());
            }
          }
        }
      }
    }
    return result;
  }


  /**
   * 根据功能块 rowId 查找当前对象下的所有属性并分页显示
   *
   * @param rowId    唯一标识
   * @param search   按照空格查询
   * @param param    按照指定字段查询
   * @param pageNum  当前第几页
   * @param pageSize 一页显示多少条
   * @param order    排序方式
   * @return ServerResult
   */
  @SuppressWarnings("unchecked")
  public ServerResult queryProPage(String rowId, String search, String param, Integer pageNum, Integer pageSize, String order) {
    LinkedList<Order> orders = UtilsTool.dataSort(FrontFuncPro.class, order);
    Condition condition;
    if (isValid(param)) { // 判断是否按照指定字段查询
      Map map = UtilsTool.jsonToObj(param, Map.class);
      condition = new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
          UtilsTool.convertMapToAndConditionSeparatedByLike(FrontFuncPro.class, map));
    } else {
      if (isValid(search)) {
        condition = new And(new FieldCondition("funcRowId", Operator.EQUAL, rowId),
            UtilsTool.createBlankQuery(Collections.singletonList("displayTitle"), UtilsTool.collectToSet(search)));
      } else {
        condition = new FieldCondition("funcRowId", Operator.EQUAL, rowId);
      }
    }
    PageResult<Map<String, Object>> pageResult;
    if (isValid(pageNum)) { // 判断是否分页查询
      pageResult = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      pageResult = new PageResult(selectMap(condition, orders));
    }
    if (isValid(pageResult)) {
      List<Map<String, Object>> list = queryProPage(pageResult.getResult());
      pageResult.setResult(list);
      return new ServerResult<>(pageResult);
    } else {
      return fail(Message.QUERY_FAIL);

    }
  }


  /**
   * 根据功能块属性rowId查询到业务对象属性下对应的中文名
   *
   * @param rowId 功能块属性rowId
   * @return 业务对象属性rowId
   */
  public String queryBusinessProName(String rowId) {
    Condition condition = new ConditionBuilder(FrontFuncPro.class).and().equal("rowId", rowId).endAnd().buildDone();
    List<FrontFuncPro> frontFuncPros = select(condition);
    if (isValid(frontFuncPros) && frontFuncPros.size() > 0) {
      String relateBusiPro = frontFuncPros.get(0).getRelateBusiPro();
      Condition buildDone = new ConditionBuilder(BusinessObjectPro.class).and().equal("rowId", relateBusiPro).endAnd().buildDone();
      List<BusinessObjectPro> businessObjectPros = businessObjectProService.select(buildDone);
      if (businessObjectPros.size() > 0) {
        return businessObjectPros.get(0).getPropertyName();
      }
    }
    return null;
  }
}
