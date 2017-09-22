package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.builder.OrderBuilder;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.phantom.Condition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional
@Rollback
public class OrderTest extends BaseTest {
    @Autowired
    MoreBatis moreBatis;
    private final String objectName="order test only";

    public void setMoreBatis(MoreBatis moreBatis) {
        this.moreBatis = moreBatis;
    }

    @Before
    public void createData() throws InterruptedException {
        BusinessObject businessObject=new BusinessObject();
        businessObject.setObjectName(objectName);
        businessObject.buildCreateInfo().insert();
        HashMap etcMap = new HashMap();
        businessObject.setEtc(etcMap);
        etcMap.put("a","1");
        businessObject.buildCreateInfo().insert();
        etcMap.put("a","2");
        businessObject.buildCreateInfo().insert();
        etcMap.put("a","3");
        businessObject.buildCreateInfo().insert();
        etcMap.put("a","4");
        businessObject.buildCreateInfo().insert();
        etcMap.put("a","5");
        businessObject.buildCreateInfo().insert();
        etcMap.put("b","5");
        businessObject.setRelateTableRowId("it works!");
        Thread.sleep(2000);
        businessObject.buildCreateInfo().insert();
    }
    @Test
    public void orderTest(){
        Condition condition=new ConditionBuilder(BusinessObject.class).and()
                .equal("objectName",objectName)
                .endAnd().buildDone();
        LinkedList<Order> orderDesc = new OrderBuilder(BusinessObject.class).desc("createTime").done();
        LinkedList<Order> orderAsc = new OrderBuilder(BusinessObject.class).asc("createTime").done();
        List<Map<String, Object>> resultAsc = moreBatis.select(BusinessObject.class).where(condition).orderBy(orderAsc).execute();
        List<Map<String, Object>> resultDesc = moreBatis.select(BusinessObject.class).where(condition).orderBy(orderDesc).execute();
        Assert.assertNotEquals("升序和降序结果不能相同",resultAsc.get(0).get("relateTableRowId"),"it works!");
        Assert.assertEquals("排序失败",resultDesc.get(0).get("relateTableRowId"),"it works!");
    }
}
