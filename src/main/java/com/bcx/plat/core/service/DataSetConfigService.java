package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.Map;

/**
 * 数据集配置信息维护 service层
 * Created by Wen Tiehu on 2017/8/8.
 */
public interface DataSetConfigService<T extends DataSetConfig> {


    /**
     *  查询数据集配置信息
     * @param map 接受传入进来的map参数
     * @return 返回查询数据以及状态
     */
    ServiceResult<T> queryDataSetConfig(Map map);

    /**
     * 新增数据集配置信息维护
     * @param entity 前端功能块属性实体
     * @return 返回新增状态
     */
    ServiceResult<T> addDataSetConfig(T entity);

    /**
     * 修改数据集配置信息维护
     * @param entity 前端功能块属性实体
     * @return 返回修改状态
     */
    ServiceResult<T> modifyDataSetConfig(T entity);

    /**
     * 删除数据集配置信息维护
     * @param dataSetConfig 接受需要删除数据的id
     * @return 返回删除的状态
     */
    ServiceResult<T> deleteDataSetConfig(DataSetConfig dataSetConfig);


}
