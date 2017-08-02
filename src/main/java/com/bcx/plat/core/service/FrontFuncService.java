package com.bcx.plat.core.service;

import com.bcx.plat.core.base.BaseMapper;
import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.FrontFunc;

import java.util.List;
import java.util.Map;

/**
 * 前端功能块service层
 * Created by Went on 2017/8/2.
 */
public interface FrontFuncService{

    /**
     *按照名称、代码、类型 条件查询
     * @param map
     * @return
     */
    List<FrontFunc> select(Map<String,Object> map);

    /**
     * 新增数据
     * @param frontFunc
     * @return
     */
    String insert(FrontFunc frontFunc);

    /**
     * 根据id修改数据
     * @param frontFunc
     * @return
     */
    int update(FrontFunc frontFunc);

    /**
     * 根据ID删除数据
     * @param frontFunc
     * @return
     */
    int delete(FrontFunc frontFunc);


}
