package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 前端功能块Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/20.
 */
public class FrontFuncControllerTest extends BaseControllerTest<FrontFuncController> {
  @Test
  public void testQueryPage() throws Exception {
    String search = "数据";
    String param = "{\"funcType\":\"s\"}";
    String pageNum = "1";
    String pageSize = "10";
    String order = "";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "fronc/queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("param", param)
            .param("search", search)
            .param("pageNum", pageNum).param("pageSize", pageSize)
            .param("order", order)
    );
    showResult(resultActions);
  }
}
