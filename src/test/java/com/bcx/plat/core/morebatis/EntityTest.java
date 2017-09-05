package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Entity 单元测试
 */
@Transactional
@Rollback
public class EntityTest extends BaseTest {

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
    businessObject1.selectById();
    //查找与新增测试
    Assert.assertTrue("插入失败", businessObject1.getObjectName().equals("testObject"));
    Assert.assertTrue("map没有被插入到数据库", businessObject1.getEtc().get("a").equals(100));
    Assert.assertTrue("map没有被插入到数据库", businessObject1.getEtc().get("b").equals("b"));

    businessObject1.setObjectName("secondName");
    businessObject1.getEtc().put("a", "update");
    businessObject1.getEtc().put("b", "successed");
    businessObject1.updateById();
    businessObject1.setObjectName("anotherName");
    businessObject1.setEtc(null);
    businessObject1.selectById();
    //更新测试
    Assert.assertTrue("更新失败", businessObject1.getObjectName().equals("secondName"));
    Assert.assertTrue("map没有被更新到数据库", businessObject1.getEtc().get("a").equals("update"));
    Assert.assertTrue("map没有被更新到数据库", businessObject1.getEtc().get("b").equals("successed"));

    businessObject1.deleteById();
    //删除测试
    Assert.assertTrue("删除失败", businessObject1.selectById() == null);
  }
}
