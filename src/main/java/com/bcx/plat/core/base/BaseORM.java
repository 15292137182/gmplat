package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.utils.SpringContextHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础 ORM 层，提供基本的方法
 * <p>
 * Create By HCL at 2017/9/4
 */
public abstract class BaseORM<T extends BeanInterface> implements BeanInterface<T> {

  @JsonIgnore
  private static MoreBatis moreBatis = (MoreBatis) SpringContextHolder.getBean("moreBatis");


  /**
   * 插入数据方法
   *
   * @return 插入后的状态码
   */
  @SuppressWarnings("unchecked")
  public int insert() {
    return moreBatis.insertEntity((T) this);
  }



  // TODO int update(condition) 更新数据     ！！！空字符串更新，null不更新
  // TODO int updateAll(condition)  更新所有数据  包括 null
  // TODO int updateById() 更新数据，根据主键，主键无效不更新       ！！！空字符串更新，null不更新
  // TODO int updateAllById()  更新所有数据，主键无效不更新      包括 null

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
