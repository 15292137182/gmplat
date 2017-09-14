package com.bcx.plat.core.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

/**
 * 键值集合测试类
 * Created by YoungerOu on 2017/9/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml"})
@WebAppConfiguration // 可调用WEB特性
public class KeySetControllerTest {

  private static final String URL_TEMPLATE = "/gmp/sys/core/keySet/";
  @Autowired
  private KeySetController keySetController;
  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(keySetController).build();
  }

  /**
   * 显示测试结果
   *
   * @param resultActions
   */
  private void showResult(ResultActions resultActions) throws UnsupportedEncodingException {
    MvcResult mvcResult = resultActions.andReturn();
    String result = mvcResult.getResponse().getContentAsString();
    System.out.println("测试结果：");
    System.out.println("=====客户端获得返回数据:" + result);
  }

  @Test
  public void testQueryKeySet() throws Exception {
    //准备参数
    String search = "[\"showControl\"]";
    //发送请求
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "queryKeySet")
            .accept(MediaType.APPLICATION_JSON).param("search", search)
    );
    //显示结果
    showResult(resultActions);
  }

  @Test
  public void testQueryKeyCode() throws Exception {
    String keyCode = "test1";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "queryKeyCode")
            .accept(MediaType.APPLICATION_JSON).param("keyCode", keyCode)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryPro() throws Exception {
    String rowId = "6e17656d-2519-4ed0-b6ee-482316d3";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "queryPro")
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
        MockMvcRequestBuilders.post(URL_TEMPLATE + "queryProPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId", rowId)
            .param("search", search)
            .param("order",order)
    );
    showResult(resultActions);
  }

  @Test
  public void testDelete() throws Exception {
    // 准备参数
    String rowId = "50ab96de-8428-4ddf-9a8d-a4ab2dc3";
    ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders
        .post(URL_TEMPLATE + "delete").accept(MediaType.APPLICATION_JSON)
        .param("rowId", rowId)
    );
    showResult(resultActions);
  }

  @Test
  public void testInsert() throws Exception {
    // 准备参数
    String keysetCode = "test2";
    String keysetName = "测试2";
    String belongModule = "类型";
    String desp = "测试2";
    //String belongSystem = "GMP";
    ResultActions resultActions = this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "add")
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
        MockMvcRequestBuilders.post(URL_TEMPLATE + "modify")
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
    String pageNum = "1";
    String pageSize = "10";
    //测试未通过，order参数提取rowId，未成功拼接入sql语句
    String order = "{\"str\":\"rowId\",\"num\":1}";
    ResultActions resultActions=this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE+"queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("search",search)
            .param("pageNum",pageNum)
            .param("pageSize",pageSize)
            .param("order",order)
    );
    showResult(resultActions);
  }

  @Test
  public void testQueryById() throws Exception{
    String rowId = "b50d7dae-ba9a-4f3c-a39b-781316a9";
    ResultActions resultActions=this.mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE+"queryById")
            .accept(MediaType.APPLICATION_JSON)
            .param("rowId",rowId)
    );
    showResult(resultActions);
  }
}
