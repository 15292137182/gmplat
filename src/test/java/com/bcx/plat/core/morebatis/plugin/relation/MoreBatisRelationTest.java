package com.bcx.plat.core.morebatis.plugin.relation;

import static org.junit.Assert.*;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
public class MoreBatisRelationTest extends BaseTest {
  @Autowired
  private MoreBatisRelation moreBatisRelation;

  @Autowired
  private MoreBatis moreBatis;
  private String name = "objRowIds";
  private String name1 = "objNames";
  private String rowId = "it works";
  private String rowId1 = "it works2";
  private Collection<String> someRowIds;
  private Collection<String> someValues;
  private List<Map<String, Object>> rows;

  public MoreBatisRelation getMoreBatisRelation() {
    return moreBatisRelation;
  }

  public void setMoreBatisRelation(
      MoreBatisRelation moreBatisRelation) {
    this.moreBatisRelation = moreBatisRelation;
  }

  public MoreBatis getMoreBatis() {
    return moreBatis;
  }

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  @Before
  public void before(){
    rows = moreBatis.select(BusinessObject.class)
        .select(moreBatis.getColumnByAlias(BusinessObject.class, Arrays.asList("rowId","objectCode")))
        .selectPage(1, 5).getResult();
    someRowIds = rows.stream().map((row)->{
          return (String)row.get("rowId");
        }).collect(Collectors.toList());
    someValues = rows.stream().map((row)->{
      return (String)row.get("objectCode");
    }).collect(Collectors.toList());
    Assert.assertTrue("数据不足2条", rows.size()>2);
    Assert.assertTrue("数据不足2条", rows.size()>2);
  }

  @Test
  public void test(){
    moreBatisRelation.putCollection(BusinessObject.class, name, rowId, Arrays.asList("1","2","3"));
    Assert.assertTrue("数据插入失败",moreBatisRelation.getCollection(BusinessObject.class, name, rowId).size()==3);
    moreBatisRelation.putCollection(BusinessObject.class, name, rowId, Arrays.asList());
    Assert.assertTrue("数据没有清空",moreBatisRelation.getCollection(BusinessObject.class, name, rowId).size()==0);
    moreBatisRelation.putCollection(BusinessObject.class, name, rowId, someRowIds);
    List<String> result = moreBatisRelation.getCollection(BusinessObject.class, name, rowId);
    Assert.assertEquals("数据获取失败",result.size(),someRowIds.size());
    List<Map<String, Object>> relationRecord = moreBatisRelation
        .getRelationRecord(BusinessObject.class, BusinessObject.class, name, rowId);
    for (Map<String, Object> afterRow : relationRecord) {
      String rowIdAfter = (String) afterRow.get("rowId");
      boolean flag=false;
      for (Map<String, Object> beforeRow : rows) {
        if (rowIdAfter.equals(beforeRow.get("rowId"))) {
          flag=true;
          break;
        }
      }
      Assert.assertTrue("实体行获取失败了,前后数据行不一样",flag);
    }
    Assert.assertTrue("反向查询失败了",moreBatisRelation.getPrimaryRowIdByValues(BusinessObject.class,name,someRowIds).contains(rowId));
    Assert.assertTrue("反向查询失败了",moreBatisRelation.getPrimaryRowIdByValues(BusinessObject.class,name,someRowIds).size()==1);
  }

  @Test
  public void allRelationTest(){
    moreBatisRelation.putCollection(BusinessObject.class, name, rowId, someRowIds);
    moreBatisRelation.putCollection(BusinessObject.class, name1, rowId, someValues);
    moreBatisRelation.putCollection(BusinessObject.class, name, rowId1, someValues);
    moreBatisRelation.putCollection(BusinessObject.class, name1, rowId1,someRowIds);
    Map<String, List> result = moreBatisRelation.getAllRelation(BusinessObject.class, rowId);
    List rowIds = result.get(name);
    List values = result.get(name1);
    Assert.assertEquals("批量获取单行全部关系失败",someRowIds,rowIds);
    Assert.assertEquals("批量获取单行全部关系失败",someValues,values);
    Map<String, Map<String, List>> result1 = moreBatisRelation.getAllRelation(BusinessObject.class, Arrays.asList(rowId,rowId1));
    result=result1.get(rowId);
    Assert.assertEquals("批量获取多行全部关系失败",someRowIds, result.get(name));
    Assert.assertEquals("批量获取多行全部关系失败",someValues, result.get(name1));
    result=result1.get(rowId1);
    Assert.assertEquals("批量获取多行全部关系失败",someValues, result.get(name));
    Assert.assertEquals("批量获取多行全部关系失败",someRowIds, result.get(name1));
  }
}