package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 用户信息维护Controller测试类
 * Created by YoungerOu on 2017/10/11
 */
public class UserControllerTest extends BaseControllerTest<UserController> {
  @Test
  public void testQueryPage() throws Exception {
    //准备参数
    String search = "[\"j\"]";
    String param = "{\"employeeName\":\"bbb\"}";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("search", search)
            .param("param", param)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryBySpecify() throws Exception {
    //准备参数
    String rowId = "123456789";
    String employeeNo = "001";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/queryBySpecify")
            .accept(MediaType.APPLICATION_JSON)
//            .param("rowId", rowId)
            .param("employeeNo", employeeNo)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryByOrganization() throws Exception {
    //准备参数
    String param = "[\"1\"]";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/queryByOrganization")
            .accept(MediaType.APPLICATION_JSON)
            .param("param", param)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testModify() throws Exception {
    //准备参数
    String rowId = "123456789";
    String employeeNo = " df";
    String employeeName = "df ";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/modify")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
            .param("employeeNo", employeeNo)
            .param("employeeName", employeeName)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testDelete() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/delete")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }
}
