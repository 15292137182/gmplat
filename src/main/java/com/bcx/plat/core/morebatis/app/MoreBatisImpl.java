package com.bcx.plat.core.morebatis.app;

import static com.bcx.plat.core.utils.UtilsTool.underlineToCamel;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.SubAttribute;
import com.bcx.plat.core.morebatis.component.Table;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import com.bcx.plat.core.morebatis.configuration.builder.EntityEntriesBuilder;
import com.bcx.plat.core.morebatis.configuration.builder.EntityEntryBuilder;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.SqlComponentTranslator;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.postgresql.util.PGobject;
import org.springframework.stereotype.Component;

@Component
public class MoreBatisImpl implements MoreBatis {

  private SuitMapper suitMapper;
  private SqlComponentTranslator translator;
  //  private Translator translator = new Translator();
  private Map<Class, Collection<Field>> entityColumns;
  private Map<Class, Collection<Field>> entityPks;
  private Map<Class, Map<String, Field>> aliasMap;
  private Map<Class, TableSource> entityTables;
  private String defaultMapColumnAlias = "etc";

  public MoreBatisImpl(SuitMapper suitMapper, SqlComponentTranslator translator,
      Collection entityEntries, String defaultMapColumnAlias) {
//    if (defaultMapColumnAlias==null)
    this.suitMapper = suitMapper;
    this.translator = translator;
    entityColumns = new HashMap<>();
    entityPks = new HashMap<>();
    entityTables = new HashMap<>();
    aliasMap = new HashMap<>();
    List<EntityEntriesBuilder> entriesBuilders=new LinkedList<>();
    for (Object entry : entityEntries) {
      EntityEntry entityEntry;
      if (entry instanceof EntityEntry) {
        entityEntry = (EntityEntry) entry;
      } else if (entry instanceof EntityEntryBuilder) {
        entityEntry = ((EntityEntryBuilder) entry).getEntry();
      } else if (entry instanceof EntityEntriesBuilder){
        entriesBuilders.add((EntityEntriesBuilder) entry);
        continue;
      }else{
        throw new UnsupportedOperationException("你输入的提供的实体类注册信息不正确");
      }
      registeEntityEntry(entityEntry);
    }
    for (EntityEntriesBuilder entriesBuilder : entriesBuilders) {
      for (EntityEntry entityEntry : entriesBuilder.getEntries()) {
        registeEntityEntry(entityEntry);
      }
    }
  }

  private void registeEntityEntry(EntityEntry entityEntry) {
    final Class entryClass = entityEntry.getEntityClass();
    final TableSource entityEntryTable = entityEntry.getTable();
    Map<String, Field> aliasMap = this.aliasMap.get(entryClass);
    LinkedList<Field> fieldInTables = new LinkedList<>();
    if (aliasMap == null) {
      aliasMap = new HashMap<>();
      this.aliasMap.put(entryClass, aliasMap);
    }
    for (Field Field : entityEntry.getFields()) {
      aliasMap.put(Field.getAlias(), Field);
    }
    entityColumns.put(entryClass,
        immute(bindWithTable((Table) entityEntryTable, entityEntry.getFields())));
    entityTables.put(entryClass, entityEntryTable);
    entityPks
        .put(entryClass, immute(bindWithTable((Table) entityEntryTable, entityEntry.getPks())));
  }

  /**
   * 获取实体类全部主键字段
   *
   * @param entityClass 实体类class
   */
  public Collection<Field> getPks(Class entityClass) {
    return entityPks.get(entityClass);
  }

  /**
   * 获取实体类全部字段
   *
   * @param entityClass 实体类class
   */
  public Collection<Field> getColumns(Class entityClass) {
    return entityColumns.get(entityClass);
  }

  /**
   * 获取实体类对应表
   *
   * @param entityClass 实体类class
   */
  public TableSource getTable(Class entityClass) {
    return entityTables.get(entityClass);
  }

