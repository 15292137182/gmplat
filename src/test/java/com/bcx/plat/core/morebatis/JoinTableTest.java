package com.bcx.plat.core.morebatis;

import com.bcx.BaseTest;
import com.bcx.plat.core.database.info.Fields;
import com.bcx.plat.core.database.info.Fields.T_BUSINESS_OBJECT_PRO;
import com.bcx.plat.core.database.info.TableInfo;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.morebatis.command.QueryAction;
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

  @Before
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
  public void test() {
    QueryAction joinTableTest = moreBatis.selectStatement().select(Fields.T_BUSINESS_OBJECT_PRO.OBJ_ROW_ID)
        .from(new JoinTable(TableInfo.T_BUSINESS_OBJECT, JoinType.INNER_JOIN,
            TableInfo.T_BUSINESS_OBJECT_PRO)
            .on(new FieldCondition(Fields.T_BUSINESS_OBJECT.ROW_ID, Operator.EQUAL,
                T_BUSINESS_OBJECT_PRO.OBJ_ROW_ID)))
        .where(new FieldCondition(Fields.T_BUSINESS_OBJECT.ROW_ID, Operator.EQUAL, primaryRowId))
        .groupBy(Fields.T_BUSINESS_OBJECT_PRO.OBJ_ROW_ID)
        .orderBy(new Order(T_BUSINESS_OBJECT_PRO.CREATE_TIME,Order.DESC));
    List<Map<String, Object>> result = joinTableTest.execute();
    Assert.assertEquals(5, result.size());
  }
}
