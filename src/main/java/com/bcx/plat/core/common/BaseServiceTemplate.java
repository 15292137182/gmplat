package com.bcx.plat.core.common;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.mapper.SuitMapper;
import com.bcx.plat.core.morebatis.phantom.Column;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import com.bcx.plat.core.morebatis.substance.Field;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import com.bcx.plat.core.morebatis.substance.condition.Or;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.TableAnnoUtil;
import com.bcx.plat.core.utils.UtilsTool;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseServiceTemplate<T extends BaseEntity<T>> implements BaseService<T>
{
  private final Class entityClass = (Class) ((ParameterizedType) this.getClass()
      .getGenericSuperclass()).getActualTypeArguments()[0];
  private final Set<String> fieldNames = getFieldNamesFromClass(entityClass);
  private final TableSource table = TableAnnoUtil.getTableSource(entityClass);
  private final List<String> pkFields = TableAnnoUtil.getPkAnnoField(entityClass);
  @Autowired
  private MoreBatis moreBatis;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  /**
   * logger 日志操作
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());

  final public List<Map<String, Object>> select(Condition condition) {
    /**
     * 此处先将就
     * 等扩展字段完善以后再来完善
     */
    return select(condition, QueryAction.ALL_FIELD);
  }

  final public List<Map<String, Object>> select(Condition condition,Column...columns) {
    final List<Map<String, Object>> queryResult = moreBatis.select().select(columns)
        .from(TableAnnoUtil.getTableSource(entityClass))
        .where(condition).execute();
    final List<Map<String, Object>> camelizedResult = underlineKeyMapListToCamel(queryResult);
    return camelizedResult;
  }

  final public PageResult<Map<String, Object>> select(Condition condition, int pageNum,int pageSize) {
    //同上
    return select(condition,pageNum,pageSize,QueryAction.ALL_FIELD);
  }

  final public PageResult<Map<String, Object>> select(Condition condition, int pageNum,int pageSize,Column ...columns) {
    final PageResult<Map<String, Object>> queryResult = moreBatis.select().select(columns)
        .from(TableAnnoUtil.getTableSource(entityClass))
        .where(condition).selectPage(pageNum, pageSize);
    final PageResult<Map<String, Object>> camelizedResult = underlineKeyMapListToCamel(queryResult);
    return camelizedResult;
  }

  @Deprecated
  public PageResult<Map<String, Object>> select(Map args, int pageNum,int pageSize) {
    args = mapFilter(args);
    return select(convertMapToFieldConditions(args),pageNum,pageSize);
  }

  @Deprecated
  public List<Map<String, Object>> select(Map args) {
    args = mapFilter(args);
    return select(convertMapToFieldConditions(args));
  }

  @Deprecated
  public PageResult<Map<String, Object>> singleInputSelect(Collection<String> column,
      Collection<String> value, int pageNum, int pageSize) {
    return select(createBlankQuery(column, value),pageNum,pageSize);
  }

  @Deprecated
  public List<Map<String, Object>> singleInputSelect(Collection<String> column,
      Collection<String> value) {
    return select(createBlankQuery(column, value));
  }

  public int insert(Map args) {
    args = mapFilter(args);
    args.remove("etc");
    InsertAction insertAction = moreBatis.insert().into(table).cols(fieldNames).values(args);
    return insertAction.execute();
  }

  public int update(Map args) {
    args = mapFilter(args);
    args.remove("etc");
    final Map<String, Object> finalCopy = args;
    return update(finalCopy,new And(pkFields.stream().map((pk) -> {
      return new FieldCondition(pk, Operator.EQUAL, finalCopy.get(pk));
    }).collect(Collectors.toList())));
  }

  public int update(Map args,Condition condition) {
    UpdateAction updateAction = moreBatis.update()
        .from(table)
        .set(args)
        .where(condition);
    return updateAction.execute();
  }

  public int delete(Map args) {
    args = mapFilter(args);
    args.remove("etc");
    return delete(convertMapToFieldConditions(args));
  }

  public int delete(Condition condition) {
    DeleteAction deleteAction = moreBatis.delete()
        .from(table)
        .where(condition);
    return deleteAction.execute();
  }

  final public And convertMapToFieldConditions(Map<String, Object> args) {
    return new And(args.entrySet().stream().filter((entry) -> {
      final String key = entry.getKey();
      final Object value = entry.getValue();
      return fieldNames.contains(key);
    }).map((entry) -> {
      final Object value = entry.getValue();
      if (value instanceof Collection) {
        return new FieldCondition(entry.getKey(), Operator.IN, value);
      } else {
        return new FieldCondition(entry.getKey(), Operator.EQUAL, value);
      }
    }).collect(Collectors.toList()));
  }

  final public Or createBlankQuery(Collection<String> columns, Collection<String> values) {
    List<Condition> conditions = new LinkedList<>();
    for (String column : columns) {
      for (String value : values) {
        conditions.add(new FieldCondition(column, Operator.LIKE_FULL, value));
      }
    }
    return new Or(conditions);
  }

  final public PageResult<Map<String, Object>> underlineKeyMapListToCamel(
      PageResult<Map<String, Object>> origin) {
    origin.setResult(underlineKeyMapListToCamel(origin.getResult()));
    return origin;
  }

  final public List<Map<String, Object>> underlineKeyMapListToCamel(List<Map<String, Object>> origin) {
    return origin.stream().map((row) -> {
      HashMap<String, Object> out = new HashMap<>();
      for (Entry<String, Object> entry : row.entrySet()) {
        out.put(UtilsTool.underlineToCamel(entry.getKey(), false), entry.getValue());
      }
      return out;
    }).collect(Collectors.toList());
  }

  final public Map<String, Object> mapFilter(Map<String, Object> map) {
    return mapFilter(map, isRemoveNull(), isRemoveBlank());
  }

  final public Map<String, Object> mapFilter(Map<String, Object> map, boolean removeNull,
      boolean removeBlank) {
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

  final private Set<String> getFieldNamesFromClass(Class clz) {
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
