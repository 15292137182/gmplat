package com.bcx.plat.core.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SequenceMapper {
	/**
	 * 获取当前id值
	 * 
	 * @param name	String
	 * @return id   int
	 */
	public int currval(String name);
	/**
	 * 返回下一个id值
	 * 
	 * @param name	String
	 * @return id   int
	 */
	public int nextval(String name);
	/**
	 * 更新id值
	 * 
	 * @param name	String
	 * @return 更新行数   int
	 */
	public int getNextval(String name);
	
	
}

