package com.bcx.plat.core.base;

import com.bcx.BaseTest;
import com.bcx.plat.core.utils.PlatResult;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.constants.Global.PLAT_SYS_PREFIX;
import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Went on 2017/8/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public abstract class BaseControllerTest<T> extends BaseTest {

  static String newRowId = null;

  private MockMvc mockMvc;

  @Autowired
  private T entityController;

  @Before
  public void before() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(entityController).build();
  }

  protected abstract String query();

  protected abstract String insert();

  protected abstract String update();

  protected abstract String delete();

  protected abstract String key1();

  protected abstract String key2();

  protected abstract String rowId();


  /**
   * 业务对象查询
   *
   * @throws Exception
   */
  @Test
  public void testquery() throws Exception {

    MvcResult mvcResult = mockMvc.perform(get(PLAT_SYS_PREFIX + query()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{")).delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
//    ServiceResult serviceResult = jsonToObj(sb.toString(), ServiceResult.class);
    PlatResult platResult = jsonToObj(sb.toString(), PlatResult.class);

    List<Map<String, Object>> data = (List<Map<String, Object>>) platResult.getData();

    logger.info("查询数据共" + data.size() + "条" + data);
    assert platResult.getState() == 1;
  }

  /**
   * 业务对象新增
   *
   * @throws Exception
   */
  @Test
  public void testAInsert() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(PLAT_SYS_PREFIX + insert())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param(key1(), UtilsTool.lengthUUID(5))
            .param(key2(), UtilsTool.lengthUUID(5)))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{"))
            .delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    PlatResult platResult = jsonToObj(sb.toString(), PlatResult.class);
    assert null != platResult;
    Map data = (Map) platResult.getData();
    if (data != null) {
      logger.info("新增数据" + data);
      newRowId = (String) data.get(rowId());
      assert (platResult.getState() == 1);
    }

  }

  /**
   * 业务对象修改
   *
   * @throws Exception
   */
  @Test
  public void testBUpdate() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(PLAT_SYS_PREFIX + update())
            .param(key1(), UtilsTool.lengthUUID(5))
            .param(key2(), UtilsTool.lengthUUID(5))
            .param(rowId(), newRowId))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{"))
            .delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    PlatResult platResult = jsonToObj(sb.toString(), PlatResult.class);
    logger.info("修改数据" + platResult.getData());
    assert (null != platResult && platResult.getState() == 1);
  }


  /**
   * 业务对象删除
   *
   * @throws Exception
   */
  @Test
  public void testCDelete() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get(PLAT_SYS_PREFIX + delete())
            .param(rowId(), newRowId))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
    sb.delete(0, sb.indexOf("{"))
            .delete(sb.lastIndexOf("}") + 1, sb.length());
    // 客户端获得 serviceResult
    PlatResult platResult = jsonToObj(sb.toString(), PlatResult.class);
    assert null != platResult;
    logger.info("删除数据" + platResult.getData());
    assert (platResult.getState() == 0);
  }


}