  public Field getColumnByAlias(Class entityClass, String alias) {
    final Map<String, Field> entityColumn = aliasMap.get(entityClass);
    try {
      return entityColumn.get(alias);
    } catch (NullPointerException e) {
      if (entityColumn == null) {
        throw new NullPointerException("实体类没有注册:" + entityClass.getName());
      } else {
        throw new NullPointerException("无效的字段别名:" + alias);
      }
    }
  }

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   *
   * @param entityClass 实体类
   * @param alias 实体类属性名称
   */
  public FieldSource getColumnOrEtcByAlias(Class entityClass, String alias) {
    final Map<String, Field> entityColumn = aliasMap.get(entityClass);
    try {
      FieldSource result = entityColumn.get(alias);
      if (result == null) {
        result = new SubAttribute(getColumnByAlias(entityClass, getDefaultMapColumnAlias()), alias);
      }
      return result;
    } catch (NullPointerException e) {
      throw new NullPointerException("实体类没有注册:" + entityClass.getName());
    }
  }

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   *
   * @param entityClass 实体类
   * @param alias 实体类属性名称
   */
  public List<Field> getColumnByAlias(Class entityClass, Collection<String> alias) {
    final Map<String, Field> entityColumn = aliasMap.get(entityClass);
    final LinkedList<Field> result = new LinkedList<>();
    try {
      for (String aly : alias) {
        result.add(entityColumn.get(aly));
      }
    } catch (NullPointerException e) {
      if (entityColumn == null) {
        throw new NullPointerException("实体类没有注册:" + entityClass.getName());
      } else {
        throw new NullPointerException("无效的字段别名:" + alias);
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
  }

  /**
   * 插入一个实体类
   *
   * @param entity 要插入的实体类
   * @return 返回插入的结果标识
   */
  public <T extends BeanInterface<T>> int insertEntity(T entity) {
    final Class entityClass = entity.getClass();
    Map<String, Object> values = entity.toDbMap();
    return insert(entityClass, values).execute();
  }

  /**
   * 根据主键删除一个实体类
   *
   * @param entity 要更新的实体类
   */
  public <T extends BeanInterface<T>> int deleteEntity(T entity) {
    Collection<Field> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toDbMap();
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlias())))
        .collect(Collectors.toList());
    return this.deleteStatement().from(table).where(new And(pkConditions)).execute();
  }

  /**
   * 根据主键更新一个实体类
   *
   * @param entity 要更新的实体类
   */
  public <T extends BeanInterface<T>> int updateEntity(T entity) {
    final Class entityClass = entity.getClass();
    Collection<Field> pks = entityPks.get(entityClass);
    Map<String, Object> values = entity.toDbMap();
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlias())))
        .collect(Collectors.toList());
    return this.update(entityClass, values).where(new And(pkConditions)).execute();
  }

  /**
   * 根据主键更新一个实体类
   *
   * @param entity 要更新的实体类
   */
  public <T extends BeanInterface<T>> int updateEntity(T entity, Object excluded) {
    final Class entityClass = entity.getClass();
    Collection<Field> pks = entityPks.get(entityClass);
    final Map<String, Object> values = entity.toDbMap();
    final Map<String, Object> valuesCopy = new HashMap<>();
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      final Object value = entry.getValue();
      if (value != excluded) {
        valuesCopy.put(entry.getKey(), entry.getValue());
      }
    }
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> new FieldCondition(pk, Operator.EQUAL, valuesCopy.get(pk.getAlias())))
        .collect(Collectors.toList());
    return this.update(entityClass, values).where(new And(pkConditions)).execute();
  }


  /**
   * 根据主键更新一个实体类
   *
   * @param entity 要更新的实体类
   */
  public <T extends BeanInterface<T>> int updateEntity(T entity, Collection excluded) {
    final Class entityClass = entity.getClass();
    Collection<Field> pks = entityPks.get(entityClass);
    Map<String, Object> values = entity.toDbMap();
    final Map<String, Object> valuesCopy = new HashMap<>();
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      final Object value = entry.getValue();
      if (!excluded.contains(value)) {
        valuesCopy.put(entry.getKey(), entry.getValue());
      }
    }
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> new FieldCondition(pk, Operator.EQUAL, valuesCopy.get(pk.getAlias())))
        .collect(Collectors.toList());
    return this.update(entityClass, valuesCopy).where(new And(pkConditions)).execute();
  }

  /**
   * 根据主键查找对象
   *
   * @param entity 入数据
   */
  public <T extends BeanInterface<T>> T selectEntityByPks(T entity) {
    Collection<Field> pks = entityPks.get(entity.getClass());
    TableSource table = entityTables.get(entity.getClass());
    Map<String, Object> values = entity.toDbMap();
    List<Condition> pkConditions = pks.stream()
        .map((pk) -> new FieldCondition(pk, Operator.EQUAL, values.get(pk.getAlias())))
        .collect(Collectors.toList());
    List<Map<String, Object>> result = this.select(entity.getClass())
        .where(new And(pkConditions)).execute();
    if (result.size() == 1) {
      HashMap<String, Object> _obj = new HashMap<>();
      result.get(0).forEach((key, value) -> _obj.put(underlineToCamel(key, false), value));
      return entity.fromDbMap(_obj);
    } else {
      return null;
    }
  }

  /**
   * 单表查询
   *
   * @param entity 要查询的实体类class
   */
  public QueryAction select(Class entity) {
    final Collection columns = entityColumns.get(entity);
    return selectStatement().select(columns)
        .from(entityTables.get(entity));
  }

  /**
   * 两个表的inner join查询
   *
   * @param primary 主表class对象
   * @param secondary 从表class对象
   * @param relationPrimary 主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @return 未设置条件的查询语句对象
   */
  public QueryAction select(Class primary,
      Class secondary, String relationPrimary, String relationSecondary) {
    return select(primary, secondary, relationPrimary, relationSecondary, JoinType.INNER_JOIN);
  }

  /**
   * 两个表join查询
   *
   * @param primary 主表class对象
   * @param secondary 从表class对象
   * @param relationPrimary 主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @param joinType join类型
   * @return 未设置条件的查询语句对象
   */
  public QueryAction select(Class primary,
      Class secondary, String relationPrimary, String relationSecondary, JoinType joinType) {
    HashMap<String, Field> columns = new HashMap<>();
    for (Field Field : entityColumns.get(primary)) {
      columns.put(Field.getAlias(), Field);
    }
    for (Field Field : entityColumns.get(secondary)) {
      columns.put(Field.getAlias(), Field);
    }
    Field primaryField = getColumnByAlias(primary, relationPrimary);
    Field secondaryField = getColumnByAlias(secondary, relationSecondary);
    final Collection values = columns.values();
    return selectStatement().select(values).from(
        new JoinTable(entityTables.get(primary), joinType, entityTables.get(secondary))
            .on(new FieldCondition(primaryField, Operator.EQUAL, secondaryField)));
  }

  private QueryAction select(Class entity, List<String> columns) {
    Map<String, Field> columnMap = aliasMap.get(entity);
    return selectStatement().select(columns.stream().map(columnMap::get)
        .collect(Collectors.toList()))
        .from(entityTables.get(entity));
  }

  public QueryAction selectStatement() {
    return new QueryAction(this, translator);
  }

  /**
   * 更新操作（未设置条件）
   *
   * @param entity 实体类的对象
   * @param value 要更新的值(支持null)
   */
  public UpdateAction update(Class entity, Map<String, Object> value) {
    UpdateAction update = updateStatement().from(entityTables.get(entity)).set(value);
    update.setEntityClass(entity);
    return update;
  }

  /**
   * 多行插入
   *
   * @param entity 实体类的class
   * @param values 插入数据库的多行数据
   */
  public InsertAction insert(Class entity, List<Map<String, Object>> values) {
    InsertAction insertAction = insertStatement().values(values);
    insertAction.setEntityClass(entity);
    return insertAction;
  }

  /**
   * 单行插入
   *
   * @param entity 实体类的class
   * @param value 插入数据库的单行数据
   * @return 未执行的插入语句对象
   */
  public InsertAction insert(Class entity, Map<String, Object> value) {
    InsertAction insertAction = insertStatement().into(entityTables.get(entity)).values(value);
    insertAction.setEntityClass(entity);
    return insertAction;
  }

  /**
   * 删除(未设置条件)
   *
   * @param entity 实体类的class
   * @return 未设置条件的删除语句对象
   */
  public DeleteAction delete(Class entity) {
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
    final LinkedList list = translator.translateQueryAction(queryAction, new LinkedList());
    List<Map<String, Object>> result = suitMapper.plainSelect(list);
    return translateJsonWithUnwrapEtc(result);
  }

  private List<Map<String, Object>> translateJsonWithUnwrapEtc(List<Map<String, Object>> rows) {
    rows.stream().forEach((row) -> {
      PGobject etcObj = (PGobject) row.remove("etc");
      if (etcObj != null) {
        HashMap etcMap = UtilsTool.jsonToObj(etcObj.toString(), HashMap.class);
        row.put("etc", etcMap);
        etcMap.forEach((k, v) -> {
          row.putIfAbsent((String) k, v);
        });
      }
    });
    return rows;
  }

  public List<Map<String, Object>> executeOrigin(QueryAction queryAction) {
    final LinkedList list = translator.translateQueryAction(queryAction, new LinkedList());
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
    LinkedList result = translator.translateInsertAction(insertAction, new LinkedList());
    return suitMapper.plainInsert(result);
  }

  public int execute(UpdateAction updateAction) {
    LinkedList result = translator.translateUpdateAction(updateAction, new LinkedList());
    return suitMapper.plainUpdate(result);
  }

  public int execute(DeleteAction deleteAction) {
    LinkedList list = translator.translateDeleteAction(deleteAction, new LinkedList());
    return suitMapper.plainDelete(list);
  }

  public String getDefaultMapColumnAlias() {
    return defaultMapColumnAlias;
  }
}