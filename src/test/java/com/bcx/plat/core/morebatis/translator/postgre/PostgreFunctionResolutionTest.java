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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

  @Test
  public void testArithmetic(){
    BusinessObject businessObject=new BusinessObject();
    final String objectName = "全是字符串，根本找不到包含数字属性的对象";
    final String etcFieldName = "所以我只好使用扩展字段了";
    final long increment=28827;
    businessObject.setObjectName(objectName);
    HashMap<String,Integer> etc=new HashMap<>();
    etc.put(etcFieldName, 100);
    businessObject.setEtc(etc);
    businessObject.buildCreateInfo().insert();
    final String rowId=businessObject.getRowId();
    Field etcField = moreBatis.getColumnByAlias(BusinessObject.class, etcFieldName);
    ArithmeticExpression expression = new ArithmeticExpression("?+" + 28827, etcField);
    HashMap<String,Object> updateArgs=new HashMap<>();
    updateArgs.put(etcFieldName, expression);
    Condition condition = new ConditionBuilder(
        BusinessObject.class).and().equal("rowId", rowId).endAnd().buildDone();
    moreBatis.update(BusinessObject.class, updateArgs).where(condition).execute();
    Long result = (Long) ((Map) moreBatis.select(BusinessObject.class).where(condition).execute().get(0)
        .get("etc")).get(etcFieldName);
    Assert.assertTrue("表达式失败了", (28827+100)== result);
  }
}