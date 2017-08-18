package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseControllerTest;
import com.bcx.plat.core.utils.ServiceResult;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static com.bcx.plat.core.utils.UtilsTool.jsonToObj;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *  前端功能块Controller层测试
 * Created by Wen Tiehu on 2017/8/16.
 */

public class FrontFuncControllerTest extends BaseControllerTest<FrontFuncController> {

    private MockMvc mockMvc;

    @Override
    protected String query() {
        return "/core/fronc/query";
    }

    @Override
    protected String insert() {
        return "/core/fronc/add";
    }

    @Override
    protected String update() {
        return "/core/fronc/modify";
    }

    @Override
    protected String delete() {
        return "/core/fronc/deleteEntity";
    }

    @Override
    protected String key1() {
        return "froncCode";
    }

    @Override
    protected String key2() {
        return "froncName";
    }

    @Override
    protected String rowId() {
        return "rowId";
    }

    @Override
    public void before() {
        super.before();
    }
    /**
     * 业务对象查询
     *
     * @throws Exception
     */
    @Override
    public void testquery() throws Exception {
        super.testquery();
    }

    /**
     * 业务对象新增
     *
     * @throws Exception
     */
    @Override
    public void testAInsert() throws Exception {
        super.testAInsert();
    }

    /**
     * 业务对象修改
     *
     * @throws Exception
     */
    @Override
    public void testBUpdate() throws Exception {
        super.testBUpdate();
    }

    /**
     * 业务对象删除
     *
     * @throws Exception
     */
    @Override
    public void testCDelete() throws Exception {
        super.testCDelete();
    }
//
//    /**
//     * 业务对象查询
//     * @throws Exception
//     */
//    @Test
//    public void queryTest() throws Exception {
//
//        MvcResult mvcResult = mockMvc.perform(get("core/fronc/queryProPage"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//        StringBuilder sb = new StringBuilder(mvcResult.getResponse().getContentAsString());
//        sb.deleteEntity(0, sb.indexOf("{")).deleteEntity(sb.lastIndexOf("}") + 1, sb.length());
//        // 客户端获得 serviceResult
//        ServiceResult serviceResult = jsonToObj(sb.toString(), ServiceResult.class);
//        List<Map<String,Object>> data = (List<Map<String,Object>> )serviceResult.getData();
//
//        logger.info("查询数据共"+data.size()+"条"+data);
//        assert (null != serviceResult && serviceResult.getState() == 1);
//    }

}
