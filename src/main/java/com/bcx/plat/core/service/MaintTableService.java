package com.bcx.plat.core.service;

import com.bcx.plat.core.domain.MaintTablePojo;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.List;

/**
 * Created by Went on 2017/7/31.
 */
public interface MaintTableService {
    /**
     *  维护数据库表查询
     * @param str 根据条件查询
     * @return
     */
    List selectMaint(String str);

    /**
     *  根据ID查询维护数据库字段
     * @param rowId
     * @return
     */
    List<MaintTablePojo> selectById(int rowId);
}
