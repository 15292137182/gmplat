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
    String param = "{\"objectName\":\"模板名称\"}";
    String search = "名";
    String pageNum = "1";
    String pageSize = "10";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "businObj/queryPage")
            .accept(MediaType.APPLICATION_JSON)
//            .param("param",param)
//            .param("search",search)
            .param("pageNum", pageNum).param("pageSize", pageSize)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryProPage() throws Exception {
    String rowId = "5dea7672-242b-4252-95f4-9e60fb60";
    String search = "1";
    String param = "{\"valueResourceType\":\"keySet\"}";
    String pageNum = "";
    String pageSize = "";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "businObj/queryProPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)//不传分页信息，查询所有
//            .param("param",param)
            .param("search", search)
    );
    showResult(resultActions);
  }
}
