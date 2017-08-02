package com.bcx.plat.core.utils;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * Dao 操作，该操作方式将在不久的将来被废弃 <p> dao.getMapper(EntityMapper.class).insert(Entity entity)
 */
@Deprecated
public class Dao extends SqlSessionTemplate {

  /**
   * 依赖注入的方法
   *
   * @param sqlSessionFactory 需要注入sqlSession 工厂
   */
  public Dao(SqlSessionFactory sqlSessionFactory) {
    super(sqlSessionFactory);
  }
}