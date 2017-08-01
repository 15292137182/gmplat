package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.BusinessObject;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Went on 2017/8/1.
 */
@Mapper
public interface BusinessObjectMapper extends BaseMapper<BusinessObject>{
    /**
     * 根据rowId删除数据
     * @param rowId
     * @return
     */
    int delete(String rowId);

}
