package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.constants.Message;
import com.bcx.plat.core.entity.FrontFuncPro;
import com.bcx.plat.core.mapper.FrontFuncProMapper;
import com.bcx.plat.core.service.FrontFuncProService;
import com.bcx.plat.core.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Message.*;

/**
 * Created by Wen Tiehu on 2017/8/4.
 */
@Service
public class FrontFuncProServiceImpl implements FrontFuncProService {

    @Autowired
    private FrontFuncProMapper frontFuncProMapper;
    /**
     * 查询前端功能块属性项信息
     *
     * @param map 接受传入进来的map参数
     * @return 返回查询数据以及状态
     */
    @Override
    public ServiceResult queryFronFuncPro(Map map) {
        List<FrontFuncPro> result = frontFuncProMapper.select(map);
        if (result.size()==0){
            return new ServiceResult(Message.QUERY_FAIL,"");
        }
        return new ServiceResult( result,QUERY_SUCCESS);
    }

    /**
     * 新增前端功能块属性项信息维护
     *
     * @param entity 前端功能块属性实体
     * @return 返回新增状态
     */
    @Override
    public ServiceResult addFronFuncPro(FrontFuncPro entity) {
        entity.buildCreateInfo();
        String rowId = entity.getRowId();
        int result = frontFuncProMapper.insert(entity);
        if (result!=1){
            return new ServiceResult(null,NEW_ADD_FAIL);
        }
        return new ServiceResult(rowId,NEW_ADD_SUCCESS);
    }

    /**
     * 修改前端功能块属性项信息维护
     *
     * @param entity 前端功能块属性实体
     * @return 返回修改状态
     */
    @Override
    public ServiceResult modifyFronFuncPro(FrontFuncPro entity) {
        entity.buildModifyInfo();
        int update = frontFuncProMapper.update(entity);
        if(update != 1){
            return  new ServiceResult<>(UPDATE_FAIL ,"");
        }
        return  new ServiceResult<>(UPDATE_SUCCESS ,"");
    }

    /**
     * 删除前端功能块属性项信息维护
     *
     * @param delData 接受需要删除数据的id
     * @return 返回删除的状态
     */
    @Override
    public ServiceResult delete(String delData) {
        frontFuncProMapper.delete(delData);
        return new ServiceResult<>(DELETE_SUCCESS,"");
    }
}
