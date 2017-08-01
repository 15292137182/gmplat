package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.base.BaseService;
import com.bcx.plat.core.entity.DBTableColumn;
import com.bcx.plat.core.mapper.DBTableColumnMapper;
import com.bcx.plat.core.service.DBTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据库字段服务类
 * Create By HCL at 2017/8/1
 */
@Service
public class DBTableColumnServiceImpl extends BaseService implements DBTableColumnService {

    @Autowired
    private DBTableColumnMapper dbTableColumnMapper;

    /**
     * 查询数据方法
     *
     * @param map 查询条件
     * @return 返回查询结果
     */
    @Override
    public List<DBTableColumn> select(Map<String, Object> map) {
        return dbTableColumnMapper.select(map);
    }

    /**
     * 新建 数据库字段信息
     *
     * @param bean 数据表bean
     * @return 操作结果状态
     */
    @Override
    public int insert(DBTableColumn bean) {
        return 0;
    }
}