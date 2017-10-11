package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 用户信息维护Controller测试类
 * Created by YoungerOu on 2017/10/11
 */
public class EmployeeControllerTest extends BaseControllerTest<EmployeeController> {
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
  public void testQueryById() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/queryById")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }
  @Test
  public void testQueryByEmpNo() throws Exception {
    //准备参数
    String employeeNo = "0011";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "employee/queryByEmployeeNo")
            .accept(MediaType.APPLICATION_JSON)
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
}
