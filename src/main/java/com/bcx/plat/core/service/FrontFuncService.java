package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.*;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.utils.ServerResult;
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
public class FrontFuncService extends BaseService<FrontFunc> {

  private final MoreBatis moreBatis;
  private final BusinessObjectProService businessObjectProService;
  private final DBTableColumnService dbTableColumnService;

  @Autowired
  public FrontFuncService(MoreBatis moreBatis, BusinessObjectProService businessObjectProService, DBTableColumnService dbTableColumnService) {
    this.moreBatis = moreBatis;
    this.businessObjectProService = businessObjectProService;
    this.dbTableColumnService = dbTableColumnService;
  }


  /**
   * 根据前端功能块的代码查询功能块属性的数据
   *
   * @param funcCode 代码
   * @return ServerResult
   */
  public ServerResult<LinkedList<Map<String, Object>>> queryFuncCode(List funcCode) {
    LinkedList<Map<String, Object>> linkedList = new LinkedList<>();
    List<Map<String, Object>> result = null;
    for (Object key : funcCode) {
      FieldCondition fieldCondition = new FieldCondition("funcCode", Operator.EQUAL, key);
      List<Map<String, Object>> keysetCode = singleSelect(FrontFunc.class, fieldCondition);
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
              List<Map> proRowId = new TemplateObjectPro().selectSimpleMap(new FieldCondition("proRowId", Operator.EQUAL, relateBusiPro));
              if (!UtilsTool.isValid(proRowId)) {
                continue;
              }
              //拿到模板对象属性给功能块属性赋值
              for (Map pro : proRowId) {
                map.put("propertyName", pro.get("cname").toString());
                map.put("ename", pro.get("ename").toString());
              }
              break;
            case BaseConstants.ATTRIBUTE_SOURCE_BASE: // 穿透↓
            case BaseConstants.ATTRIBUTE_SOURCE_EXTEND:
              List<BusinessObjectPro> businessObjectPros = businessObjectProService.select(new FieldCondition("rowId", Operator.EQUAL, relateBusiPro));
              if (!UtilsTool.isValid(businessObjectPros)) {
                continue;
              }
              for (BusinessObjectPro relate : businessObjectPros) {
                if (UtilsTool.isValid(relate.getFieldAlias())) {
                  map.put("ename", relate.getFieldAlias());
                  break;
                }
                String relateTableRowId = relate.getRelateTableColumn();
                List<DBTableColumn> dbTableColumns = dbTableColumnService.select(new FieldCondition("rowId", Operator.EQUAL, relateTableRowId));
                if (!UtilsTool.isValid(dbTableColumns)) {
                  continue;
                }
                for (DBTableColumn row : dbTableColumns) {
                  map.put("ename", row.getColumnEname());
                }
              }
              break;
            default:
              return new ServerResult<>(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL, linkedList);
          }
        }

      }
      linkedList.addAll(UtilsTool.underlineKeyMapListToCamel(result));
    }
    return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, linkedList);
  }
}
