package com.bcx.plat.core.service.Impl;

import static com.bcx.plat.core.base.BaseConstants.*;
import static com.bcx.plat.core.constants.Message.*;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.mapper.BusinessObjectMapper;
import com.bcx.plat.core.mapper.BusinessObjectProMapper;
import com.bcx.plat.core.service.BusinessObjectService;
import com.bcx.plat.core.utils.ServiceResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务对象业务层
 * <p>
 * Created by Went on 2017/8/1.
 */
@Service
@Transactional
public class BusinessObjectServiceImpl implements BusinessObjectService {

    @Autowired
    private BusinessObjectMapper businessObjectMapper;
    @Autowired
    private BusinessObjectProMapper businessObjectProMapper;

    /**
     * 查询业务对象 输入空格分隔的查询关键字（对象代码、对象名称、关联表）
     */
    @Override
    public ServiceResult<BusinessObject> select(Map map) {
        ServiceResult<BusinessObject> result = null;
        if (map.size() != 0) {
            List<BusinessObject> select = businessObjectMapper.select(map);
            for (int i = 0; i < select.size(); i++) {
                String tableCname = select.get(i).getTableCname();
                String tableSchema = select.get(i).getTableSchema();
                String string = tableSchema + "(" + tableCname + ")";
                select.get(i).setTables(string);
                logger.info(QUERY_SUCCESS);
                result = new ServiceResult(select,QUERY_SUCCESS);
            }
            if (select.size() == 0) {
                result = new ServiceResult("",QUERY_FAIL);
            }
        }
        return result;
    }

    /**
     * 新增业务对象录入框，包括：对象代码，对象名称，关联表(单选)，版本(系统生成)
     *
     * @param businessObject
     * @return
     */
    @Override
    public ServiceResult<BusinessObject> insert(BusinessObject businessObject) {
        try {
            //业务对象版本号默认从1.0开始
            businessObject.setVersion("1.0");
            businessObject.buildCreateInfo();
            //新增状态默认为失效
            businessObject.setStatus(INVALID);
            String rowId = businessObject.getRowId();
            businessObjectMapper.insert(businessObject);
            //将用户新增的rowId返回
            return new ServiceResult(rowId,NEW_ADD_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceResult("",NEW_ADD_FAIL);
        }
    }

    /**
     * 编辑对象名称字段
     */
    @Override
    public ServiceResult<BusinessObject> update(BusinessObject businessObject) {
        try {
            businessObject.buildModifyInfo();
            businessObjectMapper.update(businessObject);
            String rowId = businessObject.getRowId();
            return new ServiceResult(rowId,UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceResult("",UPDATE_FAIL);
        }
    }

    /**
     * 删除业务对象
     */
    @Override
    public ServiceResult<BusinessObject> delete(String rowId) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("rowId", rowId);
            List<BusinessObjectPro> select = businessObjectProMapper.select(null);
            for (int i = 0; i < select.size(); i++) {
                String relateTableColumn = select.get(i).getRelateTableColumn();
                if (relateTableColumn.equals(rowId)) {
                    businessObjectProMapper.deleteRelateCol(map);
                }
            }
            businessObjectMapper.delete(rowId);
            return new ServiceResult("",STATUS_SUCCESS, DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ServiceResult("",STATUS_FAIL, DELETE_FAIL);
    }

    /**
     * 获取ID对该条记录执行变更,没有生效的不能执行变更
     */
    @Override
    public ServiceResult updateTakeEffect(String rowId) {
        BusinessObject select = businessObjectMapper.selectById(rowId);
        String status = select.getStatus();
        if (!(status == TAKE_EFFECT)) {
            return new ServiceResult("数据没有生效,", "");
        } else {
            businessObjectMapper.updateTakeEffect(rowId);
            return new ServiceResult("","");
        }
    }
}