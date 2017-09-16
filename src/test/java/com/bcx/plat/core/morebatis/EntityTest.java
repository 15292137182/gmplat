package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.phantom.Condition;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity 单元测试
 */
@Transactional
@Rollback
public class EntityTest extends BaseTest {
  @Autowired
  private MoreBatis moreBatis;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  /**
   * 主要测试方法
   */
  @Test
  public void entityTest() {
    BusinessObject businessObject = new BusinessObject();
    businessObject.setObjectName("testObject");
    HashMap<String, Object> etc = new HashMap<>();
    etc.put("a", 100);
    etc.put("b", "b");
    businessObject.setEtc(etc);
    businessObject.buildCreateInfo().insert();

    final String rowId = businessObject.getRowId();
    BusinessObject businessObject1 = new BusinessObject();
    businessObject1.setRowId(rowId);
    businessObject1 = businessObject1.selectOneById();
    //查找与新增测试
    Assert.assertEquals("插入失败", businessObject1.getObjectName(),"testObject");
    Assert.assertEquals("map没有被插入到数据库", businessObject1.getEtc().get("a"),100);
    Assert.assertEquals("map没有被插入到数据库", businessObject1.getEtc().get("b"),"b");

    businessObject1.setObjectName("secondName");
    businessObject1.getEtc().put("a", "update");
    businessObject1.getEtc().put("b", "successed");
    businessObject1.updateById();
    businessObject1.setObjectName("anotherName");
    businessObject1.setEtc(null);
    businessObject1 = businessObject1.selectOneById();
    businessObject1.getEtc().put("a","update");
    businessObject1.getEtc().put("b","successed");
    businessObject1.updateById();
    businessObject1.setObjectName("anotherName");
    businessObject1.setEtc(null);
    businessObject1 = businessObject1.selectOneById();
    //更新测试
    Assert.assertEquals("更新失败", businessObject1.getObjectName(),"secondName");
    Assert.assertEquals("map没有被更新到数据库", businessObject1.getEtc().get("a"),"update");
    Assert.assertEquals("map没有被更新到数据库", businessObject1.getEtc().get("b"),"successed");
    businessObject1.setEtc(new HashMap());
    businessObject1.getEtc().put("c","c exist");
    businessObject1.getEtc().put("d","d exist");
    businessObject1.updateById();
    businessObject1 = businessObject1.selectOneById();
    //Map合并测试
    Assert.assertEquals("旧map被替换", businessObject1.getEtc().get("a"),"update");
    Assert.assertEquals("旧map被替换", businessObject1.getEtc().get("b"),"successed");
    Assert.assertEquals("新map未被更新到数据库", businessObject1.getEtc().get("c"),"c exist");
    Assert.assertEquals("新map未被更新到数据库", businessObject1.getEtc().get("d"),"d exist");

    Condition condition = new ConditionBuilder(BusinessObject.class).and().equal("a", "update").endAnd().buildDone();

    List<Map<String, Object>> result = moreBatis.select(BusinessObject.class).where(condition).execute();

    Assert.assertEquals("扩展字段条件查询失败", result.get(0).get("rowId"),businessObject1.getRowId());

    businessObject1.deleteById();
    //删除测试
    Assert.assertEquals("删除失败", businessObject1.selectOneById(),null);
//    Assert.assertTrue("删除失败", businessObject1.selectById() == null);
  }
}
