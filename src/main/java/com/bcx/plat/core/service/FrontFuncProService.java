package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.Map;

/**
 * 前端功能块属性项信息维护 service层
 * Created by Wen Tiehu on 2017/8/4.
 */
public interface FrontFuncProService<T extends FrontFuncPro> {


    /**
     *  查询前端功能块属性项信息
     * @param map 接受传入进来的map参数
     * @return 返回查询数据以及状态
     */
    ServiceResult<T> queryFronFuncPro(Map map);

    /**
     * 新增前端功能块属性项信息维护
     * @param entity 前端功能块属性实体
     * @return 返回新增状态
     */
    ServiceResult<T> addFronFuncPro(T entity);

    /**
     * 修改前端功能块属性项信息维护
     * @param entity 前端功能块属性实体
     * @return 返回修改状态
     */
    ServiceResult<T> modifyFronFuncPro(T entity);

    /**
     * 删除前端功能块属性项信息维护
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    ServiceResult<T> delete(String delData);


}
