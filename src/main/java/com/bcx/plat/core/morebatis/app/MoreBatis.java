package com.bcx.plat.core.morebatis.app;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import com.bcx.plat.core.morebatis.phantom.TableSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public interface MoreBatis {

  /**
   * 获取实体类全部主键字段
   *
   * @param entityClass 实体类class
   */
  Collection<Field> getPks(Class entityClass);

  /**
   * 获取实体类全部字段
   *
   * @param entityClass 实体类class
   */
  Collection<Field> getColumns(Class entityClass);

  /**
   * 获取实体类对应表
   *
   * @param entityClass 实体类class
   */
  TableSource getTable(Class entityClass);

  Field getColumnByAlias(Class entityClass, String alias);

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象 如果没有对应的字段就返回扩展属性
   *
   * @param entityClass 实体类
   * @param alias       实体类属性名称
   */
  FieldSource getColumnOrEtcByAlias(Class entityClass, String alias);

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   *
   * @param entityClass 实体类
   * @param alias       实体类属性名称
   */
  Collection<Field> getColumnByAlias(Class entityClass, Collection<String> alias);

  /**
   * 插入一个实体类
   *
   * @param entity 要插入的实体类
   * @return 返回插入的结果标识
   */
//  @Deprecated
  <T extends BeanInterface<T>> int insertEntity(T entity);

  /**
   * 根据主键删除一个实体类
   *
   * @param entity 要更新的实体类
   */
//  @Deprecated
  <T extends BeanInterface<T>> int deleteEntity(T entity);

  /**
   * 根据主键更新一个实体类
   *
   * @param entity 要更新的实体类
   */
//  @Deprecated
  <T extends BeanInterface<T>> int updateEntity(T entity);

  /**
   * 根据主键更新一个实体类
   *
   * @param entity 要更新的实体类
   */
//  @Deprecated
  <T extends BeanInterface<T>> int updateEntity(T entity, Object excluded);


  /**
   * 根据主键更新一个实体类
   *
   * @param entity 要更新的实体类
   */
//  @Deprecated
  <T extends BeanInterface<T>> int updateEntity(T entity, Collection excluded);

  /**
   * 单表查询
   *
   * @param entity 要查询的实体类class
   */
  QueryAction select(Class entity);

  /**
   * 两个表的inner join查询
   *
   * @param primary           主表class对象
   * @param secondary         从表class对象
   * @param relationPrimary   主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @return 未设置条件的查询语句对象
   */
  QueryAction select(Class primary,
                     Class secondary, String relationPrimary, String relationSecondary);


  /**
   * 两个表join查询 - 查询全列
   *
   * @param primary           主表class对象
   * @param secondary         从表class对象
   * @param relationPrimary   主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @param joinType          join类型
   * @return 未设置条件的查询语句对象
   */
  QueryAction select(Class primary,
                     Class secondary, String relationPrimary, String relationSecondary, JoinType joinType);

  /**
   * 两个表join查询 - 指定查询列
   *
   * @param primary           主表class对象
   * @param secondary         从表class对象
   * @param relationPrimary   主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @param fields            指定查询列
   * @param joinType          join类型
   * @return 未设置条件的查询语句对象
   */
  QueryAction select(Class primary,
                     Class secondary,  String relationPrimary, String relationSecondary,Collection<Field> fields, JoinType joinType);

  QueryAction selectStatement();

  /**
   * 更新操作（未设置条件）
   *
   * @param entity 实体类的对象
   * @param value  要更新的值(支持null)
   */
  UpdateAction update(Class entity, Map<String, Object> value);

  /**
   * 多行插入
   *
   * @param entity 实体类的class
   * @param values 插入数据库的多行数据
   */
  InsertAction insert(Class entity, List<Map<String, Object>> values);

  /**
   * 单行插入
   *
   * @param entity 实体类的class
   * @param value  插入数据库的单行数据
   * @return 未执行的插入语句对象
   */
  InsertAction insert(Class entity, Map<String, Object> value);

  /**
   * 删除(未设置条件)
   *
   * @param entity 实体类的class
   * @return 未设置条件的删除语句对象
   */
  DeleteAction delete(Class entity);

  InsertAction insertStatement();

  DeleteAction deleteStatement();

  List<Map<String, Object>> execute(QueryAction queryAction);

  int execute(InsertAction insertAction);

  int execute(UpdateAction updateAction);

  int execute(DeleteAction deleteAction);

  String getDefaultMapColumnAlias();
}