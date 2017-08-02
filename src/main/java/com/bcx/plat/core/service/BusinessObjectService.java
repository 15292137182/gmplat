package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * 业务对象业务层
 * Created by Went on 2017/8/1.
 */
public interface BusinessObjectService {
    /**
     * 查询业务对象
     * 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     * @param map
     * @return
     */
    List<BusinessObject> select(Map map);

    /**
     * 新增业务对象录入框，包括：对象代码，对象名称，关联表(单选)，版本(系统生成)
     * @param businessObject
     * @return
     */
    String insert(BusinessObject businessObject);

    /**
     * 编辑对象名称字段
     * @param businessObject
     * @return
     */
    String update(BusinessObject businessObject);

    /**
     * 删除业务对象
     * @param rowId
     * @return
     */
    int delete(String rowId);

    /**
     * 获取ID对该条记录执行变更,没有生效的不能执行变更
     * @param rowId
     * @return
     */
    ServiceResult updateExecuChange(String rowId);
}
