package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 前端功能块Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/18.
 */
public class FrontFuncProControllerTest extends BaseControllerTest<FrontFuncProController> {
  @Test
  public void testInsert() throws Exception {
    String funcRowId = "083c6449-61c4-4be7-a87b-251130eb"; // 功能块唯一标识
    String relateBusiPro = "8e0066e1-57d6-4f57-b218-39144c26"; // 关联业务对象属性
    String displayTitle = "test"; // 显示标题
    String displayWidget = "text"; // 显示控件
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "fronFuncPro/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("relateBusiPro", relateBusiPro)
            .param("displayTitle", displayTitle)
            .param("displayWidget", displayWidget)
    );
    showResult(resultActions);
  }
}
