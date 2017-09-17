package com.bcx.plat.core.morebatis.app;

import com.bcx.plat.core.base.support.BeanInterface;
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
import com.bcx.plat.core.morebatis.configuration.builder.EntityEntryBuilder;
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
public interface MoreBatis {
  /**
   * 获取实体类全部主键字段
   * @param entityClass 实体类class
   * @return
   */
  public <T extends BeanInterface<T>> Collection<Field> getPks(Class<T> entityClass);

  /**
   * 获取实体类全部字段
   * @param entityClass 实体类class
   * @return
   */
  public <T extends BeanInterface<T>> Collection<Field> getColumns(Class<T> entityClass);

  /**
   * 获取实体类对应表
   * @param entityClass 实体类class
   * @return
   */
  public <T extends BeanInterface<T>> TableSource getTable(Class<T> entityClass);

  public <T extends BeanInterface<T>> Field getColumnByAlias(Class<T> entityClass, String alias);

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   * @param entityClass 实体类
   * @param alias       实体类属性名称
   * @return
   */
  public <T extends BeanInterface<T>> Field getColumnByAliasWithoutCheck(Class<T> entityClass, String alias);

  /**
   * 根据实体类的class对象与实体类属性名称获取对应字段对象
   * @param entityClass 实体类
   * @param alias       实体类属性名称
   * @return
   */
  public <T extends BeanInterface<T>> List<Field> getColumnByAlias(Class<T> entityClass, Collection<String> alias);

  /**
   * 插入一个实体类
   * @param entity 要插入的实体类
   * @return 返回插入的结果标识
   */
  @Deprecated
  public <T extends BeanInterface<T>> int insertEntity(T entity);

  /**
   * 根据主键删除一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  @Deprecated
  public <T extends BeanInterface<T>> int deleteEntity(T entity);

  /**
   * 根据主键更新一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  @Deprecated
  public <T extends BeanInterface<T>> int updateEntity(T entity);

  /**
   * 根据主键更新一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  @Deprecated
  public <T extends BeanInterface<T>> int updateEntity(T entity,Object excluded);


  /**
   * 根据主键更新一个实体类
   * @param entity  要更新的实体类
   * @return
   */
  @Deprecated
  public <T extends BeanInterface<T>> int updateEntity(T entity,Collection excluded);

  /**
   * 根据主键查找对象
   * @param entity 入数据
   * @return
   */
  @Deprecated
  public <T extends BeanInterface<T>> T selectEntityByPks(T entity);

  /**
   * 单表查询
   * @param entity  要查询的实体类class
   * @return
   */
  public QueryAction select(Class<? extends BeanInterface> entity);

  /**
   * 两个表的inner join查询
   * @param primary           主表class对象
   * @param secondary         从表class对象
   * @param relationPrimary   主表中与从表关联的字段
   * @param relationSecondary 从表中与主表关联的字段
   * @return 未设置条件的查询语句对象
   */
  public QueryAction select(Class<? extends BeanInterface> primary,
                            Class<? extends BeanInterface> secondary, String relationPrimary, String relationSecondary);


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
                            Class<? extends BeanInterface> secondary, String relationPrimary, String relationSecondary, JoinType joinType);

  public QueryAction selectStatement();

  /**
   * 更新操作（未设置条件）
   * @param entity 实体类的对象
   * @param value  要更新的值(支持null)
   * @return
   */
  public UpdateAction update(Class<? extends BeanInterface> entity, Map<String, Object> value);

  /**
   * 多行插入
   * @param entity 实体类的class
   * @param values 插入数据库的多行数据
   * @return
   */
  public InsertAction insert(Class<? extends BeanInterface> entity, List<Map<String, Object>> values);

  /**
   * 单行插入
   * @param entity 实体类的class
   * @param value  插入数据库的单行数据
   * @return 未执行的插入语句对象
   */
  public InsertAction insert(Class<? extends BeanInterface> entity, Map<String, Object> value);

  /**
   * 删除(未设置条件)
   * @param entity 实体类的class
   * @return 未设置条件的删除语句对象
   */
  public DeleteAction delete(Class<? extends BeanInterface> entity);

  public InsertAction insertStatement();

  public DeleteAction deleteStatement();

  public List<Map<String, Object>> execute(QueryAction queryAction);

  public int execute(InsertAction insertAction);

  public int execute(UpdateAction updateAction);

  public int execute(DeleteAction deleteAction);
}