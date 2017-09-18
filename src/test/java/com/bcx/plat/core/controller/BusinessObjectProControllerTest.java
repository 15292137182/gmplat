package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 业务对象属性Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/18.
 */
public class BusinessObjectProControllerTest extends BaseControllerTest<BusinessObjectProController> {
  @Test
  public void testAddBusinessObjPro() throws Exception {
    //准备数据
    String propertyCode = "obj102017-09-18000021";//业务对象属性代码
    String propertyName = "testOu";//业务对象属性名称
    String relateTableColumn = "row_id";//关联表字段
    String valueType = "char";//值类型
//    String valueResourceType = "";//值来源类型
//    String valueResourceContent = "";//值来源内容
//    String wetherExpandPro = "";//是否为扩展属性
    String objRowId = "655e2eed-3df9-4643-b816-165a964a";//业务对象关联rowId
//    String defaultValue = "";//默认值
    String fieldAlias = "testOu";//字段别名
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "businObjPro/add")
            .accept(MediaType.APPLICATION_JSON)
        // TODO 加参数测试

    );
  }

}
