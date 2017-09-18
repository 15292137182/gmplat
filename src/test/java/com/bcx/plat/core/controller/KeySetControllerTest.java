package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 键值集合管理Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/14.
 */
public class KeySetControllerTest extends BaseControllerTest<KeySetController> {

  @Test
  public void testQueryKeySet() throws Exception {
    //准备参数
    String search = "[\"showControl\"]";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/queryKeySet")
            .accept(MediaType.APPLICATION_JSON).param("search", search)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryKeyCode() throws Exception {
    String keyCode = "test";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/queryKeyCode")
            .accept(MediaType.APPLICATION_JSON).param("keyCode", keyCode)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryPro() throws Exception {
    String rowId = "6e17656d-2519-4ed0-b6ee-482316d3";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/queryPro")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryProPage() throws Exception {
    String rowId = "6e17656d-2519-4ed0-b6ee-482316d3";
    String search = "grid,form"; // 空格查询条件，confKey和confValue
    String order = "{\"str\":\"rowId\",\"num\":1}";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/queryProPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
            .param("search", search)
            .param("order", order)
    );
    showResult(resultActions);
  }

  @Test
  public void testDelete() throws Exception {
    // 准备参数
    String rowId = "50ab96de-8428-4ddf-9a8d-a4ab2dc3";
    ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        .post(URL_TEMPLATE + "keySet/delete").accept(MediaType.APPLICATION_JSON)
        .param("rowId", rowId)
    );
    showResult(resultActions);
  }

  @Test
  public void testInsert() throws Exception {
    // 准备参数
    String keysetCode = "test5";
    String keysetName = "测试5";
    String belongModule = "类型";
    String desp = "测试5";
    //String belongSystem = "GMP";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/add")
            .accept(MediaType.APPLICATION_JSON)
            .param("keysetCode", keysetCode)
            .param("keysetName", keysetName)
            .param("belongModule", belongModule)
            //.param("belongSystem", belongSystem)
            .param("desp", desp)
    );
    showResult(resultActions);
  }

  @Test
  public void testModify() throws Exception {
    //准备参数
    String rowId = "b50d7dae-ba9a-4f3c-a39b-781316a9";
    String keysetCode = "test";
    String keysetName = "测试";
    String desp = "测试";
    String belongModule = "类型";
    String belongSystem = "";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/modify")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
            .param("keysetCode", keysetCode)
            .param("keysetName", keysetName)
            .param("desp", desp)
            .param("belongModule", belongModule)
            .param("belongSystem", belongSystem)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryPage() throws Exception {
    String search = "test"; // 空格查询条件 keysetCode,keysetName
//    String pageNum = "1";
//    String pageSize = "10";
    String order = "{\"str\":\"rowId\",\"num\":1}";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("search", search)
//            .param("pageNum",pageNum)
//            .param("pageSize",pageSize)
            .param("order", order)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryById() throws Exception {
    String rowId = "b50d7dae-ba9a-4f3c-a39b-781316a9";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "keySet/queryById")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
    );
    showResult(resultActions);
  }
}
