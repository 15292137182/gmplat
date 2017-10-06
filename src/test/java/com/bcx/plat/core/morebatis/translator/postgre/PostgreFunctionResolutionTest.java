package com.bcx.plat.core.morebatis.translator.postgre;

import static org.junit.Assert.*;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.component.function.Functions.Count;
import com.bcx.plat.core.morebatis.phantom.Aliased;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostgreFunctionResolutionTest extends BaseTest{
  @Autowired
  MoreBatis moreBatis;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  @Test
  public void testCount(){
    int totalSize=moreBatis.select(BusinessObject.class).execute().size();
    Aliased aliased=new Aliased(new Count(1),"count");
    Long count = (Long) moreBatis.selectStatement().select(aliased)
        .from(moreBatis.getTable(BusinessObject.class)).execute().get(0).get("count");
    Assert.assertTrue("失败",totalSize == count );
  }
}