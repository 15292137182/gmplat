package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.mapper.KeySetMapper;
import com.bcx.plat.core.service.KeySetService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Message.*;
import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * Created by Went on 2017/8/3.
 */
@Service
@Transactional
public class KeySetServiceImpl implements KeySetService{


    @Autowired
    private KeySetMapper keySetMapper;
    
    /**
     * 查询键值集合信息
     *
     * @param map 接受数据
     * @return
     */
    @Override
    public ServiceResult<KeySet> selete(Map<String, Object> map) {
        List<KeySet> select = keySetMapper.select(map);
        return new ServiceResult<>(QUERY_SUCCESS,select);
    }

    /**
     * 新增键值集合信息
     *
     * @param keySet
     * @return
     */
    @Override
    public ServiceResult<KeySet> insert(KeySet keySet) {
        keySet.buildCreateInfo();
        keySet.setKeySetCode(lengthUUID(8).toUpperCase());
        keySet.setVersion("1.0");
        int insert = keySetMapper.insert(keySet);
        if (insert != 1) {
            return new ServiceResult<>(NEW_ADD_FAIL);
        }
        return new ServiceResult<>(NEW_ADD_SUCCESS);
    }

    /**
     * 更新键值集合信息
     *
     * @param keySet
     * @return
     */
    @Override
    public ServiceResult<KeySet> update(KeySet keySet) {
        keySet.buildModifyInfo();
        int update = keySetMapper.update(keySet);
        if (update != 1) return new ServiceResult<>(UPDATE_FAIL);
        return new ServiceResult<>(UPDATE_SUCCESS);
    }

    /**
     * 删除键值集合信息
     *
     * @param keySet
     * @return
     */
    @Override
    public ServiceResult<KeySet> delete(KeySet keySet) {
        int delete = keySetMapper.delete(keySet);
        if (delete != 1) return new ServiceResult<>(DELETE_FAIL);
        return new ServiceResult<>(DELETE_SUCCESS);
    }
}
