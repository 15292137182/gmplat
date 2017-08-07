package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.base.BaseConstants;
import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.SysConfig;
import com.bcx.plat.core.mapper.SysConfigMapper;
import com.bcx.plat.core.service.SysConfigService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by Wen Tiehu on 2017/8/7.
 */
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 查询前端功能块属性项信息
     *
     * @param map 接受传入进来的map参数
     * @return 返回查询数据以及状态
     */
    @Override
    public ServiceResult querySysConfig(Map map) {
        List<SysConfig> result = sysConfigMapper.select(map);
        if (result.size()==0){
            return  new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.OPERATOR_FAIL);
        }
        return  new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS,result);

    }

    /**
     * 新增前端功能块属性项信息维护
     *
     * @param entity 前端功能块属性实体
     * @return 返回新增状态
     */
    @Override
    public ServiceResult addSysConfig(SysConfig entity) {
        entity.buildCreateInfo();
        String rowId = entity.getRowId();
        int result = sysConfigMapper.insert(entity);
        if (result!=1){
            return  new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.OPERATOR_FAIL);
        }
        return  new ServiceResult(BaseConstants.STATUS_SUCCESS,Message.OPERATOR_SUCCESS,rowId);
    }

    /**
     * 修改前端功能块属性项信息维护
     *
     * @param entity 前端功能块属性实体
     * @return 返回修改状态
     */
    @Override
    public ServiceResult modifySysConfig(SysConfig entity) {
        entity.buildModifyInfo();
        int update = sysConfigMapper.update(entity);
        if(update != 1){
            return  new ServiceResult().Msg(BaseConstants.STATUS_FAIL,Message.OPERATOR_FAIL);
        }
        return  new ServiceResult().Msg(BaseConstants.STATUS_SUCCESS,Message.UPDATE_SUCCESS );
    }

    /**
     * 删除前端功能块属性项信息维护
     *
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    @Override
    public ServiceResult deleteSysConfig(String delData) {
        sysConfigMapper.delete(delData);
        return new ServiceResult().Msg(BaseConstants.STATUS_SUCCESS,Message.DELETE_SUCCESS);
    }
}
