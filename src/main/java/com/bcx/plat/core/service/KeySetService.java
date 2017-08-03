package com.bcx.plat.core.service;

import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.Map;

/**
 * 键值集合信息
 * Created by Went on 2017/8/3.
 */
public interface KeySetService {


    /**
     * 查询键值集合信息
     * @param map 接受数据
     * @return
     */
    ServiceResult<KeySet> selete(Map<String,Object> map);

    /**
     * 新增键值集合信息
     * @param keySet
     * @return
     */
    ServiceResult<KeySet> insert(KeySet keySet);

    /**
     * 更新键值集合信息
     * @param keySet
     * @return
     */
    ServiceResult<KeySet> update(KeySet keySet);

    /**
     * 删除键值集合信息
     * @param keySet
     * @return
     */
    ServiceResult<KeySet> delete(KeySet keySet);
}
