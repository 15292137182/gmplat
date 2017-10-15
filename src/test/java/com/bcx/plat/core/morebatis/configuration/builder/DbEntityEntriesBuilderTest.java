package com.bcx.plat.core.morebatis.configuration.builder;

import static org.junit.Assert.*;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.TestObj;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
public class DbEntityEntriesBuilderTest extends BaseTest{
  @Autowired
  MoreBatis moreBatis;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  @Test
  public void testSelect(){
    List<Map<String, Object>> testObjs = moreBatis.select(TestObj.class).execute();
    Assert.assertTrue("查询失败", testObjs.size()>0);
    Assert.assertTrue("别名未注册", testObjs.get(0).containsKey("onlyFieldX"));
    Assert.assertTrue("主键未注册", moreBatis.getPks(TestObj.class).size()>0);
    Assert.assertTrue("主键未注册", moreBatis.getPks(TestObj.class).iterator().next().getFieldName().equals("row_id"));
  }
}