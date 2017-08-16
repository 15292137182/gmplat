package com.bcx.plat.core.controller;

import com.bcx.plat.core.base.BaseControllerTest;

/**
 *  前端功能块属性Controller层测试
 * Created by Wen Tiehu on 2017/8/16.
 */

public class FrontFuncProControllrTest extends BaseControllerTest<FrontFuncProController> {
    @Override
    protected String query() {
        return "/core/fronFuncPro/query";
    }

    @Override
    protected String insert() {
        return "/core/fronFuncPro/add";
    }

    @Override
    protected String update() {
        return "/core/fronFuncPro/modify";
    }

    @Override
    protected String delete() {
        return "/core/fronFuncPro/delete";
    }

    @Override
    protected String key1() {
        return "displayWidget";
    }

    @Override
    protected String key2() {
        return "lengthInterval";
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

}
