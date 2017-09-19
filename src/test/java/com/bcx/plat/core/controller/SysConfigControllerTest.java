package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SysConfigControllerTest extends BaseControllerTest<SysConfigController> {
  @Test
  public void testInsert() throws Exception {
    String confKey = "test1";
    String confValue = "test1";
    String desp = "test1";
    String extends1 = "test11";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "sysConfig/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("confKey", confKey)
            .param("confValue", confValue)
            .param("desp", desp)
            .param("extends1", extends1)
    );
    showResult(resultActions);
  }

  @Test
  public void testUpdate() throws Exception {
    String confKey = "test12";
    String confValue = "test12";
    String desp = "test12";
    String extends1 = "test12";
    String rowId = "21de038b-1177-4ca2-a54f-fbb41675";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "sysConfig/modify")
            .accept(MediaType.APPLICATION_JSON)
            .param("confKey", confKey)
            .param("confValue", confValue)
            .param("desp", desp)
            .param("extends1", extends1)
            .param("rowId", rowId)
    );
    showResult(resultActions);
  }

  @Test
  public void testSingleInputSelect() throws Exception {
    String search = "1";
//    String param="{\"confKey\":\"1\"}";
    String pageSize = "10";
    String pageNum = "1";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "sysConfig/queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("search", search)
//            .param("param",param)
//            .param("pageNum",pageNum).param("pageSize",pageSize)
    );
    showResult(resultActions);
  }
}
