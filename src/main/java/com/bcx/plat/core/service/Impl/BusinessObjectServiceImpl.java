package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.mapper.BusinessObjectMapper;
import com.bcx.plat.core.service.BusinessObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

/**
 * Created by Went on 2017/8/1.
 */
@Service
public class BusinessObjectServiceImpl implements BusinessObjectService{
    @Autowired
    private BusinessObjectMapper businessObjectMapper;
    /**
     * 查询业务对象
     * 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     *
     * @param map
     * @return
     */
    @Override
    public List<BusinessObject> select(Map map) {

        try {
            if (map.size() != 0) {
                List<BusinessObject> select = businessObjectMapper.select(map);
                return select;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增业务对象录入框，包括：对象代码，对象名称，关联表(单选)，版本(系统生成)
     *
     * @param businessObject
     * @return
     */
    @Override
    public String insert(BusinessObject businessObject) {
        try{
            //唯一标示用uuid生成
            String rowId = UUID.randomUUID().toString();
            businessObject.setRowId(rowId);
            //业务对象版本号默认从1.0开始
            businessObject.setVersion("1.0");
            businessObject.buildCreateInfo();
            businessObjectMapper.insert(businessObject);
            //将用户新增的rowId返回
            return rowId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 编辑对象名称字段
     *
     * @param businessObject
     * @return
     */
    @Override
    public String update(BusinessObject businessObject) {
        try{
            businessObject.buildModifyInfo();
            businessObjectMapper.update(businessObject);
            String rowId = businessObject.getRowId();
            return rowId;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 删除业务对象
     *
     * @param rowId
     * @return
     */
    @Override
    public int delete(String rowId) {
        try{
            businessObjectMapper.delete(rowId);
            return STATUS_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
        }
        return STATUS_FAIL;
    }
}
