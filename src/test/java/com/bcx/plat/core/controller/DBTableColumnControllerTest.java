package com.bcx.plat.core.controller;

import com.bcx.BaseTest;
import com.bcx.plat.core.mapper.DBTableColumnMapper;
import com.bcx.plat.core.service.DBTableColumnService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Create By HCL at 2017/8/1
 */
public class DBTableColumnControllerTest extends BaseTest {

    @Autowired
    private DBTableColumnMapper dbTableColumnMapper;

    private MockMvc mockMvc;

    @InjectMocks
    private DBTableColumnController dbTableColumnController;
    @Mock
    private DBTableColumnService dbTableColumnService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        Map<String, Object> map = new HashMap<>();
        when(dbTableColumnService.select(map)).thenReturn(dbTableColumnMapper.select(map));
        mockMvc = MockMvcBuilders.standaloneSetup(dbTableColumnController).build();
    }

    /**
     * 对controller进行单元测试
     */
    @Test
    public void test() throws Exception {
        // 使用方法： 发起请求 plat/dbTableColumn/select 查询数据
        mockMvc.perform(get("/plat/dbTableColumn/select"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("state").value(1))
                .andExpect(jsonPath("message").isString())
                .andExpect(jsonPath("data").isArray());
    }
}