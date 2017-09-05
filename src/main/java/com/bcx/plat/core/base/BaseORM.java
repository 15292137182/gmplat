package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.SpringContextHolder;
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
   * @param conditions 条件
   * @return 状态码
   */
  public int update(List<Condition> conditions) {
    return update(conditions, false);
  }

  /**
   * 更新所有数据  包括 null
   *
   * @param conditions 条件
   * @return 状态码
   */
  public int updateAllColums(List<Condition> conditions) {
    return update(conditions, true);
  }

  /**
   * 更新数据，根据主键，主键无效不更新       ！！！空字符串更新，null不更新
   *
   * @return 状态码
   */
  public int updateById() {
    Serializable _rowId = this.getPk();
    if (null != _rowId) {
      List<Condition> conditions = new ArrayList<>();
      conditions.add(NOT_DELETE_OR);
      conditions.add(new FieldCondition("rowId", Operator.EQUAL, _rowId));
      return update(conditions, false);
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
      List<Condition> conditions = new ArrayList<>();
      conditions.add(NOT_DELETE_OR);
      conditions.add(new FieldCondition("rowId", Operator.EQUAL, _rowId));
      return update(conditions, true);
    } else {
      return -1;
    }
  }

  /**
   * 更新条件
   *
   * @param conditions 条件
   * @param allColumns 所有字段
   * @return 更新后的状态码
   */
  @SuppressWarnings("unchecked")
  private int update(List<Condition> conditions, boolean allColumns) {
    Map map = this.toMap();
    if (null != map) {
      if (allColumns) {
        map.forEach((key, value) -> {
          if (null == value) {
            map.remove(key);
          }
        });
      }
      UpdateAction ua = MORE_BATIS.update(this.getClass(), this.toMap());
      conditions.add(NOT_DELETE_OR);
      // 这里不接受 collection -.-
      ua.where(new And(conditions));
      return ua.execute();
    } else {
      return -1;
    }
  }

  private static Or getNoDeleteCondition() {
    FieldCondition isNull = new FieldCondition("deleteFlag", Operator.IS_NULL, null);
    FieldCondition notFlag = new FieldCondition("deleteFlag", Operator.EQUAL, DELETE_FLAG).not();
    return new Or(isNull, notFlag);
  }


  // TODO int delete(condition)
  // TODO int deleteAll()
  // TODO int deleteById() 删除当前这条数据
  // TODO int deleteByCondition(condition) 根据条件删除

  // TODO List<T> selectAll()
  // TODO List<T> selectOne(condition)
  // TODO List<T> selectList(condition)
  // TODO Page<List<T>> selectPage(page,condition)
  // TODO T selectById()
  // TODO T selectById(id)
  // TODO int selectCount(condition)

}
