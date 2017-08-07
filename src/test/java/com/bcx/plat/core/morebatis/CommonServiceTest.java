package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.service.TestTableService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
  public void test(){
    Map<String,Object> args=new HashMap<>();
    args.put("rowId","d270714c-dfd1-43ba-a979-78d1e7cb");
    ServiceResult<PageResult<Map<String, Object>>> result = testTableService
        .select(args, 1, 20);
    Assert.assertEquals(result.getData().getResult().size(),1);
  }
}
