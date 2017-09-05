package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.bcx.plat.core.utils.UtilsTool;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.base.BaseConstants.DELETE_FLAG;

/**
 * 基础 ORM 层，提供基本的方法
 * <p>
 * Create By HCL at 2017/9/4
 */
public abstract class BaseORM<T extends BeanInterface> implements BeanInterface<T> {

  @JsonIgnore
  private static final MoreBatis MORE_BATIS = (MoreBatis) SpringContextHolder.getBean("moreBatis");
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
  public int updateAllColums(Condition condition) {
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
  public int updateAllColumsById() {
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
    Map map = this.toMap();
    if (null != map) {
      if (allColumns) {
        map.forEach((key, value) -> {
          if (null == value) {
            map.remove(key);
          }
        });
      }
      UpdateAction ua = MORE_BATIS.update(getClass(), map, new And(condition, NOT_DELETE_OR));
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
  public List<T> selectAll() {
    return selectList(null, null);
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

  public List<T> selectSimple(Condition... condition) {
    return selectList(new And(condition), (Order) null);
  }

  /**
   * 查询
   *
   * @param condition 条件
   * @return 结果集合
   */
  @SuppressWarnings("unchecked")
  public List<T> selectList(Condition condition, Order... orders) {
    Condition and;
    if (null != condition) {
      and = new And(condition, NOT_DELETE_OR);
    } else {
      and = new And(NOT_DELETE_OR);
    }
    QueryAction qa = MORE_BATIS.select(getClass()).where(and);
    List<Map<String, Object>> result;
    if (orders != null) {
      result = qa.orderBy(orders).execute();
    } else {
      result = qa.execute();
    }
    List<T> ts = new ArrayList<>();
    result.forEach(map -> {
      try {
        ts.add((T) getClass().newInstance().fromMap(map));
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    });
    return ts;
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
  public PageResult<List<T>> selectPage(Condition condition, int num, int size, Order... orders) {
    Condition and;
    if (null != condition) {
      and = new And(condition, NOT_DELETE_OR);
    } else {
      and = new And(NOT_DELETE_OR);
    }
    PageResult result = MORE_BATIS.select(getClass()).where(and).orderBy(orders).selectPage(num, size);
    List<T> data = UtilsTool.jsonToObj(UtilsTool.objToJson(result.getResult()), List.class, getClass());
    result.setResult(data);
    return result;
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
      and = new And(NOT_DELETE_OR);
    }
    return MORE_BATIS.delete(getClass(), and).execute();

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

}
