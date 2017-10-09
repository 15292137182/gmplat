package com.bcx.plat.core.morebatis.translator.postgre;

import static org.junit.Assert.*;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.builder.ConditionBuilder;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.function.ArithmeticExpression;
import com.bcx.plat.core.morebatis.component.function.Functions.Count;
import com.bcx.plat.core.morebatis.phantom.Aliased;
import com.bcx.plat.core.morebatis.phantom.Condition;
import com.bcx.plat.core.morebatis.phantom.FieldSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
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

  @Test
  public void testArithmetic(){
    ArithmeticExpression expression = new ArithmeticExpression("?+10", 100);
    Aliased aliased=new Aliased(expression,"test");
    Assert.assertTrue("表达式测试失败了", 110==(Integer) moreBatis.selectStatement().select(aliased).execute().get(0).get("test"));
  }
}