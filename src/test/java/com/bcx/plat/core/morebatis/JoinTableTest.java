package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.entity.KeySet;
import com.bcx.plat.core.entity.KeySetPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
import com.bcx.plat.core.morebatis.component.Field;
import com.bcx.plat.core.morebatis.component.FieldCondition;
import com.bcx.plat.core.morebatis.component.JoinTable;
import com.bcx.plat.core.morebatis.component.Order;
import com.bcx.plat.core.morebatis.component.constant.JoinType;
import com.bcx.plat.core.morebatis.component.constant.Operator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
public class JoinTableTest extends BaseTest {

  @Autowired
  private MoreBatis moreBatis;
  private String primaryRowId;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

//  @Before
  public void createData() {
    BusinessObject businessObject = new BusinessObject();
    businessObject.setObjectName("join test");
    businessObject.setObjectCode("JT-10012");
    businessObject.buildCreateInfo();
    businessObject.insert();
    BusinessObjectPro businessObjectPro = new BusinessObjectPro();
    businessObjectPro.setPropertyCode("c");
    businessObjectPro.setPropertyName("tesstName");
    primaryRowId = businessObject.getRowId();
    businessObjectPro.setObjRowId(primaryRowId);
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.buildCreateInfo().insert();
  }

  @Test
  public void innerJoinTest() {
    QueryAction joinTableTest = moreBatis.selectStatement().select(moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"))
        .from(new JoinTable(moreBatis.getTable(BusinessObject.class), JoinType.INNER_JOIN,
            moreBatis.getTable(BusinessObjectPro.class))
//    new FieldCondition(Fields.T_BUSINESS_OBJECT.ROW_ID
            .on(new FieldCondition(moreBatis.getColumnByAlies(BusinessObject.class,"rowId"), Operator.EQUAL,
                moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"))))
                //T_BUSINESS_OBJECT_PRO.OBJ_ROW_ID

        .where(new FieldCondition(moreBatis.getColumnByAlies(BusinessObject.class,"rowId"), Operator.EQUAL, primaryRowId));
//        .groupBy(Fields.T_BUSINESS_OBJECT_PRO.ROW_ID);
    List<Map<String, Object>> result = joinTableTest.execute();
    Assert.assertEquals(5, result.size());
  }
  @Test
  public void test() {
    QueryAction joinTableTest = moreBatis.select(KeySet.class,KeySetPro.class,"rowId","relateKeysetRowId")
        .where(new FieldCondition(moreBatis.getColumnByAlies(KeySet.class,"keysetCode"), Operator.EQUAL, "124"));
    List<Map<String, Object>> result = joinTableTest.execute();
    Assert.assertEquals(5, result.size());
  }

  @Test
  public void groupByTest() {
    QueryAction joinTableTest;
    List<Map<String, Object>> result;
    joinTableTest = moreBatis.selectStatement().select(moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"))
        .from(new JoinTable(TableInfo.T_BUSINESS_OBJECT, JoinType.INNER_JOIN,
            TableInfo.T_BUSINESS_OBJECT_PRO)
            .on(new FieldCondition(moreBatis.getColumnByAlies(BusinessObject.class,"rowId"), Operator.EQUAL,
                moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"))))
        .where(new FieldCondition(moreBatis.getColumnByAlies(BusinessObject.class,"rowId"), Operator.EQUAL, primaryRowId))
        .groupBy(moreBatis.getColumnByAlies(BusinessObjectPro.class,"objRowId"));
    result = joinTableTest.execute();
    Assert.assertEquals(1, result.size());
  }
}
