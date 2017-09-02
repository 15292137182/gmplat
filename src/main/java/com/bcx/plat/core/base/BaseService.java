package com.bcx.plat.core.base;


import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Create By HCL at 2017/8/1
 */
public interface BaseService<T extends BaseEntity> {

  PageResult<Map<String, Object>> select(Map condition, int page, int limit);

  List<Map<String, Object>> select(Map condition);

  List<Map<String, Object>> select(Condition condition, Collection<String> columns, List<Order> orders);

  PageResult<Map<String, Object>> select(Condition condition, Collection<String> columns, List<Order> orders, int pageNum, int pageSize);

  int update(Map value);

  int insert(Map value);

  int delete(Map condition);
}