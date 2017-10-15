package com.bcx.plat.core.morebatis.configuration.builder;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.entity.MaintDBTables;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.plugin.relation.entity.Relation;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DbEntityEntriesBuilder implements EntityEntriesBuilder {
  @Override
  public List<EntityEntry> getEntries() {
    MoreBatis morebatis = SpringContextHolder.getBean("moreBatis");
    ConditionBuilder conditionBuilder=new ConditionBuilder(BusinessObject.class);
    Condition condition = conditionBuilder.and()
        .notEqual("className", null).endAnd().buildDone();
    List<Map<String, Object>> businessObjects = morebatis.select(BusinessObject.class).where(condition).execute();
    List<String> tableRowIds = businessObjects.stream().map(map -> {
                                  return (String) map.get("relateTableRowId");
                                }).collect(Collectors.toList());
    List<String> objRowIds = businessObjects.stream().map(map -> {
                                  return (String) map.get("rowId");
                                }).collect(Collectors.toList());
    List<Map<String, Object>> tables = morebatis.select(MaintDBTables.class)
        .where(new FieldCondition("rowId", Operator.IN, tableRowIds)).execute();
    Map<String, Map<String, Object>> tablesMap = tables.stream().collect(Collectors.toMap(table -> {
      return (String) table.get("rowId");
    }, table -> {
      return table;
    }));
    List<Map<String, Object>> props = morebatis.select(BusinessObjectPro.class)
        .where(new FieldCondition("objRowId", Operator.IN, objRowIds)).execute();
    List<String> columnsRowId = props.stream().map(map -> {
      return (String) map.get("relateTableColumn");
    }).collect(Collectors.toList());
    List<Map<String, Object>> columns = morebatis.select(DBTableColumn.class)
        .where(new FieldCondition("rowId", Operator.IN, columnsRowId)).execute();
    Map<String, Map<String, Object>> columnMap = columns.stream().collect(Collectors.toMap(map -> {
      return (String) map.get("rowId");
    }, map -> {
      return map;
    }));
    Map<String, List<Map<String, Object>>> propsMap = props.stream()
        .collect(Collectors.groupingBy(map -> {
          return (String) map.get("objRowId");
        }));
    List<EntityEntry> entries = businessObjects.stream().map(businessObject -> {
      final String objRowId = (String) businessObject.get("rowId");
      List<Map<String, Object>> objProps = propsMap.get(objRowId);
      final Map<String, Object> tableMap = tablesMap.get(businessObject.get("relateTableRowId"));
      final Table table = new Table((String) tableMap.get("schema"),
          (String) tableMap.get("tableName"));
      final List<Field> fields = new LinkedList<>();
      final List<Field> pks = new LinkedList<>();
      objProps.stream().forEach(objProp -> {
        final String columnRowId = (String) objProp.get("relateTableColumn");
        final Map<String, Object> column = columnMap.get(columnRowId);
        final String fieldName = (String) column.get("columnEname");
        String alias = (String) column.get("fieldAlias");
        alias = alias != null ? alias : UtilsTool.underlineToCamel(fieldName, false);
        Field field = new Field(table, fieldName, alias);
        fields.add(field);
        final boolean isPk = ((Integer) column.get("isPk")) == 1 ? true : false;
        if (isPk) {
          pks.add(field);
        }
      });
      try {
        return new EntityEntry((Class<? extends BeanInterface>) Class
            .forName((String) businessObject.get("className")), table, fields, pks);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return null;
      }
    }).collect(Collectors.toList());
    return entries;
  }
}
