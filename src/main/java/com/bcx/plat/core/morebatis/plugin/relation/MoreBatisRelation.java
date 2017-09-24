package com.bcx.plat.core.morebatis.plugin.relation;

import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.plugin.relation.entity.Relation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class MoreBatisRelation {

  @Autowired
  private MoreBatis moreBatis;

  public MoreBatisRelation(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  /**
   * 添加集合
   */
  public void putCollection(Class primary, String name, String rowId, Collection<String> values) {
    Relation relation = getRelationMap(primary, rowId, name);
    deleteCollection(primary, name, rowId);
    Integer sort = 0;
    for (String value : values) {
      relation.setValue(value);
      relation.setSort(sort++);
      relation.setRowId(UUID.randomUUID().toString());
      moreBatis.insert(Relation.class, relation.toDbMap()).execute();
    }
  }


  /**
   * 获取集合
   */
  public List<String> getCollection(Class primary, String name, String rowId) {
    Condition condition = new ConditionBuilder(Relation.class).and()
        .equal("primaryTable", getTableNameFromClass(primary))
        .equal("name", name)
        .equal("primaryRowId", rowId)
        .endAnd()
        .buildDone();
    List<Map<String, Object>> rows = moreBatis.select(Relation.class)
        .select(moreBatis.getColumnByAlias(Relation.class, "value"))
        .where(condition)
        .orderBy(new Order(moreBatis.getColumnByAlias(Relation.class, "sort"), Order.ASC))
        .execute();
    return rows.stream().map((row) -> {
      return (String) row.get("value");
    }).collect(Collectors.toList());
  }

  /**
   * 删除集合记录
   */
  public int deleteCollection(Class primary, String name, String rowId) {
    Condition condition = new ConditionBuilder(Relation.class).and()
        .equal("primaryTable", getTableNameFromClass(primary))
        .equal("name", name)
        .equal("primaryRowId", rowId)
        .endAnd()
        .buildDone();
    return moreBatis.delete(Relation.class).where(condition).execute();
  }

  /**
   * 根据值查找包含的主记录rowId
   */
  public Set<String> getPrimaryRowIdByValues(Class primary, String name,
      Collection<String> values) {
    Condition condition = new ConditionBuilder(Relation.class).and()
        .equal("primaryTable", getTableNameFromClass(primary))
        .equal("name", name)
        .in("value", values)
        .endAnd()
        .buildDone();
    List<Map<String, Object>> rows = moreBatis.select(Relation.class)
        .select(moreBatis.getColumnByAlias(Relation.class, "primaryRowId"))
        .where(condition)
        .orderBy(new Order(moreBatis.getColumnByAlias(Relation.class, "sort"), Order.ASC))
        .execute();
    final HashSet<String> result = new HashSet<>();
    rows.forEach((row) -> {
      result.add((String) row.get("primaryRowId"));
    });
    return result;
  }

  /**
   * 删除集合记录
   */
  public int deleteCollection(Class primary, String rowId) {
    Condition condition = new ConditionBuilder(Relation.class).and()
        .equal("primaryTable", getTableNameFromClass(primary))
        .equal("primaryRowId", rowId)
        .endAnd()
        .buildDone();
    return moreBatis.delete(Relation.class).where(condition).execute();
  }

  /**
   * 获取关联的子记录
   */
  public List<Map<String, Object>> getRelationRecord(Class primary, Class secondary, String name,
      String rowId) {
    Condition condition = new ConditionBuilder(secondary).and()
        .in("rowId", getCollection(primary, name, rowId)).endAnd().buildDone();
    return moreBatis.select(secondary).where(condition).execute();
  }

  /**
   * 获取某一其他表中行的的全部的集合数据
   */
  public Map<String, List> getAllRelation(Class primary, String rowId) {
    Condition condition = new ConditionBuilder(Relation.class).and()
        .equal("primaryTable", getTableNameFromClass(primary))
        .equal("primaryRowId", rowId)
        .endAnd()
        .buildDone();
    List<Map<String, Object>> rows = moreBatis.select(Relation.class)
        .select(moreBatis.getColumnByAlias(Relation.class, Arrays.asList("value","name")))
        .where(condition)
        .orderBy(new Order(moreBatis.getColumnByAlias(Relation.class, "sort"), Order.ASC))
        .execute();
    Map<String, List> listMap = new HashMap<>();
    rows.forEach((row) -> {
      putListMap(listMap, (String) row.get("name"), row.get("value"));
    });
    return listMap;
  }

  /**
   * 获取某一其他表中行的的全部的集合数据
   */
  public Map<String, Map<String, List>> getAllRelation(Class primary, Collection rowIds) {
    Condition condition = new ConditionBuilder(Relation.class).and()
        .equal("primaryTable", getTableNameFromClass(primary))
        .in("primaryRowId", rowIds)
        .endAnd()
        .buildDone();
    List<Map<String, Object>> rows = moreBatis.select(Relation.class)
        .select(moreBatis.getColumnByAlias(Relation.class, Arrays.asList("value","name","primaryRowId")))
        .where(condition)
        .orderBy(
            new Order(moreBatis.getColumnByAlias(Relation.class, "primaryRowId"), Order.DESC),
            new Order(moreBatis.getColumnByAlias(Relation.class, "sort"), Order.ASC)
        )
        .execute();
    Map<String, Map<String, List>> result = new HashMap<>();
    rows.forEach((row) -> {
      putMapListMap(result, (String) row.get("primaryRowId"), (String) row.get("name"),
          row.get("value"));
    });
    return result;
  }

  /**
   * 向ListMap中存值
   */
  private Map<String, List> putListMap(Map<String, List> listMap, String key, Object value) {
    List list = listMap.get(key);
    if (list == null) {
      list = new LinkedList();
      listMap.put(key, list);
    }
    list.add(value);
    return listMap;
  }

  /**
   * 向ListMap中存值
   */
  private Map<String, Map<String, List>> putMapListMap(Map<String, Map<String, List>> mapListMap,
      String rowId, String key, Object value) {
    Map<String, List> listMap = mapListMap.get(rowId);
    if (listMap == null) {
      listMap = new HashMap<>();
      mapListMap.put(rowId, listMap);
    }
    putListMap(listMap, key, value);
    return mapListMap;
  }

  /**
   * 产生一个没设值和序号的Relation
   */
  private Relation getRelationMap(Class primary, String rowId, String name) {
    final String tableName = getTableNameFromClass(primary);
    Relation relation = new Relation();
    relation.setName(name);
    relation.setPrimaryRowId(rowId);
    relation.setPrimaryTable(tableName);
    return relation;
  }

  private String getTableNameFromClass(Class primary) {
    final Table table = (Table) moreBatis.getTable(primary);
    return table.getSchema() + table.getTableName();
  }
}
