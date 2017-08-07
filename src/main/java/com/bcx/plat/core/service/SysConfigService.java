package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.Map;

/**
 * 系统资源配置信息维护 service层
 * Created by Wen Tiehu on 2017/8/7.
 */
public interface SysConfigService<T extends SysConfig> {


    /**
     *  查询系统资源配置信息
     * @param map 接受传入进来的map参数
     * @return 返回查询数据以及状态
     */
    ServiceResult<T> querySysConfig(Map map);

    /**
     * 新增系统资源配置信息维护
     * @param entity 前端功能块属性实体
     * @return 返回新增状态
     */
    ServiceResult<T> addSysConfig(T entity);

    /**
     * 修改系统资源配置信息维护
     * @param entity 前端功能块属性实体
     * @return 返回修改状态
     */
    ServiceResult<T> modifySysConfig(T entity);

    /**
     * 删除系统资源配置信息维护
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    ServiceResult<T> deleteSysConfig(String delData);


}
