package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bcx.BaseTest;
import com.bcx.plat.core.utils.ServiceResult;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Created by Went on 2017/8/1.
 */
public class BusinessObjectControllerTest extends BaseTest {

  private MockMvc mockMvc;

  @Autowired
  private BusinessObjectController businessObjectController;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(businessObjectController).build();
  }

  @Test
  public void test() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/businObj/select"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
//    sb.delete(0, sb.indexOf("{")).delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    String json = sb.toString();
    json=json.substring("/**/callback(".length(),json.length());
    json=json.substring(0,json.length()-");".length());
    ServiceResult<List> serviceResult = jsonToObj(json, ServiceResult.class);
//    assert (null != serviceResult && serviceResult.getState() == 1);
  }
}
