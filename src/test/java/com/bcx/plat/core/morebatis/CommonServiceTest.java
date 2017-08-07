package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.cctv1.PageResult;
import com.bcx.plat.core.morebatis.service.TestTableService;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    for(int i=0;i<40;i++) businessObject.buildCreateInfo().insert();
    Map<String,Object> args=new HashMap<>();
    args.put("objectName",testName);
    ServiceResult<PageResult<Map<String, Object>>> result = testTableService
        .select(args, 1, 20);
    Assert.assertEquals(20,result.getData().getResult().size());
    Assert.assertEquals(60,result.getData().getTotal());
  }

  @Test
  @Transactional
  @Rollback
  public void emptyConditionTest(){
    args.put("objectName","");
    Assert.assertTrue(((List)testTableService.selectList(args).getData()).size()>0);
  }

  @Test
  @Transactional
  @Rollback
  public void testConditionalPageQuery(){
    businessObject.setObjectCode("for test2");
    for(int i=0;i<10;i++) businessObject.buildCreateInfo().insert();
    args.put("objectCode","for test2");
    ServiceResult<PageResult<Map<String, Object>>> result = testTableService.select(args, 1, 20);
    Assert.assertEquals(10,result.getData().getResult().size());
    Assert.assertEquals(10,result.getData().getTotal());
  }
  
  @Test
  @Transactional
  @Rollback
  public void testCommonDelete(){
    ServiceResult<List<Map<String, Object>>> result = testTableService.selectList(args);
    Assert.assertEquals(20,result.getData().size());
    testTableService.delete(args);
    result = testTableService.selectList(args);
    Assert.assertEquals(0,result.getData().size());
  }

  @Test
  @Transactional
  @Rollback
  public void testCommonInsert(){
    ServiceResult<List<Map<String, Object>>> result = testTableService.selectList(args);
    Assert.assertEquals(20,result.getData().size());    
  }

  @Test
  @Transactional
  @Rollback
  public void testCommonUpdate(){
    ServiceResult<List<Map<String, Object>>> result = testTableService.selectList(args);
    Assert.assertEquals(20,result.getData().size());
    List<String> rowIds = result.getData().subList(0, 10).stream().map((row) -> {
      return (String)row.get("row_id");
    }).collect(Collectors.toList());

    HashMap<String,Object> updateMap=new HashMap<>();
    updateMap.put("rowId",rowIds.get(0));
    updateMap.put("objectName","这是一个全新的名字");

    testTableService.update(updateMap);

    result = testTableService.selectList(args);
    Assert.assertEquals(19,result.getData().size());
  }
  @Before
  public void setUp() {
    businessObject = new BusinessObject();
    testName = UUID.randomUUID().toString();
    businessObject.setObjectName(testName);
    for(int i=0;i<20;i++) businessObject.buildCreateInfo().insert();
    args = new HashMap<>();
    args.put("objectName", testName);
  }
}
