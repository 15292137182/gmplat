package com.bcx.plat.core.base;

import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.UtilsTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.*;

import static com.bcx.plat.core.utils.UtilsTool.createBlankQuery;

/**
 * BaseService
 * <p>
 * Create By HCL at 2017/8/1
 */
public abstract class BaseService<T extends BaseEntity<T>> {

  /**
   * 日志
   */
  private Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * @return 泛型 T 的 class
   */
  @SuppressWarnings("unchecked")
  private Class<T> getTClass() {
    return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }

  /**
   * 根据参数查询实体类，不分页
   *
   * @param condition 条件
   * @return 返回查询结果
   */
  public List<T> select(Condition condition) {
    return select(condition, null);
  }

  /**
   * 根据参数查询，查询泛型为 map
   *
   * @param condition 条件
   * @return 返回查询结果
   */
  public List<Map> selectMap(Condition condition) {
    return selectMap(condition, null);
  }

  @SuppressWarnings("unchecked")
  public List<T> select(Condition condition, List<Order> orders) {
    List<T> result = new ArrayList<>();
    try {
      result = getTClass().newInstance().selectList(condition, orders, true);
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public List<Map> selectMap(Condition condition, List<Order> orders) {
    List<Map> result = new ArrayList<>();
    try {
      result = getTClass().newInstance().selectList(condition, orders, false);
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 分页查询,返回查询结果为 类型 T
   *
   * @param condition 条件
   * @param orders    排序
   * @param pageNum   页码
   * @param pageSize  页面大小
   * @return 返回查询结果
   */
  public PageResult<T> selectPage(Condition condition, List<Order> orders, int pageNum, int pageSize) {
    try {
      return getTClass().newInstance().selectPage(condition, pageNum, pageSize, orders);
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 分页查询,返回查询结果为 类型 Map<String, Object>
   *
   * @param condition 条件
   * @param orders    排序
   * @param pageNum   页码
   * @param pageSize  页面大小
   * @return 返回查询结果
   */
  public PageResult<Map<String, Object>> selectPageMap(Condition condition, List<Order> orders, int pageNum, int pageSize) {
    try {
      return getTClass().newInstance().selectPageMap(condition, pageNum, pageSize, orders);
    } catch (InstantiationException | IllegalAccessException e) {
      logger.error(e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 分页查询
   *
   * @param condition 条件
   * @param pageNum   页码
   * @param pageSize  页面大小
   * @return 返回查询结果
   */
  public PageResult<T> select(Condition condition, int pageNum, int pageSize) {
    return selectPage(condition, null, pageNum, pageSize);
  }

  /**
   * 空格输入查询
   *
   * @param blankInput 输入框的值
   * @param pageNum    页面号
   * @param pageSize   页面大小
   * @param orders     排序
   * @return 返回查询结果, 泛型为javaBean
   */
  @SuppressWarnings("unchecked")
  public PageResult<T> blankQuerySelect(String blankInput,
                                        List blankFields,
                                        Condition condition,
                                        int pageNum,
                                        int pageSize,
                                        List<Order> orders) {
    return blankQuerySelectCommon(blankInput, blankFields, condition, pageNum, pageSize, orders, true);
  }

  /**
   * 空格输入查询
   *
   * @param blankInput 输入框的值
   * @param pageNum    页面号
   * @param pageSize   页面大小
   * @param orders     排序
   * @return 返回查询结果, 泛型为 Map
   */
  @SuppressWarnings("unchecked")
  public PageResult<Map> blankQuerySelectMap(String blankInput,
                                             List blankFields,
                                             Condition condition,
                                             int pageNum,
                                             int pageSize,
                                             List<Order> orders) {
    return blankQuerySelectCommon(blankInput, blankFields, condition, pageNum, pageSize, orders, false);
  }


  private PageResult blankQuerySelectCommon(String blankInput,
                                            List blankFields,
                                            Condition condition,
                                            int pageNum,
                                            int pageSize,
                                            List<Order> orders,
                                            boolean convert) {
    List<Condition> conditions = new ArrayList<>();
    Or or = getSingleInputOr(blankInput, blankFields);
    if (null != or) {
      conditions.add(or);
    }
    if (null != condition) {
      conditions.add(condition);
    }
    PageResult result = null;
    try {
      if (convert) {
        result = getTClass().newInstance().selectPage(new And(conditions), pageNum, pageSize, orders);
      } else {
        result = getTClass().newInstance().selectPageMap(new And(conditions), pageNum, pageSize, orders);
      }

    } catch (InstantiationException | IllegalAccessException e) {
      logger.error("错误消息：{}", e.getMessage());
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 根据空格查询的值构造条件
   *
   * @param singleValue 输入框的值
   * @return 返回条件
   */
  private Or getSingleInputOr(String singleValue, Collection<String> blankFields) {
    Set<String> values = UtilsTool.collectToSet(singleValue);
    if (!values.isEmpty()) {
      Collection<String> keys = blankFields;
      if (null == keys) {
        try {
          keys = getTClass().newInstance().toMap().keySet();
        } catch (InstantiationException | IllegalAccessException e) {
          e.printStackTrace();
        }
      }
      if (keys != null) {
        return createBlankQuery(keys, values);
      }
    }
    return null;
  }

}