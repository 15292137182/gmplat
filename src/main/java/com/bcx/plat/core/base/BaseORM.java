package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.configuration.annotation.IgnoredField;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.*;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;

/**
 * 基础 ORM 层，提供基本的方法
 * <p>
 * Create By HCL at 2017/9/4
 */
public abstract class BaseORM<T extends BeanInterface> implements BeanInterface<T> {

  @JsonIgnore
  @IgnoredField
  private static final MoreBatis MORE_BATIS = (MoreBatis) SpringContextHolder.getBean("moreBatis");
  @IgnoredField
  @JsonIgnore
  private static final Or NOT_DELETE_OR = getNoDeleteCondition();

  /**
   * 插入数据方法
   *
   * @return 插入后的状态码
   */
  @SuppressWarnings("unchecked")
  public int insert() {
    return MORE_BATIS.insertEntity((T) this);
  }

  /**
   * 更新数据     ！！！空字符串更新，null不更新
   *
   * @param condition 条件
   * @return 状态码
   */
  public int update(Condition condition) {
    return update(condition, false);
  }

  /**
   * 更新所有数据  包括 null
   *
   * @param condition 条件
   * @return 状态码
   */
  public int updateAllColumns(Condition condition) {
    return update(condition, true);
  }

  /**
   * 更新数据，根据主键，主键无效不更新       ！！！空字符串更新，null不更新
   *
   * @return 状态码
   */
  public int updateById() {
    Serializable _rowId = this.getPk();
    if (null != _rowId) {
      return update(new FieldCondition("rowId", Operator.EQUAL, _rowId), false);
    } else {
      return -1;
    }
  }

  /**
   * 更新所有数据，主键无效不更新      包括 null
   *
   * @return 状态码
   */
  public int updateAllColumnsById() {
    Serializable _rowId = this.getPk();
    if (null != _rowId) {
      return update(new FieldCondition("rowId", Operator.EQUAL, _rowId), true);
    } else {
      return -1;
    }
  }

  /**
   * 更新条件
   *
   * @param condition  条件
   * @param allColumns 所有字段
   * @return 更新后的状态码
   */
  @SuppressWarnings("unchecked")
  private int update(Condition condition, boolean allColumns) {
    Map map = this.toDbMap();
    if (null != map) {
      if (!allColumns) {
        Set keys = new HashSet(map.keySet());
        keys.forEach(key -> {
          if (null == map.get(key)) {
            map.remove(key);
          }
        });
      }
      UpdateAction ua = MORE_BATIS.update(getClass(), map).where(new And(condition, NOT_DELETE_OR));
      return ua.execute();
    } else {
      return -1;
    }
  }

  /**
   * 查询所有
   *
   * @return 结果集合
   */
  @SuppressWarnings("unchecked")
  public List<T> selectAll() {
    return selectList(null, null, true);
  }

  /**
   * 查询一个
   *
   * @return 状态码
   */
  public T selectOneById() {
    return selectOneById(getPk());
  }

