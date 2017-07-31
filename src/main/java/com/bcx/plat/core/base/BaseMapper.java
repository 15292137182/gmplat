package com.bcx.plat.core.base;

import java.util.List;

/**
 * mybatis sql映射自定义接口
 *
 * @author wzp
 * 2015年12月17日
 */
public interface BaseMapper<T> {

    /**
     * 查询方法,返回记录List；
     * 参数: bean
     * <p>
     * 2015年12月17日
     */
    List<T> select(T bean);

    /**
     * 插入方法
     * 参数: Map
     * <p>
     * 2015年12月17日
     */
    int insert(T bean);


    /**
     * 更新方法
     * 参数: bean
     * <p>
     * 2015年12月17日
     */
    int update(T bean);


    /**
     * 删除方法
     * 参数: Map
     * <p>
     * 2015年12月17日
     */
    int delete(T bean);

}
