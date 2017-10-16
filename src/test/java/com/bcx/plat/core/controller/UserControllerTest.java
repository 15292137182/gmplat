package com.bcx.plat.core.controller;

import com.bcx.plat.core.utils.UtilsTool;
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
    String search = "j";
    String param = "{\"status\":\"03\"}";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/queryPage")
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
    String id = "004";
    String name = "zhangsan";
    String password = "12345";
    String belongOrg = "2";
    String nickname = "nick";
    String mobilePhone = "15618908988";
    String officePhone = "010-68508899";
    String email = "abc@126.com";
    String gender = "男";
    String job = "主席";
    String idCard = "310102199801251726";
    String hiredate = UtilsTool.getDateTimeNow();
    String description = "说明";
    String remarks = "备注";

    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("id", id)
            .param("name", name)
            .param("password", password)
            .param("belongOrg", belongOrg)
            .param("nickname", nickname)
            .param("mobilePhone", mobilePhone)
            .param("officePhone", officePhone)
            .param("email", email)
            .param("gender", gender)
            .param("job", job)
            .param("hiredate", hiredate)
            .param("description", description)
            .param("remarks", remarks)
            .param("idCard", idCard)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testModifyPassword() throws Exception {
    //准备参数
    String rowId = "123456789";
    String oldPassword = "123456";
    String password = "123457890";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "user/modifyPassword")
            .accept(MediaType.APPLICATION_JSON)
            .param("password", password)
            .param("rowId", rowId)
            .param("oldPassword", oldPassword)
    );
    //显示结果
    showResult(resultActions);
  }
}
