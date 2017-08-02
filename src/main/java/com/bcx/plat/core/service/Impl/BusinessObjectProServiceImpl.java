package com.bcx.plat.core.service.Impl;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.mapper.BusinessObjectProMapper;
import com.bcx.plat.core.service.BusinessObjectProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.STATUS_FAIL;
import static com.bcx.plat.core.base.BaseConstants.STATUS_SUCCESS;

/**
 * Created by Went on 2017/8/1.
 */
@Transactional
@Service
public class BusinessObjectProServiceImpl implements BusinessObjectProService {
    @Value("BusinessObjectPro")
    private String BusinessObjectPro;

    @Autowired
    private BusinessObjectProMapper businessObjectProMapper;
    /**
     * 查询业务对象属性
     *
     * @param map
     * @return
     */
    @Override
    public List<BusinessObjectPro> select(Map map) {
        try {
            if (map.size() != 0) {
                List<BusinessObjectPro> select = businessObjectProMapper.select(map);
                return select;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 新增业务对象属性
     *
     * @param businessObjectPro
     * @return
     */
    @Override
    public String insert(BusinessObjectPro businessObjectPro) {
        try{
            //唯一标示用uuid生成
            String rowId = UUID.randomUUID().toString();
            businessObjectPro.setPropertyCode(BusinessObjectPro);
            businessObjectPro.setRowId(rowId);
            businessObjectPro.buildCreateInfo();
            businessObjectProMapper.insert(businessObjectPro);
            //将用户新增的rowId返回
            return rowId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除业务对象属性
     *
     * @param rowId
     * @return
     */
    @Override
    public int delete(String rowId) {
        try{
            businessObjectProMapper.delete(rowId);
            return STATUS_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
        }
        return STATUS_FAIL;
    }
}
