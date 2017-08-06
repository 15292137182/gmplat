package com.bcx.plat.core.base;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

/**
 * mybatis sql映射自定义接口
 *
 * @author wzp 2015年12月17日
 */
@Mapper
public interface BaseMapper<T extends BaseEntity> {

  /**
   * 查询方法,返回记录 List 参数: entity <p> 2015年12月17日
   */
  List<T> select(Map map);

  /**
   * 插入方法 参数: entity <p> 2015年12月17日
   */
  int insert(T entity);


  /**
   * 更新方法 参数: entity <p> 2015年12月17日
   */
  int update(T entity);


  /**
   * 删除方法 参数: entity <p> 2015年12月17日
   */
  int delete(T entity);

  /**
   * 批量删除数据
   *
   * Created by hcl at 2017-08-08
   */
  int batchDelete(Map map);

}
