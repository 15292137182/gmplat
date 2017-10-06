package com.bcx.plat.core.morebatis.configuration.builder;

import com.bcx.plat.core.entity.BusinessObject;
import com.bcx.plat.core.morebatis.configuration.EntityEntry;
import java.util.Arrays;
import java.util.List;

public class TestEntityEntriesBuilder implements EntityEntriesBuilder {

  @Override
  public List<EntityEntry> getEntries() {
//    <bean class="com.bcx.plat.core.morebatis.configuration.builder.DefaultEntryBuilder"
//    p:entityClass="com.bcx.plat.core.entity.BusinessObject" p:tableName="t_business_object"
//    p:pks="#{'rowId'}"/>
    DefaultEntryBuilder defaultEntryBuilder=new DefaultEntryBuilder(BusinessObject.class,"t_business_object",
        Arrays.asList("rowId"));
    return Arrays.asList(defaultEntryBuilder.getEntry());
  }
}
