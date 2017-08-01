package com.bcx.plat.core.database.action.mapper;

import com.bcx.plat.core.database.action.QueryActionLite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * The interface Temp suit mapper.
 */
@Mapper
public interface TempSuitMapper {
	/**
	 * 通用查询方法.
	 *
	 * @param queryActionLite 查询参数
	 * @return 查询结果
	 */
	public List<Map<String,Object>> select(QueryActionLite queryActionLite);
}

