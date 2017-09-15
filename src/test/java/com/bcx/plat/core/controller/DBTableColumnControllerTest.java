package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 数据库表信息Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/15.
 */
public class DBTableColumnControllerTest extends BaseControllerTest<DBTableColumnController> {
  @Test
  public void testQueryPageById() throws Exception {
    //准备参数
    String search = "test";
    String rowId = "5243fc43-a9c6-42f0-a0c8-fd74e7fb";
    String pageNum = "1";
    String pageSize = "10";
    String order = "{\"str\":\"rowId\",\"num\":1}";
    //发送请求
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "dbTableColumn/queryPageById")
            .accept(MediaType.APPLICATION_JSON)
            .param("search", search)
            .param("rowId", rowId)
            .param("pageNum", pageNum)
            .param("pageSize", pageSize)
            .param("order", order)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testSingleInputSelect() throws Exception {
    String search = "test";
    String rowId = "5243fc43-a9c6-42f0-a0c8-fd74e7fb";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "dbTableColumn/queryTabById")
            .accept(MediaType.APPLICATION_JSON)
            .param("search", search)
            .param("rowId", rowId)
    );
    showResult(resultActions);
  }

  @Test
  public void testAddMaintDB() throws Exception {
    String relateTableRowId = "5dde046d-9bdf-41e2-8a2a-92c22fa7";
    String columnEname = "test2";
    String columnCname = "test3";
    String desp = "test4";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "dbTableColumn/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("relateTableRowId", relateTableRowId)
            .param("columnEname", columnEname)
            .param("columnCname", columnCname)
            .param("desp", desp)
    );
    showResult(resultActions);
  }

  @Test
  public void testModifyBusinessObjPro() throws Exception {

  }
}
