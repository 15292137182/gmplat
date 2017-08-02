package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.FrontFunc;
import com.bcx.plat.core.mapper.FrontFuncMapper;
import com.bcx.plat.core.service.FrontFuncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Went on 2017/8/2.
 */
@Service
@Transactional
public class FromtFuncServiceImpl extends BaseService implements FrontFuncService{
    @Autowired
    private FrontFuncMapper frontFuncMapper;

    /**
     * 按照名称、代码、类型 条件查询
     *
     * @param map 接受参数
     * @return    返回查询结果
     */
    @Override
    public List<FrontFunc> select(Map<String, Object> map) {
        return frontFuncMapper.select(map);
    }

    /**
     * 新增数据
     *
     * @param frontFunc 接受新增数据
     * @return      返回新增数据ID
     */
    @Override
    public String insert(FrontFunc frontFunc) {
        frontFunc.buildCreateInfo();
        String rowId = frontFunc.getRowId();
        frontFuncMapper.insert(frontFunc);
        return rowId;
    }

    /**
     * 根据id修改数据
     *
     * @param frontFunc 接受修改参数
     * @return
     */
    @Override
    public int update(FrontFunc frontFunc) {
        int update = frontFuncMapper.update(frontFunc);
        return update;
    }

    /**
     * 根据ID删除数据
     *
     * @param frontFunc
     * @return
     */
    @Override
    public int delete(FrontFunc frontFunc) {
        frontFuncMapper.delete(frontFunc);
        return 0;
    }
}
