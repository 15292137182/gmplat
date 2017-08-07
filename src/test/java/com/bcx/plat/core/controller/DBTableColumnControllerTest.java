package com.bcx.plat.core.controller;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bcx.BaseTest;
import com.bcx.plat.core.utils.ServiceResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Create By HCL at 2017/8/1
 */
public class DBTableColumnControllerTest extends BaseTest {

  private MockMvc mockMvc;

  @Autowired
  private DBTableColumnController dbTableColumnController;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(dbTableColumnController).build();
  }

  /**
   * 对controller进行单元测试
   */
  @Test
  public void test() throws Exception {
    // 使用方法： 发起请求 plat/dbTableColumn/select 查询数据
    MvcResult mvcResult = mockMvc.perform(get("/core/dbTableColumn/query"))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{"))
        .delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    ServiceResult serviceResult = jsonToObj(sb.toString(), ServiceResult.class);
    assert (null != serviceResult && serviceResult.getState() == 1);
  }
}