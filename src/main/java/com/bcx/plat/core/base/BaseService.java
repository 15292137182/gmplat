package com.bcx.plat.core.base;

import com.bcx.plat.core.base.support.BeanInterface;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.condition.And;
import com.bcx.plat.core.morebatis.component.condition.Or;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.utils.ServerResult;
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

    /**
     * 实体类新增方法
     *
     * @return int
     */
    public int insert() {
        try {
            return getTClass().newInstance().insert();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**`
     * 通用更新方法``
     *`
     * @param condition 接受参数
     * @return int
     */
    public int update(Condition condition) {
        try {
            return getTClass().newInstance().update(condition);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 通用删除方法
     *
     * @param condition 接受参数
     * @return int
     */
    public int delete(Condition condition) {
        try {
            return getTClass().newInstance().delete(condition);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return -1;
    }


    /**
     * 主从表关联查询数据
     *
     * @param primary           主表Class
     * @param secondary         从表Class
     * @param relationPrimary   主表连接条件
     * @param relationSecondary 从表连接条件
     * @param condition         过滤参数
     * @return List
     */
    protected List<Map<String, Object>> leftAssociationQuery(Class<? extends BeanInterface> primary,
                                                             Class<? extends BeanInterface> secondary, String relationPrimary, String relationSecondary, Condition condition) {
        try {
            return getTClass().newInstance().associationQuery(primary, secondary, relationPrimary, relationSecondary, condition);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提供关联查询时的过滤条件
     *
     * @param entityClass Class
     * @param alias       别名
     * @return Field
     */
    protected Field columnByAlias(Class entityClass, String alias) {
        Field field = null;
        if (entityClass != null && (!alias.isEmpty())) {
            try {
                field = getTClass().newInstance().columnByAlias(entityClass, alias);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return field;
    }

    /**
     * 单表执行查询
     * @param entityClass   接受一个实体
     * @param condition 过滤条件
     * @return  List
     */
    public List<Map<String, Object>> singleSelect(Class entityClass, Condition condition) {
        List<Map<String, Object>> list = null;
        try {
            list = getTClass().newInstance().singleSelect(entityClass, condition);

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 单表查询添加添加排序
     * @param entityClass   接受一个实体
     * @param condition 过滤条件
     * @param orders  排序
     * @return  List
     */
    public List<Map<String, Object>> singleSelectSort(Class entityClass, Condition condition,List<Order> orders) {
        List<Map<String, Object>> list = null;
        try {
            list = getTClass().newInstance().singleSelectSort(entityClass, condition,orders);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 单个实体修改数据
     * @param entityClass 接受实体类
     * @param param 要修改的参数
     * @param condition 过滤条件
     * @return int
     */
    public int singleUpdate(Class entityClass,Map<String, Object> param,Condition condition){
        try {
            int i = getTClass().newInstance().singleUpdate(entityClass, param, condition);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}