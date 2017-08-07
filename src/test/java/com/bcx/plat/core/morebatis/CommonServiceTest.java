package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.service.TestTableService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class CommonServiceTest extends BaseTest{
  @Autowired
  TestTableService testTableService;

  public TestTableService getTestTableService() {
    return testTableService;
  }

  public void setTestTableService(TestTableService testTableService) {
    this.testTableService = testTableService;
  }

  @Test
  @Transactional
  @Rollback
  public void test(){
    BusinessObject businessObject=new BusinessObject();
    businessObject.setObjectName("for test");
    for(int i=0;i<40;i++) businessObject.buildCreateInfo().insert();
    Map<String,Object> args=new HashMap<>();
    args.put("objectName","for test");
    ServiceResult<PageResult<Map<String, Object>>> result = testTableService
        .select(args, 1, 20);
    Assert.assertEquals(20,result.getData().getResult().size());
    Assert.assertEquals(40,result.getData().getTotal());
  }
}
