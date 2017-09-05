package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.AliasedColumn;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.UtilsTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class BaseServiceTemplate<T extends BaseEntity<T>> extends BaseService {

  protected final Class<? extends BaseEntity> entityClass =
          (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  private final Set<String> fieldNames = getFieldNamesFromClass(entityClass);

  /**
   * logger 日志操作
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private MoreBatis moreBatis;

  final public List<Map<String, Object>> select(Condition condition) {
    /**
     * 此处先将就
     * 等扩展字段完善以后再来完善
     */
    return select(condition, null);
  }

  private List<Order> emptyDefaultModifyTime(List<Order> orders) {
    return orders;
  }

  public List<Map<String, Object>> select(Condition condition, List<Order> orders) {
    return selectColumns(condition, moreBatis.getColumns(entityClass), orders);
  }

  public List<Map<String, Object>> selectColumns(Condition condition, Collection<AliasedColumn> aliasedColumns, List<Order> orders) {
    return moreBatis.selectStatement().select(aliasedColumns).from(moreBatis.getTable(entityClass))
            .orderBy(orders).where(condition).execute();
  }

  public PageResult<Map<String, Object>> select(Condition condition, List<Order> orders, int pageNum, int pageSize) {
    return selectColumns(condition, moreBatis.getColumns(entityClass), orders, pageNum, pageSize);
  }

  private PageResult<Map<String, Object>> selectColumns(Condition condition, Collection<AliasedColumn> aliasedColumns, List<Order> orders, int pageNum, int pageSize) {
    final PageResult<Map<String, Object>> queryResult = moreBatis.select(entityClass)
            .where(UtilsTool.excludeDeleted(condition)).orderBy(emptyDefaultModifyTime(orders)).selectPage(pageNum, pageSize);
    final PageResult<Map<String, Object>> camelizedResult = UtilsTool.underlineKeyMapListToCamel(queryResult);
    return camelizedResult;
  }

  public PageResult<Map<String, Object>> select(Condition condition, int pageNum, int pageSize) {
    //同上
    return select(condition, null, pageNum, pageSize);
  }


  @Deprecated
  public PageResult<Map<String, Object>> select(Map args, int pageNum, int pageSize) {
    args = mapFilter(args);
    return select(UtilsTool.convertMapToFieldConditions(args), pageNum, pageSize);
  }

  @Deprecated
  public List<Map<String, Object>> select(Map args) {
    args = mapFilter(args);
    return select(UtilsTool.convertMapToFieldConditions(args));
  }

  public List<Map<String, Object>> select(Condition condition, Collection<String> columns, List<Order> orders) {
    return selectColumns(condition, moreBatis.getColumnByAlies(entityClass, columns), orders);
  }

  public PageResult<Map<String, Object>> select(Condition condition, Collection<String> columns, List<Order> orders, int pageNum, int pageSize) {
    return selectColumns(condition, moreBatis.getColumnByAlies(entityClass, columns), orders, pageNum, pageSize);
  }

  public PageResult<Map<String, Object>> singleInputSelect(Collection<String> column,
                                                           Collection<String> value, int pageNum, int pageSize, List<Order> orders) {
    return singleInputSelect(column, value, pageNum, pageSize, moreBatis.getColumns(entityClass), orders);
  }

  @Deprecated
  private PageResult<Map<String, Object>> singleInputSelect(Collection<String> column,
                                                            Collection<String> value, int pageNum, int pageSize, Collection<AliasedColumn> aliasedColumns, List<Order> orders) {
    return selectColumns(UtilsTool.createBlankQuery(column, value), aliasedColumns, orders, pageNum, pageSize);
  }

  @Deprecated
  public List<Map<String, Object>> singleInputSelect(Collection<String> column,
                                                     Collection<String> value) {
    return select(UtilsTool.createBlankQuery(column, value));
  }

  public int insert(Map args) {
    args = mapFilter(args);
    args.remove("etc");
    InsertAction insertAction = moreBatis.insertStatement().into(moreBatis.getTable(entityClass)).cols(fieldNames).values(args);
    insertAction.setEntityClass(entityClass);
    return insertAction.execute();
  }

  public int update(Map args) {
    args = mapFilter(args);
    args.remove("etc");
    final Map<String, Object> finalCopy = args;
    List<Condition> condition = (List<Condition>) moreBatis.getPks(entityClass).stream().map((pk) -> {
      return new FieldCondition((AliasedColumn) pk, Operator.EQUAL, finalCopy.get(((AliasedColumn) pk).getAlies()));
    }).collect(Collectors.toList());
    return update(finalCopy, new And(condition));
  }

  public int update(Map args, Condition condition) {
    return 0;
  }

  public int delete(Map args) {
    args = mapFilter(args);
    args.remove("etc");
    return delete(UtilsTool.convertMapToFieldConditions(args));
  }

  public int delete(Condition condition) {
    DeleteAction deleteAction = moreBatis.deleteStatement()
            .from(moreBatis.getTable(entityClass))
            .where(condition);
    return deleteAction.execute();
  }


  public Map<String, Object> mapFilter(Map<String, Object> map) {
    return mapFilter(map, isRemoveNull(), isRemoveBlank());
  }

  private Map<String, Object> mapFilter(Map<String, Object> map, boolean removeNull, boolean removeBlank) {
    Map<String, Object> outputMap = new HashMap<>();
    if (removeBlank == false && removeNull == false) {
      return outputMap;
    }
    for (Entry<String, Object> entry : map.entrySet()) {
      Object value = entry.getValue();
      if (value == null && removeNull) {
        continue;
      }
      if (value.equals("") && removeBlank) {
        continue;
      }
      outputMap.put(entry.getKey(), entry.getValue());
    }
    return outputMap;
  }

  private Set<String> getFieldNamesFromClass(Class clz) {
    HashSet<String> result = new HashSet<>();
    while (clz != Object.class) {
      result.addAll(Arrays.stream(clz.getDeclaredFields()).map((field -> field.getName())).collect(
              Collectors.toList()));
      clz = clz.getSuperclass();
    }
    result.remove("etc");
    return result;
  }

  public boolean isRemoveBlank() {
    return true;
  }

  private boolean isRemoveNull() {
    return true;
  }
}
