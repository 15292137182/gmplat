package com.bcx.plat.core.morebatis.app;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.builder.AndConditionBuilder;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
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
import com.bcx.plat.core.morebatis.translator.Translator;
import com.bcx.plat.core.utils.UtilsTool;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.bcx.plat.core.utils.UtilsTool.underlineToCamel;

@Component
public class MoreBatis {

  private SuitMapper suitMapper;
  private SqlComponentTranslator translator;
  Translator translatorX = new Translator();
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
      final Class<? extends BeanInterface> entryClass = entityEntry.getEntityClass();
      final TableSource entityEntryTable = entityEntry.getTable();
      Map<String, Field> aliesMap = aliesMaps.get(entryClass);
      LinkedList<Field> fieldInTables = new LinkedList<>();
      if (aliesMap == null) {
        aliesMap = new HashMap<>();
        aliesMaps.put(entryClass, aliesMap);
      }
      for (Field Field : entityEntry.getFields()) {
        aliesMap.put(Field.getAlies(), Field);
      }
      entityColumns.put(entryClass, immute(bindWithTable((Table) entityEntryTable, entityEntry.getFields())));
      entityTables.put(entryClass, entityEntryTable);
      entityPks.put(entryClass, immute(bindWithTable((Table) entityEntryTable, entityEntry.getPks())));
    }
  }

  /**
   * 获取实体类全部主键字段
   * @param entityClass 实体类class
   * @return
   */
  public <T extends BeanInterface<T>> Collection<Field> getPks(Class<T> entityClass) {
    return entityPks.get(entityClass);
  }

  /**
   * 获取实体类全部字段
   * @param entityClass 实体类class
   * @return
   */
  public <T extends BeanInterface<T>> Collection<Field> getColumns(Class<T> entityClass) {
    return entityColumns.get(entityClass);
  }

  /**
   * 获取实体类对应表
   * @param entityClass 实体类class
   * @return
   */
  public <T extends BeanInterface<T>> TableSource getTable(Class<T> entityClass) {
    return entityTables.get(entityClass);
  }

  public <T extends BeanInterface<T>> Field getColumnByAlies(Class<T> entityClass, String alies) {
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

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   * @param entityClass 实体类
   * @param alies       实体类属性名称
   * @return
   */
  public <T extends BeanInterface<T>> Field getColumnByAliesWithoutCheck(Class<T> entityClass, String alies) {
    final Map<String, Field> entityColumn = aliesMaps.get(entityClass);
    try {
      return entityColumn.get(alies);
    } catch (NullPointerException e) {
      throw new NullPointerException("实体类没有注册:" + entityClass.getName());
    }
  }

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   * @param entityClass 实体类
   * @param alies       实体类属性名称
   * @return
   */
  public <T extends BeanInterface<T>> List<Field> getColumnByAlies(Class<T> entityClass, Collection<String> alies) {
    final Map<String, Field> entityColumn = aliesMaps.get(entityClass);
    final LinkedList<Field> result = new LinkedList<>();
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

  /**
   * 插入一个实体类
   * @param entity 要插入的实体类
   * @return 返回插入的结果标识
   */
  public <T extends BeanInterface<T>> int insertEntity(T entity) {
    final Class<? extends BeanInterface> entityClass = entity.getClass();
    Map<String, Object> values = entity.toMap();
    return insert(entityClass, values).execute();
  }

  /**
   * 根据主键删除一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  public <T extends BeanInterface<T>> int deleteEntity(T entity) {
    Collection<Field> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toMap();
    List<Condition> pkConditions = pks.stream()
            .map((pk) -> new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlies())))
            .collect(Collectors.toList());
    return this.deleteStatement().from(table).where(new And(pkConditions)).execute();
  }

  /**
   * 根据主键更新一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  public <T extends BeanInterface<T>> int updateEntity(T entity) {
    final Class<? extends BeanInterface> entityClass = entity.getClass();
    Collection<Field> pks = entityPks.get(entityClass);
    Map<String, Object> values = entity.toMap();
    List<Condition> pkConditions = pks.stream()
            .map((pk) -> new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlies())))
            .collect(Collectors.toList());
    return this.update(entityClass, values).where(new And(pkConditions)).execute();
  }


  /**
   * 根据主键更新一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  public <T extends BeanInterface<T>> int updateEntity(T entity,Object excluded) {
    final Class<? extends BeanInterface> entityClass = entity.getClass();
    Collection<Field> pks = entityPks.get(entityClass);
    final Map<String, Object> values = entity.toMap();
    final Map<String, Object> valuesCopy = new HashMap<>();
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      final Object value = entry.getValue();
      if (value!=excluded) valuesCopy.put(entry.getKey(),entry.getValue());
    }
    List<Condition> pkConditions = pks.stream()
            .map((pk) -> new FieldCondition(pk, Operator.EQUAL, valuesCopy.get(pk.getAlies())))
            .collect(Collectors.toList());
    return this.update(entityClass, values).where(new And(pkConditions)).execute();
  }

  /**
   * 根据主键更新一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  public <T extends BeanInterface<T>> int updateEntity(T entity,Collection excluded) {
    final Class<? extends BeanInterface> entityClass = entity.getClass();
    Collection<Field> pks = entityPks.get(entityClass);
    Map<String, Object> values = entity.toMap();
//    values.forEach((key,value)->{
//      if (excluded.contains(value)) values.remove(key);
//    });
    final Map<String, Object> valuesCopy = new HashMap<>();
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      final Object value = entry.getValue();
      if (!excluded.contains(value)) valuesCopy.put(entry.getKey(),entry.getValue());
    }
    List<Condition> pkConditions = pks.stream()
            .map((pk) -> new FieldCondition(pk, Operator.EQUAL, valuesCopy.get(pk.getAlies())))
            .collect(Collectors.toList());
    return this.update(entityClass, valuesCopy).where(new And(pkConditions)).execute();
  }


  /**
   * 根据主键查找对象
   * @param entity 入数据
   * @return
   */
  public <T extends BeanInterface<T>> T selectEntityByPks(T entity) {
    Collection<Field> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toMap();
    List<Condition> pkConditions = pks.stream()
            .map((pk) -> new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlies())))
            .collect(Collectors.toList());
    List<Map<String, Object>> result = this.select(entity.getClass())
            .where(new And(pkConditions)).execute();
    if (result.size() == 1) {
      HashMap<String, Object> _obj = new HashMap<>();
      result.get(0).forEach((key, value) -> _obj.put(underlineToCamel(key, false), value));
      return entity.fromMap(_obj);
    } else {
      return null;
    }
  }

  /**
   * 单表查询
   * @param entity  要查询的实体类class
   * @return
   */
  public QueryAction select(Class<? extends BeanInterface> entity) {
    final Collection columns = entityColumns.get(entity);
    return selectStatement().select(columns)
            .from(entityTables.get(entity));
  }

  /**
   * 两个表的inner join查询
   * @param primary           主表class对象
   * @param secondary         从表class对象
   * @param relationPrimary   主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @return 未设置条件的查询语句对象
   */
  public QueryAction select(Class<? extends BeanInterface> primary,
                            Class<? extends BeanInterface> secondary, String relationPrimary, String relationSecondary) {
    return select(primary, secondary, relationPrimary, relationSecondary, JoinType.INNER_JOIN);
  }


  /**
   * 两个表join查询
   * @param primary           主表class对象
   * @param secondary         从表class对象
   * @param relationPrimary   主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @param joinType          join类型
   * @return 未设置条件的查询语句对象
   */
  public QueryAction select(Class<? extends BeanInterface> primary,
                            Class<? extends BeanInterface> secondary, String relationPrimary, String relationSecondary, JoinType joinType) {
    HashMap<String, Field> columns = new HashMap<>();
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

  private QueryAction select(Class<? extends BeanInterface> entity, List<String> columns) {
    Map<String, Field> columnMap = aliesMaps.get(entity);
    return selectStatement().select(columns.stream().map(columnMap::get)
            .collect(Collectors.toList()))
            .from(entityTables.get(entity));
  }

  public QueryAction selectStatement() {
    return new QueryAction(this, translator);
  }

  /**
   * 更新操作（未设置条件）
   * @param entity 实体类的对象
   * @param value  要更新的值(支持null)
   * @return
   */
  public UpdateAction update(Class<? extends BeanInterface> entity, Map<String, Object> value) {
    UpdateAction update = updateStatement().from(entityTables.get(entity)).set(value);
    update.setEntityClass(entity);
    return update;
  }

  /**
   * 多行插入
   * @param entity 实体类的class
   * @param values 插入数据库的多行数据
   * @return
   */
  public InsertAction insert(Class<? extends BeanInterface> entity, List<Map<String, Object>> values) {
    InsertAction insertAction = insertStatement().values(values);
    insertAction.setEntityClass(entity);
    return insertAction;
  }

  /**
   * 单行插入
   * @param entity 实体类的class
   * @param value  插入数据库的单行数据
   * @return 未执行的插入语句对象
   */
  public InsertAction insert(Class<? extends BeanInterface> entity, Map<String, Object> value) {
    InsertAction insertAction = insertStatement().into(entityTables.get(entity)).values(value);
    insertAction.setEntityClass(entity);
    return insertAction;
  }

  /**
   * 删除(未设置条件)
   * @param entity 实体类的class
   * @return 未设置条件的删除语句对象
   */
  public DeleteAction delete(Class<? extends BeanInterface> entity){
    return deleteStatement().from(getTable(entity));
  }

  public InsertAction insertStatement() {
    return new InsertAction(this, translator);
  }

  private UpdateAction updateStatement() {
    return new UpdateAction(this, translator);
  }

  public DeleteAction deleteStatement() {
    return new DeleteAction(this, translator);
  }

  public List<Map<String, Object>> execute(QueryAction queryAction) {
    final LinkedList list = translatorX.translateQueryAction(queryAction, new LinkedList());
    List<Map<String, Object>> result = suitMapper.plainSelect(list);
    return translateJson(result);
  }

  private List<Map<String, Object>> translateJson(List<Map<String, Object>> result) {
    for (Map<String, Object> row : result) {
      for (Map.Entry<String, Object> col : row.entrySet()) {
        Object value = col.getValue();
        if (value instanceof PGobject && ((PGobject) value).getType().startsWith("json")) {
          row.put(col.getKey(), UtilsTool.jsonToObj(value.toString(), HashMap.class));
        }
      }
    }
    return result;
  }

  public int execute(InsertAction insertAction) {
    LinkedList result = translatorX.translateInsertAction(insertAction, new LinkedList());
    return suitMapper.plainInsert(result);
  }

  public int execute(UpdateAction updateAction) {
    LinkedList result = translatorX.translateUpdateAction(updateAction, new LinkedList());
    return suitMapper.plainUpdate(result);
  }

  public int execute(DeleteAction deleteAction) {
    LinkedList list = translatorX.translateDeleteAction(deleteAction, new LinkedList());
    return suitMapper.plainDelete(list);
  }
}