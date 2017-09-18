package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 业务对象Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/18.
 */
public class BusinessObjectControllerTest extends BaseControllerTest<BusinessObjectController> {
  @Test
  public void testSingleInputSelect() throws Exception {

  }

  @Test
  public void testQueryProPage() throws Exception {
    String rowId = "655e2eed-3df9-4643-b816-165a964a";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "businObj/queryProPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)//不传分页信息，查询所有
    );
    showResult(resultActions);
  }
}
