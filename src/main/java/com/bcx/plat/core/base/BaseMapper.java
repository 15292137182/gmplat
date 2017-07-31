package com.bcx.plat.core.base;

import java.util.List;

/**
 * mybatis sql映射自定义接口
 * 
 * @author wzp
 * @time 2015年12月17日
 */
public interface BaseMapper {
	
	/**
	 * 查询方法,返回记录List；
	 * 参数: Map
	 * 
	 * @time 2015年12月17日
	 */
	List select(Object map);
	
	/**
	 * 插入方法
	 * 参数: Map
	 * 
	 * @time 2015年12月17日
	 */
	int insert(Object map);

	
	/**
	 * 更新方法
	 * 参数: Map
	 * 
	 * @time 2015年12月17日
	 */
	int update(Object map);

	
	/**
	 * 删除方法
	 * 参数: Map
	 * 
	 * @time 2015年12月17日
	 */
	int delete(Object map);

}
