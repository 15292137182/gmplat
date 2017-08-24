package com.bcx.plat.core.morebatis.builder;

import com.bcx.BaseTest;
import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.entity.BusinessObjectPro;
import com.bcx.plat.core.morebatis.app.MoreBatis;
import com.bcx.plat.core.utils.UtilsTool;
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
public class ChainConditionBuilderTest extends BaseTest {

  @Autowired
  private MoreBatis moreBatis;
  private String objRowId;
  private String objName;

  public void setMoreBatis(MoreBatis moreBatis) {
    this.moreBatis = moreBatis;
  }

  @Before
  public void setUp() throws Exception {
    BusinessObject businessObject = new BusinessObject();
    businessObject.setObjectName("testOfChainCondition:" + UtilsTool.lengthUUID(32));
    businessObject.buildCreateInfo().insert();
    objRowId = businessObject.getRowId();
    objName = businessObject.getObjectName();
    businessObject.setObjectName("testOfChainCondition:" + UtilsTool.lengthUUID(32));
    businessObject.buildCreateInfo().insert();
    businessObject.setObjectName("testOfChainCondition:" + UtilsTool.lengthUUID(32));
    businessObject.buildCreateInfo().insert();
    businessObject.setObjectName("testOfChainCondition:" + UtilsTool.lengthUUID(32));
    businessObject.buildCreateInfo().insert();
    businessObject.setObjectName("testOfChainCondition:" + UtilsTool.lengthUUID(32));
    businessObject.buildCreateInfo().insert();
    BusinessObjectPro businessObjectPro = new BusinessObjectPro();
    businessObjectPro.setObjRowId(objRowId);
    businessObjectPro.setPropertyName(objName);
    businessObjectPro.setPropertyName("10");
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.setPropertyName("11");
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.setPropertyName("12");
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.setPropertyName("13");
    businessObjectPro.buildCreateInfo().insert();
    businessObjectPro.setPropertyName("20");
    businessObjectPro.buildCreateInfo().insert();
  }

  @Test
  public void conditionBuilder() {
    ConditionBuilder conditionBuilder = new ConditionBuilder(BusinessObjectPro.class).and()
        .equal(BusinessObject.class, "rowId", objRowId).rightLike("propertyName", "1").endAnd();
    List<Map<String, Object>> result = moreBatis
        .select(BusinessObject.class, BusinessObjectPro.class, "rowId", "objRowId")
        .where(conditionBuilder.buildDone()).execute();
    Assert.assertEquals(4,result.size());
  }

}