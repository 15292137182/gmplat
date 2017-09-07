package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.constant.Operator;
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
  private static final MoreBatis MORE_BATIS = (MoreBatis) SpringContextHolder.getBean("moreBatis");

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
    return MORE_BATIS.update(getClass(), removeNull(toMap()))
            .where(condition)
            .execute();
  }

  /**
   * 更新所有数据  包括 null
   *
   * @param condition 条件
   * @return 状态码
   */
  public int updateAllColumns(Condition condition) {
    return MORE_BATIS.updateEntity((T) this);
  }

  /**
   * 更新数据，根据主键，主键无效不更新       ！！！空字符串更新，null不更新
   *
   * @return 状态码
   */
  public int updateById() {
    return MORE_BATIS.updateEntity((T) this, (Object) null);
//    Serializable _rowId = this.getPk();
//    if (null != _rowId) {
//      return update(new FieldCondition("rowId", Operator.EQUAL, _rowId), false);
//    } else {
//      return -1;
//    }
  }

  /**
   * 更新所有数据，主键无效不更新      包括 null
   *
   * @return 状态码
   */
  public int updateAllColumnsById() {
    return MORE_BATIS.updateEntity((T) this);
  }

  /**
   * 更新条件
   *
   * @return 更新后的状态码
   */
//  @SuppressWarnings("unchecked")
//  private int update(Condition condition, boolean allColumns) {
//    Map map = this.toMap();
//    if (null != map) {
//      if (allColumns) {
//        map.forEach((key, value) -> {
//          if (null == value) {
//            map.remove(key);
//          }
//        });
//      }
//      UpdateAction ua = MORE_BATIS.update(getClass(), map).where(new And(condition, getNoDeleteCondition()));
//      return ua.execute();
//    } else {
//      return -1;
//    }
//  }

  private Map removeNull(Map map){
    map.forEach((key,value)->{
      if (value==null) {
        map.remove(key);
      }
    });
    return map;
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
  public T selectByPks() {
    return (T)MORE_BATIS.selectEntityByPks((T) this);
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
   * @param condition 条件
   * @return 结果集合
   */
  @SuppressWarnings("unchecked")
  public List selectList(Condition condition, List<Order> orders, boolean convert) {
    QueryAction qa = MORE_BATIS.select(getClass()).where(andNoDelete(condition));
    List<Map<String, Object>> result;
    if (orders != null) {
      qa = qa.orderBy(orders);
    }
    result = qa.execute();
    if (convert) {
      List<T> ts = new ArrayList<>();
      result.forEach(map -> {
        try {
          ts.add((T) getClass().newInstance().fromMap(map));
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
   * @param condition 条件
   * @param num       页面号
   * @param size      大小
   * @return 返回页面查询信息
   */
  @SuppressWarnings("unchecked")
  public PageResult<T> selectPage(Condition condition, int num, int size, List<Order> orders) {
    PageResult result = MORE_BATIS.select(getClass())
            .where(andNoDelete(condition))
            .orderBy(orders)
            .selectPage(num, size);
    List<T> data = new ArrayList<>();
    result.getResult().forEach(map -> {
      try {
        data.add((T) getClass().newInstance().fromMap((Map) map));
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
    return MORE_BATIS.select(getClass())
            .where(andNoDelete(condition))
            .orderBy(orders)
            .selectPage(num, size);
  }

  /**
   * 物理删除，所有删除模版都是物理删除
   *
   * @param condition 条件
   * @return 状态码
   */
  public int delete(Condition condition) {
    return MORE_BATIS.delete(getClass())
            .where(andNoDelete(condition))
            .execute();
  }

  /**
   * 物理删除所有数据
   *
   * @return 状态码
   */
  public int deleteAll() {
    return delete(null);
  }

  public int delete() {
    return MORE_BATIS.deleteEntity((T) this);
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

  private Condition getNoDeleteCondition() {
    return new ConditionBuilder(this.getClass())
            .or().isNull("deleteFlag").equal("deleteFlag",DELETE_FLAG).endOr()
            .buildDone();
  }

  private Condition andNoDelete(Condition condition){
    if (condition==null) {
      return getNoDeleteCondition();
    }else{
      return new ConditionBuilder(getClass())
              .and().addCondition(getNoDeleteCondition()).addCondition(condition).endAnd()
              .buildDone();
    }
  }
}
