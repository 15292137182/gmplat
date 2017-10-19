package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class RoleControllerTest extends BaseControllerTest<RoleController> {
  @Test
  public void testAdd() throws Exception {
    //准备参数
    String roleId = "123456791";
    String roleName = "普通管理员";
    String roleType = "类别";
    String desc = "说明";
    String remarks = "备注";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "role/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("roleId", roleId)
            .param("roleName", roleName)
            .param("roleType", roleType)
            .param("desc", desc)
            .param("remarks", remarks)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testModify() throws Exception {
    //准备参数
    String rowId = "2a4b2c06-fe63-4c59-be33-6db965f2";
    String roleId = "123456789";
    String roleName = "权限名";
    String roleType = "权限类";
    String desc = "说";
    String remarks = "备";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "role/modify")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
            .param("roleId", roleId)
            .param("roleName", roleName)
            .param("roleType", roleType)
            .param("desc", desc)
            .param("remarks", remarks)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryPage() throws Exception {
    //准备参数
    String search = "权限 说";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "role/queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("search", search)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryBySpecify() throws Exception {
    //准备参数
    String rowId = "2a4b2c06-fe63-4c59-be33-6db965f2";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "role/queryBySpecify")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }
  @Test
  public void testQueryUsers() throws Exception {
    //准备参数
    String rowId = "2a4b2c06-fe63-4c59-be33-6db965f2";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "role/queryUsers")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    //显示结果
    showResult(resultActions);
  }
}
