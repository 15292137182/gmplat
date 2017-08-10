package com.bcx.plat.core.controller;

import com.bcx.BaseTest;
import com.bcx.plat.core.utils.ServiceResult;
import com.bcx.plat.core.utils.UtilsTool;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Went on 2017/8/1.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BusinessObjectControllerTest extends BaseTest {

  static String newRowId = null;

  private MockMvc mockMvc;

  @Autowired
  private BusinessObjectController businessObjectController;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(businessObjectController).build();
  }

  static String query = "/core/businObj/query";
  static String insert = "/core/businObj/add";
  static String update = "/core/businObj/modify";
  static String delete = "/core/businObj/delete";
  static String key1 = "objectCode";
  static String key2 = "objectName";
  static String name3 = "rowId";


  /**
   * 业务对象查询
   * @throws Exception
   */
  @Test
  public void test() throws Exception {

    MvcResult mvcResult = mockMvc.perform(get(query))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();

    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{")).delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    ServiceResult serviceResult = jsonToObj(sb.toString(), ServiceResult.class);
    assert (null != serviceResult && serviceResult.getState() == 1);
  }



  /**
   * 业务对象新增
   * @throws Exception
   */
  @Test
  public void testAInsert() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(insert)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param(key1,UtilsTool.lengthUUID(5))
            .param(key2,UtilsTool.lengthUUID(5)))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn();
    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{"))
        .delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    ServiceResult serviceResult = jsonToObj(sb.toString(), ServiceResult.class);
    Map data = (Map) serviceResult.getData();
    newRowId = (String) data.get(name3);
    assert (null != serviceResult && serviceResult.getState() == 1);
  }

  /**
   * 业务对象修改
   * @throws Exception
   */
  @Test
  public void testBUpdate() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(update)
            .param(key1,UtilsTool.lengthUUID(5))
            .param(key2, UtilsTool.lengthUUID(5))
            .param(name3,newRowId))
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


  /**
   * 业务对象删除
   * @throws Exception
   */
  @Test
  public void testCDelete() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(delete)
            .param(name3,newRowId))
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
