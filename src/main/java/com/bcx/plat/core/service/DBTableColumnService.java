package com.bcx.plat.core.service;

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

import static com.bcx.plat.core.utils.UtilsTool.isValid;

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

  /**
   * 新增表字段信息
   *
   * @param param 接受map参数
   * @return serverResult
   */
  public ServerResult addTableColumn(Map<String, Object> param) {
    String columnEname = String.valueOf(param.get("columnEname")).trim();
    param.remove("columnEname");
    param.put("columnEname", columnEname);
    if (!"".equals(columnEname)) {
      Condition condition = new ConditionBuilder(DBTableColumn.class).and()
          .equal("columnEname", columnEname)
          .equal("relateTableRowId", param.get("relateTableRowId"))
          .endAnd().buildDone();
      List<DBTableColumn> select = dbTableColumnService.select(condition);
      if (select.size() == 0) {
        DBTableColumn dbTableColumn = new DBTableColumn().buildCreateInfo().fromMap(param);
        int insert = dbTableColumn.insert();
        if (insert != -1) {
          return successData(Message.NEW_ADD_SUCCESS, dbTableColumn);
        } else {
          return error(Message.NEW_ADD_FAIL);
        }
      } else {
        return error(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    }
    return error(Message.DATA_CANNOT_BE_EMPTY);
  }

  /**
   * 数据库表字段修改
   *
   * @param param 修改参数
   * @return ServerResult
   */
  public ServerResult updateTableColumn(Map<String, Object> param) {
    String columnEname = String.valueOf(param.get("columnEname")).trim();
    param.put("columnEname", columnEname);
    if (!"".equals(columnEname)) {
      Condition condition = new ConditionBuilder(DBTableColumn.class).and()
          .equal("columnEname", columnEname)
          .equal("relateTableRowId", param.get("relateTableRowId"))
          .endAnd().buildDone();
      List<DBTableColumn> select = dbTableColumnService.select(condition);
      if (select.size() == 0 || select.get(0).getRowId().equals(param.get("rowId"))) {
        DBTableColumn dbTableColumn = new DBTableColumn().buildModifyInfo().fromMap(param);
        int update = dbTableColumn.updateById();
        if (update != -1) {
          return successData(Message.UPDATE_SUCCESS, dbTableColumn);
        } else {
          return error(Message.UPDATE_FAIL);
        }
      } else {
        return error(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    }
    return error(Message.DATA_CANNOT_BE_EMPTY);
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
  @SuppressWarnings("unchecked")
  public ServerResult queryPageById(String search, String param, String rowId, LinkedList<Order> orders, Integer pageNum, Integer pageSize) {
    Condition condition;
    if (isValid(param)) { // 判断是否根据指定字段查询
      Map<String, Object> map = UtilsTool.jsonToObj(param, Map.class);
      condition = new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId), UtilsTool.convertMapToAndConditionSeparatedByLike(DBTableColumn.class, map));
    } else {
      if (isValid(search)) {
        condition = new And(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId),
            UtilsTool.createBlankQuery(blankSelectFields(), UtilsTool.collectToSet(search)));
      } else {
        condition = new FieldCondition("relateTableRowId", Operator.EQUAL, rowId);
      }
    }
    PageResult<Map<String, Object>> relateTableRowId;
    if (isValid(pageNum)) { // 判断是否分页查询
      relateTableRowId = selectPageMap(condition, orders, pageNum, pageSize);
    } else {
      relateTableRowId = new PageResult(selectMap(condition, orders));
    }
    return new ServerResult<>(relateTableRowId);
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
      return error(Message.QUERY_FAIL);
    }
    return successData(Message.QUERY_SUCCESS, relateTableRowId);
  }

  /**
   * 根据rowId删除维护数据库表信息
   *
   * @param rowId 唯一标识
   * @return ServerResult
   */
  public ServerResult delete(String rowId) {
    List<BusinessObjectPro> relateTableColumn = businessObjectProService.select(new FieldCondition("relateTableColumn", Operator.EQUAL, rowId));
    if (relateTableColumn.size() == 0) {
      DBTableColumn dbTableColumn = new DBTableColumn().buildDeleteInfo();
      Condition condition = new ConditionBuilder(DBTableColumn.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<DBTableColumn> dbTableColumns = select(condition);
      if (isValid(dbTableColumns) && dbTableColumns.size() > 0) {
        int del = dbTableColumn.deleteById(rowId);
        if (del != -1) {
          return successData(Message.DELETE_SUCCESS, dbTableColumns);
        } else {
          return error(Message.DELETE_FAIL);
        }
      } else {
        return error(Message.QUERY_FAIL);
      }
    } else {
      return error(Message.DATA_QUOTE);
    }
  }


}