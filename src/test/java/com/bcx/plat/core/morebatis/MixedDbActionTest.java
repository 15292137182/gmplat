package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.DeleteAction;
import com.bcx.plat.core.morebatis.command.InsertAction;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.command.UpdateAction;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class MixedDbActionTest{

//  @Autowired
//  MoreBatis moreBatis;
//
//  public void setMoreBatis(MoreBatis moreBatis) {
//    this.moreBatis = moreBatis;
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void MixedTest() {
//    final FieldCondition idCondition = new FieldCondition("id", Operator.EQUAL, 999);
//    final QueryAction queryAction = moreBatis.select()
//        .from(TableInfo.TEST)
//        .where(idCondition);
//    int sizeBefore = queryAction.execute().size();
//    final Set<String> columns = Sets.newSet("id", "testFirst", "testSecond");
//    Map<String, Object> row;
//    List<Map<String, Object>> rows = new LinkedList<>();
//
//    row = new HashMap<>();
//    row.put("id", 999);
//    row.put("testFirst", UUID.randomUUID().toString());
//    row.put("testSecond", UUID.randomUUID().toString());
//    rows.add(row);
//
//    row = new HashMap<>();
//    row.put("id", 999);
//    row.put("testFirst", "why???");
//    row.put("testSecond", "这是为什么？");
//    rows.add(row);
//
//    row = new HashMap<>();
//    row.put("id", 999);
//    row.put("testFirst", UUID.randomUUID().toString());
//    row.put("testSecond", UUID.randomUUID().toString());
//    rows.add(row);
//
//    row = new HashMap<>();
//    row.put("id", 999);
//    row.put("testFirst", UUID.randomUUID().toString());
//    row.put("testSecond", UUID.randomUUID().toString());
//    rows.add(row);
//
//    row = new HashMap<>();
//    row.put("id", 1000);
//    row.put("testFirst", UUID.randomUUID().toString());
//    row.put("testSecond", UUID.randomUUID().toString());
//    rows.add(row);
//
//    InsertAction insertAction = moreBatis.insertEntity()
//        .into(TableInfo.TEST)
//        .cols(columns)
//        .values(rows);
//    insertAction.execute();
//    int sizeAfter = queryAction.execute().size();
//    Assert.assertEquals(sizeAfter - sizeBefore, 4);
//    DeleteAction deleteAction = moreBatis.deleteEntity().from(TableInfo.TEST).where(idCondition);
//    Map<String, Object> args = new HashMap<>();
//    args.put("id", 2017);
//    deleteAction.execute();
//    Assert.assertEquals(queryAction.execute().size(), 0);
//    final FieldCondition id1000 = new FieldCondition("id", Operator.EQUAL, 1000);
//    QueryAction updatedQuery = moreBatis.select().selectAll()
//        .from(TableInfo.TEST)
//        .where(id1000);
//    final int id1000SizeBefore = updatedQuery.execute().size();
//    UpdateAction updateAction = moreBatis.updateEntity()
//        .from(TableInfo.TEST)
//        .set(args)
//        .where(id1000);
//    updateAction.execute();
//    final int id1000SizeAfter = updatedQuery.execute().size();
//    Assert.assertTrue(id1000SizeBefore > id1000SizeAfter);
//  }
//
//  @Test
//  @Transactional
//  @Rollback
//  public void notConditionTest(){
//    QueryAction queryAction=moreBatis.select()
//        .selectAll()
//        .from(TableInfo.TEST);
//    int total=queryAction.execute().size();
//    queryAction.where(new FieldCondition("testFirst",Operator.EQUAL,"json works"));
//    int j=queryAction.execute().size();
//    queryAction.where(new FieldCondition("testFirst",Operator.EQUAL,"json works",true));
//    int notJ=queryAction.execute().size();
//    Assert.assertNotEquals("没有测试数据",0,total);
//    Assert.assertNotEquals("至少准备几条测试查询的数据",0,j);
//    Assert.assertEquals("程序逻辑错误",total,j+notJ);
//  }
//
//  public void jsonTest() {
//    final FieldCondition jsonFieldCondition = new FieldCondition("testFirst", Operator.EQUAL,
//        "json works");
//    Map<String, Object> row = new HashMap<>();
//    Map<Object, Object> jsonTest = new HashMap<>();
//    jsonTest.put(1, "OK");
//    jsonTest.put("OK", 1);
//    row.put("id", 1024);
//    row.put("testFirst", "json works");
//    row.put("testSecond", "json works2");
//    row.put("jsonTest", jsonTest);
//    InsertAction insertAction = moreBatis.insert().into(TableInfo.TEST)
//        .cols(Arrays.asList("id", "testFirst","testSecond", "jsonTest"))
//        .values(Arrays.asList(row));
//    QueryAction queryAction = moreBatis.select().selectAll().from(TableInfo.TEST)
//        .where(jsonFieldCondition);
//    DeleteAction deleteAction = moreBatis.deleteEntity().from(TableInfo.TEST)
//        .where(jsonFieldCondition);
//    insertAction.execute();
//    List result = queryAction.execute();
//    result.size();
//    result.get(0);
//    deleteAction.execute();
//  }
}