package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.FrontFuncPro;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Went on 2017/8/4.
 */
@Mapper
public interface FrontFuncProMapper extends BaseMapper<FrontFuncPro> {
    /**
     * 删除方法 参数: entity <p> 2015年12月17日
     *
     * @param delData
     */
    int delete(String delData);
}