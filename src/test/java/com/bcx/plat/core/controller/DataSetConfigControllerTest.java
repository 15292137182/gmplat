package com.bcx.plat.core.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * 数据集Controller测试类
 * <p>
 * Created by YoungerOu on 2017/9/18.
 */
public class DataSetConfigControllerTest extends BaseControllerTest<DataSetConfigController> {
  @Test
  public void testQuery() throws Exception {
    String param = "{\"datasetName\":\"新增测试\"}";
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post(URL_TEMPLATE + "dataSetConfig/queryPage")
            .accept(MediaType.APPLICATION_JSON)
            .param("param", param)
    );
    showResult(resultActions);
  }
}
