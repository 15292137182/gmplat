package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 维护表信息Service层
 * Created by Wen Tiehu on 2017/8/11.
 */
@Service
public class MaintDBTablesService extends BaseService<MaintDBTables> {
  @Autowired
  private BusinessObjectService businessObjectService;
  @Autowired
  private DBTableColumnService dbTableColumnService;


  /**
   * 新增表信息属性
   *
   * @param param 接受实体参数
   * @return PlatResult
   */
  public ServerResult addMaintDB(Map<String, Object> param) {
    String tableEname = String.valueOf(param.get("tableEname")).trim();
    String tableSchema = String.valueOf(param.get("tableSchema"));
    param.put("tableEname", tableEname);
    if (UtilsTool.isValid(tableEname)) {
      Condition condition = new ConditionBuilder(MaintDBTables.class).and().equal("tableEname", tableEname).equal("tableSchema", tableSchema).endAnd().buildDone();
      List<Map> select = selectMap(condition);
      if (select.size() == 0) {
        MaintDBTables maintDBTables = new MaintDBTables().buildCreateInfo().fromMap(param);
        int insert = maintDBTables.insert();
        if (insert != -1) {
          return successData(Message.NEW_ADD_SUCCESS, maintDBTables);
        } else {
          return fail(Message.NEW_ADD_FAIL);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    }
    return fail(Message.DATA_CANNOT_BE_EMPTY);
  }


  /**
   * 编辑业务对象属性
   *
   * @param param 实体参数
   * @return Map
   */
  public ServerResult modifyBusinessObjPro(Map<String, Object> param) {
    String tableEname = String.valueOf(param.get("tableEname")).trim();
    String tableSchema = String.valueOf(param.get("tableSchema"));
    param.put("tableEname", tableEname);
    if (!"".equals(tableEname)) {
      Condition condition = new ConditionBuilder(MaintDBTables.class).and().equal("tableEname", tableEname).equal("tableSchema", tableSchema).endAnd().buildDone();
      List<Map> select = selectMap(condition);
      if (select.size() == 0 || select.get(0).get("rowId").equals(param.get("rowId"))) {
        MaintDBTables maintDBTables = new MaintDBTables().buildModifyInfo().fromMap(param);
        int update = maintDBTables.updateById();
        if (update != -1) {
          return successData(Message.UPDATE_SUCCESS, maintDBTables);
        } else {
          return fail(Message.UPDATE_FAIL);
        }
      } else {
        return fail(Message.DATA_CANNOT_BE_DUPLICATED);
      }
    }
    return fail(Message.DATA_CANNOT_BE_EMPTY);
  }


  /**
   * 根据维护表信息rowId删除数据
   *
   * @param rowId 唯一标识
   * @return ServerResult
   */
  public ServerResult delete(String rowId) {
    AtomicReference<Map<String, Object>> map = new AtomicReference<>(new HashMap<>());
    if (UtilsTool.isValid(rowId)) {
      //新增完成后将数据返回
      Condition buildDone = new ConditionBuilder(MaintDBTables.class).and().equal("rowId", rowId).endAnd().buildDone();
      List<MaintDBTables> dbTables = select(buildDone);
      map.get().put("relateTableRowId", rowId);
      List<Map> relateTableRowId = businessObjectService.selectMap(new FieldCondition("relateTableRowId"
          , Operator.EQUAL, rowId));
      if (relateTableRowId.size() == 0) {
        List<Map> list = dbTableColumnService.selectMap(new FieldCondition("relateTableRowId", Operator.EQUAL, rowId));
        if (UtilsTool.isValid(list)) { // 如果有，先删除关联信息
          List<String> rowIds = list.stream().map((row) ->
              (String) row.get("rowId")).collect(Collectors.toList());
          ConditionBuilder conditionBuilder = new ConditionBuilder(MaintDBTables.class);
          conditionBuilder.and().equal("rowId", rowIds);
          new DBTableColumn().buildDeleteInfo().delete(new FieldCondition("rowId", Operator.IN, rowIds));
        }
        MaintDBTables maintDBTables = new MaintDBTables().buildDeleteInfo();
        maintDBTables.delete(new FieldCondition("rowId", Operator.EQUAL, rowId));
        return successData(Message.DELETE_SUCCESS, dbTables);
      }
    }
    return fail(Message.DATA_QUOTE);
  }

}
