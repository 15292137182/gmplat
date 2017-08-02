package com.bcx.plat.core.mapper;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static com.bcx.plat.core.base.BaseConstants.UNAVAILABLE;

/**
 * Created by Went on 2017/8/1.
 */
public class BusinessObjectProMapperTest extends BaseTest{

    @Autowired
    private BusinessObjectProMapper businessObjectProMapper;
    /**
     * 测试业务对象属性Mapper接口的查询
     */
    @Test
    public void selectTest(){
        List<BusinessObject> select = businessObjectProMapper.select("");
            logger.info("++++++++++++"+select);
        }

    /**
     * 测试业务对象属性Mapper接口的新增
     */
    @Test
    public void insertTest(){
        BusinessObjectPro businessObjectPro = new BusinessObjectPro();
        businessObjectPro.setRowId(UUID.randomUUID().toString());
        businessObjectPro.setPropertyCode("00001");
        businessObjectPro.setPropertyName("00002");
        businessObjectPro.setRelateTableColumn("00003");
        businessObjectPro.buildCreateInfo();
        int select = businessObjectProMapper.insert(businessObjectPro);
    }


   /**
     * 测试业务对象Mapper接口的删除
     */
    @Test
    public void deleteTest(){
        String rowId = "165d283f-cafe-4ead-8553-607ea9c3063b";
        int select = businessObjectProMapper.delete(rowId);
        logger.info(select+"");
    }


}
