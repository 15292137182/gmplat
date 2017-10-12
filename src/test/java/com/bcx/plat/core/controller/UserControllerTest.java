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
    String param = "{\"name\":\"joe\"}";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/queryPage")
            .accept(MediaType.APPLICATION_JSON)
//            .param("search", search)
//            .param("param", param)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryBySpecify() throws Exception {
    //准备参数
    String rowId = "123456789";
    String id = "001";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/queryBySpecify")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
//            .param("id", id)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryByOrg() throws Exception {
    //准备参数
    String param = "[\"1\"]";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/queryByOrg")
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
    String id = " df";
    String name = "df ";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/modify")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
            .param("id", id)
            .param("name", name)
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
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/delete")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testLock() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/lock")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testUnLock() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/unLock")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testInUse() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/inUse")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testOutOfUse() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/inUse")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testResetPassword() throws Exception {
    //准备参数
    String rowId = "123456789";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/resetPassword")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testAdd() throws Exception {
    //准备参数
    String id = "003";
    String name = "zhangsan";
    String password = "12345";
    String belongOrg = "2";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", id)
            .param("name", name)
            .param("password", password)
            .param("belongOrg", belongOrg)
    );
    //显示结果
    showResult(resultActions);
  }
}
