package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseControllerTest;

/**
 *  序列规则Controller层测试
 * Created by Wen Tiehu on 2017/8/16.
 */

public class SysConfigControllerTest extends BaseControllerTest<SysConfigController> {
    @Override
    protected String query() {
        return "/core/sysConfig/query";
    }

    @Override
    protected String insert() {
        return "/core/sysConfig/add";
    }

    @Override
    protected String update() {
        return "/core/sysConfig/modify";
    }

    @Override
    protected String delete() {
        return "/core/sysConfig/deleteEntity";
    }

    @Override
    protected String key1() {
        return "confKey";
    }

    @Override
    protected String key2() {
        return "confValue";
    }

    @Override
    protected String rowId() {
        return "rowId";
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
     * 新增
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

}
