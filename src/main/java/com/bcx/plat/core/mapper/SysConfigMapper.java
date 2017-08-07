package com.bcx.plat.core.mapper;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统资源配置mapper
 *
 * Created by Wen Tiehu on 2017/8/7.
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig>{


    /**
     * 删除方法 参数
     *
     * @param rowId
     */
    int delete(String rowId);
}