  /**
   * 查询一个
   *
   * @return 结果
   */
  public T selectOneById(Serializable id) {
    if (null != id) {
      List<T> ts = selectSimple(new FieldCondition("rowId", Operator.EQUAL, id));
      if (!ts.isEmpty()) {
        return ts.get(0);
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public List<T> selectSimple(Condition... condition) {
    return selectList(new And(condition), null, true);
  }

  /**
   * 查询所有
   *
   * @return 结果集合
   */
  @SuppressWarnings("unchecked")
  public List<Map> selectAllMap() {
    return selectList(null, null, false);
  }

  /**
   * 查询一个
   *
   * @return 状态码
   */
  public Map selectOneByIdMap() {
    return selectOneByIdMap(getPk());
  }

  /**
   * 查询一个
   *
   * @return 结果
   */
  public Map selectOneByIdMap(Serializable id) {
    if (null != id) {
      List<Map> ts = selectSimpleMap(new FieldCondition("rowId", Operator.EQUAL, id));
      if (!ts.isEmpty()) {
        return ts.get(0);
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  public List<Map> selectSimpleMap(Condition... condition) {
    return selectList(new And(condition), null, false);
  }

  /**
   * 查询
   *
   * @param condition 条件
   * @return 结果集合
   */
  @SuppressWarnings("unchecked")
  public List selectList(Condition condition, List<Order> orders, boolean convert) {
    Condition and;
    if (null != condition) {
      and = new And(condition, NOT_DELETE_OR);
    } else {
      and = NOT_DELETE_OR;
    }
    QueryAction qa = MORE_BATIS.select(getClass()).where(and);
    List<Map<String, Object>> result;
    if (orders != null) {
      qa = qa.orderBy(orders);
    }
    result = qa.execute();
    if (convert) {
      List<T> ts = new ArrayList<>();
      result.forEach(map -> {
        try {
          ts.add((T) getClass().newInstance().fromDbMap(map));
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
        }
      });
      return ts;
    } else {
      return result;
    }
  }

  /**
   * 分页查询
   *
   * @param condition 条件
   * @param num       页面号
   * @param size      大小
   * @return 返回页面查询信息
   */
  @SuppressWarnings("unchecked")
  public PageResult<T> selectPage(Condition condition, int num, int size, List<Order> orders) {
    Condition and;
    if (null != condition) {
      and = new And(condition, NOT_DELETE_OR);
    } else {
      and = NOT_DELETE_OR;
    }
    PageResult result = MORE_BATIS.select(getClass()).where(and).orderBy(orders).selectPage(num, size);
    List<T> data = new ArrayList<>();
    result.getResult().forEach(map -> {
      try {
        data.add((T) getClass().newInstance().fromDbMap((Map) map));
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
    result.setResult(data);
    return result;
  }

  /**
   * 分页查询
   *
   * @param condition 条件
   * @param num       页面号
   * @param size      大小
   * @return 返回页面查询信息
   */
  @SuppressWarnings("unchecked")
  public PageResult<Map<String, Object>> selectPageMap(Condition condition, int num, int size, List<Order> orders) {
    Condition and;
    if (null != condition) {
      and = new And(condition, NOT_DELETE_OR);
    } else {
      and = NOT_DELETE_OR;
    }
    return MORE_BATIS.select(getClass()).where(and).orderBy(orders).selectPage(num, size);
  }

  /**
   * 物理删除，所有删除模版都是物理删除
   *
   * @param condition 条件
   * @return 状态码
   */
  public int delete(Condition condition) {
    Condition and;
    if (null != condition) {
      and = new And(condition, NOT_DELETE_OR);
    } else {
      and = NOT_DELETE_OR;
    }
    return MORE_BATIS.delete(getClass()).where(and).execute();

  }

  /**
   * 物理删除所有数据
   *
   * @return 状态码
   */
  public int deleteAll() {
    return delete(null);
  }

  public int deleteById() {
    return deleteById(getPk());
  }

  public int deleteByIds(Collection<Serializable> ids) {
    return delete(new FieldCondition("rowId", Operator.IN, ids));
  }

  public int deleteById(Serializable id) {
    if (null == id) {
      return -1;
    } else {
      return delete(new FieldCondition("rowId", Operator.EQUAL, id));
    }
  }

  private static Or getNoDeleteCondition() {
    FieldCondition isNull = new FieldCondition("deleteFlag", Operator.IS_NULL, null);
    FieldCondition notFlag = new FieldCondition("deleteFlag", Operator.EQUAL, DELETE_FLAG).not();
    return new Or(isNull, notFlag);
  }

  /**
   * 主从表关联查询数据
   *
   * @param primary           主表Class
   * @param secondary         从表Class
   * @param relationPrimary   主表连接条件
   * @param relationSecondary 从表连接条件
   * @param condition         过滤参数
   * @return List
   */
  public List<Map<String, Object>> associationQuery(Class<? extends BeanInterface> primary,
                                                    Class<? extends BeanInterface> secondary, String relationPrimary, String relationSecondary, Condition condition) {
    List<Map<String, Object>> execute = null;
    if (condition != null) {
      execute = MORE_BATIS.select(primary, secondary, relationPrimary, relationSecondary, JoinType.LEFT_JOIN).where(condition).execute();
    }
    return execute;
  }

  /**
   * 提供关联查询时的过滤条件
   *
   * @param entityClass Class
   * @param alias       别名
   * @return
   */
  public Field columnByAlias(Class<T> entityClass, String alias) {
    Field columnByAlias = null;
    if (entityClass != null && (!alias.isEmpty())) {
      columnByAlias = MORE_BATIS.getColumnByAlias(entityClass, alias);
    }
    return columnByAlias;
  }

  /**
   * 单表查询
   *
   * @param entity    接受实体类
   * @param condition 过滤条件
   * @return list
   */
  public List<Map<String, Object>> singleSelect(Class entity, Condition condition) {
    List<Map<String, Object>> execute = null;
    if (entity != null && condition != null) {
      execute = MORE_BATIS.select(entity).where(condition).execute();
    }
    return execute;
  }

  /**
   * 单表查询添加添加排序
   * @param entity  接受实体类
   * @param condition 过滤条件
   * @param orders  排序
   * @return  list
   */
  public List<Map<String, Object>> singleSelectSort(Class entity, Condition condition,List<Order> orders) {
    List<Map<String, Object>> execute = null;
    if (entity != null && condition != null) {
      execute = MORE_BATIS.select(entity).where(condition).orderBy(orders).execute();
    }
    return execute;
  }
}
