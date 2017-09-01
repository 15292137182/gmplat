package com.bcx.plat.core.morebatis.app;

import static com.bcx.plat.core.utils.UtilsTool.underlineToCamel;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.morebatis.cctv1.FieldInTable;
import com.bcx.plat.core.morebatis.cctv1.ImmuteField;
import com.bcx.plat.core.morebatis.cctv1.ImmuteTable;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MoreBatis {

  private SuitMapper suitMapper;
  private SqlComponentTranslator translator;
  private Map<Class, Collection<Column>> entityColumns;
  private Map<Class, List<Column>> entityPks;
  private Map<Class, Map<String, Column>> aliesMaps;
  private Map<Class, TableSource> entityTables;

  public MoreBatis(SuitMapper suitMapper, SqlComponentTranslator translator,
      List<EntityEntry> entityEntries) {
    this.suitMapper = suitMapper;
    this.translator = translator;
    entityColumns = new HashMap<>();
    entityPks = new HashMap<>();
    entityTables = new HashMap<>();
    aliesMaps = new HashMap<>();
    for (EntityEntry entityEntry : entityEntries) {
      final Class<? extends BaseEntity> entryClass = entityEntry.getEntityClass();
      final TableSource entityEntryTable = entityEntry.getTable();
      Map<String, Column> aliesMap = aliesMaps.get(entryClass);
      LinkedList<Column> fieldInTables=new LinkedList<>();
      if (aliesMap == null) {
        aliesMap = new HashMap<>();
        aliesMaps.put(entryClass, aliesMap);
      }
      for (Column column : entityEntry.getFields()) {
        aliesMap.put(column.getAlies(), new FieldInTable((Field) column, (Table) entityEntryTable));
      }
      entityColumns.put(entryClass, immute(bindWithTable((Table) entityEntryTable,entityEntry.getFields())));
      entityTables.put(entryClass, new ImmuteTable(entityEntryTable));
      entityPks.put(entryClass, immute(bindWithTable((Table) entityEntryTable,entityEntry.getPks())));
    }
  }

  public <T extends BaseEntity<T>> Collection<Column> getPks(Class<T> entityClass) {
    return entityPks.get(entityClass);
  }

  public <T extends BaseEntity<T>> Collection<Column> getColumns(Class<T> entityClass) {
    return entityColumns.get(entityClass);
  }

  public <T extends BaseEntity<T>> TableSource getTable(Class<T> entityClass) {
    return entityTables.get(entityClass);
  }

  public <T extends BaseEntity<T>> Column getColumnByAlies(Class<T> entityClass, String alies) {
    final Map<String, Column> entityColumn = aliesMaps.get(entityClass);
    try {
      return entityColumn.get(alies);
    } catch (NullPointerException e) {
      if (entityColumn == null) {
        throw new NullPointerException("实体类没有注册:" + entityClass.getName());
      } else {
        throw new NullPointerException("无效的字段别名:" + alies);
      }
    }
  }

  private List<Column> immute(Collection<Column> columns) {
    return columns.stream().map((column) -> {
      return new ImmuteField(column);
    }).collect(Collectors.toList());
  }

  private List<Column> bindWithTable(Table table,Collection<Column> columns) {
    return columns.stream().map((column) -> {
      return new FieldInTable((Field) column,table);
    }).collect(Collectors.toList());
  }

  public <T extends BaseEntity<T>> int insertEntity(T entity) {
    final Class<? extends BaseEntity> entityClass = entity.getClass();
    TableSource table = entityTables.get(entityClass);
    Map<String, Object> values = entity.toMap();
    Map etc = (Map) values.remove("etc");
    return this.insertStatement().into(table).cols(values.keySet()).values(Arrays.asList(values))
        .execute();
  }

  public <T extends BaseEntity<T>> int deleteEntity(T entity) {
    List<Column> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toMap();
    Map etc = (Map) values.remove("etc");
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> {
          return new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlies()));
        })
        .collect(Collectors.toList());
    return this.deleteStatement().from(table).where(new And(pkConditions)).execute();
  }

  public <T extends BaseEntity<T>> int updateEntity(T entity) {
    List<Column> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toMap();
    Map etc = (Map) values.remove("etc");
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> {
          return new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlies()));
        })
        .collect(Collectors.toList());
    return this.updateStatement().from(table).set(values).where(new And(pkConditions)).execute();
  }

  public <T extends BaseEntity<T>> T selectEntityByPks(T entity) {
    List<Column> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toMap();
    Map etc = (Map) values.remove("etc");
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> {
          return new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlies()));
        })
        .collect(Collectors.toList());
    List<Map<String, Object>> result = this.select(entity.getClass()).from(table)
        .where(new And(pkConditions)).execute();
    if (result.size() == 1) {
      HashMap<String, Object> _obj = new HashMap<>();
      result.get(0).entrySet().stream().forEach((entry) -> {
        _obj.put(underlineToCamel(entry.getKey(), false), entry.getValue());
      });
      return entity.fromMap(_obj);
    } else {
      return null;
    }
  }


  public QueryAction select(Class<? extends BaseEntity> entity) {
    return selectStatement().select(entityColumns.get(entity))
        .from(entityTables.get(entity));
  }

  public QueryAction select(Class<? extends BaseEntity> primary,
                            Class<? extends BaseEntity> secondary, String relationPrimary, String relationSecondary){
    return select(primary,secondary,relationPrimary,relationSecondary,JoinType.INNER_JOIN);
  }

  public QueryAction select(Class<? extends BaseEntity> primary,
      Class<? extends BaseEntity> secondary, String relationPrimary, String relationSecondary,JoinType joinType) {
    HashMap<String,Column> columns = new HashMap<>();
    for (Column column : entityColumns.get(primary)) {
      columns.put(column.getAlies(),column);
    }
    for (Column column : entityColumns.get(secondary)) {
      columns.put(column.getAlies(),column);
    }
    Column primaryField = getColumnByAlies(primary, relationPrimary);
    Column secondaryField = getColumnByAlies(secondary, relationSecondary);
    return selectStatement().select(columns.values()).from(
        new JoinTable(entityTables.get(primary), joinType, entityTables.get(secondary))
            .on(new FieldCondition(primaryField, Operator.EQUAL, secondaryField)));
  }

  public QueryAction select(Class<? extends BaseEntity> entity, List<String> columns) {
    Map<String, Column> columnMap = aliesMaps.get(entity);
    return selectStatement().select(columns.stream().map((columnAlies) -> {
      return columnMap.get(columnAlies);
    }).collect(Collectors.toList())).from(entityTables.get(entity));
  }

  public QueryAction selectStatement() {
    return new QueryAction(this, translator);
  }

  public InsertAction insertStatement() {
    return new InsertAction(this, translator);
  }

  public UpdateAction updateStatement() {
    return new UpdateAction(this, translator);
  }

  public DeleteAction deleteStatement() {
    return new DeleteAction(this, translator);
  }

  public List<Map<String, Object>> execute(QueryAction queryAction) {
    return suitMapper.select(queryAction);
  }

  public int execute(InsertAction insertAction) {
    return suitMapper.insert(insertAction);
  }

  public int execute(UpdateAction updateAction) {
    return suitMapper.update(updateAction);
  }

  public int execute(DeleteAction deleteAction) {
    return suitMapper.delete(deleteAction);
  }
}