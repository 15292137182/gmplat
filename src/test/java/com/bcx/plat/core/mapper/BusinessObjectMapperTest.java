package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.UNAVAILABLE;

/**
 * Created by Went on 2017/8/1.
 */
public class BusinessObjectMapperTest extends BaseTest{

    @Autowired
    private BusinessObjectMapper businessObjectMapper;
    /**
     * 测试业务对象Mapper接口的查询
     */
    @Test
    public void selectTest(){
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
    public void insertTest(){
        BusinessObject businessObject = new BusinessObject();
        businessObject.setObjectCode("001");
        businessObject.setRowId(UUID.randomUUID().toString());
        businessObject.setObjectName("002");
        businessObject.setStatus(UNAVAILABLE);
        businessObject.setRelateTableRowId("1112");
        businessObject.buildCreateInfo();
        int select = businessObjectMapper.insert(businessObject);
        logger.info(select+"");
    }

    /**
     * 测试业务对象Mapper接口的更新
     */
    @Test
    public void updateTest(){
        BusinessObject businessObject = new BusinessObject();
        businessObject.setObjectCode("0001");
        businessObject.setObjectName("0002");
        businessObject.setRowId("a99e0db6-8652-4025-969b-3a11d3fdfe84");
        businessObject.buildModifyInfo();
        int select = businessObjectMapper.update(businessObject);
        logger.info(select+"");
    }

   /**
     * 测试业务对象Mapper接口的删除
     */
    @Test
    public void deleteTest(){
        String rowId = "165d283f-cafe-4ead-8553-607ea9c3063b";
        int select = businessObjectMapper.delete(rowId);
        logger.info(select+"");
    }


}
