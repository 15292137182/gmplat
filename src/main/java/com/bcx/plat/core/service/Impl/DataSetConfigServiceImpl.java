package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.DataSetConfig;
import com.bcx.plat.core.mapper.DataSetConfigMapper;
import com.bcx.plat.core.service.DataSetConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Wen Tiehu on 2017/8/8.
 */
@Service
@Transactional
public class DataSetConfigServiceImpl implements DataSetConfigService {

    @Autowired
    private DataSetConfigMapper dataSetConfigMapper;
    /**
     * 查询数据集配置信息
     *
     * @param map 接受传入进来的map参数
     * @return 返回查询数据以及状态
     */
    @Override
    public ServiceResult queryDataSetConfig(Map map) {
        List<DataSetConfig> result = dataSetConfigMapper.select(map);
        if (result.size()==0){
            return  new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.QUERY_FAIL);
        }
        return new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS,result);
    }

    /**
     * 新增数据集配置信息维护
     *
     * @param entity 前端功能块属性实体
     * @return 返回新增状态
     */
    @Override
    public ServiceResult addDataSetConfig(DataSetConfig entity) {
        entity.buildCreateInfo();
        String rowId = entity.getRowId();
        int result = dataSetConfigMapper.insert(entity);
        if (result!=1){
            return  new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.OPERATOR_FAIL);
        }
        return new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS,rowId);
    }

    /**
     * 修改数据集配置信息维护
     *
     * @param entity 前端功能块属性实体
     * @return 返回修改状态
     */
    @Override
    public ServiceResult modifyDataSetConfig(DataSetConfig entity) {
        entity.buildModifyInfo();
        int update = dataSetConfigMapper.update(entity);
        if(update != 1){
            return  new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.OPERATOR_FAIL);
        }
        return  new ServiceResult().Msg(BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS);
    }

    /**
     * 删除数据集配置信息维护
     *
     * @param dataSetConfig 接受需要删除数据的id
     * @return 返回删除的状态
     */
    @Override
    public ServiceResult deleteDataSetConfig(DataSetConfig dataSetConfig) {
        dataSetConfigMapper.delete(dataSetConfig);
        return  new ServiceResult().Msg(BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS);

    }
}
