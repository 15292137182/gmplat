package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import org.junit.Assert;
import org.junit.Test;

public class EntityTest
//    extends BaseTest
{
//  @Test
  public void entityTest(){
    BusinessObject businessObject=new BusinessObject();
    businessObject.setObjectName("testObject");
    businessObject.buildCreateInfo().insert();

    final String rowId=businessObject.getRowId();
    BusinessObject businessObject1=new BusinessObject();
    businessObject1.setRowId(rowId);
    businessObject1.selectByPks();
    //查找与新增测试
    Assert.assertTrue(businessObject1.getObjectName().equals("testObject"));

    businessObject1.setObjectName("secondName");
    businessObject1.update();
    businessObject1.setObjectName("anotherName");
    businessObject1.selectByPks();
    //更新测试
    Assert.assertTrue(businessObject1.getObjectName().equals("secondName"));

    businessObject1.delete();
    //删除测试
    Assert.assertTrue(businessObject1.selectByPks()==null);
  }
}
