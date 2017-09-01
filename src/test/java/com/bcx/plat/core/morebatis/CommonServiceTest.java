package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.service.TestTableService;
import com.bcx.plat.core.utils.UtilsTool;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class CommonServiceTest extends BaseTest{
  @Autowired
  TestTableService testTableService;
  private BusinessObject businessObject;
  private Map<String, Object> args;
  private String testName;

  public TestTableService getTestTableService() {
    return testTableService;
  }

  public void setTestTableService(TestTableService testTableService) {
    this.testTableService = testTableService;
  }

  @Test
  @Transactional
  @Rollback
  public void testPageQuery(){
    BusinessObject businessObject=new BusinessObject();
    businessObject.setObjectName(testName);
    for(int i=0;i<40;i++) testTableService.insert(businessObject.buildCreateInfo().toMap());
    Map<String,Object> args=new HashMap<>();
    args.put("objectName",testName);
    PageResult<Map<String, Object>> result = testTableService
        .select(args, 1, 20);
    Assert.assertEquals(20,result.getResult().size());
    Assert.assertEquals(60,result.getTotal());
  }

  @Test
  @Transactional
  @Rollback
  public void emptyConditionTest(){
    args.put("objectName","");
    Assert.assertTrue(testTableService.select(args).size()>0);
  }

  @Test
  @Transactional
  @Rollback
  public void testConditionalPageQuery(){
    businessObject.buildCreateInfo();
    businessObject.setObjectCode("for test2");
    for(int i=0;i<10;i++) {
      businessObject.setRowId(UtilsTool.lengthUUID(32));
      testTableService.insert(businessObject.toMap());
    }
    args.put("objectCode","for test2");
    PageResult<Map<String, Object>> result = testTableService.select(args, 1, 20);
    Assert.assertEquals(10,result.getResult().size());
    Assert.assertEquals(10,result.getTotal());
  }
  
  @Test
  @Transactional
  @Rollback
  public void testCommonDelete(){
    List<Map<String, Object>> result = testTableService.select(args);
    Assert.assertEquals(20,result.size());
    testTableService.delete(args);
    result = testTableService.select(args);
    Assert.assertEquals(0,result.size());
  }

  @Test
  @Transactional
  @Rollback
  public void testCommonInsert(){
    List<Map<String, Object>> result = testTableService.select(args);
    Assert.assertEquals(20,result.size());    
  }

  @Test
  @Transactional
  @Rollback
  public void testCommonUpdate(){
    List<Map<String, Object>> result = testTableService.select(args);
    Assert.assertEquals(20,result.size());
    HashMap<String,Object> updateMap=new HashMap<>();
    updateMap.put("rowId",result.get(0).get("rowId"));
    updateMap.put("objectName","这是一个全新的名字");

    testTableService.update(updateMap);

    result = testTableService.select(args);
    Assert.assertEquals(19,result.size());
  }

  @Test
  @Transactional
  @Rollback
  public void testBlankSelect(){
    List<Map<String,Object>> result = testTableService.select(UtilsTool.createBlankQuery(Arrays.asList("objectName", "objectCode"), Arrays.asList("ax1", "ax2")));
    int result1Before = result.size();
    result = testTableService.select(UtilsTool.createBlankQuery(Arrays.asList("objectName", "objectCode"), Arrays.asList("ax1")));
    int result2Before = result.size();
    result = testTableService.select(UtilsTool.createBlankQuery(Arrays.asList("objectName", "objectCode"), Arrays.asList("ax2")));
    int result3Before = result.size();
    BusinessObject businessObject=new BusinessObject();
    businessObject.setObjectCode("aaax1aaa");
    businessObject.setObjectName("aaax1aaa");
    businessObject.setRowId(UtilsTool.lengthUUID(32));
    businessObject.insert();
    businessObject.setObjectCode("aaax1aaa");
    businessObject.setObjectName("aaax2aaa");
    businessObject.setRowId(UtilsTool.lengthUUID(32));
    businessObject.insert();
    businessObject.setObjectCode("aaax2aaa");
    businessObject.setObjectName("aaax1aaa");
    businessObject.setRowId(UtilsTool.lengthUUID(32));
    businessObject.insert();
    businessObject.setObjectCode("aaax2aaa");
    businessObject.setObjectName("aaax2aaa");
    businessObject.setRowId(UtilsTool.lengthUUID(32));
    businessObject.insert();
    result = testTableService.select(UtilsTool.createBlankQuery(Arrays.asList("objectName", "objectCode"), Arrays.asList("ax1", "ax2")));
    int result1After = result.size();
    result = testTableService.select(UtilsTool.createBlankQuery(Arrays.asList("objectName", "objectCode"), Arrays.asList("ax1")));
    int result2After = result.size();
    result = testTableService.select(UtilsTool.createBlankQuery(Arrays.asList("objectName", "objectCode"), Arrays.asList("ax2")));
    int result3After = result.size();
    Assert.assertEquals(4,result1After-result1Before);
    Assert.assertEquals(3,result2After-result2Before);
    Assert.assertEquals(3,result3After-result3Before);
  }

  @Before
  public void setUp() {
    businessObject = new BusinessObject();
    testName = UUID.randomUUID().toString();
    businessObject.setObjectName(testName);
    businessObject.buildCreateInfo();
    for(int i=0;i<20;i++){
      businessObject.setRowId(UtilsTool.lengthUUID(32));
      testTableService.insert(businessObject.toMap());
    }
    args = new HashMap<>();
    args.put("objectName", testName);
  }
}
