package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class RoleControllerTest extends BaseControllerTest<RoleController> {
  @Test
  public void testResetPassword() throws Exception {
    //准备参数
    String roleId = "123456789";
    String roleName = "权限名称";
    String roleType = "权限类别";
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
}
