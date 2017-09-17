package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
@Service
public class DBTableColumnService extends BaseService<DBTableColumn> {

  @Autowired
  private BusinessObjectProService businessObjectProService;
  @Autowired
  private DBTableColumnService dbTableColumnService;


  /**
   * 模糊搜索的条件
   *
   * @return 字段
   */
  protected List<String> blankSelectFields() {
    return Arrays.asList("columnEname", "columnCname");
  }

  public ServerResult addTableColumn(Map<String, Object> param) {
    ServerResult result = new ServerResult();
    String columnEname = String.valueOf(param.get("columnEname"));
    Condition condition = new ConditionBuilder(DBTableColumn.class).and()
            .equal("columnEname", columnEname)
            .equal("relateTableRowId", param.get("relateTableRowId"))
            .endAnd().buildDone();
    List<DBTableColumn> select = dbTableColumnService.select(condition);
    if (select.size() == 0) {
      DBTableColumn dbTableColumn = new DBTableColumn().buildCreateInfo().fromMap(param);
      int insert = dbTableColumn.insert();
      if (insert != -1) {
        return result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.NEW_ADD_SUCCESS);
      } else {
        return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.NEW_ADD_FAIL);
      }
    } else {
      return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_CANNOT_BE_DUPLICATED);
    }
  }


  /**
   * 通过表信息字段rowId查询表信息并分页显示
   *
   * @param search   搜索条件
   * @param rowId    表字段rowId
   * @param orders   排序条件
   * @param pageNum  页码
   * @param pageSize 一页显示多少条
   * @return ServerResult
   */
  public ServerResult queryPageById(String search, String rowId, LinkedList<Order> orders, int pageNum, int pageSize) {
    pageNum = UtilsTool.isValid(search) ? 1 : pageNum;
    PageResult<Map<String, Object>> relateTableRowId = selectPageMap(new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId),
                    UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search))),
            orders, pageNum, pageSize);
    return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, relateTableRowId);
  }

  /**
   * 根据表信息的rowId来查询表字段中的信息
   *
   * @param rowId  表信息rowId
   * @param search 搜索条件
   * @return ServerResult
   */
  public ServerResult queryTableById(String rowId, String search) {
    List<DBTableColumn> relateTableRowId = select(new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId),
            UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search))));
    if (relateTableRowId.size() == 0) {
      return new ServerResult().setStateMessage(BaseConstants.STATUS_FAIL, Message.QUERY_FAIL);
    }
    return new ServerResult<>(BaseConstants.STATUS_SUCCESS, Message.QUERY_SUCCESS, relateTableRowId);
  }

  /**
   * 根据rowId删除维护数据库表信息
   *
   * @param rowId 唯一标识
   * @return ServerResult
   */
  public ServerResult delete(String rowId) {
    ServerResult result = new ServerResult();
    List<BusinessObjectPro> relateTableColumn = businessObjectProService.select(new FieldCondition("relateTableColumn", Operator.EQUAL, rowId));
    if (relateTableColumn.size() == 0) {
      DBTableColumn dbTableColumn = new DBTableColumn().buildDeleteInfo();
      int del = dbTableColumn.deleteById(rowId);
      if (del != -1) {
        return result.setStateMessage(BaseConstants.STATUS_SUCCESS, Message.DELETE_SUCCESS);
      } else {
        return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DELETE_FAIL);
      }
    } else {
      return result.setStateMessage(BaseConstants.STATUS_FAIL, Message.DATA_QUOTE);
    }
  }


}