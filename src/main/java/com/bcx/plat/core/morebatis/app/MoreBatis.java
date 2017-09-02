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
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MoreBatis {

  private SuitMapper suitMapper;
  private SqlComponentTranslator translator;
  private Map<Class, Collection<Field>> entityColumns;
  private Map<Class, Collection<Field>> entityPks;
  private Map<Class, Map<String, Field>> aliesMaps;
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
      Map<String, Field> aliesMap = aliesMaps.get(entryClass);
      LinkedList<Field> fieldInTables=new LinkedList<>();
      if (aliesMap == null) {
        aliesMap = new HashMap<>();
        aliesMaps.put(entryClass, aliesMap);
      }
      for (Field Field : entityEntry.getFields()) {
        aliesMap.put(Field.getAlies(), Field);
      }
      entityColumns.put(entryClass, immute(bindWithTable((Table) entityEntryTable,entityEntry.getFields())));
      entityTables.put(entryClass, entityEntryTable);
      entityPks.put(entryClass, immute(bindWithTable((Table) entityEntryTable,entityEntry.getPks())));
    }
  }

  public <T extends BaseEntity<T>> Collection<Field> getPks(Class<T> entityClass) {
    return entityPks.get(entityClass);
  }

  public <T extends BaseEntity<T>> Collection<Field> getColumns(Class<T> entityClass) {
    return entityColumns.get(entityClass);
  }

  public <T extends BaseEntity<T>> TableSource getTable(Class<T> entityClass) {
    return entityTables.get(entityClass);
  }

  public <T extends BaseEntity<T>> Field getColumnByAlies(Class<T> entityClass, String alies) {
    final Map<String, Field> entityColumn = aliesMaps.get(entityClass);
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

  public <T extends BaseEntity<T>> List<Field> getColumnByAlies(Class<T> entityClass, Collection<String> alies) {
    final Map<String, Field> entityColumn = aliesMaps.get(entityClass);
    final LinkedList<Field> result=new LinkedList<>();
    try {
      for (String aly : alies) {
        result.add(entityColumn.get(aly));
      }
    } catch (NullPointerException e) {
      if (entityColumn == null) {
        throw new NullPointerException("实体类没有注册:" + entityClass.getName());
      } else {
        throw new NullPointerException("无效的字段别名:" + alies);
      }
    }
    return result;
  }

  private Collection<Field> immute(Collection<Field> fields) {
    return fields;
//    return fields.stream().map((column) -> {
//      return new ImmuteField(column);
//    }).collect(Collectors.toList());
  }

  private Collection<Field> bindWithTable(Table table, Collection<Field> fields) {
    for (Field field : fields) {
      field.setTable(table);
    }
    return fields;
//    return Fields.stream().map((column) -> {
//      return new FieldInTable((Field) column,table);
//    }).collect(Collectors.toList());
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
    Collection<Field> pks = entityPks.get(entity.getClass());
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
    Collection<Field> pks = entityPks.get(entity.getClass());
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
    Collection<Field> pks = entityPks.get(entity.getClass());
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
    final Collection columns = entityColumns.get(entity);
    return selectStatement().select(columns)
        .from(entityTables.get(entity));
  }

  public QueryAction select(Class<? extends BaseEntity> primary,
                            Class<? extends BaseEntity> secondary, String relationPrimary, String relationSecondary){
    return select(primary,secondary,relationPrimary,relationSecondary,JoinType.INNER_JOIN);
  }

  public QueryAction select(Class<? extends BaseEntity> primary,
      Class<? extends BaseEntity> secondary, String relationPrimary, String relationSecondary,JoinType joinType) {
    HashMap<String,Field> columns = new HashMap<>();
    for (Field Field : entityColumns.get(primary)) {
      columns.put(Field.getAlies(), Field);
    }
    for (Field Field : entityColumns.get(secondary)) {
      columns.put(Field.getAlies(), Field);
    }
    Field primaryField = getColumnByAlies(primary, relationPrimary);
    Field secondaryField = getColumnByAlies(secondary, relationSecondary);
    final Collection values = columns.values();
    return selectStatement().select(values).from(
        new JoinTable(entityTables.get(primary), joinType, entityTables.get(secondary))
            .on(new FieldCondition(primaryField, Operator.EQUAL, secondaryField)));
  }

  public QueryAction select(Class<? extends BaseEntity> entity, List<String> columns) {
    Map<String, Field> columnMap = aliesMaps.get(entity);
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