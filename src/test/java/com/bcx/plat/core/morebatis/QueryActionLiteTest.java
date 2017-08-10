package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.morebatis.mapper.TempSuitMapper;
import com.bcx.plat.core.morebatis.substance.Field;
import com.bcx.plat.core.morebatis.substance.FieldCondition;
import com.bcx.plat.core.morebatis.substance.Table;
import com.bcx.plat.core.morebatis.substance.condition.And;
import com.bcx.plat.core.morebatis.substance.condition.Operator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryActionLiteTest extends BaseTest {

  final Field fieldId = new Field("id");
  final Field fieldDemo1 = new Field("test_first");
  final Field fieldDemo2 = new Field("test_second");

  @Autowired
  TempSuitMapper tempSuitMapper;

  public void setTempSuitMapper(TempSuitMapper tempSuitMapper) {
    this.tempSuitMapper = tempSuitMapper;
  }

  @Test
  public void smogTest() {
    QueryAction queryActionLite = new QueryAction().select(fieldId, fieldDemo1, fieldDemo2)
        .from(new Table("test_only.test_table1"))
        .where(new FieldCondition(fieldId, Operator.IN, Arrays.asList(1, 2, 3)));
    final List<Map<String, Object>> select = tempSuitMapper.select(queryActionLite);
    Assert.assertTrue(select.size() > 0);
  }

  @Test
  public void andConditionTest() {
    QueryAction queryActionLite = new QueryAction().select(fieldId, fieldDemo1, fieldDemo2)
        .from(new Table("test_only.test_table1"))
        .where(new And(new FieldCondition(fieldId, Operator.IN, Arrays.asList(1, 2, 3)),
            new FieldCondition(fieldId, Operator.EQUAL, 1)));
    Assert.assertTrue(tempSuitMapper.select(queryActionLite).size() == 1);
  }

  @Test
  public void selectAll() {
    QueryAction queryActionLite = new QueryAction().selectAll()
        .from(new Table("test_only.test_table1"))
        .where(new FieldCondition(fieldId, Operator.IN, Arrays.asList(1, 2, 3)));
    final List<Map<String, Object>> result = tempSuitMapper.select(queryActionLite);
    Assert.assertTrue(result.get(0).entrySet().size() > 0);
  }
}