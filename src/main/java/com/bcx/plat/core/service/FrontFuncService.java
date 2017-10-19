package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.TemplateObjectPro;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.isValid;
import static com.bcx.plat.core.utils.UtilsTool.underlineToCamel;

/**
 * 功能块业务层
 * Created by Wen Tiehu on 2017/8/9.
 */
@Service
public class FrontFuncService extends BaseService<FrontFunc> {

  private final BusinessObjectProService businessObjectProService;
  private final DBTableColumnService dbTableColumnService;
  private final KeySetService keySetService;

  @Autowired
  public FrontFuncService(BusinessObjectProService businessObjectProService, KeySetService keySetService, DBTableColumnService dbTableColumnService) {
    this.businessObjectProService = businessObjectProService;
    this.dbTableColumnService = dbTableColumnService;
    this.keySetService = keySetService;
  }


  /**
   * 前端功能块新增数据
   *
   * @param param 接受一个实体参数
   * @return 返回操作信息
   */
  public ServerResult insertFront(Map<String, Object> param) {
    ServerResult serverResult;
    String funcCode = String.valueOf(param.get("funcCode")).trim();
    param.put("funcCode", funcCode);
    if (isValid(funcCode)) {
      Condition condition = new ConditionBuilder(FrontFunc.class).and()
          .equal("funcCode", funcCode)
          .endAnd().buildDone();
      List<FrontFunc> select = select(condition);
      if (select.size() == 0) {
        FrontFunc frontFunc = new FrontFunc().buildCreateInfo().fromMap(param);
        int insert = frontFunc.insert();
        if (insert != -1) {
          serverResult = successData(Message.NEW_ADD_SUCCESS, frontFunc);
        } else {
          serverResult =  fail(Message.NEW_ADD_FAIL);
        }
      } else {
        serverResult =  fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    }else{
      serverResult =  fail(Message.DATA_CANNOT_BE_EMPTY);
    }
    return  serverResult;
  }

  /**
   * 前端功能块修改数据
   *
   * @param param 接受需要修改的参数
   * @return ServerResult
   */
  public ServerResult updateFront(Map<String, Object> param) {
    ServerResult serverResult;
    int update;
    String funcCode = String.valueOf(param.get("funcCode")).trim();
    param.put("funcCode", funcCode);
    if (isValid(funcCode)) {
      Condition condition = new ConditionBuilder(FrontFunc.class).and()
          .equal("funcCode", funcCode)
          .endAnd().buildDone();
      List<FrontFunc> select = select(condition);
      if (select.size() == 0 || select.get(0).getRowId().equals(param.get("rowId"))) {
        FrontFunc frontFunc = new FrontFunc();
        FrontFunc modify = frontFunc.fromMap(param).buildModifyInfo();
        update = modify.updateById();
        if (update != -1) {
          serverResult = successData(Message.UPDATE_SUCCESS, modify);
        } else {
          serverResult = fail(Message.UPDATE_FAIL);
        }
      } else {
        serverResult = fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    } else {
      serverResult = fail(Message.DATA_CANNOT_BE_EMPTY);
    }
    return serverResult;
  }


  /**
   * 根据前端功能块的代码查询功能块属性的数据
   *
   * @param funcCode 代码
   * @return ServerResult
   */
  public ServerResult queryFuncCode(List funcCode) {
    List<Map<String, Object>> result = null;
    LinkedList<Order> orders = UtilsTool.dataSort("{\"str\":\"sort\", \"num\":1}");//默认按照显示标题排序
    for (Object key : funcCode) {
      FieldCondition fieldCondition = new FieldCondition("funcCode", Operator.EQUAL, key);
      List<Map<String, Object>> frontFunc = singleSelect(FrontFunc.class, fieldCondition);
      if (isValid(frontFunc)) {
        for (Map<String, Object> fronc : frontFunc) {
          //构建查询条件
          Condition condition = new ConditionBuilder(FrontFuncPro.class).and().equal("funcRowId", fronc.get("rowId")).endAnd().buildDone();
          //执行单一查询
          result = singleSelectSort(FrontFuncPro.class, condition, orders);
          result = queryFuncCode(result, fronc);
        }
      } else {
        return fail(Message.QUERY_FAIL);
      }
    }
    return successData(Message.QUERY_SUCCESS, result);
  }

  /**
   * 封装根据前端功能块的代码查询功能块属性的数据
   *
   * @param result 查询功能块属性
   * @param fronc  获取功能块数据
   * @return List 返回查询关联的数据
   */
  private List<Map<String, Object>> queryFuncCode(List<Map<String, Object>> result, Map<String, Object> fronc) {
    for (Map<String, Object> map : result) {
      map.put("funcType", String.valueOf(fronc.get("funcType")));
      String relateBusiPro = String.valueOf(map.get("relateBusiPro"));
      String attrSource = String.valueOf(map.get("attrSource"));
      String customField = String.valueOf(map.get("customField"));
      if (!"null".equals(customField.trim())) {
        map.put("ename", customField);
      } else if (!"null".equals(relateBusiPro.trim())) {
        switch (attrSource) {
          case BaseConstants.ATTRIBUTE_SOURCE_MODULE:
            List<Map> proRowId = new TemplateObjectPro().selectSimpleMap(new FieldCondition("proRowId", Operator.EQUAL, relateBusiPro));
            if (!UtilsTool.isValid(proRowId)) {
              continue;
            }
            //拿到模板对象属性给功能块属性赋值
            for (Map pro : proRowId) {
              String cname = String.valueOf(pro.get("cname"));
              String ename = String.valueOf(pro.get("ename"));
              if (ename != null && ename.contains("_")) {
                ename = underlineToCamel(String.valueOf(pro.get("ename")), false);
              }
              map.put("propertyName", cname);
              map.put("ename", ename);
            }
            break;
          case BaseConstants.ATTRIBUTE_SOURCE_BASE: // 穿透↓
          case BaseConstants.ATTRIBUTE_SOURCE_EXTEND:
            List<BusinessObjectPro> businessObjectPros = businessObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
            if (!UtilsTool.isValid(businessObjectPros)) {
              continue;
            }
            for (BusinessObjectPro relate : businessObjectPros) {
              String keysetCode = "";
              Condition buildDone = new ConditionBuilder(KeySet.class).and().equal("rowId", relate.getValueResourceContent()).endAnd().buildDone();
              List<KeySet> keySets = keySetService.select(buildDone);
              if (keySets != null && keySets.size() > 0) {
                keysetCode = keySets.get(0).getKeysetCode();
              }
              String fieldAlias = relate.getFieldAlias();
              if (fieldAlias != null && relate.getFieldAlias().contains("_")) {
                fieldAlias = underlineToCamel(relate.getFieldAlias(), false);
              }
              map.put("ename", fieldAlias);
              map.put("keysetCode", keysetCode);
              map.put("valueResourceContent", relate.getValueResourceContent());
              map.put("valueResourceType", relate.getValueResourceType());
              map.put("valueType", relate.getValueType());
              String relateTableRowId = relate.getRelateTableColumn();
              List<DBTableColumn> dbTableColumns = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
              if (!UtilsTool.isValid(dbTableColumns)) {
                continue;
              }
              for (DBTableColumn row : dbTableColumns) {
                String columnEname = row.getColumnEname();
                if (row.getColumnEname() != null && row.getColumnEname().contains("_")) {
                  columnEname = underlineToCamel(row.getColumnEname(), false);
                }
                map.put("ename", columnEname);
              }
            }
            break;
          default:
            map.put("ename", "");
        }
      }
    }
    return result;
  }


  @Resource
  private FrontFuncProService frontFuncProService;

  /**
   * 根据功能块 rowId 查询属性列表，查询中对 rowId 没有做校验
   *
   * @param rowId 主键 rowId
   * @return 返回查询结果集合
   */
  public List<FrontFuncPro> selectPropertiesByRowId(String rowId) {
    Condition condition = new FieldCondition("func_row_id", Operator.EQUAL, rowId);
    return frontFuncProService.select(condition);
  }
}
