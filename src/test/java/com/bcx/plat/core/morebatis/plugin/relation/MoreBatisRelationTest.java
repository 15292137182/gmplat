package com.bcx.plat.core.morebatis.plugin.relation;

import static org.junit.Assert.*;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
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
  private String name = "it's test";
  private String rowId = "nonRowId";
  private Collection<String> someRowIds;
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
        .select(moreBatis.getColumnByAlias(BusinessObject.class, "rowId"))
        .selectPage(1, 5).getResult();
    someRowIds = rows.stream().map((row)->{
          return (String)row.get("rowId");
        }).collect(Collectors.toList());
  }

  @Test
  public void test(){
    Assert.assertTrue("数据不足2条", someRowIds.size()>2);
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
}