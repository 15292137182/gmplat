package com.bcx.plat.core.base;


import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.List;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface BaseService<T extends BaseEntity> {

  /**
   * 查询方法
   *
   * @param map 查询参数
   * @param page 是否分页
   * @return 返回
   */
  ServiceResult<PageResult<Map<String,Object>>> select(Map condition, int page, int limit);

  ServiceResult<List<Map<String,Object>>> selectList(Map condition);

  ServiceResult<List<Map<String, Object>>> blankSelectList(List<String> column,List<String> value);

  ServiceResult<PageResult<Map<String, Object>>> blankSelect(List<String> column,List<String> value, int pageNum, int pageSize);

  ServiceResult<Map<String,Object>> update(Map value);

  ServiceResult<Map<String,Object>> insert(Map value);

  ServiceResult<Object> delete(Map condition);
}