package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseController;
import com.bcx.plat.core.base.BaseEntity;
import com.bcx.plat.core.base.BaseService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

/**
 * Controller测试基类
 * <p>
 * Created by YoungerOu on 2017/9/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring-*.xml"})
@WebAppConfiguration // 可调用WEB特性
public abstract class BaseControllerTest<T extends BaseController> {

  protected static final String URL_TEMPLATE = "/gmp/sys/core/";
  @Autowired
  protected T controller;
  protected MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  /**
   * 显示测试结果
   *
   * @param resultActions
   */
  protected void showResult(ResultActions resultActions) throws UnsupportedEncodingException {
    MvcResult mvcResult = resultActions.andReturn();
    String result = mvcResult.getResponse().getContentAsString();
    System.out.println("测试结果：");
    System.out.println("=====客户端获得返回数据:" + result);
  }
}
