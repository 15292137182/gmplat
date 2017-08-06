package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.INVALID;
import static com.bcx.plat.core.base.BaseConstants.UNAVAILABLE;
import static com.bcx.plat.core.utils.UtilsTool.lengthUUID;

/**
 * Created by Went on 2017/8/1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessObjectMapperTest extends BaseTest{

    @Autowired
    private BusinessObjectMapper businessObjectMapper;

    static String newRowId = null;

    /**
     * 测试业务对象Mapper接口的查询
     */
    @Test
    public void AselectTest(){
        List<BusinessObject> select = businessObjectMapper.select(null);
        for (int i=0;i<select.size();i++){
            BusinessObject businessObject = select.get(i);
            String rowId = businessObject.getRowId();
            logger.info("++++++++++++"+rowId);
        }
    }

    /**
     * 测试业务对象Mapper接口的新增
     */
    @Test
    public void BinsertTest(){
        BusinessObject businessObject = new BusinessObject();
        businessObject.setObjectCode("001");
        businessObject.setRowId(lengthUUID(32));
        businessObject.setObjectName("002");
        businessObject.setStatus(INVALID);
        businessObject.setRelateTableRowId("1112");
        businessObject.buildCreateInfo();
        businessObjectMapper.insert(businessObject);
        newRowId = businessObject.getRowId();
    }

    /**
     * 测试业务对象Mapper接口的更新
     */
    @Test
    public void CupdateTest(){
        BusinessObject businessObject = new BusinessObject();
        businessObject.setObjectCode("0001");
        businessObject.setObjectName("0002");
        businessObject.setRowId(newRowId);
        businessObject.buildModifyInfo();
        businessObjectMapper.update(businessObject);
    }

   /**
     * 测试业务对象Mapper接口的删除
     */
    @Test
    public void DdeleteTest(){
        String rowId = newRowId;
        businessObjectMapper.delete(rowId);
    }


}
